package com.apicall.furelise.interfaces;

import org.json.JSONObject;

/**
 * Created on 1/5/15.
 */
public interface ApiCallback {
    public void onSuccess(JSONObject response);
    public void onError();
}
