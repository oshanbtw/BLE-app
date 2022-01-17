package org.vosk.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.clj.fastble.BleManager;

public class Koltuklar extends AppCompatActivity {

    TextView tv_birinciKoltuk, tv_ikinciKoltuk, tv_ucuncuKoltuk, tv_dorduncuKoltuk;
    ImageView img_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koltuklar);

        init();
        acilistaOlacaklar();
        clickOlaylari();
    }

    private void acilistaOlacaklar() {
        getSupportFragmentManager().beginTransaction().replace(R.id.koltuk_frameLayout, new KoltukBirinciFragment()).commit(); //Açılışta 1.koltuk fragmentini açıyor.
        tv_birinciKoltuk.setBackground(getResources().getDrawable(R.drawable.koltuklar_bgtext_black)); // 1. koltuğun seçili olduğunu göstermek için arkaplanı değiştiriyor.
        tv_birinciKoltuk.setTextColor(getResources().getColor(R.color.white)); //1. koltuğun seçili olduğunu göstermek için yazı rengini değiştiriyor.
    }

    private void clickOlaylari() {
        //Bardaki koltuk textlerine tıklayınca olacaklar.
        tv_birinciKoltuk.setOnClickListener(new View.OnClickListener() { // Birinci Koltuğa tıklandığında
            @Override
            public void onClick(View v) {
                    tv_birinciKoltuk.setBackground(getResources().getDrawable(R.drawable.koltuklar_bgtext_black)); //Seçtiğimiz zaman arkaplanı değişssin
                    tv_birinciKoltuk.setTextColor(getResources().getColor(R.color.white));
                    //Diğerlerinin arkaplanı normal olsun.
                    tv_ikinciKoltuk.setBackgroundResource(android.R.color.transparent);
                    tv_ikinciKoltuk.setTextColor(getResources().getColor(R.color.black));

                    tv_ucuncuKoltuk.setBackgroundResource(android.R.color.transparent);
                    tv_ucuncuKoltuk.setTextColor(getResources().getColor(R.color.black));

                    tv_dorduncuKoltuk.setBackgroundResource(android.R.color.transparent);
                    tv_dorduncuKoltuk.setTextColor(getResources().getColor(R.color.black));

                    KoltukBirinciFragment seciliFragment = new KoltukBirinciFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.koltuk_frameLayout, seciliFragment).commit(); //Fragmenti açma komutu.
            }
        });
        tv_ikinciKoltuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    tv_ikinciKoltuk.setBackground(getResources().getDrawable(R.drawable.koltuklar_bgtext_black)); //Seçtiğimiz zaman arkaplanı değişssin
                    tv_ikinciKoltuk.setTextColor(getResources().getColor(R.color.white));
                    //Diğerlerinin arkaplanı normal olsun.
                    tv_birinciKoltuk.setBackgroundResource(android.R.color.transparent);
                    tv_birinciKoltuk.setTextColor(getResources().getColor(R.color.black));

                    tv_ucuncuKoltuk.setBackgroundResource(android.R.color.transparent);
                    tv_ucuncuKoltuk.setTextColor(getResources().getColor(R.color.black));

                    tv_dorduncuKoltuk.setBackgroundResource(android.R.color.transparent);
                    tv_dorduncuKoltuk.setTextColor(getResources().getColor(R.color.black));

                    KoltukIkinciFragment seciliFragment = new KoltukIkinciFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.koltuk_frameLayout, seciliFragment).commit(); //Fragmenti açma komutu.
            }
        });
        tv_ucuncuKoltuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    tv_ucuncuKoltuk.setBackground(getResources().getDrawable(R.drawable.koltuklar_bgtext_black)); //Seçtiğimiz zaman arkaplanı değişssin
                    tv_ucuncuKoltuk.setTextColor(getResources().getColor(R.color.white));
                    //Diğerlerinin arkaplanı normal olsun.
                    tv_birinciKoltuk.setBackgroundResource(android.R.color.transparent);
                    tv_birinciKoltuk.setTextColor(getResources().getColor(R.color.black));

                    tv_ikinciKoltuk.setBackgroundResource(android.R.color.transparent);
                    tv_ikinciKoltuk.setTextColor(getResources().getColor(R.color.black));

                    tv_dorduncuKoltuk.setBackgroundResource(android.R.color.transparent);
                    tv_dorduncuKoltuk.setTextColor(getResources().getColor(R.color.black));

                    KoltukUcuncuFragment seciliFragment = new KoltukUcuncuFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.koltuk_frameLayout, seciliFragment).commit(); //Fragmenti açma komutu.
            }
        });
        tv_dorduncuKoltuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    tv_dorduncuKoltuk.setBackground(getResources().getDrawable(R.drawable.koltuklar_bgtext_black)); //Seçtiğimiz zaman arkaplanı değişssin
                    tv_dorduncuKoltuk.setTextColor(getResources().getColor(R.color.white));
                    //Diğerlerinin arkaplanı normal olsun.
                    tv_birinciKoltuk.setBackgroundResource(android.R.color.transparent);
                    tv_birinciKoltuk.setTextColor(getResources().getColor(R.color.black));

                    tv_ikinciKoltuk.setBackgroundResource(android.R.color.transparent);
                    tv_ikinciKoltuk.setTextColor(getResources().getColor(R.color.black));

                    tv_ucuncuKoltuk.setBackgroundResource(android.R.color.transparent);
                    tv_ucuncuKoltuk.setTextColor(getResources().getColor(R.color.black));

                    KoltukDorduncuFragment seciliFragment = new KoltukDorduncuFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.koltuk_frameLayout, seciliFragment).commit(); //Fragmenti açma komutu.
            }
        });

        //Home tuşuna basınca menüye gidiyor.
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Koltuklar.this, SistemSonrasi.class);
                    startActivity(intent);
                    finish();
            }
        });
    }

    private void init(){
        tv_birinciKoltuk = (TextView) findViewById(R.id.koltuklar_tvKoltuk1);
        tv_ikinciKoltuk = (TextView) findViewById(R.id.koltuklar_tvKoltuk2);
        tv_ucuncuKoltuk = (TextView) findViewById(R.id.koltuklar_tvKoltuk3);
        tv_dorduncuKoltuk = (TextView) findViewById(R.id.koltuklar_tvKoltuk4);

        img_home = (ImageView) findViewById(R.id.koltuklar_imgHome);
    }
}