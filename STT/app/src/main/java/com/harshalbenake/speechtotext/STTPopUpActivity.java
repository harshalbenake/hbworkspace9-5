package com.harshalbenake.speechtotext;

import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class STTPopUpActivity extends AppCompatActivity {

    private TextView mtv_words;
    String mStrWords = "";
    StringBuilder mStringBuilder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stt_popup_activity);
        mtv_words = (TextView) findViewById(R.id.tv_words);
        Button btn_clear = (Button) findViewById(R.id.btn_clear);
        Button button = (Button) findViewById(R.id.btn_speak);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Enter TrackParameter");
                startActivityForResult(i, 1);

            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStringBuilder = new StringBuilder();
                mStrWords = mStringBuilder.append("").toString();
                mtv_words.setText(mStrWords);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result.contains("Blood Pressure")) {
                Toast.makeText(getApplicationContext(), "Blood Pressure", Toast.LENGTH_LONG).show();
            } else if (result.contains("Blood")) {
                Toast.makeText(getApplicationContext(), "Blood", Toast.LENGTH_LONG).show();
            } else if (result.contains("BP")) {
                Toast.makeText(getApplicationContext(), "BP", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), result + "", Toast.LENGTH_LONG).show();
            }

            float[] confidence = data.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);

            String score = "";
            for (int i = 0; i < confidence.length; i++) {
                score =  confidence[i] +" , " +score;
            }
            mStrWords = mStringBuilder.append(result + " " + score+ "\n").toString();
            mtv_words.setText(mStrWords);
        }

    }

}
