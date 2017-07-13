package com.example.gandh.hw03;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gandh on 2/4/2017.
 */

public class Secon extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        setTitle("Words");
        LinearLayout l2 = (LinearLayout) findViewById(R.id.l2);

      ArrayList<Integer> count_finder=  getIntent().getExtras().getIntegerArrayList("key");
        ArrayList<String> word_finder=  getIntent().getExtras().getStringArrayList("keyw");


        for(int i=0; i< count_finder.size();i++)
        {
            LinearLayout local2 = new LinearLayout(this);
            Log.d( "demo_se",count_finder.get(0)+"");
            TextView t1 = new TextView(this);
            TextView t2 = new TextView(this);
            t1.setPadding(10,0,0,0);
            t1.setText(word_finder.get(i)+":");
            t2.setText(" "+count_finder.get(i));
            local2.addView(t1);
            local2.addView(t2);
            l2.addView(local2);
        }

        Button b = (Button) findViewById(R.id.finish);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }
}
