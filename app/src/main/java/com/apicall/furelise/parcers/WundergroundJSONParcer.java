package com.apicall.furelise.parcers;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created on 1/5/15.
 */
public class WundergroundJSONParcer {

    private static WundergroundJSONParcer wundergroundJSONParcer;

    public static WundergroundJSONParcer getWundergroundJSONParcer() {
        if (wundergroundJSONParcer == null) {
            wundergroundJSONParcer = new WundergroundJSONParcer();
        }
        return wundergroundJSONParcer;
    }

    public String getTodaysTempFromJSON(JSONObject forecastJSON) {
        try {
            JSONObject currentObservation = forecastJSON.getJSONObject("current_observation");
            double tempF = currentObservation.getDouble("temp_f");
            return Double.toString(tempF);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
