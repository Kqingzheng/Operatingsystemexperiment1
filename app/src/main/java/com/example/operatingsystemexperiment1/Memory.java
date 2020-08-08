package com.example.operatingsystemexperiment1;

public class Memory {
    int begin;
    int end;
    int size;
    Memory before;
    Memory next;
    final int fragment=4;
    boolean flag=false;
    public Memory(int begin, int size) {
        this.begin = begin;
        this.size = size;
        this.end=begin+size-1;
    }

    public Memory() {
    }

    public Memory(int size) {
        this.size = size;
    }
    public Boolean hasnext() {
        return this.next == null ? false : true;
    }
    public void Allocated_memory(PCB pcb){
        Memory memory=this.next;
        this.Bubble_sort_size();
        while(memory!=null&&memory.size<pcb.length)
            memory=memory.next;
        if(memory!=null) {
            flag=true;
            if (memory.size - pcb.length <= fragment) {
                pcb.start = memory.begin;
                pcb.end = memory.end;
                pcb.length = memory.size;
                if (memory.before == this) {
                    if (memory.next == null) {
                        System.out.println("内存已满无法分配");
                        this.next=null;
                    }
                    else {
                        memory = memory.next;
                    }
                } else if (memory.next == null) {
                    memory.before.next = null;
                } else {
                    memory.before.next = memory.next;
                }
            } else {
                pcb.start = memory.begin;
                pcb.end = memory.begin + pcb.length;
                memory.begin = memory.begin + pcb.length;
                memory.size = memory.size - pcb.length;
            }
        }else flag=false;


    }
    public void Recovery_memeory(PCB pcb){
        Memory memory=new Memory(pcb.start,pcb.length);
        Memory me=this.next;
        this.Bubble_sort_address();
        if(me==null) this.next=memory;//无空闲内存
        else {
            while (me.hasnext() && me.begin < memory.begin)
                me = me.next;
            if (me == this.next && me.next == null) { //空闲分区只有一个
                if (memory.begin + memory.size == me.begin) {
                    me.size += memory.size;
                    me.begin = memory.begin;
                } else if (me.begin + me.size == memory.begin) {
                    me.size += memory.size;
                    me.end = memory.end;
                } else {
                    if (me.begin < memory.begin) {
                        me.next = memory;
                        memory.before = me;
                    } else {
                        this.next = memory;
                        memory.before = this;
                        memory.next = me;
                        me.before = memory;
                    }
                }
            } else { //空闲分区有多个
                if (me == this.next) {  //空闲分区起始地址最低
                    if (memory.begin + memory.size == me.begin) {
                        me.size += memory.size;
                        me.begin = memory.begin;
                    } else {
                        this.next = memory;
                        memory.before = this;
                        memory.next = me;
                        me.before = memory;
                    }
                } else if (me.next == null && me.begin < memory.begin) {  //新空闲分区起始地址最高
                    if (me.begin + me.size == memory.begin) {
                        me.size += memory.size;
                        me.end = memory.end;
                    } else {
                        me.next = memory;
                        memory.before = me;
                        memory.next = null;
                    }
                } else {
                    Memory tem = me.before;
                    if (tem.begin + tem.size == memory.begin) {
                        tem.size += memory.size;
                        tem.end = memory.end;
                        if (tem.begin + tem.size == me.begin) {
                            me.size += tem.size;
                            me.begin = tem.begin;
                            tem.before.next = me;
                            me.before = tem.before;
                        }
                    } else if (memory.begin + memory.size == me.begin) {
                        me.size += memory.size;
                        me.begin = memory.begin;
                    } else {
                        memory.before = tem;
                        tem.next = memory;
                        memory.next = me;
                        me.before = memory;
                    }
                }
            }
        }
    }
    //对空闲分区进行从小到大冒泡排序
    public void Bubble_sort_size(){
        if(this!=null&&this.next!=null){
            Memory memory_head = null, memory_tail = null;
            memory_head = this.next;
            while (memory_head.next != memory_tail) {
                while (memory_head.next != memory_tail) {
                    if (memory_head.size > memory_head.next.size) {
                        int tmp_size = memory_head.size;
                        int tmp_begin = memory_head.begin;
                        memory_head.size = memory_head.next.size;
                        memory_head.begin = memory_head.next.begin;
                        memory_head.end=memory_head.begin+memory_head.size-1;
                        memory_head.next.size = tmp_size;
                        memory_head.next.begin = tmp_begin;
                        memory_head.next.end=memory_head.next.size+memory_head.next.begin-1;
                    }
                    memory_head = memory_head.next;
                }
                memory_tail = memory_head;
                memory_head = this.next;
            }
            memory_head = this.next;
            memory_head.before=this;
            while(memory_head.next!=null){
                memory_head.next.before=memory_head;
                memory_head=memory_head.next;
            }

        }
    }
    //对内存的空闲分区起始地址进行排序
    public void Bubble_sort_address() {
        if (this == null || this.next == null) {
        } else {
            Memory cur = null, tail = null;
            cur = this.next;
            while (cur.next != tail) {
                while (cur.next != tail) {
                    if (cur.begin > cur.next.begin) {
                        int tmp_size = cur.size;
                        int tmp_begin = cur.begin;
                        int tmp_end=cur.end;
                        cur.size = cur.next.size;
                        cur.begin = cur.next.begin;
                        cur.end=cur.next.end;
                        cur.next.size = tmp_size;
                        cur.next.begin = tmp_begin;
                        cur.next.end=tmp_end;
                    }
                    cur = cur.next;
                }
                tail = cur;
                cur = this.next;
            }
            Memory memory_head = this.next;
            memory_head.before = this;
            while (memory_head.next != null) {
                memory_head.next.before = memory_head;
                memory_head = memory_head.next;
            }

        }
    }
    @Override
    public String toString() {
        return "Memory{" +
                "begin=" + begin +
                ", end=" + end +
                ", size=" + size +
                "}\n";
    }
    public String show(){
        String string=" ";
        Memory memory=this.next;
        while(memory!=null){
           string+=memory.toString();
            memory=memory.next;
        }
        string+="************************************************\n";
        return string.substring(1);
        //System.out.println("*************************************************************");
    }
}

