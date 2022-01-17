package org.vosk.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothGatt;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;

import java.util.concurrent.TimeUnit;

public class AcilisEkrani extends AppCompatActivity {

    String macAdress = "90:9A:77:2B:63:C9";

    public BleDevice mbleDevice;
    private LinearLayout btn_layoutTekrarDene;
    private TextView tv_tekrarDene;

    int sayacBaglanti = 0, sayacTekrarDene = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acilis_ekrani);//Sonradan Değiştirmeyi Unutma

        init();
        bleKontrol();
        ClickOlaylari();
    }

    private void ClickOlaylari() {
        btn_layoutTekrarDene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayacTekrarDene++;
                bleKontrol();
            }
        });
        tv_tekrarDene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayacTekrarDene++;
                bleKontrol();
            }
        });
    }

    public void bleKontrol() {
        //Bluetooth'un açık olup olmadığı kontrol ediliyor. Kapalıysa açıyor.
        if (!BleManager.getInstance().isBlueEnable()) BleManager.getInstance().enableBluetooth();

        //Bluetooth açılsın diye 1 saniye bekletiyorum.
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Belirtilen mac adresine bağlanma.
        if (!BleManager.getInstance().isConnected(macAdress)) {
            BleManager.getInstance().connect(macAdress, new BleGattCallback() {
                @Override
                public void onStartConnect() {
                    Log.v("Cihaz", String.valueOf(BleManager.getInstance().isConnected(macAdress)));
                    Log.v("Cihaz", "Bağlantı isteği başladı.");
                    if (sayacTekrarDene > 0) {
                        showToast("Tekrar Deneniyor");
                    }
                }

                @Override
                public void onConnectFail(BleDevice bleDevice, BleException exception) {
                    Log.v("Cihaz", "Bağlantı başarısız.");
                    showToast("Bağlantı Başarısız.");
                    sayacBaglanti = 0;
                    sayacTekrarDene++;
                    Log.v("Cihaz", String.valueOf(exception));
                }

                @Override
                public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                    Log.v("Cihaz", "Bağlantı başarılı. Bağlandı.");
                    mbleDevice = bleDevice;
                    sayacBaglanti++;
                    sayacTekrarDene = 0;
                    BleManager.getInstance().disconnect(mbleDevice);
                    Intent intent = new Intent(AcilisEkrani.this, SistemOncesi.class);
                    startActivity(intent);

                }

                @Override
                public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
                    Log.v("Cihaz", "Bağlantı kesildi.");
                    sayacBaglanti = 0;
                }
            });
        } else {
            Log.v("Cihaz", "Belirtilen cihaza bağlı.");
            Intent intent = new Intent(AcilisEkrani.this, SistemOncesi.class);
            startActivity(intent);
            finish();
        }
    }

    private void init() {
        BleManager.getInstance().init(getApplication());

        btn_layoutTekrarDene = (LinearLayout) findViewById(R.id.main_layoutTekrarDene);

        tv_tekrarDene = (TextView) findViewById(R.id.main_tvTekrarDene);
    }

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
