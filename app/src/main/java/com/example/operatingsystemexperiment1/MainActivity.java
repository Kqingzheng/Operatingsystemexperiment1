package com.example.operatingsystemexperiment1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {
    OSone oSone;
    String input;
    int insize;
    TextView textone;
    TextView texttwo;
    Queue<String> queue = new LinkedList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textone=(TextView)findViewById(R.id.testone);
        texttwo=(TextView)findViewById(R.id.testtwo);
        textone.setMovementMethod(ScrollingMovementMethod.getInstance());
        texttwo.setMovementMethod(ScrollingMovementMethod.getInstance());
        Button block=(Button)findViewById(R.id.block);
        Button weak=(Button)findViewById(R.id.weak);
        Button time=(Button)findViewById(R.id.time);
        Button end=(Button)findViewById(R.id.end);
        Intent intent=getIntent();
        int s1 = Integer.parseInt(intent.getStringExtra("s1"));
        int s2 = Integer.parseInt(intent.getStringExtra("s2"));
        if(s2>0) {
            Toast.makeText(getApplicationContext(), "创建成功", Toast.LENGTH_SHORT).show();
            oSone = new OSone(s1, s2);
            block.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    oSone.Block();
                    if (oSone.run_head.aBoolean) {
                        oSone.showAll(textone);
                        refreshAlarmView(textone, "\n");
                        oSone.showMemory(texttwo);
                        refreshAlarmView(texttwo, "\n");
                    } else
                        Toast.makeText(getApplicationContext(), "无正在执行的进程", Toast.LENGTH_SHORT).show();
                }
            });
            weak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    oSone.Weak();
                    if (oSone.block_head.aBoolean) {
                        oSone.showAll(textone);
                        refreshAlarmView(textone, "\n");
                        oSone.showMemory(texttwo);
                        refreshAlarmView(texttwo, "\n");
                    } else
                        Toast.makeText(getApplicationContext(), "无正在阻塞的进程", Toast.LENGTH_SHORT).show();
                }
            });
            time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    oSone.Time_rotation();
                    oSone.showAll(textone);
                    refreshAlarmView(textone, "\n");
                    oSone.showMemory(texttwo);
                    refreshAlarmView(texttwo, "\n");
                }
            });
            end.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PCB p=oSone.run_head.next;
                    if(p!=null){
//                        for (String q : queue) {
//                            if (q.equals(p.name)) queue.remove(q);
//                        }
                        Iterator<String> it_b=queue.iterator();
                        while(it_b.hasNext()){
                            String a=it_b.next();
                            if (a.equals(p.name)) {
                                it_b.remove();
                            }
                        }
                    }

                    oSone.End();
                    if (oSone.run_head.aBoolean) {
                        oSone.showAll(textone);
                        refreshAlarmView(textone, "\n");
                        oSone.showMemory(texttwo);
                        refreshAlarmView(texttwo, "\n");
                    } else
                        Toast.makeText(getApplicationContext(), "无正在执行的进程", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "创建失败", Toast.LENGTH_SHORT).show();
            finish();;
        }

    }
    public void alert_edit(View view){
        LayoutInflater inflater=getLayoutInflater();
        final View layout=inflater.inflate(R.layout.mydialog,(ViewGroup)findViewById(R.id.mydialog));
        final EditText name=(EditText)layout.findViewById(R.id.name);
        final EditText size=(EditText)layout.findViewById(R.id.size11);
        new AlertDialog.Builder(this).setTitle("创建一个进程")
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        input = name.getText().toString();
                        insize = Integer.valueOf(size.getText().toString());
                        boolean bol = false;
                        for (String q : queue) {
                            if (q.equals(input)) bol = true;
                        }
                        if (!bol) {
                            queue.offer(input);
                            PCB pcb = new PCB(input, insize);
                            oSone.Creat(pcb);
                            if (oSone.memory_head.flag) {
                                oSone.showAll(textone);
                                refreshAlarmView(textone, "\n");
                                oSone.showMemory(texttwo);
                                refreshAlarmView(texttwo, "\n");
                            } else
                                Toast.makeText(getApplicationContext(), "申请内存过大无法分配", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "该PCB已存在", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消",null).show();

    }

    private void refreshAlarmView(TextView textView,String msg){
        textView.append(msg);
        int offset=textView.getLineCount()*textView.getLineHeight();
        if(offset>(textView.getHeight()-textView.getLineHeight()-20)){
            textView.scrollTo(0,offset-textView.getHeight()+textView.getLineHeight()+20);
        }
    }
}
