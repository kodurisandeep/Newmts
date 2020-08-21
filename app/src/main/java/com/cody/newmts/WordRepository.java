package com.cody.newmts;

import android.app.Application;
import android.os.AsyncTask;
import android.view.textclassifier.TextLinks;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import okhttp3.OkHttpClient;

class WordRepository extends AsyncTask<String, Void, String> {

    private static String jsonString;
    private String address="";
    private AsyncResponse delegate;

    WordRepository(AsyncResponse delegate){
     this.delegate=delegate;
    }



    @Override
    protected String doInBackground(String... url) {
        for(int i = 0; i <=0; i++) {
            address=url[i];
            try {
                jsonString = getJsonFromServer(url[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonString;
    }

    private static String getJsonFromServer(String url) throws IOException {
        String jsonResult="";

        BufferedReader inputStream = null;

        URL jsonUrl = new URL(url);
        URLConnection dc = jsonUrl.openConnection();

        dc.setConnectTimeout(5000);
        dc.setReadTimeout(5000);

        inputStream = new BufferedReader(new InputStreamReader(
                dc.getInputStream()));

        // read the JSON results into a string
        jsonResult = inputStream.readLine();
        return jsonResult;
    }

    @Override
    protected void onPostExecute(String result) {
        /*if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }*/
        if(result!=null)
            result = result.trim();
        else
            result="noserver";
        delegate.processFinish(result,address);
    }//onPostExecute

}
