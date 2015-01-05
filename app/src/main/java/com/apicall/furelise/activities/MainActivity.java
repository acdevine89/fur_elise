package com.apicall.furelise.activities;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.apicall.furelise.R;
import com.apicall.furelise.interfaces.ApiCallback;
import com.apicall.furelise.parcers.WundergroundJSONParcer;
import com.apicall.furelise.wundergroundapi.WundergroundApi;

import org.json.JSONObject;


public class MainActivity extends Activity implements ApiCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WundergroundApi.getWundergroundApi().getForecast("MI", "Detroit", this);
    }

    @Override
    public void onSuccess(JSONObject response) {
        WundergroundJSONParcer wundergroundJSONParcer = WundergroundJSONParcer.getWundergroundJSONParcer();
        String temperature = wundergroundJSONParcer.getTodaysTempFromJSON(response);

        TextView temperatureTextView = (TextView) findViewById(R.id.temp_text_view);
        temperatureTextView.setText(temperature);
    }

    @Override
    public void onError() {
        //notify
    }
}
