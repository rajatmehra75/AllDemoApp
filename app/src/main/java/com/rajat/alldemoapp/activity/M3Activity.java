package com.rajat.alldemoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.rajat.alldemoapp.R;
import com.rajat.alldemoapp.utils.DataHandler;

public class M3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m3);
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(M3Activity.this,M1Activity.class);
                startActivity(intent);
                DataHandler.getInstance().getStack().push(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (DataHandler.getInstance().getStack().size() > 0){
            Intent intent = DataHandler.getInstance().getStack().pop();
//            Intent intent = new Intent(M1Activity.this,pop.getClass());
            startActivity(intent);
        }
    }
}
