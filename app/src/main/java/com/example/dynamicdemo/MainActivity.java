package com.example.dynamicdemo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vcom.lib_guide.GuideUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GuideUtils.getInstance(this).addView(findViewById(R.id.tv1), R.layout.main_layout_bottom_tab_guide, R.id.btn_cancel);
        GuideUtils.getInstance(this).addView(findViewById(R.id.btn2), R.layout.main_layout_bottom_tab_guide, R.id.btn_cancel);
        GuideUtils.getInstance(this).addNoPassIdView(findViewById(R.id.iv3), R.layout.main_layout_camera_guide).show();

        EditText et4 = findViewById(R.id.et4);
        EditText et5 = findViewById(R.id.et5);
        et4.setText(getFormatDate());
        et5.setText(getFormatDate());

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("save", MODE_PRIVATE);
                sp.edit().putString("change", et4.getText().toString().trim()).apply();
                sp.edit().putString("reset", et5.getText().toString().trim()).apply();
                Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static String getFormatDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

}