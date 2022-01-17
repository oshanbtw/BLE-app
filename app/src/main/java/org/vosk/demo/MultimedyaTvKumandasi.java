package org.vosk.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothGatt;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;

public class MultimedyaTvKumandasi extends AppCompatActivity {

    BleDevice mbleDevice;
    String macAdress = "90:9A:77:2B:63:C9", service = "0000ffe0-0000-1000-8000-00805f9b34fb", characteristic = "0000ffe1-0000-1000-8000-00805f9b34fb";
    int sayacBaglanti = 0, sayacBaglantiTekrar = 0;

    ImageView img_Home, img_Back, img_Power;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multimedya_tv_kumandasi);

        BleManager.getInstance().init(getApplication()); //Bluetooth bağlantısı için gerekli.

        init();
        cliclOlaylari();
    }

    private void cliclOlaylari() {
        //
        img_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sayacBaglantiTekrar != 0) {
                    if (BleManager.getInstance().isConnected(macAdress)) {
                        Intent intent = new Intent(MultimedyaTvKumandasi.this, SistemSonrasi.class);
                        BleManager.getInstance().disconnect(mbleDevice);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(MultimedyaTvKumandasi.this, AcilisEkrani.class);
                        startActivity(intent);
                        finish();
                    }
                } else{
                    Intent intent = new Intent(MultimedyaTvKumandasi.this, SistemSonrasi.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sayacBaglantiTekrar != 0) {
                    if (BleManager.getInstance().isConnected(macAdress)) {
                        Intent intent = new Intent(MultimedyaTvKumandasi.this, Multimedya.class);
                        BleManager.getInstance().disconnect(mbleDevice);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(MultimedyaTvKumandasi.this, AcilisEkrani.class);
                        startActivity(intent);
                        finish();
                    }
                } else{
                    Intent intent = new Intent(MultimedyaTvKumandasi.this, Multimedya.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        img_Power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sayacBaglantiTekrar == 0) {
                    sayacBaglantiTekrar++;
                    baglan();
                }
            }
        });

    }

    private void init() {
        img_Home = (ImageView) findViewById(R.id.tvkumandasi_imgHome);
        img_Back = (ImageView) findViewById(R.id.tvkumandasi_imgBack);
        img_Power = (ImageView) findViewById(R.id.tvkumandasi_imgPower);
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