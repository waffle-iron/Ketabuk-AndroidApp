package com.ketabuk.android.network;

import android.app.Application;

import com.ketabuk.android.R;
import com.ketabuk.android.interfaces.KetabukAPIInterface;
import com.ketabuk.android.utilities.PrefUtils;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Karim Mostafa on 10/8/16.
 */

public class KetabukAPI implements KetabukAPIInterface {

    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    @Inject
    OkHttpClient okHttpClient;
    @Inject
    Application application;

    @Inject
    public KetabukAPI() {
    }

    @Override
    public void login(String email, String password, final KetabukAPIResponse ketabukAPIResponse) {
        String url = application.getString(R.string.login);
        JSONObject parameters = new JSONObject();
        JSONObject credentials = new JSONObject();
        try {
            credentials.put("email", email);
            credentials.put("password", password);
            parameters.put("credentials", credentials);
        } catch (JSONException e) {
            ketabukAPIResponse.onError("JSONException: " + e.getMessage());
            return;
        }
        RequestBody body = RequestBody.create(JSON, parameters.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ketabukAPIResponse.onError("IOException: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if (response.header("Authorization") != null) {
                        String token = response.header("Authorization").substring(7);          // remove the bearer prefix
                        PrefUtils.setToken(application, token);
                    }

                    if (response.code() != HttpURLConnection.HTTP_OK) {
                        ketabukAPIResponse.onError(response.body().string());
                        return;
                    }
                    ketabukAPIResponse.onSuccess(response.body().string());
                } catch (IOException e) {
                    ketabukAPIResponse.onError("IOException: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void register(String name, String email, String password, String journalName, final KetabukAPIResponse ketabukAPIResponse) {
        String url = application.getString(R.string.register);
        JSONObject parameters = new JSONObject();
        JSONObject registrationForm = new JSONObject();
        JSONObject user = new JSONObject();
        JSONObject journal = new JSONObject();
        try {

            journal.put("name", journalName);

            user.put("journal", journal);
            user.put("name", name);
            user.put("email", email);

            registrationForm.put("user", user);
            registrationForm.put("password", password);

            parameters.put("credentials", registrationForm);

        } catch (JSONException e) {
            ketabukAPIResponse.onError("JSONException: " + e.getMessage());
            return;
        }
        RequestBody body = RequestBody.create(JSON, parameters.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ketabukAPIResponse.onError("IOException: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if (response.code() != HttpURLConnection.HTTP_OK) {
                        ketabukAPIResponse.onError(response.body().string());
                        return;
                    }
                    ketabukAPIResponse.onSuccess(response.body().string());
                } catch (IOException e) {
                    ketabukAPIResponse.onError("IOException: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void getUserJournal(final KetabukAPIResponse ketabukAPIResponse) {
        String url = application.getString(R.string.journal_me);

        if(PrefUtils.getToken(application) != null)
            url.concat("?token=" + PrefUtils.getToken(application));

        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ketabukAPIResponse.onError("IOException: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if (response.code() != HttpURLConnection.HTTP_OK) {
                        ketabukAPIResponse.onError(response.body().string());
                        return;
                    }
                    ketabukAPIResponse.onSuccess(response.body().string());
                } catch (IOException e) {
                    ketabukAPIResponse.onError("IOException: " + e.getMessage());
                }
            }
        });

    }

    @Override
    public void getAllJournals(final KetabukAPIResponse ketabukAPIResponse) {

        String url = application.getString(R.string.journal);

        if(PrefUtils.getToken(application) != null)
            url.concat("?token=" + PrefUtils.getToken(application));

        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ketabukAPIResponse.onError("IOException: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if (response.code() != HttpURLConnection.HTTP_OK) {
                        ketabukAPIResponse.onError(response.body().string());
                        return;
                    }
                    ketabukAPIResponse.onSuccess(response.body().string());
                } catch (IOException e) {
                    ketabukAPIResponse.onError("IOException: " + e.getMessage());
                }
            }
        });
    }
}
