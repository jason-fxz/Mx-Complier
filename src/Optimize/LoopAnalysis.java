package Optimize;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import IR.node.IRRoot;
import IR.node.IRblock;
import IR.node.def.IRFuncDef;

public class LoopAnalysis {
    IRRoot irRoot;
    CFGBuilder CFG;
    Map<IRblock, Set<IRblock>> loops;

    public LoopAnalysis(IRRoot irRoot) {
        this.irRoot = irRoot;
        CFG = new CFGBuilder();
    }

    public void run() {
        var timer = Util.ExecutionTimer.timer;
        timer.start("LoopAnalysis");
        for (var func : irRoot.funcs) {
            CFG.buildCFG(func).calcDom();
            findBackEdges(func);
            getLoops(func);
        }
        timer.stop("LoopAnalysis");
    }

    void findBackEdges(IRFuncDef func) {
        loops = new HashMap<>();
        for (var block : func.blocks.values()) {
            for (var nextBlock : block.getNextBlocks()) {
                if (CFG.isAncestor(nextBlock, block)) {
                    if (!loops.containsKey(nextBlock)) {
                        loops.put(nextBlock, new HashSet<>());
                    }
                    loops.get(nextBlock).add(block);
                }
            }
        }
    }

    void getLoops(IRFuncDef func) {
        for (var block : func.blocks.values()) {
            block.loopDepth = 0;
        }

        for (var loop : loops.entrySet()) {
            var header = loop.getKey();
            var tails = loop.getValue();
            var loopBlocks = new HashSet<IRblock>();
            loopBlocks.add(header);

            Queue<IRblock> queue = new LinkedList<>();
            for (var tail : tails) {
                if (tail != header) {
                    queue.add(tail);
                    loopBlocks.add(tail);
                }
            }

            while (!queue.isEmpty()) {
                var block = queue.poll();
                assert CFG.isAncestor(header, block);
                // System.err.println(block.Label);
                for (var prevBlock : block.getPrevBlocks()) {
                    if (!loopBlocks.contains(prevBlock)) {
                        loopBlocks.add(prevBlock);
                        queue.add(prevBlock);
                    }
                }
            }
        
            for (var block : loopBlocks) {
                block.loopDepth++;
            }
        }


    } 

}