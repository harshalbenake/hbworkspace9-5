package com.harshalbenake.apiencryptionaes;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Family Profile Service AsyncTask
 */
public class FamilyProfileService extends AsyncTask<String, String, HashMap<String, String>> {
    MainActivity mContext;
    private ProgressDialog mProgressDialog;

    public FamilyProfileService(MainActivity context) {
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
                String strUrl ="https://core.allizhealth.com/PHR/api/Person/ListRelatives";
                String strJsonData="{\"Header\":{\"RequestID\":\"79a8af1c-a8b3-47b6-9a7a-8998c5faba5b\",\"DateTime\":\"03-Jul-2018\",\"ApplicationCode\":\"AIH_HRA_PORTAL\",\"AuthTicket\":\"YXe3kMq25XVqhUWcN4nRjaT7OcAvyxJ11tuoG8xA+m7kyDwZBW0NQrKhPAWhf6E1r\\/fT9NuCTAVwzlHafq1onFO2RV0nPxcP9gJapNoNGCUqzlLJLNJJnf\\/hrST9KbykdE\\/oUpbXWpYIZkpfjL6KX4iSGA9mRgJEbWqai6wMoTQVGAoB0oZ\\/ABdrUvpVk4\\/P1Mu6+IEewYjrxhTuDRZ3NT1IS8aW+Ffk8B70QyrsorKwjrsqbPonN19\\/eU2xIFHWVeUqX4xTl5c9+wWRCyovI95\\/9OtB7eYnTAEKALLdXBrFp347tGAr2onMbUQ+wuuiq4P3IldBSV0bKPQcV9CdoDCOCJWCxkuE4jsHtjwBtztV5SpfjFOXlz37BZELKi8j3n\\/060Ht5idMAQoAst1cGsWnfju0YCvaicxtRD7C66Krg\\/ciV0FJXRso9BxX0J2gR+QZFTTQQ+30GyU2F18HrMcaRhLLU6hpqD3Df2Tgs8Wlte+Q9LJUIf6rH5X9vogl5LDcjJQRxWXh8FGSys3MQdQfR2jt0DI8d0DwGq9mlWbbFpdnXYFxsdN9mQgd5a2v9BslNhdfB6zHGkYSy1Ooaag9w39k4LPFpbXvkPSyVCH+qx+V\\/b6IJeSw3IyUEcVl4fBRksrNzEFxwWbkRCJlgH7H09lRccxajf\\/5WKDRoXutiuUI043iyip9307kdgKXDCtm+Deiz+GexBt5iapMbMKTUbhVhvO390LjQ5l6tO5HrqBhjZ\\/PkcCi5n8\\/dh7CNwUBaYIoxVdVdX\\/AaJsbXnjYWHuYf8QKBfVb8btN7ta\\/41JtU2dRBdiSQJdg98tLe3ZIMjmhNKcqzlLJLNJJnUmhifMTwnlzKs5SySzSSZ2LhYKQXigeZSGsyhnKfEjUp3td+9Sn2XQWK9OhSfrLoCrOUsks0kmdCUUGvMA16tek18PMiwjx10RN\\/5r74GmtNQGeJRfQiSd1wJ6XEZGHuaUHMqZdddTP2ZKP6QV5pokC6LVxlv1wFZoXu0WzYO4ncmhMjgg21bYT3qKT2GKYM0f8QcNI2NDU5ov18kHecKQY+L9v0HuQwS3QfDMH2sUjcaNcRcTjTEfZ8JLnCnRptcr6HhgO2l\\/vYJqXRgXNnnHtfvEAjksV4g==\",\"PartnerCode\":\"ALLIZHEALTH\",\"EntityType\":\"P\",\"HandShake\":\"PER\"},\"JSONData\":\"{\\\"PersonID\\\":\\\"46726\\\",\\\"Message\\\":\\\"Loading Details...\\\"}\"}";
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
