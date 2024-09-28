# Optimize

## Mem2reg

即在 IR 上删除 alloca 指令，我们开在栈上的变量都可以挪到虚拟寄存器里。这会导致插入 phi 指令。这一步需要对 IR 基本块建图，称为控制流图 CFG （Control Flow Graph），在图上跑出**支配边界**，再进行 phi 指令放置。具体可以参考 [mem2reg made simple](https://longfangsong.github.io/en/mem2reg-made-simple/)

## SSA-RA 

基于静态单赋值形式的寄存器分配。详细见 [SSA-RA](https://pan.baidu.com/s/1l_7Wlei52Dlg4Im_iukjiQ?pwd=skyb)，摘自 [寄存器分配引论 华保健](https://books.google.com.hk/books/about/%E5%AF%84%E5%AD%98%E5%99%A8%E5%88%86%E9%85%8D%E5%BC%95%E8%AE%BA.html?id=bPdOzwEACAAJ&redir_esc=y)

这里我使用的溢出策略是：cost=count_use*10^(loopDepth)


## DCE (Dead Code Elimination)

对无副作用的代码进行删除。对于大部分语句，如果其 def 没有被其他语句 use，那么它是可以被删除的。又因为静态单赋值，每个变量只有一次 def。那么这个算法就呼之欲出了，类似于 topu 排序，我们先将所有 0 use 的 def 语句塞进队列。出队指令被删除，并且将这条语句 use 到的变量的 use 数减一，如果减到 0 了，那么就将这个变量的 def 语句塞进队列。直到队列为空。

以下指令是有副作用的：

- store 指令, 会改变内存, 而我们不会对内存进行分析，所以 store 指令不应被删除。
- branch/jump 指令
- return 指令
- call 指令, 因为不知道调用的函数是否有副作用，所以 call 指令不应被删除，**即使 call 返回的数值没有被 use**。

## 循环分析

### 在 IR 上找到循环

首先要对 CFG 建出支配树。

**回边**：是指一条 a->b 的边，其中 b 支配了 a。（即在支配树上 b 支配 a）

> 如果 CFG 是*可归约*的，回边又等价于 CFG 的 DFS 树上的后退边。在实际情况中，只用 if-then-else, while-do, continue, break 这些语句，CFG 是可归约的。

- 循环入口是唯一存在的，称为循环头，这个入口节点支配了循环中的所有节点，否则它不是循环的唯一入口。
- 必然存在一条进入循环头的回边。
- 给定一个回边 n->d, 那这条边所对应的自然循环就是 d 加上那些不用经过 n 就能到达 d 的节点。

因此，我们找循环的方法如下：
- 找到所有回边 a->b, (b 支配 a), 记录所有 b 为 loop header。同时 a 记录到 loop body 中。
- 从 a 出发反向便利流图，直到遇到 b，这些节点就是 loop body。