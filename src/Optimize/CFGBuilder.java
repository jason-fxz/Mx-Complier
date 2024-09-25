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

import IR.node.IRRoot;
import IR.node.IRblock;
import IR.node.def.IRFuncDef;
import IR.node.ins.branchIns;
import IR.node.ins.jumpIns;

public class CFGBuilder {
    private IRFuncDef curFunc;

    public List<IRblock> blockList;

    // Dominator Tree
    public Map<IRblock, BitSet> domSets;
    public Map<IRblock, Set<IRblock>> domFrontiers;
    public Map<IRblock, Set<IRblock>> domChildren;
    public Map<IRblock, IRblock> domParent; // IDom

    public CFGBuilder() { }

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

    private void computeDominance(LinkedHashMap<String, IRblock> blocks) {
        var ord = getReversePostOrder(blocks);
        boolean changed = true;
        int tot = blocks.size();
        while (changed) {
            changed = false;
            for (var block : ord) {
                BitSet newDomSet = new BitSet(tot);
                if (!block.Label.equals("entry")) {
                    newDomSet.set(0, tot);
                }
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
            if (block.Label.equals("entry"))
                continue;
            IRblock idom = null;
            BitSet domSet = domSets.get(block);
            if (domSet.cardinality() == 0)
                continue;
            for (int i = domSet.nextSetBit(0); i >= 0; i = domSet.nextSetBit(i + 1)) {
                if (domSets.get(blockList.get(i)).cardinality() == domSet.cardinality() - 1) {
                    idom = blockList.get(i);
                }
            }
            if (idom == null) {
                throw new RuntimeException("idom == null");
            }
            domParent.put(block, idom);
            domChildren.get(idom).add(block);
        }
    }

    public void build(IRFuncDef func) {
        curFunc = func;

        blockList = new ArrayList<>(func.blocks.values());
        for (int i = 0; i < blockList.size(); i++) {
            blockList.get(i).index = i;
        }
        initializeDomSets(func.blocks);
        computeDominance(func.blocks);
        computeDominanceFrontier(func.blocks);
        buildDomTree();
    }



}
