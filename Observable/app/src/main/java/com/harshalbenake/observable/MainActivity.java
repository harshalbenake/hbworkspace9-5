package com.harshalbenake.observable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private CustomObservable customObservable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create CustomObservable
        customObservable = new CustomObservable();
        // Set customObservable default value to Dummy Data
        customObservable.setValue("Dummy Data");
        // Create frag1
        MainFragment mainFragment = new MainFragment();
        Bundle args = new Bundle();
        // Put customObservable (That why CustomObservable implements Serializable)
        args.putSerializable(MainFragment.PARAM, customObservable);
        mainFragment.setArguments(args);
        // Add MainFragment on screen
        getFragmentManager().beginTransaction().add(R.id.ll_container, mainFragment).commit();
        final EditText edittext = (EditText) findViewById(R.id.edittext);
        edittext.setText(customObservable.getValue());
        // Add a button to change value of a dynamically
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set a new value in a customObservable
                customObservable.setValue(edittext.getText().toString());
            }
        });
    }
}
