package Optimize;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import IR.node.IRblock;
import IR.node.def.IRFuncDef;
import IR.node.ins.branchIns;
import IR.node.ins.icmpbranchIns;
import IR.node.ins.jumpIns;
import IR.node.ins.returnIns;

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
    private List<IRblock> getReversePostOrder() {
        List<IRblock> reversePostOrder = new ArrayList<>();
        Set<IRblock> visited = new HashSet<>();
        Deque<IRblock> stack = new ArrayDeque<>();

        for (var block : blockList) {
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

    private void initializeDomSets() {
        domSets = new HashMap<>();
        int tot = blockList.size();
        for (var curblock : blockList) {
            BitSet domSet = new BitSet(tot);
            if (curblock == curFunc.entryBlock) {
                domSet.set(0); // the entry block only dominates itself
            } else {
                domSet.set(0, tot); // set all bits to 1
            }
            domSets.put(curblock, domSet);
        }
    }

    private void computeDominance() {
        var ord = getReversePostOrder();
        boolean changed = true;
        int tot = blockList.size();
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

    private void computeDominanceFrontier() {
        domFrontiers = new HashMap<>();

        for (var curblock : blockList)
            domFrontiers.put(curblock, new HashSet<>()); // initialize the dominance frontier
        
        int tot = blockList.size();
        for (IRblock block : blockList) {
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

    public CFGBuilder buildDomTree() {
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
        return this;
    }

    public boolean isAncestor(IRblock ance, IRblock child) {
        return domSets.get(child).get(ance.index);
    }

    public CFGBuilder buildCFG(IRFuncDef func) {
        curFunc = func;
        blockList = func.blockList;
        for (int i = 0; i < blockList.size(); i++) {
            blockList.get(i).index = i;
        }
        var blocks = func.blocks;
        for (var block : blockList) {
            block.initPrevNextBlocks();
        }
        for (var curblock : blockList) {
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
            } else if (curblock.endIns instanceof icmpbranchIns) {
                IRblock trueBlock = blocks.get(((icmpbranchIns) curblock.endIns).trueLabel);
                IRblock falseBlock = blocks.get(((icmpbranchIns) curblock.endIns).falseLabel);
                curblock.addNextBlock(trueBlock);
                curblock.addNextBlock(falseBlock);
                trueBlock.addPrevBlock(curblock);
                falseBlock.addPrevBlock(curblock);
            } else if (curblock.endIns instanceof returnIns) {
                // do nothing
            } else {
                throw new RuntimeException("buildCFG: unexpected endIns");
            }
        }
        return this;
    }

    public CFGBuilder calcDom() {
        if (curFunc == null) {
            throw new RuntimeException("calcDom: curFunc == null");
        }
        initializeDomSets();
        computeDominance();
        return this;
    }
    
    
    public CFGBuilder calcDomF() {
        if (curFunc == null) {
            throw new RuntimeException("calcDomF: curFunc == null");
        }
        computeDominanceFrontier();
        return this;
    }



    // public void build(IRFuncDef func) {
    //     curFunc = func;

    //     blockList = new ArrayList<>(func.blocks.values());
    //     for (int i = 0; i < blockList.size(); i++) {
    //         blockList.get(i).index = i;
    //     }
    //     initializeDomSets(func.blocks);
    //     computeDominance(func.blocks);
    //     computeDominanceFrontier(func.blocks);
    //     buildDomTree();
    // }



}
