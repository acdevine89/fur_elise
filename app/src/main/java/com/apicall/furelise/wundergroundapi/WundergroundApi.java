package com.apicall.furelise.wundergroundapi;

import android.net.Uri;
import android.os.AsyncTask;

import com.apicall.furelise.interfaces.ApiCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created on 1/5/15.
 */
public class WundergroundApi {

    private static final String ROOT_URL = "api.wunderground.com";
    private static final String API_KEY = "222735dd198fa01a";

    private static WundergroundApi wundergroundApi;

    public static WundergroundApi getWundergroundApi() {
        if (wundergroundApi == null) {
            wundergroundApi = new WundergroundApi();
        }
        return wundergroundApi;
    }

    public void getForecast(String state, String city, ApiCallback apiCallback) {

        Uri uri = new Uri.Builder()
                .scheme("http")
                .authority(ROOT_URL)
                .appendPath("api")
                .appendPath(API_KEY)
                .appendPath("conditions")
                .appendPath("q")
                .appendPath(state)
                .appendPath(city)
                .appendPath(".json")
                .build();

        new LoadDataInBackground(apiCallback).execute(uri);
    }

    private JSONObject getJSONObjectFromUri(Uri uri) throws IOException, JSONException {
        URLConnection httpURLConnection = new URL(uri.toString()).openConnection();

        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        int bytesRead;
        StringBuilder stringBuilder = new StringBuilder();

        while ((bytesRead = bufferedInputStream.read()) != -1) {
            stringBuilder.append((char)bytesRead);
        }

        inputStream.close();
        bufferedInputStream.close();

        return new JSONObject(stringBuilder.toString());
    }

    private class LoadDataInBackground extends AsyncTask<Uri, Void, JSONObject> {

        private ApiCallback apiCallback;

        private LoadDataInBackground(ApiCallback apiCallback) {
            this.apiCallback = apiCallback;
        }

        @Override
        protected JSONObject doInBackground(Uri... params) {

            try {
                Uri uri = params[0];
                return getJSONObjectFromUri(uri);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            if (result != null) {
                this.apiCallback.onSuccess(result);
            } else {
                this.apiCallback.onError();
            }
        }
    }
}
