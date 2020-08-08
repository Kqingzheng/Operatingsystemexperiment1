package com.example.operatingsystemexperiment1;

import android.util.Log;

public class PCB {
    String name;
    int start;
    int length;
    int end;
    PCB next;
    boolean aBoolean=false;
    public PCB(String name, int start, int length, PCB next) {
        this.name = name;
        this.start = start;
        this.length = length;
        this.next = next;
        this.end = start+length;
    }

    public PCB(String name, int start, int length) {
        this.name = name;
        this.start = start;
        this.length = length;
    }

    public PCB(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public boolean hasNext(){
        return this.next == null ? false : true;
    }

    public void add(PCB pcb){
        PCB t = this;
        while (t.hasNext()) {
            t = t.next;
        }
        t.next = pcb;
        aBoolean=true;
    }
    public PCB deQuene(){
        PCB pcb=this.next;
        if(pcb==null){
            System.out.println("队列为空");
            aBoolean=false;
            return null;
        }else{
            this.next=pcb.next;
            pcb.next=null;
            return pcb;
        }
    }
    @Override
    public String toString() {
        return "PCB{" +
                "name='" + name + '\'' +
                ", start=" + start +
                ", length=" + length +
                "}\n";
    }
    public String show(){
        String string=" ";
        PCB pcb=this;
        while(pcb.hasNext()){
            pcb=pcb.next;
            Log.v("11111",pcb.toString());
            string+=pcb.toString();
        }
        if(string==null) return "\n";
        else return string.substring(1);
    }
}

