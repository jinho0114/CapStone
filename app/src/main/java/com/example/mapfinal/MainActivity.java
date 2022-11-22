package com.example.mapfinal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.style.SuperscriptSpan;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int patient;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView imageView10;
    ImageView imageView11;
    ImageView imageView12;
    ImageView imageView13;
    ImageView imageView14;
    ImageView imageView15;
    ImageView imageView16;

    public class myDBHelper extends SQLiteOpenHelper{
        public myDBHelper(Context context){
            super(context, "patientDB",null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE patientDB (name varchar(10) PRIMARY KEY, all_patient INTEGER, dead_patient INERGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS patientDB");
            onCreate(db);

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        






















        imageView1 = (ImageView) findViewById(R.id.busan);
        imageView1.setColorFilter(Color.parseColor("#55ff0000"));
        imageView2 = (ImageView) findViewById(R.id.chungbuk);
        imageView2.setColorFilter(Color.parseColor("#44220000"));
        imageView3 = (ImageView) findViewById(R.id.chungnam);
        imageView3.setColorFilter(Color.parseColor("#55ff0000"));
        imageView4 = (ImageView) findViewById(R.id.daegu);
        imageView4.setColorFilter(Color.parseColor("#55ff0000"));
        imageView5 = (ImageView) findViewById(R.id.daejeon);
        imageView5.setColorFilter(Color.parseColor("#55ff0000"));
        imageView6 = (ImageView) findViewById(R.id.gangwondo);
        imageView6.setColorFilter(Color.parseColor("#55ff0000"));
        imageView7 = (ImageView) findViewById(R.id.gyeongbuk);
        imageView7.setColorFilter(Color.parseColor("#55ff0000"));
        imageView8 = (ImageView) findViewById(R.id.gyeonggido);
        imageView8.setColorFilter(Color.parseColor("#55ff0000"));
        imageView9 = (ImageView) findViewById(R.id.gyeongnam);
        imageView9.setColorFilter(Color.parseColor("#55ff0000"));
        imageView10 = (ImageView) findViewById(R.id.incheon);
        imageView10.setColorFilter(Color.parseColor("#55ff0000"));
        imageView11 = (ImageView) findViewById(R.id.jeju);
        imageView11.setColorFilter(Color.parseColor("#55ff0000"));
        imageView12 = (ImageView) findViewById(R.id.jeonbuk);
        imageView12.setColorFilter(Color.parseColor("#55ff0000"));
        imageView13 = (ImageView) findViewById(R.id.jeonnam);
        imageView13.setColorFilter(Color.parseColor("#55ff0000"));
        imageView14 = (ImageView) findViewById(R.id.sejong);
        imageView14.setColorFilter(Color.parseColor("#55ff0000"));
        imageView15 = (ImageView) findViewById(R.id.seoul);
        imageView15.setColorFilter(Color.parseColor("#55ff0000"));
        imageView16 = (ImageView) findViewById(R.id.ulsan);
        imageView16.setColorFilter(Color.parseColor("#55ff0000"));


    }
}