package Optimize;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import Frontend.IRBuilder;
import IR.item.IRitem;
import IR.item.IRvar;
import IR.node.IRRoot;
import IR.node.IRblock;
import IR.node.def.IRFuncDef;
import IR.node.ins.IRIns;
import IR.node.ins.allocaIns;
import IR.node.ins.branchIns;
import IR.node.ins.jumpIns;
import IR.node.ins.loadIns;
import IR.node.ins.storeIns;

public class Mem2Reg {
    public IRFuncDef curFunc;

    private Set<IRvar> allocVar;

    // block index
    private List<IRblock> blockList;

    // Dominator Tree
    private Map<IRblock, BitSet> domSets;
    private Map<IRblock, Set<IRblock>> domFrontiers;
    private Map<IRblock, Set<IRblock>> domChildren;
    private Map<IRblock, IRblock> domParent; // IDom

    // Phi
    private Map<IRvar, Set<IRblock>> phiPosition;

    private Map<IRitem, IRitem> replaceMap;

    public Mem2Reg() {
        curFunc = null;
    }

    void addAlloc(IRvar ptr) {
        allocVar.add(ptr);
    }

    boolean isAlloc(IRvar ptr) {
        return allocVar.contains(ptr);
    }

    // DFS to get reverse post order
    private List<IRblock> getReversePostOrder(LinkedHashMap<String, IRblock> blocks) {
        List<IRblock> reversePostOrder = new ArrayList<>();
        Set<IRblock> visited = new HashSet<>();
        Deque<IRblock> stack = new ArrayDeque<>();

        for (IRblock block : blocks.values()) {
            if (!visited.contains(block)) {
                dfs(block, visited, stack);
            }
        }

        while (!stack.isEmpty()) {
            reversePostOrder.add(stack.pop());
        }

        return reversePostOrder;
    }

    private void dfs(IRblock block, Set<IRblock> visited, Deque<IRblock> stack) {
        visited.add(block);
        for (IRblock next : block.getNextBlocks()) {
            if (!visited.contains(next)) {
                dfs(next, visited, stack);
            }
        }
        stack.push(block);
    }

    private void initializeDomSets(LinkedHashMap<String, IRblock> blocks) {
        domSets = new HashMap<>();
        domFrontiers = new HashMap<>();

        int tot = blocks.size();
        for (var curblock : blocks.values()) {
            BitSet domSet = new BitSet(tot);
            if (curblock == curFunc.entryBlock) {
                domSet.set(0); // the entry block only dominates itself
            } else {
                domSet.set(0, tot); // set all bits to 1
            }
            domSets.put(curblock, domSet);
            domFrontiers.put(curblock, new HashSet<>()); // initialize the dominance frontier
            // calculate the predecessors
            if (curblock.endIns instanceof branchIns) {
                IRblock trueBlock = blocks.get(((branchIns) curblock.endIns).trueLabel);
                IRblock falseBlock = blocks.get(((branchIns) curblock.endIns).falseLabel);
                curblock.addNextBlock(trueBlock);
                curblock.addNextBlock(falseBlock);
                trueBlock.addPrevBlock(curblock);
                falseBlock.addPrevBlock(curblock);
            } else if (curblock.endIns instanceof jumpIns) {
                IRblock nextBlock = blocks.get(((jumpIns) curblock.endIns).label);
                curblock.addNextBlock(nextBlock);
                nextBlock.addPrevBlock(curblock);
            }
        }
    }

    public void computeDominance(LinkedHashMap<String, IRblock> blocks) {
        initializeDomSets(blocks);
        var ord = getReversePostOrder(blocks);
        boolean changed = true;
        int tot = blocks.size();
        while (changed) {
            changed = false;
            for (var block : ord) {
                BitSet newDomSet = new BitSet(tot);
                newDomSet.set(0, tot);
                for (IRblock pred : block.getPrevBlocks()) {
                    newDomSet.and(domSets.get(pred));
                }
                newDomSet.set(block.index);
                if (!newDomSet.equals(domSets.get(block))) {
                    domSets.put(block, newDomSet);
                    changed = true;
                }
            }
        }
    }

    private void computeDominanceFrontier(LinkedHashMap<String, IRblock> blocks) {
        int tot = blocks.size();
        for (IRblock block : blocks.values()) {
            BitSet domf = new BitSet(tot);

            for (var perd : block.getPrevBlocks()) {
                BitSet mdom = (BitSet) domSets.get(perd).clone();
                BitSet ndom = (BitSet) domSets.get(block).clone();

                // ∪𝑚∈preds(𝑛) (Dom(𝑚) − (Dom(𝑛) − {𝑛}))
                ndom.clear(block.index);
                ndom.flip(0, tot);
                mdom.and(ndom);
                domf.or(mdom);
            }

            for (int i = domf.nextSetBit(0); i >= 0; i = domf.nextSetBit(i + 1)) {
                domFrontiers.get(blockList.get(i)).add(block);
            }
        }
    }

    private void buildDomTree() {
        domChildren = new HashMap<>();
        domParent = new HashMap<>();

        for (IRblock block : blockList)
            domChildren.put(block, new HashSet<>());

        for (IRblock block : blockList) {
            IRblock idom = null;
            BitSet domSet = domSets.get(block);
            if (domSet.cardinality() == 0)
                continue;
            for (int i = domSet.nextSetBit(0); i >= 0; i = domSet.nextSetBit(i + 1)) {
                if (domSets.get(blockList.get(i)).cardinality() == domSet.cardinality() - 1) {
                    idom = blockList.get(i);
                }
            }
            domParent.put(block, idom);
            domChildren.get(idom).add(block);
        }
    }

    private void phiInsertPositions(IRFuncDef funcDef) {
        phiPosition = new HashMap<>();
        Map<IRvar, Set<IRblock>> block_contain_store = new HashMap<>();
        var allocVar = funcDef.getAllocas();

        for (IRvar var : allocVar) {
            phiPosition.put(var, new HashSet<>());
            block_contain_store.put(var, new HashSet<>());
            addAlloc(var);
        }

        for (IRblock block : blockList) {
            for (IRIns ins : block.insList) {
                if (ins instanceof storeIns) {
                    IRvar ptr = ((storeIns) ins).pointer;
                    if (block_contain_store.containsKey(ptr)) {
                        block_contain_store.get(ptr).add(block);
                    }
                }
            }
        }

        for (IRvar var : allocVar) {
            Set<IRblock> visited = block_contain_store.get(var);
            Queue<IRblock> extandlist = new ArrayDeque<>(visited);
            while (!extandlist.isEmpty()) {
                var cur = extandlist.poll();
                for (var frontier : domFrontiers.get(cur)) {
                    phiPosition.get(var).add(frontier);
                    if (!visited.contains(frontier)) {
                        visited.add(frontier);
                        extandlist.add(frontier);
                    }
                }
            }
        }
    }

    private void insertPhi() {

    }

    Pair<IRblock, IRitem> findVarValue(IRvar var, Stack<Map<IRvar, Pair<IRblock, IRitem>>> curVarValue) {
        for (int i = curVarValue.size() - 1; i >= 0; i--) {
            if (curVarValue.get(i).containsKey(var)) {
                return curVarValue.get(i).get(var);
            }
        }
        return null;
    }



    void replaceVar(IRblock block, Set<IRblock> visited, Stack<Map<IRvar, Pair<IRblock, IRitem>>> curVarValue) {
        for (var phi : block.phiList) {
            var cur = findVarValue(phi.var, curVarValue);
            if (cur == null) {
                throw new RuntimeException("replaceVar: phi.var not found");
            }
            phi.addValue(cur.second, cur.first.Label);
            curVarValue.peek().put(phi.var, new Pair<>(block, phi.result));
        }

        if (visited.contains(block)) {
            return;
        }
        visited.add(block); 
        for (var ins : block.insList) {
            if (ins instanceof loadIns) {
                IRvar ptr = ((loadIns) ins).pointer;
                if (isAlloc(ptr)) {
                    var cur = findVarValue(ptr, curVarValue);
                    if (cur == null) {
                        throw new RuntimeException("replaceVar: load.ptr not found");
                    } 
                    // remove ins
                    ins.removed = true;     
                    replaceMap.put(((loadIns)ins).result, cur.second);
                }
            } else if (ins instanceof storeIns) {
                IRvar ptr = ((storeIns) ins).pointer;
                if (isAlloc(ptr)) {
                    curVarValue.peek().put(ptr, new Pair<IRblock, IRitem>(block, ((storeIns) ins).value));
                    ins.removed = true;
                }
            }
        }

        if (block.endIns instanceof jumpIns) {
            IRblock nextBlock = curFunc.blocks.get(((jumpIns) block.endIns).label);
            replaceVar(nextBlock, visited, curVarValue);
        } else if (block.endIns instanceof branchIns) {
            IRblock trueBlock = curFunc.blocks.get(((branchIns) block.endIns).trueLabel);
            IRblock falseBlock = curFunc.blocks.get(((branchIns) block.endIns).falseLabel);

            curVarValue.push(new HashMap<>());
            replaceVar(trueBlock, visited, curVarValue);
            curVarValue.pop();

            curVarValue.push(new HashMap<>());
            replaceVar(falseBlock, visited, curVarValue);
            curVarValue.pop();
        }
    }

    void tidyUp() {
        for (var block : blockList) {
            block.insList.removeIf(ins -> ins.removed);
            block.insList.forEach(ins -> ins.replaceUse(replaceMap));
        }
    }

    void visit(IRFuncDef funcDef) {
        curFunc = funcDef;

        // init block index
        blockList = new ArrayList<>(funcDef.blocks.values());
        for (int i = 0; i < blockList.size(); i++) {
            blockList.get(i).index = i;
        }

        computeDominance(funcDef.blocks);
        computeDominanceFrontier(funcDef.blocks);
        buildDomTree();

        phiInsertPositions(funcDef);
        insertPhi();

        replaceVar(funcDef.entryBlock, new HashSet<>(), new Stack<>());
        tidyUp();

    }

    void visit(IRRoot root) {
        for (var funcDef : root.funcs) {
            visit(funcDef);
        }
    }

}

class Pair<T1, T2> {
    public final T1 first;
    public final T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pair) {
            Pair<?, ?> other = (Pair<?, ?>) obj;
            return first.equals(other.first) && second.equals(other.second);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return first.hashCode() ^ second.hashCode();
    }

    @Override
    public String toString() {
        return "(" + first.toString() + ", " + second.toString() + ")";
    }

}