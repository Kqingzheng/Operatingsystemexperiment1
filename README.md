# Operatingsystemexperiment1
操作系统实验一
一、进程控制


1.1目的


模拟操作系统内核对进程的控制和管理：包括进程的创建和撤销、进程状态的切换和简单的内存空间管理。掌握进程三种状态转换的原理以及内存的分配算法。


1.2内容


1、定义管理每个进程的数据结构 PCB：包含进程名称、队列指针、分配的物理内存区域（基址和长度）。每创建一个进程时，需要为其创建 PCB 并分配空闲内存空间，对 PCB 进行初始化，并加入就绪队列。


2、模拟触发进程状态转换的事件：采用键盘控制方法来模拟触发进程状态切换的事件（例如输入 1 代表创建新进程、2 执行进程时间片到、3 阻塞执行进 程、4 唤醒第一个阻塞进程、5 终止执行进程），实现对应的控制程序。


3、根据当前发生的事件对进程的状态进行切换，并显示出当前系统中的执行队列、就绪队列和阻塞队列。


4、完成可变分区的分配与回收，创建进程的同时申请一块连续的内存空间，在 PCB 中设置好基址和长度，结束进程时回收分配的内存空间。分配可采用最佳适应算法，碎片大小为 4Kb,最后回收所有进程的空间，对空间分区的合并。可以查看进程所占的空间和系统空闲空间。


1.3数据结构


定义PCB类


public class PCB {
    String name;
    int start;
    int length;
    int end;
    PCB next;
    boolean aBoolean = false;
}


定义内存Memory类


public class Memory {
    int begin;
    int end;
    int size;
    Memory before;
    Memory next;
    final int fragment = 2;
}


定义就绪、执行、阻塞队列和系统内存大小


final PCB ready_head = new PCB("head", 0);
final PCB block_head = new PCB("head", 0);
final PCB run_head = new PCB("head", 0);
final Memory memory_head = new Memory();

1.4算法设计及流程图

创建进程：将新创建的进程（不与已存在的进程重名）申请内存空间成功后加入就绪队列，进行自动调度。

时间片轮转：执行队列出队的进程入队就绪队列，进行自动调度。

自动调度：执行队列是否为空，为空则就绪队列出队的进程入队执行队列。

阻塞：执行队列出队的进程入队阻塞队列，进行自动调度。

唤醒：阻塞队列出队的进程入队就绪队列，进行自动调度。

终止进程：执行队列出队，出队的进程将内存空间释放。

