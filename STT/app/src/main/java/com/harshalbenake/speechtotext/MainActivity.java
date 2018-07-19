package com.harshalbenake.speechtotext;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Button btn_withpopup = (Button) findViewById(R.id.btn_withpopup);
        Button btn_withoutpopup = (Button) findViewById(R.id.btn_withoutpopup);
        Button btn_usinggotev = (Button) findViewById(R.id.btn_usinggotev);

        btn_withpopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,STTPopUpActivity.class));
            }
        });

        btn_withoutpopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,VoiceRecognitionActivity.class));
            }
        });

        btn_usinggotev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Gotev.class));
            }
        });
    }

}
