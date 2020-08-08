package com.example.operatingsystemexperiment1;

import android.widget.TextView;
import android.widget.Toast;

import java.util.Scanner;

public class OSone {
    final PCB ready_head = new PCB("head", 0);
    final PCB block_head = new PCB("head", 0);
    final PCB run_head = new PCB("head", 0);
    final Memory memory_head = new Memory();
    int begin;
    int size;

    public OSone() {
    }

    public OSone(int begin, int size) {
        this.begin = begin;
        this.size = size;
        Memory memory=new Memory(this.begin,this.size);
        memory_head.next=memory;
        memory.before=memory_head;
    }
    public void Creat(PCB pcb){
        memory_head.Allocated_memory(pcb);
        if(memory_head.flag) {
            ready_head.add(pcb);
            Automatic_scheduling();
        }
    }
    public void Automatic_scheduling(){
        if(!run_head.hasNext())
            run_head.add(ready_head.deQuene());
    }
    public void End(){
        PCB pcb=run_head.deQuene();
        if(pcb==null) run_head.aBoolean=false;
        else {
            memory_head.Recovery_memeory(pcb);
            Automatic_scheduling();
        }
    }
    public void Block(){
        PCB pcb=run_head.deQuene();
        if(pcb==null) run_head.aBoolean=false;
        else{
            block_head.add(pcb);
            Automatic_scheduling();
        }

    }
    public void Weak(){
        PCB pcb=block_head.deQuene();
        if(pcb==null) block_head.aBoolean=false;
        else {
            ready_head.add(pcb);
            Automatic_scheduling();
        }
    }
    public void Time_rotation(){
        PCB pcb=run_head.deQuene();
        ready_head.add(pcb);
        Automatic_scheduling();
    }
    public void showAll(TextView textView){

        textView.append("就绪态:\n");
        textView.append(ready_head.show());
        textView.append("********************************************\n");
        textView.append("执行态：\n");
        textView.append(run_head.show());
        textView.append("********************************************\n");
        textView.append("阻塞态：\n");
        textView.append(block_head.show());
        textView.append("********************************************\n");
    }
    public void showMemory(TextView textView){
        textView.append(memory_head.show());
    }
    public void Help(){
        System.out.println("c:创建一个进程\nt:时间片到\ne:结束一个进程\nb:阻塞一个进程\n" +
                "w:唤醒一个进程\ns:查看状态\nm:查看内存\nh:帮助\nz:结束");
    }
//    public void Start(){
//        Scanner sc=new Scanner(System.in);
//        System.out.println("请输入内存的起始地址和大小：");
//        this.begin=sc.nextInt();
//        this.size=sc.nextInt();
//        Memory memory=new Memory(this.begin,this.size);
//        memory_head.next=memory;
//        memory.before=memory_head;
//        while (true){
//            System.out.println("请输入命令c/t/e/b/w/s/m/h/z:");
//            char choose = sc.next().charAt(0);
//            switch (choose){
//                case 'c':
//                    System.out.println("请输入要装入内存的PCB名字和大小");
//                    String s=sc.next();
//                    int i=sc.nextInt();
//                    PCB pcb=new PCB(s,i);
//                    Creat(pcb);
//                    break;
//                case 't':
//                    Time_rotation();
//                    break;
//                case 'e':
//                    End();
//                    break;
//                case 'b':
//                    Block();
//                    break;
//                case 'w':
//                    Weak();
//                    break;
//                case 's':
//                    showAll();
//                    break;
//                case 'm':
//                    showMemory();
//                    break;
//                case 'h':
//                    Help();
//                    break;
//                case 'z':
//                    System.exit(0);
//
//            }
//        }
//
//    }

//    public static void main(String[] args) {
//        OSone oSone=new OSone();
//        oSone.Start();
//    }
}
