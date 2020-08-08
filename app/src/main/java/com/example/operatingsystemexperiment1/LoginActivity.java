package com.example.operatingsystemexperiment1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        final EditText ed1=(EditText)findViewById(R.id.adress);
        final EditText ed2=(EditText)findViewById(R.id.startsize);
        Button button=(Button)findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1=ed1.getText().toString();
                String s2=ed2.getText().toString();
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("s1",s1);
                intent.putExtra("s2",s2);
                startActivity(intent);
            }
        });
    }
}
