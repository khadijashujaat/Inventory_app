package com.example.sqldemo2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class display_piechart extends AppCompatActivity {
    TextView percent;
    ProgressBar piechart;
    ArrayList<String> arrayList;
    int num;
    FloatingActionButton click;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_display_piechart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        piechart=findViewById(R.id.stats_progressbar);
        percent=findViewById(R.id.id_percent);
        click=findViewById(R.id.fab_2);

        Intent intent=getIntent();
        arrayList= intent.getStringArrayListExtra("list");

      num=calculate_percent();

        setPiechart(num);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(display_piechart.this, MainActivity2.class);
                startActivity(intent);
            }
        });

    }

    public void setPiechart(int quantity){
        if(isBetween(quantity,1,41)){
          //  piechart.getIndeterminateDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            piechart.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            piechart.setProgress(quantity);
            percent.setText(quantity+" %");

          }if (isBetween(quantity,42,70)){
            piechart.getProgressDrawable().setColorFilter(Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN);
            piechart.setProgress(quantity);
            percent.setText(quantity+" %");
          }if (isBetween(quantity,71,100)){
            piechart.getProgressDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
            piechart.setProgress(quantity);
            percent.setText(quantity+" %");

          }


    }
    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    public int calculate_percent(){

        int size,avg,j,sum=0;

        for (int i=0;i<arrayList.size();i++) {
            j=Integer.parseInt(arrayList.get(i));
            sum=sum+j;
        }
        size=arrayList.size();
        avg=sum/size;
        return avg;
    }




}