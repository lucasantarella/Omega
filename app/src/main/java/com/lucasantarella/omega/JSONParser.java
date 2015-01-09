package com.lucasantarella.omega;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Luca on 1/7/2015.
 */
public class JSONParser extends IntentService{
    public static final String TAG = JSONParser.class.getSimpleName();
    public static JSONObject JSONResult = null;
    private JSONDataSource jsonDataSource = new JSONDataSource(this);
    public JSONParser() {
        super("JSONParser");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");
        jsonDataSource.open();
        jsonDataSource.inValidateTable();
        jsonDataSource.close();
        new getJSON().execute();
    }

    public class getJSON extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... params) {
            DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpPost httppost = new HttpPost("http://www.oratoryprepomega.org/?json=get_recent_posts&count=20");
            httppost.setHeader("Content-type", "application/json");
            InputStream inputStream = null;

            try {
                HttpResponse response = httpClient.execute(httppost);
                HttpEntity entity = response.getEntity();

                inputStream = entity.getContent();
                // json is UTF-8 by default
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                JSONResult = new JSONObject(sb.toString());
            } catch (Exception e){
                Log.e(JSONParser.class.getSimpleName(), "onCrashed\n", e);
            }

            finally{
                try {
                    if (inputStream != null) inputStream.close();
                } catch (Exception e) {
                    Log.e(TAG, "Failed on inputStream!", e);
                }
            }
            return JSONResult;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            Log.d(TAG, "onPostExecute");
            JSONArray jsonArray = null;
            JSONObject currentObject;
            int count = 0;
            try {
                jsonArray = jsonObject.getJSONArray("posts");
                count = Integer.parseInt(jsonObject.getString("count"));
            } catch (JSONException e) {
                Log.e(TAG, "Failed to parse array from JSONObject or parse count!\n", e);
            }
            jsonDataSource.open();
            if(jsonArray!=null && count != 0){
            for (int i = 0; i <= count - 1; i++) {
                    try {
                        currentObject = jsonArray.getJSONObject(i);
                        jsonDataSource.insertIntoDatabase(
                                Jsoup.parse(currentObject.getString("title").replaceAll("////", "")).toString(),
                                currentObject.getJSONObject("author").getString("name"),
                                currentObject.getString("date"),
                                currentObject.getJSONArray("categories").getJSONObject(0).getString("slug"),
                                Jsoup.parse(currentObject.getString("content").substring(0, 47) + "...".replaceAll("////", "")).toString(),
                                Jsoup.parse(currentObject.getString("content").toString().replaceAll("////", "")).toString(),
                                currentObject.getJSONArray("attachments").getJSONObject(0).getString("url").replaceAll("////", ""),
                                currentObject.getString("url").replaceAll("////", "")
                        );
                        Log.d(TAG, "Got title: " + currentObject.getString("content").replaceAll("////", ""));
                        Log.d(TAG, String.format("Inserted JSONObject number %s out of %s", i+1, count));
                    } catch (JSONException e) {
                        Log.e(TAG, "Failed reading from Objects list!", e);
                    }
                }
            }else{
                Log.d(TAG, "JSONArray was empty! Did not iterate over the array and create list!");
            }
            jsonDataSource.close();
        }
    }

}