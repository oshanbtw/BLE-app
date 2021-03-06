// Copyright 2019 Alpha Cephei Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.vosk.demo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;
import org.vosk.android.RecognitionListener;
import org.vosk.android.SpeechService;
import org.vosk.android.SpeechStreamService;
import org.vosk.android.StorageService;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class VoskActivity extends Activity implements
        RecognitionListener {

    static private final int STATE_START = 0;
    static private final int STATE_READY = 1;
    static private final int STATE_DONE = 2;
    static private final int STATE_FILE = 3;
    static private final int STATE_MIC = 4;

    /* Used to handle permission request */
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    private Model model;
    private SpeechService speechService;
    private SpeechStreamService speechStreamService;
    private TextView resultView;
    String finalHypothesis;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.main);

        //
        resultView = findViewById(R.id.result_text);
        setUiState(STATE_START);

        //Butonlara bas??l??nca olacaklar
        findViewById(R.id.recognize_mic).setOnClickListener(view -> recognizeMicrophone());
        ((ToggleButton) findViewById(R.id.pause)).setOnCheckedChangeListener((view, isChecked) -> pause(isChecked));

        // Mikrofon kayd?? izni istiyor.
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
        } else {
            initModel();
        }
    }

    //Dil paketini par??al??yor.
    private void initModel() {
        StorageService.unpack(this, "model-en-us", "model",
                (model) -> {
                    this.model = model;
                    setUiState(STATE_READY);
                },
                (exception) -> setErrorState("Failed to unpack the model" + exception.getMessage()));
    }


    //Mikrofon kayd?? izninin verilip verilmedi??ine bak??yor.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_RECORD_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Recognizer initialization is a time-consuming and it involves IO,
                // so we execute it in async task
                initModel();
            } else {
                finish();
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (speechService != null) {
            speechService.stop();
            speechService.shutdown();
        }

        if (speechStreamService != null) {
            speechStreamService.stop();
        }
    }

    //Anla????lan son kelime ya da c??mle
    @Override
    public void onResult(String hypothesis) {
        resultView.append(hypothesis + "\n");

        //Gelen verileri temizliyoruz.
        hypothesis = hypothesis.replace("text", "");
        hypothesis = hypothesis.replace("\"", "");
        hypothesis = hypothesis.replace("\n", "");
        hypothesis = hypothesis.replace(" ", "");
        hypothesis = hypothesis.replace("{", "");
        hypothesis = hypothesis.replace("}", "");
        hypothesis = hypothesis.replace(":", "");

        //Final Result'ta komutun temizlenmi?? halini yazd??rmak i??in bo?? bir string'e aktar??yoruz.
        finalHypothesis = hypothesis;

        //Gelen sesleri ay??klad??ktan sonra gelen komutlar, bizim istedi??imiz komutlarda e??le??iyorsa burdan di??er sayfalara veri g??nderiyoruz.
        if (hypothesis.equals("??????klar??k??rm??z??yap")) {
            sesliKomutSonrasi(hypothesis);
        } else if (hypothesis.equals("??????klar??k??rm??z??ya")) {
            sesliKomutSonrasi(hypothesis);
        } else if (hypothesis.equals("??????klar??k??rm??z??yada")) {
            sesliKomutSonrasi(hypothesis);
        } else if (hypothesis.equals("??????klar??ye??ilyap")) {
            sesliKomutSonrasi(hypothesis);
        } else if (hypothesis.equals("??????klar??ye??ilya")) {
            sesliKomutSonrasi(hypothesis);
        } else if (hypothesis.equals("??????klar??ye??il")) {
            sesliKomutSonrasi(hypothesis);
        } else if (hypothesis.equals("??????klar??mavi")) {
            sesliKomutSonrasi(hypothesis);
        } else if (hypothesis.equals("??????klar??maviyap")) {
            sesliKomutSonrasi(hypothesis);
        } else if (hypothesis.equals("??????klar??maviya")) {
            sesliKomutSonrasi(hypothesis);
        } else if (hypothesis.equals("??????klar??mavihap")) {
            sesliKomutSonrasi(hypothesis);
        }

    }

    //Son duyulan ses. Genelde bo?? oluyor.
    @Override
    public void onFinalResult(String hypothesis) {
        resultView.setText(finalHypothesis);
        setUiState(STATE_DONE);
        if (speechStreamService != null) {
            speechStreamService = null;
        }
    }

    //S??rekli
    @Override
    public void onPartialResult(String hypothesis) {
        resultView.append(hypothesis + "\n");
    }

    @Override
    public void onError(Exception e) {
        setErrorState(e.getMessage());
    }

    @Override
    public void onTimeout() {
        setUiState(STATE_DONE);
    }

    //Butonlar??n ve metnin, mikrofonun haz??r olup olmamas??na g??re yada a????k veya kapal?? olmas??na g??re aktif ve deaktif etme.
    private void setUiState(int state) {
        switch (state) {
            case STATE_START:
                resultView.setText(R.string.preparing);
                resultView.setMovementMethod(new ScrollingMovementMethod());
                findViewById(R.id.recognize_mic).setEnabled(false);
                findViewById(R.id.pause).setEnabled((false));
                break;
            case STATE_READY:
                resultView.setText(R.string.ready);
                ((Button) findViewById(R.id.recognize_mic)).setText(R.string.recognize_microphone);
                findViewById(R.id.recognize_mic).setEnabled(true);
                findViewById(R.id.pause).setEnabled((false));
                break;
            case STATE_DONE:
                ((Button) findViewById(R.id.recognize_mic)).setText(R.string.recognize_microphone);
                findViewById(R.id.recognize_mic).setEnabled(true);
                findViewById(R.id.pause).setEnabled((false));
                break;
            case STATE_FILE:
                resultView.setText(getString(R.string.starting));
                findViewById(R.id.recognize_mic).setEnabled(false);
                findViewById(R.id.pause).setEnabled((false));
                break;
            case STATE_MIC:
                ((Button) findViewById(R.id.recognize_mic)).setText(R.string.stop_microphone);
                resultView.setText(getString(R.string.say_something));
                findViewById(R.id.recognize_mic).setEnabled(true);
                findViewById(R.id.pause).setEnabled((true));
                break;
            default:
        }
    }

    //Hata mesaj??
    private void setErrorState(String message) {
        resultView.setText(message);
        ((Button) findViewById(R.id.recognize_mic)).setText(R.string.recognize_microphone);
        findViewById(R.id.recognize_mic).setEnabled(false);
    }

    //Mikrofonu butonuuna bas??nca olacaklar.
    private void recognizeMicrophone() {
        if (speechService != null) {
            setUiState(STATE_DONE);
            speechService.stop();
            speechService = null;
        } else {
            setUiState(STATE_MIC);
            try {
                Recognizer rec = new Recognizer(model, 16000.0f);
                speechService = new SpeechService(rec, 16000.0f);
                speechService.startListening(this);
            } catch (IOException e) {
                setErrorState(e.getMessage());
            }
        }
    }

    //Pause tu??una bas??nca olacaklar.
    private void pause(boolean checked) {
        if (speechService != null) {
            speechService.setPause(checked);
        }
    }

    private void sesliKomutSonrasi(String hypothesis) {
        recognizeMicrophone();
        Intent intent = new Intent(VoskActivity.this, Isiklar.class);
        intent.putExtra("fulRenkValue", hypothesis);
        startActivity(intent);
        finish();
    }

}
