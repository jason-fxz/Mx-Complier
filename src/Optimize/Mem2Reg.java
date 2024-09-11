package Optimize;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import IR.item.IRvar;
import IR.node.IRRoot;
import IR.node.IRblock;
import IR.node.def.IRFuncDef;
import IR.node.ins.allocaIns;
import IR.node.ins.branchIns;
import IR.node.ins.jumpIns;
import IR.node.ins.loadIns;
import IR.node.ins.storeIns;
import Util.VMemInfo;

public class Mem2Reg {
    public IRFuncDef curFunc;
    public HashMap<IRvar, VMemInfo> varInfo; // ptr -> info
    public HashMap<IRvar, IRvar> curBlockvar; // ptr -> var

    private Map<IRblock, Integer> blockIndex;
    private List<IRblock> blockList;
    private Map<IRblock, BitSet> domSets;
    private Map<IRblock, Set<IRblock>> domFrontier;

    public Mem2Reg() {
        curFunc = null;
        varInfo = new HashMap<>();
        curBlockvar = new HashMap<>();

    }

    void addAlloc(IRvar ptr) {
        if (varInfo.containsKey(ptr))
            throw new RuntimeException("AddVar: ptr already exists");
        varInfo.put(ptr, new VMemInfo());
    }

    boolean isAlloc(IRvar ptr) {
        return varInfo.containsKey(ptr);
    }

    IRvar getLastDef(IRvar ptr) {
        return curBlockvar.get(ptr);
    }

    void visit(IRblock block) {
        // TODO
        curBlockvar.clear();
        for (var inst : block.insList) {
            if (inst instanceof allocaIns) {
                addAlloc(((allocaIns) inst).result);
            } else if (inst instanceof loadIns) {
                if (isAlloc(((loadIns) inst).pointer)) {
                    IRvar lastDef = getLastDef(((loadIns) inst).pointer);
                    if (lastDef != null) {
                        ((loadIns) inst).pointer = lastDef;
                    } else {

                    }

                }
            } else if (inst instanceof storeIns) {

            }
        }
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
        blockIndex = new HashMap<>();
        blockList = new ArrayList<>(blocks.values());
        domSets = new HashMap<>();
        domFrontier = new HashMap<>();

        int tot = blocks.size(), i = 0;
        for (var curblock : blocks.values()) {
            BitSet domSet = new BitSet(tot);
            if (curblock == curFunc.entryBlock) {
                domSet.set(0); // the entry block only dominates itself
            } else {
                domSet.set(0, tot); // set all bits to 1
            }
            blockIndex.put(curblock, i++); // assign index to each block
            domSets.put(curblock, domSet);
            domFrontier.put(curblock, new HashSet<>()); // initialize the dominance frontier
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
                newDomSet.set(blockIndex.get(block));
                if (!newDomSet.equals(domSets.get(block))) {
                    domSets.put(block, newDomSet);
                    changed = true;
                }
            }
        }
        computeDominanceFrontier(blocks);
    }

    private void computeDominanceFrontier(LinkedHashMap<String, IRblock> blocks) {
        int tot = blocks.size();
        for (IRblock block : blocks.values()) {
            BitSet domf = new BitSet(tot);

            for (var perd : block.getPrevBlocks()) {
                BitSet mdom = (BitSet)domSets.get(perd).clone();
                BitSet ndom = (BitSet) domSets.get(block).clone();

                // ∪𝑚∈preds(𝑛) (Dom(𝑚) − (Dom(𝑛) − {𝑛}))
                ndom.clear(blockIndex.get(block));
                ndom.flip(0, tot);
                mdom.and(ndom);
                domf.or(mdom);
            }

            for (int i = domf.nextSetBit(0); i >= 0; i = domf.nextSetBit(i + 1)) {
                domFrontier.get(blockList.get(i)).add(block);
            }

        }
    }



    void visit(IRFuncDef funcDef) {
        curFunc = funcDef;
        varInfo.clear();

        computeDominance(funcDef.blockList);

        


        
        // curFunc.blockList.forEach(block -> {
        // visit(block);
        // });
    }

    void visit(IRRoot root) {
        for (var funcDef : root.funcs) {
            visit(funcDef);
        }
    }

}