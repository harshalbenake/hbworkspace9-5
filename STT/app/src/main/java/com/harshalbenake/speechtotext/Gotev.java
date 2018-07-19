package com.harshalbenake.speechtotext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.gotev.speech.GoogleVoiceTypingDisabledException;
import net.gotev.speech.Speech;
import net.gotev.speech.SpeechDelegate;
import net.gotev.speech.SpeechRecognitionNotAvailable;

import java.util.List;

import android.Manifest;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import net.gotev.speech.SpeechUtil;
import net.gotev.speech.TextToSpeechCallback;
import net.gotev.speech.ui.SpeechProgressView;

import com.tbruyelle.rxpermissions.RxPermissions;

public class Gotev extends AppCompatActivity implements SpeechDelegate {

    private Button btn_speak;
    private TextView text;
    private SpeechProgressView progress;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gotev);
        Speech.init(this, getPackageName());

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        btn_speak = (Button) findViewById(R.id.btn_speak);
        btn_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick();
            }
        });

        text = (TextView) findViewById(R.id.text);
        progress = (SpeechProgressView) findViewById(R.id.progress);

        int[] colors = {
                ContextCompat.getColor(this, android.R.color.black),
                ContextCompat.getColor(this, android.R.color.darker_gray),
                ContextCompat.getColor(this, android.R.color.black),
                ContextCompat.getColor(this, android.R.color.holo_orange_dark),
                ContextCompat.getColor(this, android.R.color.holo_red_dark)
        };
        progress.setColors(colors);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Speech.getInstance().shutdown();
    }

    private void onButtonClick() {
        if (Speech.getInstance().isListening()) {
            Speech.getInstance().stopListening();
        } else {
            RxPermissions.getInstance(this)
                    .request(Manifest.permission.RECORD_AUDIO);
                   /* .subscribe(granted -> {
                        if (granted) { // Always true pre-M
                            onRecordAudioPermissionGranted();
                        } else {
                            Toast.makeText(gotev.this, "You need to grant the permission to use the microphone", Toast.LENGTH_LONG);
                        }
                    });*/
            onRecordAudioPermissionGranted();
        }
    }

    private void onRecordAudioPermissionGranted() {
        btn_speak.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);

        try {
            Speech.getInstance().stopTextToSpeech();
            Speech.getInstance().startListening(progress, Gotev.this);

        } catch (SpeechRecognitionNotAvailable exc) {
            showSpeechNotSupportedDialog();

        } catch (GoogleVoiceTypingDisabledException exc) {
            showEnableGoogleVoiceTyping();
        }
    }

    @Override
    public void onStartOfSpeech() {
    }

    @Override
    public void onSpeechRmsChanged(float value) {
        System.out.println("Speech recognition rms is now");
    }

    @Override
    public void onSpeechResult(String result) {
        btn_speak.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);
        text.setText(result);
        if (result.equalsIgnoreCase("") || result == null) {
            Speech.getInstance().say("repeat");

        } else {
            Speech.getInstance().say(result);
        }
    }

    @Override
    public void onSpeechPartialResults(List<String> results) {
        text.setText("");
        for (String partial : results) {
            text.append(partial + " ");
        }
    }

    private void showSpeechNotSupportedDialog() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        SpeechUtil.redirectUserToGoogleAppOnPlayStore(Gotev.this);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("speech_not_available")
                .setCancelable(false)
                .setPositiveButton("yes", dialogClickListener)
                .setNegativeButton("no", dialogClickListener)
                .show();
    }

    private void showEnableGoogleVoiceTyping() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("enable_google_voice_typing")
                .setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // do nothing
                    }
                })
                .show();
    }

}