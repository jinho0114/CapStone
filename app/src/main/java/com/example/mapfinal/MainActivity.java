package com.example.mapfinal;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    TextView textView2;
    Button button_newCase, button_totalCase, button_death;
    ImageView busan, chungbuk, chungnam, daegu,daejeon,gangwon,gyeongbuk,gyeonggi,gyeongnam,incheon,jeju,jeonbuk,jeonnam,sejong,seoul,ulsan,chart1,chart2,chart3;
    final static String TAG = "MapFinal";
    String resultText;
    ImageView[] imageViews = new ImageView[16];
    Integer[] imageViewsId = {R.id.seoul, R.id.incheon, R.id.gyeonggido, R.id.gangwondo, R.id.gyeongbuk, R.id.gyeongnam, R.id.jeonbuk, R.id.jeonnam, R.id.chungbuk, R.id.chungnam, R.id.sejong, R.id.busan, R.id.daegu, R.id.daejeon, R.id.ulsan, R.id.jeju};
    String[] region_arr = {"seoul", "incheon", "gyeonggi", "gangwon", "gyeongbuk", "gyeongnam", "jeonbuk", "jeonnam", "chungbuk", "chungnam", "sejong", "busan", "daegu", "daejeon", "ulsan", "jeju"};

    int i;
    int value_num = 0;
    String region_select;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_btn1:
                final String[] region_arr2 = new String[]{"서울", "인천", "경기", "강원", "경북", "경남", "전북", "전남", "충북", "충남", "세종", "부산", "대구", "대전", "울산", "제주"};
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setItems(region_arr2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder dlg2 = new AlertDialog.Builder(MainActivity.this);
                        dlg2.setTitle(region_arr2[which] + " 정보");
                        for (i = 0; i < 16; i++) {
                            if (region_arr2[i] == region_arr2[which]) {
                                region_select = region_arr[i];
                            }
                        }
                        Region_inform(region_select);
                        dlg2.setMessage(textView2.getText());
                        dlg2.setPositiveButton("확인", null);
                        dlg2.show();
                    }
                });
                dlg.setTitle("지역을 선택하세요");
                dlg.setPositiveButton("확인", null);
                dlg.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        button_newCase = (Button) findViewById(R.id.button_newCase);
        button_totalCase = (Button) findViewById(R.id.button_totalCase);
        button_death = (Button) findViewById(R.id.button_death);
        busan = (ImageView) findViewById(R.id.busan);
        chungbuk = (ImageView) findViewById(R.id.chungbuk);
        chungnam = (ImageView) findViewById(R.id.chungnam);
        daegu = (ImageView) findViewById(R.id.daegu);
        daejeon = (ImageView) findViewById(R.id.daejeon);
        gangwon = (ImageView) findViewById(R.id.gangwondo);
        gyeongbuk = (ImageView) findViewById(R.id.gyeongbuk);
        gyeonggi = (ImageView) findViewById(R.id.gyeonggido);
        gyeongnam = (ImageView) findViewById(R.id.gyeongnam);
        incheon = (ImageView) findViewById(R.id.incheon);
        jeju = (ImageView) findViewById(R.id.jeju);
        jeonbuk = (ImageView) findViewById(R.id.jeonbuk);
        jeonnam = (ImageView) findViewById(R.id.jeonnam);
        sejong = (ImageView) findViewById(R.id.sejong);
        seoul = (ImageView) findViewById(R.id.seoul);
        ulsan = (ImageView) findViewById(R.id.ulsan);
        chart1 = (ImageView) findViewById(R.id.chart_newCase);
        chart2 = (ImageView) findViewById(R.id.chart_totalCase);
        chart3 = (ImageView) findViewById(R.id.chart_death);


        button_newCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (i = 0; i < 16; i++) {
                    Region(region_arr[i], "newCase");
                    Color_newCase(region_arr[i], value_num);
                }
                chart1.setVisibility(View.VISIBLE);
                chart2.setVisibility(View.INVISIBLE);
                chart3.setVisibility(View.INVISIBLE);
            }
        });
        button_totalCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (i = 0; i < 16; i++) {
                    Region(region_arr[i], "totalCase");
                    Color_totalCase(region_arr[i], value_num);
                }
                chart1.setVisibility(View.INVISIBLE);
                chart2.setVisibility(View.VISIBLE);
                chart3.setVisibility(View.INVISIBLE);
            }
        });
        button_death.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (i = 0; i < 16; i++) {
                    Region(region_arr[i], "death");
                    Color_death(region_arr[i], value_num);
                }
                chart1.setVisibility(View.INVISIBLE);
                chart2.setVisibility(View.INVISIBLE);
                chart3.setVisibility(View.VISIBLE);
            }
        });

    }
    private void Color_newCase(String countryname, int newCase) {
        for (i = 0; i < 16; i++) {
            imageViews[i] = (ImageView) findViewById(imageViewsId[i]);
            if (countryname.equals(region_arr[i])) {
                if (newCase < 1000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EEC9C9"));
                } else if (newCase < 2000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EBADAD"));
                } else if (newCase < 2500) {
                    imageViews[i].setColorFilter(Color.parseColor("#EA9090"));
                } else if (newCase < 4000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EB6F6F"));
                } else if (newCase < 5000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EF4D4D"));
                } else {
                    imageViews[i].setColorFilter(Color.parseColor("#F62828"));
                }
                break;
            }
        }
    }

//        switch (countryname) {
//            case "busan":
//                if (newCase < 1000) {
//                    busan.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (newCase < 2000) {
//                    busan.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (newCase < 3000) {
//                    busan.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (newCase < 4000) {
//                    busan.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (newCase < 5000) {
//                    busan.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    busan.setColorFilter(Color.parseColor("#F62828"));
//                }
//                break;
//            case "chungbuk":
//                if (newCase < 1000) {
//                    chungbuk.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (newCase < 2000) {
//                    chungbuk.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (newCase < 3000) {
//                    chungbuk.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (newCase < 4000) {
//                    chungbuk.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (newCase < 5000) {
//                    chungbuk.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    chungbuk.setColorFilter(Color.parseColor("#F62828"));
//                }
//                break;
//            case "chungnam":
//                if (newCase < 1000) {
//                    chungnam.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (newCase < 2000) {
//                    chungnam.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (newCase < 3000) {
//                    chungnam.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (newCase < 4000) {
//                    chungnam.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (newCase < 5000) {
//                    chungnam.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    chungnam.setColorFilter(Color.parseColor("#F62828"));
//                }
//                break;
//            case "daegu":
//                if (newCase < 1000) {
//                    daegu.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (newCase < 2000) {
//                    daegu.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (newCase < 3000) {
//                    daegu.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (newCase < 4000) {
//                    daegu.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (newCase < 5000) {
//                    daegu.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    daegu.setColorFilter(Color.parseColor("#F62828"));
//                }
//                break;
//            case "daejeon":
//                if (newCase < 1000) {
//                    daejeon.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (newCase < 2000) {
//                    daejeon.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (newCase < 3000) {
//                    daejeon.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (newCase < 4000) {
//                    daejeon.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (newCase < 5000) {
//                    daejeon.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    daejeon.setColorFilter(Color.parseColor("#F62828"));
//                }
//                break;
//            case "gangwon":
//                if (newCase < 1000) {
//                    gangwon.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (newCase < 2000) {
//                    gangwon.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (newCase < 3000) {
//                    gangwon.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (newCase < 4000) {
//                    gangwon.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (newCase < 5000) {
//                    gangwon.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    gangwon.setColorFilter(Color.parseColor("#F62828"));
//                }
//                break;
//            case "gyeongbuk":
//                if (newCase < 1000) {
//                    gyeongbuk.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (newCase < 2000) {
//                    gyeongbuk.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (newCase < 3000) {
//                    gyeongbuk.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (newCase < 4000) {
//                    gyeongbuk.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (newCase < 5000) {
//                    gyeongbuk.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    gyeongbuk.setColorFilter(Color.parseColor("#F62828"));
//                }
//                break;
//            case "gyeonggi":
//                if (newCase < 1000) {
//                    gyeonggi.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (newCase < 2000) {
//                    gyeonggi.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (newCase < 3000) {
//                    gyeonggi.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (newCase < 4000) {
//                    gyeonggi.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (newCase < 5000) {
//                    gyeonggi.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    gyeonggi.setColorFilter(Color.parseColor("#F62828"));
//                }
//                break;
//            case "gyeongnam":
//                if (newCase < 1000) {
//                    gyeongnam.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (newCase < 2000) {
//                    gyeongnam.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (newCase < 3000) {
//                    gyeongnam.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (newCase < 4000) {
//                    gyeongnam.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (newCase < 5000) {
//                    gyeongnam.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    gyeongnam.setColorFilter(Color.parseColor("#F62828"));
//                }
//                break;
//            case "incheon":
//                if (newCase < 1000) {
//                    incheon.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (newCase < 2000) {
//                    incheon.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (newCase < 3000) {
//                    incheon.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (newCase < 4000) {
//                    incheon.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (newCase < 5000) {
//                    incheon.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    incheon.setColorFilter(Color.parseColor("#F62828"));
//                }
//                break;
//            case "jeju":
//                if (newCase < 1000) {
//                    jeju.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (newCase < 2000) {
//                    jeju.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (newCase < 3000) {
//                    jeju.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (newCase < 4000) {
//                    jeju.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (newCase < 5000) {
//                    jeju.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    jeju.setColorFilter(Color.parseColor("#F62828"));
//                }
//                break;
//            case "jeonbuk":
//                if (newCase < 1000) {
//                    jeonbuk.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (newCase < 2000) {
//                    jeonbuk.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (newCase < 3000) {
//                    jeonbuk.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (newCase < 4000) {
//                    jeonbuk.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (newCase < 5000) {
//                    jeonbuk.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    jeonbuk.setColorFilter(Color.parseColor("#F62828"));
//                }
//                break;
//            case "jeonnam":
//                if (newCase < 1000) {
//                    jeonnam.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (newCase < 2000) {
//                    jeonnam.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (newCase < 3000) {
//                    jeonnam.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (newCase < 4000) {
//                    jeonnam.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (newCase < 5000) {
//                    jeonnam.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    jeonnam.setColorFilter(Color.parseColor("#F62828"));
//                }
//                break;
//            case "sejong":
//                if (newCase < 1000) {
//                    sejong.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (newCase < 2000) {
//                    sejong.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (newCase < 3000) {
//                    sejong.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (newCase < 4000) {
//                    sejong.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (newCase < 5000) {
//                    sejong.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    sejong.setColorFilter(Color.parseColor("#F62828"));
//                }
//                break;
//            case "seoul":
//                if (newCase < 1000) {
//                    seoul.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (newCase < 2000) {
//                    seoul.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (newCase < 3000) {
//                    seoul.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (newCase < 4000) {
//                    seoul.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (newCase < 5000) {
//                    seoul.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    seoul.setColorFilter(Color.parseColor("#F62828"));
//                }
//                break;
//            case "ulsan":
//                if (newCase < 1000) {
//                    ulsan.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (newCase < 2000) {
//                    ulsan.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (newCase < 3000) {
//                    ulsan.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (newCase < 4000) {
//                    ulsan.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (newCase < 5000) {
//                    ulsan.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    ulsan.setColorFilter(Color.parseColor("#F62828"));
//                }
//                break;
    private void Color_totalCase(String countryname, int totalCase) {
        for (i = 0; i < 16; i++) {
            imageViews[i] = (ImageView) findViewById(imageViewsId[i]);
            if (countryname.equals(region_arr[i])) {
                if (totalCase < 500000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EEC9C9"));
                } else if (totalCase < 900000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EBADAD"));
                } else if (totalCase < 1300000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EA9090"));
                } else if (totalCase < 1800000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EB6F6F"));
                } else if (totalCase < 5000000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EF4D4D"));
                } else {
                    imageViews[i].setColorFilter(Color.parseColor("#F62828"));
                }
                break;
            }
        }
    }
//        switch (countryname) {   //500000, 900000,1300000,1800000,5000000
//            case "busan":
//                if (totalCase < 500000) {
//                    busan.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (totalCase < 900000) {
//                    busan.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (totalCase < 1300000) {
//                    busan.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (totalCase < 1800000) {
//                    busan.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (totalCase < 5000000) {
//                    busan.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    busan.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "chungbuk":
//                if (totalCase < 500000) {
//                    chungbuk.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (totalCase < 900000) {
//                    chungbuk.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (totalCase < 1300000) {
//                    chungbuk.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (totalCase < 1800000) {
//                    chungbuk.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (totalCase < 5000000) {
//                    chungbuk.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    chungbuk.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "chungnam":
//                if (totalCase < 500000) {
//                    chungnam.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (totalCase < 900000) {
//                    chungnam.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (totalCase < 1300000) {
//                    chungnam.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (totalCase < 1800000) {
//                    chungnam.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (totalCase < 5000000) {
//                    chungnam.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    chungnam.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "daegu":
//                if (totalCase < 500000) {
//                    daegu.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (totalCase < 900000) {
//                    daegu.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (totalCase < 1300000) {
//                    daegu.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (totalCase < 1800000) {
//                    daegu.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (totalCase < 5000000) {
//                    daegu.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    daegu.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "daejeon":
//                if (totalCase < 500000) {
//                    daejeon.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (totalCase < 900000) {
//                    daejeon.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (totalCase < 1300000) {
//                    daejeon.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (totalCase < 1800000) {
//                    daejeon.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (totalCase < 5000000) {
//                    daejeon.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    daejeon.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "gangwon":
//                if (totalCase < 500000) {
//                    gangwon.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (totalCase < 900000) {
//                    gangwon.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (totalCase < 1300000) {
//                    gangwon.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (totalCase < 1800000) {
//                    gangwon.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (totalCase < 5000000) {
//                    gangwon.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    gangwon.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "gyeongbuk":
//                if (totalCase < 500000) {
//                    gyeongbuk.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (totalCase < 900000) {
//                    gyeongbuk.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (totalCase < 1300000) {
//                    gyeongbuk.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (totalCase < 1800000) {
//                    gyeongbuk.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (totalCase < 5000000) {
//                    gyeongbuk.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    gyeongbuk.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "gyeonggi":
//                if (totalCase < 500000) {
//                    gyeonggi.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (totalCase < 900000) {
//                    gyeonggi.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (totalCase < 1300000) {
//                    gyeonggi.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (totalCase < 1800000) {
//                    gyeonggi.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (totalCase < 5000000) {
//                    gyeonggi.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    gyeonggi.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "gyeongnam":
//                if (totalCase < 500000) {
//                    gyeongnam.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (totalCase < 900000) {
//                    gyeongnam.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (totalCase < 1300000) {
//                    gyeongnam.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (totalCase < 1800000) {
//                    gyeongnam.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (totalCase < 5000000) {
//                    gyeongnam.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    gyeongnam.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "incheon":
//                if (totalCase < 500000) {
//                    incheon.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (totalCase < 900000) {
//                    incheon.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (totalCase < 1300000) {
//                    incheon.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (totalCase < 1800000) {
//                    incheon.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (totalCase < 5000000) {
//                    incheon.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    incheon.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "jeju":
//                if (totalCase < 500000) {
//                    jeju.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (totalCase < 900000) {
//                    jeju.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (totalCase < 1300000) {
//                    jeju.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (totalCase < 1800000) {
//                    jeju.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (totalCase < 5000000) {
//                    jeju.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    jeju.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "jeonbuk":
//                if (totalCase < 500000) {
//                    jeonbuk.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (totalCase < 900000) {
//                    jeonbuk.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (totalCase < 1300000) {
//                    jeonbuk.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (totalCase < 1800000) {
//                    jeonbuk.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (totalCase < 5000000) {
//                    jeonbuk.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    jeonbuk.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "jeonnam":
//                if (totalCase < 500000) {
//                    jeonnam.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (totalCase < 900000) {
//                    jeonnam.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (totalCase < 1300000) {
//                    jeonnam.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (totalCase < 1800000) {
//                    jeonnam.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (totalCase < 5000000) {
//                    jeonnam.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    jeonnam.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "sejong":
//                if (totalCase < 500000) {
//                    sejong.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (totalCase < 900000) {
//                    sejong.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (totalCase < 1300000) {
//                    sejong.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (totalCase < 1800000) {
//                    sejong.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (totalCase < 5000000) {
//                    sejong.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    sejong.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "seoul":
//                if (totalCase < 500000) {
//                    seoul.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (totalCase < 900000) {
//                    seoul.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (totalCase < 1300000) {
//                    seoul.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (totalCase < 1800000) {
//                    seoul.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (totalCase < 5000000) {
//                    seoul.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    seoul.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "ulsan":
//                if (totalCase < 500000) {
//                    ulsan.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (totalCase < 900000) {
//                    ulsan.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (totalCase < 1300000) {
//                    ulsan.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (totalCase < 1800000) {
//                    ulsan.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (totalCase < 5000000) {
//                    ulsan.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    ulsan.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//        }
    private void Color_death(String countryname, int death) {
        for (i = 0; i < 16; i++) {
            imageViews[i] = (ImageView) findViewById(imageViewsId[i]);
            if (countryname.equals(region_arr[i])) {
                if (death < 100) {
                    imageViews[i].setColorFilter(Color.parseColor("#EEC9C9"));
                } else if (death < 500) {
                    imageViews[i].setColorFilter(Color.parseColor("#EBADAD"));
                } else if (death < 1000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EA9090"));
                } else if (death < 1800) {
                    imageViews[i].setColorFilter(Color.parseColor("#EB6F6F"));
                } else if (death < 6000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EF4D4D"));
                } else {
                    imageViews[i].setColorFilter(Color.parseColor("#F62828"));
                }
                break;
            }
        }
    }
//        switch (countryname) {  //100,500,1000,2000,5000
//            case "busan":
//                if (death < 100) {
//                    busan.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (death < 500) {
//                    busan.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (death < 1000) {
//                    busan.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (death < 2000) {
//                    busan.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (death < 5000) {
//                    busan.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    busan.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "chungbuk":
//                if (death < 100) {
//                    chungbuk.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (death < 500) {
//                    chungbuk.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (death < 1000) {
//                    chungbuk.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (death < 2000) {
//                    chungbuk.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (death < 5000) {
//                    chungbuk.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    chungbuk.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "chungnam":
//                if (death < 100) {
//                    chungnam.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (death < 500) {
//                    chungnam.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (death < 1000) {
//                    chungnam.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (death < 2000) {
//                    chungnam.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (death < 5000) {
//                    chungnam.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    chungnam.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "daegu":
//                if (death < 100) {
//                    daegu.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (death < 500) {
//                    daegu.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (death < 1000) {
//                    daegu.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (death < 2000) {
//                    daegu.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (death < 5000) {
//                    daegu.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    daegu.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "daejeon":
//                if (death < 100) {
//                    daejeon.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (death < 500) {
//                    daejeon.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (death < 1000) {
//                    daejeon.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (death < 2000) {
//                    daejeon.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (death < 5000) {
//                    daejeon.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    daejeon.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "gangwon":
//                if (death < 100) {
//                    gangwon.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (death < 500) {
//                    gangwon.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (death < 1000) {
//                    gangwon.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (death < 2000) {
//                    gangwon.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (death < 5000) {
//                    gangwon.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    gangwon.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "gyeongbuk":
//                if (death < 100) {
//                    gyeongbuk.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (death < 500) {
//                    gyeongbuk.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (death < 1000) {
//                    gyeongbuk.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (death < 2000) {
//                    gyeongbuk.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (death < 5000) {
//                    gyeongbuk.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    gyeongbuk.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "gyeonggi":
//                if (death < 100) {
//                    gyeonggi.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (death < 500) {
//                    gyeonggi.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (death < 1000) {
//                    gyeonggi.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (death < 2000) {
//                    gyeonggi.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (death < 5000) {
//                    gyeonggi.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    gyeonggi.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "gyeongnam":
//                if (death < 100) {
//                    gyeongnam.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (death < 500) {
//                    gyeongnam.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (death < 1000) {
//                    gyeongnam.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (death < 2000) {
//                    gyeongnam.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (death < 5000) {
//                    gyeongnam.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    gyeongnam.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "incheon":
//                if (death < 100) {
//                    incheon.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (death < 500) {
//                    incheon.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (death < 1000) {
//                    incheon.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (death < 2000) {
//                    incheon.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (death < 5000) {
//                    incheon.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    incheon.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "jeju":
//                if (death < 100) {
//                    jeju.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (death < 500) {
//                    jeju.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (death < 1000) {
//                    jeju.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (death < 2000) {
//                    jeju.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (death < 5000) {
//                    jeju.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    jeju.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "jeonbuk":
//                if (death < 100) {
//                    jeonbuk.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (death < 500) {
//                    jeonbuk.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (death < 1000) {
//                    jeonbuk.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (death < 2000) {
//                    jeonbuk.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (death < 5000) {
//                    jeonbuk.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    jeonbuk.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "jeonnam":
//                if (death < 100) {
//                    jeonnam.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (death < 500) {
//                    jeonnam.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (death < 1000) {
//                    jeonnam.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (death < 2000) {
//                    jeonnam.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (death < 5000) {
//                    jeonnam.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    jeonnam.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "sejong":
//                if (death < 100) {
//                    sejong.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (death < 500) {
//                    sejong.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (death < 1000) {
//                    sejong.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (death < 2000) {
//                    sejong.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (death < 5000) {
//                    sejong.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    sejong.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "seoul":
//                if (death < 100) {
//                    seoul.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (death < 500) {
//                    seoul.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (death < 1000) {
//                    seoul.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (death < 2000) {
//                    seoul.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (death < 5000) {
//                    seoul.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    seoul.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//            case "ulsan":
//                if (death < 100) {
//                    ulsan.setColorFilter(Color.parseColor("#EEC9C9"));
//                } else if (death < 500) {
//                    ulsan.setColorFilter(Color.parseColor("#EBADAD"));
//                } else if (death < 1000) {
//                    ulsan.setColorFilter(Color.parseColor("#EA9090"));
//                } else if (death < 2000) {
//                    ulsan.setColorFilter(Color.parseColor("#EB6F6F"));
//                } else if (death < 5000) {
//                    ulsan.setColorFilter(Color.parseColor("#EF4D4D"));
//                } else {
//                    ulsan.setColorFilter(Color.parseColor("#F62828"));
//                }break;
//        }
    public int Region(String region, String value) {
        try {
            resultText = new Task().execute().get();
            JSONObject jsonObject = new JSONObject(resultText);
            JSONObject name = (JSONObject) jsonObject.get(region);
            JSONObject korea = (JSONObject) jsonObject.get("korea");
            StringBuilder sb = new StringBuilder();
            sb.append("한국의 누적 확진자 수 :"+ korea.get(value));
            textView.setText(sb.toString());
            String totalCase_get = name.get(value).toString();
            String totalCase_str = totalCase_get.replace(",","");
            value_num = Integer.parseInt(totalCase_str);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value_num;
    }

    public void Region_inform(String region) {
        try {
            resultText = new Task().execute().get();
            JSONObject jsonObject = new JSONObject(resultText);
            JSONObject name = (JSONObject) jsonObject.get(region);
            StringBuilder sb = new StringBuilder();
            sb.append(name.get("countryName") + "의 신규 확진자 수 :" + name.get("newCase")
                    + "\n" +name.get("countryName") + "의 누적 확진자 수 :"+ name.get("totalCase")
                    + "\n"+name.get("countryName") + "의 누적 사망자 수 :"+ name.get("death")
                    + "\n"+name.get("countryName") + "의 신규 해외유입 확진자 수 :"+ name.get("newFcase"));
            textView2.setText(sb.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
