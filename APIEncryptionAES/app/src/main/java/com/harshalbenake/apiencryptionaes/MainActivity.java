package com.harshalbenake.apiencryptionaes;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView mtv_url,mtv_requestjson,mtv_requestencoded,mtv_responsencoded,mtv_responsejson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();
    }

    /**
     * init Layout
     */
    private void initLayout() {
        mtv_url = (TextView) findViewById(R.id.tv_url);
        mtv_requestjson = (TextView) findViewById(R.id.tv_requestjson);
        mtv_requestencoded = (TextView) findViewById(R.id.tv_requestencoded);
        mtv_responsencoded = (TextView) findViewById(R.id.tv_responsencoded);
        mtv_responsejson = (TextView) findViewById(R.id.tv_responsejson);

        asyncFamilyProfile();
    }

    /**
     * async Family Profile
     */
    private void asyncFamilyProfile() {
        FamilyProfileService familyProfileService = new FamilyProfileService(MainActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            familyProfileService.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            familyProfileService.execute();
        }
    }


}
