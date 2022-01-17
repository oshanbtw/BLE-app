package org.vosk.demo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;

import java.util.ArrayList;
import java.util.Locale;

public class SistemSonrasi extends AppCompatActivity {

    BleDevice mbleDevice;
    String macAdress = "90:9A:77:2B:63:C9", service = "0000ffe0-0000-1000-8000-00805f9b34fb", characteristic = "0000ffe1-0000-1000-8000-00805f9b34fb", sesSonucu;

    ConstraintLayout layout_Koltuk, layout_Isik, layout_Multimedya, layout_Kontroller;
    ImageView img_Koltuk, img_Isik, img_mikrofon, img_Multimedya, img_Kontroller, img_SesUp, img_MikrofonEn;
    TextView tv_Koltuk, tv_Isik, tv_SistemAcKapa, tv_Multimedya, tv_Kontroller;

    int sayacBaglanti = 0, sayacBaglantiTekrar = 0; //Sol ve Sağtaki tuşlar için cihaza bağlı olmak gerekiyor. Bundan dolayı diğer sayfalardaki gibi eklemeyi unutma.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sistem_sonrasi);

        init();
        ClickOlaylari();
    }

    private void ClickOlaylari() {
        //Mikrofon
        img_mikrofon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sayacBaglantiTekrar == 0) {
                    Intent intent = new Intent(SistemSonrasi.this, VoskActivity.class);
                    startActivity(intent);
                } else {
                    BleManager.getInstance().disconnect(mbleDevice);
                    Intent intent = new Intent(SistemSonrasi.this, VoskActivity.class);
                    startActivity(intent);
                }
            }
        });

        img_MikrofonEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    String languagePref = "en-US";
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, languagePref);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please Speak");
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, languagePref);
                    intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, languagePref);
                    try {
                        startActivityForResult(intent, 1);
                    }catch (Exception e){
                        Log.v("Cihaz", String.valueOf(e));
                    }
            }
        });

        //Koltuk sekmesine basınca olacaklar.
        layout_Koltuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BleManager.getInstance().disconnect(mbleDevice);
                Intent intent = new Intent(SistemSonrasi.this, Koltuklar.class);
                startActivity(intent);
            }
        });

        img_Koltuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BleManager.getInstance().disconnect(mbleDevice);
                Intent intent = new Intent(SistemSonrasi.this, Koltuklar.class);
                startActivity(intent);
            }
        });

        tv_Koltuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BleManager.getInstance().disconnect(mbleDevice);
                Intent intent = new Intent(SistemSonrasi.this, Koltuklar.class);
                startActivity(intent);
            }
        });

        //Işık sekmesine basınca olacaklar.
        layout_Isik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BleManager.getInstance().disconnect(mbleDevice);
                Intent intent = new Intent(SistemSonrasi.this, Isiklar.class);
                startActivity(intent);
            }
        });

        img_Isik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BleManager.getInstance().disconnect(mbleDevice);
                Intent intent = new Intent(SistemSonrasi.this, Isiklar.class);
                startActivity(intent);
            }
        });

        tv_Isik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BleManager.getInstance().disconnect(mbleDevice);
                Intent intent = new Intent(SistemSonrasi.this, Isiklar.class);
                startActivity(intent);
            }
        });

        //Multimedya sekmesine basınca olacaklar.
        layout_Multimedya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BleManager.getInstance().disconnect(mbleDevice);
                Intent intent = new Intent(SistemSonrasi.this, Multimedya.class);
                startActivity(intent);
            }
        });

        img_Multimedya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BleManager.getInstance().disconnect(mbleDevice);
                Intent intent = new Intent(SistemSonrasi.this, Multimedya.class);
                startActivity(intent);
            }
        });

        tv_Multimedya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BleManager.getInstance().disconnect(mbleDevice);
                Intent intent = new Intent(SistemSonrasi.this, Multimedya.class);
                startActivity(intent);
            }
        });

        //Multimedya sekmesine basınca olacaklar.
        layout_Kontroller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BleManager.getInstance().disconnect(mbleDevice);
                Intent intent = new Intent(SistemSonrasi.this, Kontroller.class);
                startActivity(intent);
            }
        });

        img_Kontroller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BleManager.getInstance().disconnect(mbleDevice);
                Intent intent = new Intent(SistemSonrasi.this, Kontroller.class);
                startActivity(intent);
            }
        });

        tv_Kontroller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BleManager.getInstance().disconnect(mbleDevice);
                Intent intent = new Intent(SistemSonrasi.this, Kontroller.class);
                startActivity(intent);
            }
        });

        //Alt taraftaki menüde sistem aç-kapa yazısına basınca olacaklar.
        tv_SistemAcKapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sayacBaglantiTekrar == 0) {
                    Intent intent = new Intent(SistemSonrasi.this, SistemOncesi.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    BleManager.getInstance().disconnect(mbleDevice);
                    Intent intent = new Intent(SistemSonrasi.this, SistemOncesi.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        img_SesUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sayacBaglantiTekrar == 0) {
                    sayacBaglantiTekrar++;
                    baglan();
                }
                //İşlemler
            }
        });
    }

    private void init() {
        layout_Koltuk = (ConstraintLayout) findViewById(R.id.sistemsonrasi_layoutKoltuk);
        layout_Isik = (ConstraintLayout) findViewById(R.id.sistemsonrasi_layoutLight);
        layout_Multimedya = (ConstraintLayout) findViewById(R.id.sistemsonrasi_layoutMultimedia);
        layout_Kontroller = (ConstraintLayout) findViewById(R.id.sistemsonrasi_layoutController);

        img_Koltuk = (ImageView) findViewById(R.id.sistemsonrasi_imgKoltuk);
        img_Isik = (ImageView) findViewById(R.id.sistemsonrasi_imgLight);
        img_Multimedya = (ImageView) findViewById(R.id.sistemsonrasi_imgMultimedia);
        img_Kontroller = (ImageView) findViewById(R.id.sistemsonrasi_imgController);
        img_mikrofon = (ImageView) findViewById(R.id.sistemsonrasi_imgMicrophone);
        img_SesUp = (ImageView) findViewById(R.id.sistemsonrasi_imgSesUp);
        img_MikrofonEn = (ImageView) findViewById(R.id.sistemsonrasi_imgMicrophoneEn);

        tv_Koltuk = (TextView) findViewById(R.id.sistemsonrasi_tvKoltuk);
        tv_Isik = (TextView) findViewById(R.id.sistemsonrasi_tvLight);
        tv_Multimedya = (TextView) findViewById(R.id.sistemsonrasi_tvMultimedya);
        tv_Kontroller = (TextView) findViewById(R.id.sistemsonrasi_tvController);
        tv_SistemAcKapa = (TextView) findViewById(R.id.sistemsonrasi_tvSistemAcKapat);
    }

    private void baglan() {
        BleManager.getInstance().connect(macAdress, new BleGattCallback() {
            @Override
            public void onStartConnect() {
                Log.v("Cihaz", String.valueOf(BleManager.getInstance().isConnected(macAdress)));
                Log.v("Cihaz", "Bağlantı isteği başladı.");
            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                Log.v("Cihaz", "Bağlantı başarısız.");
                showToast("Bağlantı Başarısız.");
                sayacBaglanti = 0;
                Log.v("Cihaz", String.valueOf(exception));
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                Log.v("Cihaz", "Bağlantı başarılı. Bağlandı.");
                mbleDevice = bleDevice;
                sayacBaglanti++;
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
                Log.v("Cihaz", "Bağlantı kesildi.");
                sayacBaglanti = 0;
            }
        });
    }

    //Toast mesajı gösterme (Kısa).
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    //onActivityResult, açık olan activity içerisinde yukardaki mikrofona konuşma penceresi gibi ekrandan gelen activity'den gelen cebavı dinler.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Log.v("Cihaz", result.get(0));
        }
    }
}