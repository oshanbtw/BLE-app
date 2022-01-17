package org.vosk.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;

public class SistemOncesi extends AppCompatActivity {

    LinearLayout layoutSistemAcKapat;
    TextView tvSistemAcKapat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sistem_oncesi);

        init();
        clickOlayları();

    }

    private void clickOlayları() {
        //Layout'a tıklayınca
        layoutSistemAcKapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(SistemOncesi.this, SistemSonrasi.class);
                    startActivity(intent);
                    finish();
            }
        });

        //Text'e tıklayınca
        tvSistemAcKapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(SistemOncesi.this, SistemSonrasi.class);
                    startActivity(intent);
                    finish();
            }
        });
    }

    private void init(){
        layoutSistemAcKapat = (LinearLayout) findViewById(R.id.sistemoncesi_layoutSistemAcKapat);
        tvSistemAcKapat = (TextView) findViewById(R.id.sistemoncesi_tvSistemAcKapat);
    }

}