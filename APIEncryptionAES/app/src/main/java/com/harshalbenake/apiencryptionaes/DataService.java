package com.harshalbenake.apiencryptionaes;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Data Service AsyncTask
 */
public class DataService extends AsyncTask<String, String, HashMap<String, String>> {
    MainActivity mContext;
    private ProgressDialog mProgressDialog;

    public DataService(MainActivity context) {
            this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (NetworkUtility.isOnline(mContext)) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        } else {
            Toast.makeText(mContext, "Not connected to Internet", Toast.LENGTH_SHORT).show();
            cancel(true);
        }
    }

    @Override
    protected HashMap<String, String> doInBackground(String... params) {
        HashMap<String, String> hashMap=null;
        if (!isCancelled()) {
            try {
                String strUrl ="xxxURLxxx";
                String strJsonData="xxxJsonDataxxx";
                 hashMap = OKHTTPService.requestACallToServer(strUrl,strJsonData.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return hashMap;
    }

    @Override
    protected void onPostExecute(HashMap<String, String> resultMap) {
        super.onPostExecute(resultMap);
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing() == true) {
                mProgressDialog.dismiss();
            }
            if(resultMap!=null && resultMap.size()>0){
                mContext.mtv_url.setText(OKHTTPService.mStrUrl+": "+resultMap.get(OKHTTPService.mStrUrl)+"\n");
                mContext.mtv_requestjson.setText(OKHTTPService.mStrRequestJson+": "+resultMap.get(OKHTTPService.mStrRequestJson)+"\n");
                mContext.mtv_requestencoded.setText(OKHTTPService.mStrRequestEncoded+": "+resultMap.get(OKHTTPService.mStrRequestEncoded)+"\n");
                mContext.mtv_responsencoded.setText(OKHTTPService.mStrResponseEncoded+": "+resultMap.get(OKHTTPService.mStrResponseEncoded)+"\n");
                mContext.mtv_responsejson.setText(OKHTTPService.mStrResponseJson+": "+resultMap.get(OKHTTPService.mStrResponseJson)+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
