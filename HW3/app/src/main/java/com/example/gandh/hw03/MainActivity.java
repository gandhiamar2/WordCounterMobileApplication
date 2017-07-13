package com.example.gandh.hw03;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.ResultReceiver;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static android.R.color.white;
import static com.example.gandh.hw03.R.drawable.minus;

public class MainActivity extends AppCompatActivity {


    LinearLayout l1,local1;
    ProgressDialog pd;
    int i,btno,j=0;
    int p;
    ArrayList<LinearLayout> local = new ArrayList<>();
    ArrayList<EditText> et1 = new ArrayList<>();
    ArrayList<ImageButton> ib1 = new ArrayList<>();
    ArrayList<FloatingActionButton> fb1 = new ArrayList<>();
    ArrayList<FloatingActionButton> fb2 = new ArrayList<>();
    ArrayList<Integer> clicked = new ArrayList<>();
    ArrayList<Integer> count_finder = new ArrayList<Integer>();
    ArrayList<String> word_finder = new ArrayList<String>();
    Button search;
    String book;
    int count_done =0;
    CheckBox cb;


    int id_plus,id_minus;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        setContentView(R.layout.activity_main);
         l1 = (LinearLayout) findViewById(R.id.ll1);
        id_plus  = getResources().getIdentifier("com.example.gandh.hw03:drawable/plus", null, null);
        id_minus = getResources().getIdentifier("com.example.gandh.hw03:drawable/minus", null, null);
        view_generator(0);
        cb = (CheckBox)findViewById(R.id.checkBox);
        cb.setChecked(false);
        search = (Button) findViewById(R.id.button1);
        try{
            InputStream is = getAssets().open("textfile.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            book = new String(buffer);
            is.close();
        }
        catch  (IOException e) {
            e.printStackTrace();
        }
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;

                //CharSequence word_finder[] = new CharSequence[et1.size()];
                word_finder = new ArrayList<String>();
                for (EditText et :
                        et1) {

                    word_finder.add(i, String.valueOf(et.getText()));
                    i++;
                }
                if(word_finder.contains(""))
                {
                    Toast.makeText(MainActivity.this,"Search word should not be null",Toast.LENGTH_LONG).show();
                }

                else {
                    pd.show();
                    if (cb.isChecked()) {
                        for (String c :
                                word_finder) {
                            new searcher().execute(c, "yes");
                        }
                    } else {
                        for (String c :
                                word_finder) {
                            new searcher().execute(c, "no");
                        }
                    }
                }
                cb.setChecked(false);
            }
        });
    }

    public void output_generator(int i){
        //CharSequence count_finder[] = new CharSequence[et1.size()];
        count_finder.add(j,i);
        Log.d("demo",count_finder.get(j)+"element added");
        j++;


        if(count_done == et1.size())
        {
            Log.d("demo","its done");
            pd.dismiss();
            Intent ia = new Intent(MainActivity.this,Secon.class);
            ia.putExtra("key",count_finder);
            ia.putExtra("keyw",word_finder);

            startActivityForResult(ia, 100);

        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void view_generator(int i){

            ib1.add(i, new ImageButton(this));
       // fb1.add(i,new FloatingActionButton (this));
            et1.add(i, new EditText(this));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100,100);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(600,110);
        ib1.get(i).setLayoutParams(lp);
        et1.get(i).setLayoutParams(lp1);

           ib1.get(i).setId(View.generateViewId());
       // fb1.get(i).setId(View.generateViewId());
          ib1.get(i).setImageResource(id_plus);

       // fb1.get(i).setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.ic_input_add));
       // fb1.get(i).setImageResource(android.R.drawable.ic_input_add);
       // fb1.get(i).setForegroundTintList(ColorStateList.valueOf(Color.WHITE));

            et1.get(i).setId(View.generateViewId());
            local.add(i, new LinearLayout(this));
            local.get(i).addView(et1.get(i));
       // local.get(i).addView(fb1.get(i));
            local.get(i).addView(ib1.get(i));
            l1.addView(local.get(i));
            for (final ImageButton b :
                    ib1) {
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int removal = ib1.indexOf(b);
                        l1.removeView(local.get(removal));
                        local.remove(removal);
                        ib1.remove(removal);
                        et1.remove(removal);

                    }
                });

                ib1.get(ib1.size() - 1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ib1.size()<20) {
                            ib1.get(ib1.size() - 1).setImageResource(id_minus);
                            //et1.get(ib1.size() - 1).setText(ib1.size() - 1 + "generated");
                            view_generator(ib1.size());
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "only 20 search words are allowed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }


    }



    public class searcher extends AsyncTask<String,String ,Integer>{


        @Override
        protected Integer doInBackground(String... params) {
            String s = params[0];
            int count =0;

            StringTokenizer st = new StringTokenizer(book," ");
            //Log.d("demo",st.countTokens()+"tokesn");
           while(st.hasMoreTokens()) {
               if(params[1]== "no")
               {
                   if(s.equalsIgnoreCase(st.nextToken()))
                   {
                       count++;
                   }
               }
               else {
                   if (s.equals(st.nextToken())) {
                       count++;
                   }

               }
            }
            Log.d("demo",count+"counted");
            publishProgress("done");
            return count;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if(values[0].equals("done"))
            {
                count_done++;
            }
        }

        @Override
        protected void onPostExecute(Integer o) {

          Log.d("demo",o+"post execute");
            output_generator(o);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode ==100) {
            if (resultCode == RESULT_OK) {
                //setContentView(R.layout.activity_main);
               this.recreate();
            }
        }
    }
}
