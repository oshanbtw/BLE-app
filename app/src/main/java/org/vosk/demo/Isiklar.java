package org.vosk.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;

import java.util.concurrent.TimeUnit;

public class Isiklar extends AppCompatActivity {

    String macAdress = "90:9A:77:2B:63:C9", service = "0000ffe0-0000-1000-8000-00805f9b34fb", characteristic = "0000ffe1-0000-1000-8000-00805f9b34fb";
    public BleDevice mbleDevice;

    private ImageView img_Home, img_TavanKirmizi, img_TavanYesil, img_TavanMavi, img_TavanBeyaz, img_TavanGri, img_YanKirmizi,
            img_YanYesil, img_YanMavi, img_YanBeyaz, img_YanGri, img_IcKirmizi, img_IcYesil, img_IcMavi,
            img_IcBeyaz, img_IcGri, img_WheelMemory1, img_WheelMemory2, img_WheelMemory3;
    private SeekBar sb_TavanKirmizi, sb_TavanYesil, sb_TavanMavi, sb_YanKirmizi, sb_YanYesil, sb_YanMavi, sb_IcKirmizi, sb_IcYesil, sb_IcMavi;
    private RelativeLayout layout_TavanKapat, layout_YanKapat, layout_IcKapat, layout_SolOkumaLambasi, layout_SagOkumaLambasi;
    private TextView tv_TavanKapat, tv_YanKapat, tv_IcKapat, tv_SolOkumaLambasi, tv_SagOkumaLambasi;

    int[] renkTutucu = new int[9], renkTutucu2 = new int[9], renkTutucu3 = new int[9];
    int sayacBaglanti = 0;
    boolean hafizaDurumu = false, hafizaDurumu2 = false, hafizaDurumu3 = false;

    //Cihaz hafızası için.
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private final Context context = Isiklar.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isiklar);

        BleManager.getInstance().init(getApplication()); //Bluetooth bağlantısı için gerekli.

        init();
        acilistaOlacaklar();
        baglan();
        clickOlaylari();
    }

    private void sesliKomutYakalama() {
        //Sesli komut sayfasından veri alıyoruz.
        Bundle gelenveri = getIntent().getExtras();
        //Gelen veri boşsa yani sesli komut gelmemişse içeri girmiyor.
        if (gelenveri != null) {
            //Gelen veriyi data stringine aktarıyor.
            String data = gelenveri.getString("fulRenkValue");

            //Burdan sonrası gelen veriye göre hangi işlemi yaptığımız.
            if(data.equals("ışıklarıkırmızıyap")){
                sb_TavanKirmizi.setProgress(255);
                sb_TavanMavi.setProgress(0);
                sb_TavanYesil.setProgress(0);

                sb_YanKirmizi.setProgress(255);
                sb_YanMavi.setProgress(0);
                sb_YanYesil.setProgress(0);

                sb_IcKirmizi.setProgress(255);
                sb_IcMavi.setProgress(0);
                sb_IcYesil.setProgress(0);

                //Bu kısımda bağlı olduğumuz cihaza veri gönderiyoruz.
                //Veriye "\n" ekliyoruz ki doğru bir şekilde gitsin.
                data = data + "\n";
                byte[] dataByte = data.getBytes();
                veriGonder(mbleDevice, service, characteristic, dataByte);
            }
            else if(data.equals("ışıklarıkırmızıyada")){
                sb_TavanKirmizi.setProgress(255);
                sb_TavanMavi.setProgress(0);
                sb_TavanYesil.setProgress(0);

                sb_YanKirmizi.setProgress(255);
                sb_YanMavi.setProgress(0);
                sb_YanYesil.setProgress(0);

                sb_IcKirmizi.setProgress(255);
                sb_IcMavi.setProgress(0);
                sb_IcYesil.setProgress(0);

                data = data + "\n";
                byte[] dataByte = data.getBytes();
                veriGonder(mbleDevice, service, characteristic, dataByte);
            }
            else if(data.equals("ışıklarıkırmızıya")){
                sb_TavanKirmizi.setProgress(255);
                sb_TavanMavi.setProgress(0);
                sb_TavanYesil.setProgress(0);

                sb_YanKirmizi.setProgress(255);
                sb_YanMavi.setProgress(0);
                sb_YanYesil.setProgress(0);

                sb_IcKirmizi.setProgress(255);
                sb_IcMavi.setProgress(0);
                sb_IcYesil.setProgress(0);

                data = data + "\n";
                byte[] dataByte = data.getBytes();
                veriGonder(mbleDevice, service, characteristic, dataByte);
            }
            else if(data.equals("ışıklarıyeşilyap")){
                sb_TavanKirmizi.setProgress(0);
                sb_TavanMavi.setProgress(0);
                sb_TavanYesil.setProgress(255);

                sb_YanKirmizi.setProgress(0);
                sb_YanMavi.setProgress(0);
                sb_YanYesil.setProgress(255);

                sb_IcKirmizi.setProgress(0);
                sb_IcMavi.setProgress(0);
                sb_IcYesil.setProgress(255);

                data = data + "\n";
                byte[] dataByte = data.getBytes();
                veriGonder(mbleDevice, service, characteristic, dataByte);
            }
            else if(data.equals("ışıklarıyeşilya")){
                sb_TavanKirmizi.setProgress(0);
                sb_TavanMavi.setProgress(0);
                sb_TavanYesil.setProgress(255);

                sb_YanKirmizi.setProgress(0);
                sb_YanMavi.setProgress(0);
                sb_YanYesil.setProgress(255);

                sb_IcKirmizi.setProgress(0);
                sb_IcMavi.setProgress(0);
                sb_IcYesil.setProgress(255);

                data = data + "\n";
                byte[] dataByte = data.getBytes();
                veriGonder(mbleDevice, service, characteristic, dataByte);
            }
            else if(data.equals("ışıklarıyeşil")){
                sb_TavanKirmizi.setProgress(0);
                sb_TavanMavi.setProgress(0);
                sb_TavanYesil.setProgress(255);

                sb_YanKirmizi.setProgress(0);
                sb_YanMavi.setProgress(0);
                sb_YanYesil.setProgress(255);

                sb_IcKirmizi.setProgress(0);
                sb_IcMavi.setProgress(0);
                sb_IcYesil.setProgress(255);

                data = data + "\n";
                byte[] dataByte = data.getBytes();
                veriGonder(mbleDevice, service, characteristic, dataByte);
            }
            else if(data.equals("ışıklarımavi")){
                sb_TavanKirmizi.setProgress(0);
                sb_TavanMavi.setProgress(255);
                sb_TavanYesil.setProgress(0);

                sb_YanKirmizi.setProgress(0);
                sb_YanMavi.setProgress(255);
                sb_YanYesil.setProgress(0);

                sb_IcKirmizi.setProgress(0);
                sb_IcMavi.setProgress(255);
                sb_IcYesil.setProgress(0);

                data = data + "\n";
                byte[] dataByte = data.getBytes();
                veriGonder(mbleDevice, service, characteristic, dataByte);
            }
            else if(data.equals("ışıklarımaviyap")){
                sb_TavanKirmizi.setProgress(0);
                sb_TavanMavi.setProgress(255);
                sb_TavanYesil.setProgress(0);

                sb_YanKirmizi.setProgress(0);
                sb_YanMavi.setProgress(255);
                sb_YanYesil.setProgress(0);

                sb_IcKirmizi.setProgress(0);
                sb_IcMavi.setProgress(255);
                sb_IcYesil.setProgress(0);

                data = data + "\n";
                byte[] dataByte = data.getBytes();
                veriGonder(mbleDevice, service, characteristic, dataByte);
            }
            else if(data.equals("ışıklarımaviya")){
                sb_TavanKirmizi.setProgress(0);
                sb_TavanMavi.setProgress(255);
                sb_TavanYesil.setProgress(0);

                sb_YanKirmizi.setProgress(0);
                sb_YanMavi.setProgress(255);
                sb_YanYesil.setProgress(0);

                sb_IcKirmizi.setProgress(0);
                sb_IcMavi.setProgress(255);
                sb_IcYesil.setProgress(0);

                data = data + "\n";
                byte[] dataByte = data.getBytes();
                veriGonder(mbleDevice, service, characteristic, dataByte);
            }
            else if(data.equals("ışıklarımavihap")){
                sb_TavanKirmizi.setProgress(0);
                sb_TavanMavi.setProgress(255);
                sb_TavanYesil.setProgress(0);

                sb_YanKirmizi.setProgress(0);
                sb_YanMavi.setProgress(255);
                sb_YanYesil.setProgress(0);

                sb_IcKirmizi.setProgress(0);
                sb_IcMavi.setProgress(255);
                sb_IcYesil.setProgress(0);

                data = data + "\n";
                byte[] dataByte = data.getBytes();
                veriGonder(mbleDevice, service, characteristic, dataByte);
            }

        }
    }

    private void acilistaOlacaklar() {
        //Seek Bar'ların max değerini ayarlıyoruz.
        sb_TavanKirmizi.setMax(255);
        sb_TavanYesil.setMax(255);
        sb_TavanMavi.setMax(255);

        sb_YanKirmizi.setMax(255);
        sb_YanYesil.setMax(255);
        sb_YanMavi.setMax(255);

        sb_IcKirmizi.setMax(255);
        sb_IcYesil.setMax(255);
        sb_IcMavi.setMax(255);

        int colorRed = Color.parseColor("#FF0000");
        int colorGreen = Color.parseColor("#8BC34A");
        int colorBlue = Color.parseColor("#2196F3");

        //Seek Bar'ın Arkaplan ve progress kısmındaki renk
        sb_TavanKirmizi.getProgressDrawable().setColorFilter(colorRed, PorterDuff.Mode.MULTIPLY);
        sb_YanKirmizi.getProgressDrawable().setColorFilter(colorRed, PorterDuff.Mode.MULTIPLY);
        sb_IcKirmizi.getProgressDrawable().setColorFilter(colorRed, PorterDuff.Mode.MULTIPLY);

        sb_TavanYesil.getProgressDrawable().setColorFilter(colorGreen, PorterDuff.Mode.MULTIPLY);
        sb_YanYesil.getProgressDrawable().setColorFilter(colorGreen, PorterDuff.Mode.MULTIPLY);
        sb_IcYesil.getProgressDrawable().setColorFilter(colorGreen, PorterDuff.Mode.MULTIPLY);

        sb_TavanMavi.getProgressDrawable().setColorFilter(colorBlue, PorterDuff.Mode.MULTIPLY);
        sb_YanMavi.getProgressDrawable().setColorFilter(colorBlue, PorterDuff.Mode.MULTIPLY);
        sb_IcMavi.getProgressDrawable().setColorFilter(colorBlue, PorterDuff.Mode.MULTIPLY);

        //Seek Bar'da Yuvarlaktaki Renk
        sb_TavanKirmizi.getThumb().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
        sb_YanKirmizi.getThumb().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
        sb_IcKirmizi.getThumb().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);

        sb_TavanYesil.getThumb().setColorFilter(getResources().getColor(R.color.yesil), PorterDuff.Mode.SRC_ATOP);
        sb_YanYesil.getThumb().setColorFilter(getResources().getColor(R.color.yesil), PorterDuff.Mode.SRC_ATOP);
        sb_IcYesil.getThumb().setColorFilter(getResources().getColor(R.color.yesil), PorterDuff.Mode.SRC_ATOP);

        sb_TavanMavi.getThumb().setColorFilter(getResources().getColor(R.color.mavi), PorterDuff.Mode.SRC_ATOP);
        sb_YanMavi.getThumb().setColorFilter(getResources().getColor(R.color.mavi), PorterDuff.Mode.SRC_ATOP);
        sb_IcMavi.getThumb().setColorFilter(getResources().getColor(R.color.mavi), PorterDuff.Mode.SRC_ATOP);
    }

    private void clickOlaylari() {

        //Home butonuna basınca olacaklar.
        img_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    Intent intent = new Intent(Isiklar.this, SistemSonrasi.class);
                    BleManager.getInstance().disconnect(mbleDevice);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    BleManager.getInstance().disconnect(mbleDevice);
                    finish();
                }
            }
        });

        //Tavan Işık Kapatma Butonları
        layout_TavanKapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_TavanKirmizi.setProgress(0);
                    sb_TavanYesil.setProgress(0);
                    sb_TavanMavi.setProgress(0);

                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        tv_TavanKapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_TavanKirmizi.setProgress(0);
                    sb_TavanYesil.setProgress(0);
                    sb_TavanMavi.setProgress(0);
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //Yan Işık Kapatma Butonları
        layout_YanKapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_YanKirmizi.setProgress(0);
                    sb_YanYesil.setProgress(0);
                    sb_YanMavi.setProgress(0);
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        tv_YanKapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_YanKirmizi.setProgress(0);
                    sb_YanYesil.setProgress(0);
                    sb_YanMavi.setProgress(0);
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //İç Işık Kapama Butonları
        layout_IcKapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_IcKirmizi.setProgress(0);
                    sb_IcYesil.setProgress(0);
                    sb_IcMavi.setProgress(0);
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        tv_IcKapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_IcKirmizi.setProgress(0);
                    sb_IcYesil.setProgress(0);
                    sb_IcMavi.setProgress(0);
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //Tavan Işıkları (Yuvarlak Renkli)
        img_TavanKirmizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_TavanKirmizi.setProgress(255);
                    sb_TavanYesil.setProgress(0);
                    sb_TavanMavi.setProgress(0);
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        img_TavanYesil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_TavanKirmizi.setProgress(0);
                    sb_TavanYesil.setProgress(128);
                    sb_TavanMavi.setProgress(0);
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        img_TavanMavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_TavanKirmizi.setProgress(0);
                    sb_TavanYesil.setProgress(0);
                    sb_TavanMavi.setProgress(255);
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        img_TavanBeyaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_TavanKirmizi.setProgress(255);
                    sb_TavanYesil.setProgress(255);
                    sb_TavanMavi.setProgress(255);
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        img_TavanGri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_TavanKirmizi.setProgress(128);
                    sb_TavanYesil.setProgress(128);
                    sb_TavanMavi.setProgress(128);
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //Yan Işıklar (Yuvarlak Renkli)
        img_YanKirmizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_YanKirmizi.setProgress(255);
                    sb_YanYesil.setProgress(0);
                    sb_YanMavi.setProgress(0);
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        img_YanYesil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_YanKirmizi.setProgress(0);
                    sb_YanYesil.setProgress(128);
                    sb_YanMavi.setProgress(0);
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        img_YanMavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_YanKirmizi.setProgress(0);
                    sb_YanYesil.setProgress(0);
                    sb_YanMavi.setProgress(255);
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        img_YanBeyaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_YanKirmizi.setProgress(255);
                    sb_YanYesil.setProgress(255);
                    sb_YanMavi.setProgress(255);
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        img_YanGri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_YanKirmizi.setProgress(128);
                    sb_YanYesil.setProgress(128);
                    sb_YanMavi.setProgress(128);
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //İç Işıklar (Yuvarlak Renkli)
        img_IcKirmizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_IcKirmizi.setProgress(255);
                    sb_IcYesil.setProgress(0);
                    sb_IcMavi.setProgress(0);
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        img_IcYesil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_IcKirmizi.setProgress(0);
                    sb_IcYesil.setProgress(128);
                    sb_IcMavi.setProgress(0);
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        img_IcMavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_IcKirmizi.setProgress(0);
                    sb_IcYesil.setProgress(0);
                    sb_IcMavi.setProgress(255);
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        img_IcBeyaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_IcKirmizi.setProgress(255);
                    sb_IcYesil.setProgress(255);
                    sb_IcMavi.setProgress(255);
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        img_IcGri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleManager.getInstance().isConnected(macAdress)) {
                    sb_IcKirmizi.setProgress(128);
                    sb_IcYesil.setProgress(128);
                    sb_IcMavi.setProgress(128);
                } else {
                    Intent intent = new Intent(Isiklar.this, AcilisEkrani.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //Hafıza Butonlarına baasıldığında
        img_WheelMemory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hafizaDurumu = preferences.getBoolean("HafizaDurumu1", false);
                if (hafizaDurumu) {


                    //Butona uzun basıldığında, kayıt işlemi gerçekleştikten sonra cihaza kaydedilen veriyi burda geri çağırıyoruz.
                    for (int i = 0; i < 9; i++) {
                        renkTutucu[i] = preferences.getInt("TavanRenkleri1" + i, 0);
                    }

                    sb_TavanKirmizi.setProgress(renkTutucu[0]);
                    sb_TavanYesil.setProgress(renkTutucu[1]);
                    sb_TavanMavi.setProgress(renkTutucu[2]);

                    sb_YanKirmizi.setProgress(renkTutucu[3]);
                    sb_YanYesil.setProgress(renkTutucu[4]);
                    sb_YanMavi.setProgress(renkTutucu[5]);

                    sb_IcKirmizi.setProgress(renkTutucu[6]);
                    sb_IcYesil.setProgress(renkTutucu[7]);
                    sb_IcMavi.setProgress(renkTutucu[8]);
                } else {
                    showToastShort("Slot Boş");
                }
            }
        });

        img_WheelMemory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hafizaDurumu2 = preferences.getBoolean("HafizaDurumu2", false);
                if (hafizaDurumu2) {
                    //Butona uzun basıldığında, kayıt işlemi gerçekleştikten sonra cihaza kaydedilen veriyi burda geri çağırıyoruz.
                    for (int i = 0; i < 9; i++) {
                        renkTutucu2[i] = preferences.getInt("TavanRenkleri2" + i, 0);
                    }
                    sb_TavanKirmizi.setProgress(renkTutucu2[0]);
                    sb_TavanYesil.setProgress(renkTutucu2[1]);
                    sb_TavanMavi.setProgress(renkTutucu2[2]);

                    sb_YanKirmizi.setProgress(renkTutucu2[3]);
                    sb_YanYesil.setProgress(renkTutucu2[4]);
                    sb_YanMavi.setProgress(renkTutucu2[5]);

                    sb_IcKirmizi.setProgress(renkTutucu2[6]);
                    sb_IcYesil.setProgress(renkTutucu2[7]);
                    sb_IcMavi.setProgress(renkTutucu2[8]);
                } else {
                    showToastShort("Slot Boş");
                }
            }
        });

        img_WheelMemory3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hafizaDurumu3 = preferences.getBoolean("HafizaDurumu3", false);
                if (hafizaDurumu3) {
                    for (int i = 0; i < 9; i++) {
                        //Butona uzun basıldığında, kayıt işlemi gerçekleştikten sonra cihaza kaydedilen veriyi burda geri çağırıyoruz.
                        renkTutucu3[i] = preferences.getInt("TavanRenkleri3" + i, 0);
                    }
                    sb_TavanKirmizi.setProgress(renkTutucu3[0]);
                    sb_TavanYesil.setProgress(renkTutucu3[1]);
                    sb_TavanMavi.setProgress(renkTutucu3[2]);

                    sb_YanKirmizi.setProgress(renkTutucu3[3]);
                    sb_YanYesil.setProgress(renkTutucu3[4]);
                    sb_YanMavi.setProgress(renkTutucu3[5]);

                    sb_IcKirmizi.setProgress(renkTutucu3[6]);
                    sb_IcYesil.setProgress(renkTutucu3[7]);
                    sb_IcMavi.setProgress(renkTutucu3[8]);
                } else {
                    showToastShort("Slot Boş");
                }
            }
        });

        //Hafıza butonlarına uzun basıldığında
        img_WheelMemory1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Verileri kaydediyoruz.
                renkTutucu[0] = sb_TavanKirmizi.getProgress();
                renkTutucu[1] = sb_TavanYesil.getProgress();
                renkTutucu[2] = sb_TavanMavi.getProgress();

                renkTutucu[3] = sb_YanKirmizi.getProgress();
                renkTutucu[4] = sb_YanYesil.getProgress();
                renkTutucu[5] = sb_YanMavi.getProgress();

                renkTutucu[6] = sb_IcKirmizi.getProgress();
                ;
                renkTutucu[7] = sb_IcYesil.getProgress();
                renkTutucu[8] = sb_IcMavi.getProgress();
                ;

                //Daha önceden kayıt olup olmadığını konrol edip ekrana yazdırmak için.
                hafizaDurumu = true;

                //Verileri bir anahtar kelime ile Cihaza kaydediyoruz. Böylece uygulama kapanıp açıldıktan sonra bu veriler çağırıldığında geri gelecek.
                editor = preferences.edit();
                for (int i = 0; i < 9; i++) {
                    editor.putInt("TavanRenkleri1" + i, renkTutucu[i]);
                }
                editor.putBoolean("HafizaDurumu1", true);
                editor.apply();

                showToastShort("Kayıt Alındı");

                return false;
            }
        });

        img_WheelMemory2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Verileri kaydediyoruz.
                renkTutucu2[0] = sb_TavanKirmizi.getProgress();
                renkTutucu2[1] = sb_TavanYesil.getProgress();
                renkTutucu2[2] = sb_TavanMavi.getProgress();

                renkTutucu2[3] = sb_YanKirmizi.getProgress();
                renkTutucu2[4] = sb_YanYesil.getProgress();
                renkTutucu2[5] = sb_YanMavi.getProgress();

                renkTutucu2[6] = sb_IcKirmizi.getProgress();
                renkTutucu2[7] = sb_IcYesil.getProgress();
                renkTutucu2[8] = sb_IcMavi.getProgress();

                //Daha önceden kayıt olup olmadığını konrol edip ekrana yazdırmak için.
                hafizaDurumu2 = true;

                //Verileri bir anahtar kelime ile Cihaza kaydediyoruz. Böylece uygulama kapanıp açıldıktan sonra bu veriler çağırıldığında geri gelecek.
                editor = preferences.edit();
                for (int i = 0; i < 9; i++) {
                    editor.putInt("TavanRenkleri2" + i, renkTutucu2[i]);
                }
                editor.putBoolean("HafizaDurumu2", true);
                editor.apply();

                showToastShort("Kayıt Alındı");

                return false;
            }
        });

        img_WheelMemory3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Verileri kaydediyoruz.
                renkTutucu3[0] = sb_TavanKirmizi.getProgress();
                renkTutucu3[1] = sb_TavanYesil.getProgress();
                renkTutucu3[2] = sb_TavanMavi.getProgress();

                renkTutucu3[3] = sb_YanKirmizi.getProgress();
                renkTutucu3[4] = sb_YanYesil.getProgress();
                renkTutucu3[5] = sb_YanMavi.getProgress();

                renkTutucu3[6] = sb_IcKirmizi.getProgress();
                renkTutucu3[7] = sb_IcYesil.getProgress();
                renkTutucu3[8] = sb_IcMavi.getProgress();

                //Daha önceden kayıt olup olmadığını konrol edip ekrana yazdırmak için.
                hafizaDurumu3 = true;

                //Verileri bir anahtar kelime ile Cihaza kaydediyoruz. Böylece uygulama kapanıp açıldıktan sonra bu veriler çağırıldığında geri gelecek.
                editor = preferences.edit();
                for (int i = 0; i < 9; i++) {
                    editor.putInt("TavanRenkleri3" + i, renkTutucu3[i]);
                }
                editor.putBoolean("HafizaDurumu3", true);
                editor.apply();

                showToastShort("Kayıt Alındı");

                return false;
            }
        });

        //Okuma Lambaları Butonları
        layout_SolOkumaLambasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //İşlemler
            }
        });

        tv_SolOkumaLambasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //İşlemler
            }
        });

        layout_SagOkumaLambasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //İşlemler
            }
        });

        tv_SagOkumaLambasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //İşlemler
            }
        });

        //Seek Bar'larda Değişiklik
        sb_TavanKirmizi.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                byte[] data;
                String dataString = String.valueOf(progress);
                data = dataString.getBytes();
                veriGonder(mbleDevice, service, characteristic, data);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_TavanYesil.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                byte[] data;
                String dataString = String.valueOf(progress);
                data = dataString.getBytes();
                veriGonder(mbleDevice, service, characteristic, data);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_TavanMavi.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                byte[] data;
                String dataString = String.valueOf(progress);
                data = dataString.getBytes();
                veriGonder(mbleDevice, service, characteristic, data);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_YanKirmizi.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                byte[] data;
                String dataString = String.valueOf(progress);
                data = dataString.getBytes();
                veriGonder(mbleDevice, service, characteristic, data);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_YanYesil.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                byte[] data;
                String dataString = String.valueOf(progress);
                data = dataString.getBytes();
                veriGonder(mbleDevice, service, characteristic, data);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_YanMavi.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                byte[] data;
                String dataString = String.valueOf(progress);
                data = dataString.getBytes();
                veriGonder(mbleDevice, service, characteristic, data);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_IcKirmizi.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                byte[] data;
                String dataString = String.valueOf(progress);
                data = dataString.getBytes();
                veriGonder(mbleDevice, service, characteristic, data);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_IcYesil.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                byte[] data;
                String dataString = String.valueOf(progress);
                data = dataString.getBytes();
                veriGonder(mbleDevice, service, characteristic, data);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_IcMavi.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                byte[] data;
                String dataString = String.valueOf(progress);
                data = dataString.getBytes();
                veriGonder(mbleDevice, service, characteristic, data);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //

    }

    //Bluetooth cihazına bağlanma.
    private void baglan(){
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

                sesliKomutYakalama();

            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
                Log.v("Cihaz", "Bağlantı kesildi.");
                sayacBaglanti = 0;
            }
        });
    }

    //Toast mesajı gösterme (Kısa).
    private void showToastShort(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void init() {
        img_Home = (ImageView) findViewById(R.id.isiklar_imgHome);
        img_TavanKirmizi = (ImageView) findViewById(R.id.isiklar_imgTavanKirmizi);
        img_TavanYesil = (ImageView) findViewById(R.id.isiklar_imgTavanYesil);
        img_TavanMavi = (ImageView) findViewById(R.id.isiklar_imgTavanMavi);
        img_TavanBeyaz = (ImageView) findViewById(R.id.isiklar_imgTavanGriv2);
        img_TavanGri = (ImageView) findViewById(R.id.isiklar_imgTavanGri);
        img_YanKirmizi = (ImageView) findViewById(R.id.isiklar_imgYanKirmizi);
        img_YanYesil = (ImageView) findViewById(R.id.isiklar_imgYanYesil);
        img_YanMavi = (ImageView) findViewById(R.id.isiklar_imgYanMavi);
        img_YanBeyaz = (ImageView) findViewById(R.id.isiklar_imgYanGriv2);
        img_YanGri = (ImageView) findViewById(R.id.isiklar_imgYanGri);
        img_IcKirmizi = (ImageView) findViewById(R.id.isiklar_imgIcKirmizi);
        img_IcYesil = (ImageView) findViewById(R.id.isiklar_imgIcYesil);
        img_IcMavi = (ImageView) findViewById(R.id.isiklar_imgIcMavi);
        img_IcBeyaz = (ImageView) findViewById(R.id.isiklar_imgIcGriv2);
        img_IcGri = (ImageView) findViewById(R.id.isiklar_imgIcGri);
        img_WheelMemory1 = (ImageView) findViewById(R.id.isiklar_imgWheelMemory1);
        img_WheelMemory2 = (ImageView) findViewById(R.id.isiklar_imgWheelMemory2);
        img_WheelMemory3 = (ImageView) findViewById(R.id.isiklar_imgWheelMemory3);

        sb_TavanKirmizi = (SeekBar) findViewById(R.id.isiklar_sbTavanKirmizi);
        sb_TavanYesil = (SeekBar) findViewById(R.id.isiklar_sbTavanYesil);
        sb_TavanMavi = (SeekBar) findViewById(R.id.isiklar_sbTavanMavi);

        sb_YanKirmizi = (SeekBar) findViewById(R.id.isiklar_sbYanKirmizi);
        sb_YanYesil = (SeekBar) findViewById(R.id.isiklar_sbYanYesil);
        sb_YanMavi = (SeekBar) findViewById(R.id.isiklar_sbYanMavi);

        sb_IcKirmizi = (SeekBar) findViewById(R.id.isiklar_sbIcKirmizi);
        sb_IcYesil = (SeekBar) findViewById(R.id.isiklar_sbIcYesil);
        sb_IcMavi = (SeekBar) findViewById(R.id.isiklar_sbIcMavi);

        layout_TavanKapat = (RelativeLayout) findViewById(R.id.isiklar_layoutTavanKapat);
        layout_YanKapat = (RelativeLayout) findViewById(R.id.isiklar_layoutYanKapat);
        layout_IcKapat = (RelativeLayout) findViewById(R.id.isiklar_layoutIcKapat);
        layout_SolOkumaLambasi = (RelativeLayout) findViewById(R.id.isiklar_layoutSolOkumaLambasi);
        layout_SagOkumaLambasi = (RelativeLayout) findViewById(R.id.isiklar_layoutSagOkumaLambasi);

        tv_TavanKapat = (TextView) findViewById(R.id.isiklar_tvTavanKapat);
        tv_YanKapat = (TextView) findViewById(R.id.isiklar_tvYanKapat);
        tv_IcKapat = (TextView) findViewById(R.id.isiklar_tvIcKapat);
        tv_SolOkumaLambasi = (TextView) findViewById(R.id.isiklar_tvSolOkumaLambasi);
        tv_SagOkumaLambasi = (TextView) findViewById(R.id.isiklar_tvSagOkumaLambasi);

        preferences = this.getSharedPreferences("com.hansheaven.bleproject", Context.MODE_PRIVATE);

    }

    //Veri gönderdiğimiz metot.
    private void veriGonder(BleDevice bleDev, String ser, String charac, byte[] data) {
        BleManager.getInstance().write(
                bleDev,
                ser,
                charac,
                data,
                new BleWriteCallback() {
                    @Override
                    public void onWriteSuccess(int current, int total, byte[] justWrite) {
                        Log.v("Cihaz", "Yazma işlemi başarılı.");
                    }

                    @Override
                    public void onWriteFailure(BleException exception) {
                        Log.v("Cihaz", "Yazma işlemi başarısız. " + String.valueOf(exception));
                    }
                });
    }

    //Toast mesajı gösterme (Kısa).
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BleManager.getInstance().disconnect(mbleDevice);
        finish();
    }
}
