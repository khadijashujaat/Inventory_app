package com.example.sqldemo2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Inventory_pg_activity extends AppCompatActivity {

    TextView d_name,d_price,d_quantity,percent;
    int per,per2;
    ProgressBar piechart;
    FloatingActionButton click;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.single_item);

        d_name=findViewById(R.id.scard_name);
        d_price=findViewById(R.id.scard_price);
        d_quantity=findViewById(R.id.scard_quantity);
        piechart=findViewById(R.id.stats_progressbar);
        percent=findViewById(R.id.id_percent);
        click=findViewById(R.id.fab_3);



        Intent intent=getIntent();

        String name=intent.getStringExtra("NAME");
        String price=intent.getStringExtra("PRICE");
        String quantity=intent.getStringExtra("QUANTITY");

        d_name.setText(name);
        d_price.setText(price);
        d_quantity.setText(quantity);


        setPiechart(Integer.parseInt(quantity));


        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Inventory_pg_activity.this, MainActivity2.class);
                startActivity(intent);
            }
        });



    }



    public void setPiechart(int quantity){
     //   quantity=(quantity/50)*100;
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

    private int calculate_percent(int num) {
        per2=(num/50)*100;
        return per;

    }

}