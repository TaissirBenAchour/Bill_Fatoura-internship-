package com.internship.billFatoura;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    Button btn1_pdf;
    Button btn2_img;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_layout);
        TextView txt_welcome = (TextView) findViewById(R.id.txtwelcome);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/You're So Cool - OTF.otf");
        txt_welcome.setTypeface(face);


        btn2_img = (Button) findViewById(R.id.button2);
        btn2_img.setOnClickListener(this);
        btn1_pdf = (Button) findViewById(R.id.button1);
        btn1_pdf.setOnClickListener(this);




    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                Intent intent = new Intent(MainActivity.this, Activity3Pdfs.class);
                startActivity(intent);
                break;
            case R.id.button2:
                Intent intent1 = new Intent(MainActivity.this, Activity2Images.class);
                startActivity(intent1);
                break;
            default: break;
    }
}


}