package com.ketabuk.android.network;

import android.os.Handler;
import android.os.Looper;

import com.ketabuk.android.interfaces.KetabukAPIInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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


    public KetabukAPI() {

    }

    @Override
    public void login(String email, String password, final KetabukAPIResponse ketabukAPIResponse) {

    }

    @Override
    public void Register(String name, String email, String password, String journalName, KetabukAPIResponse ketabukAPIResponse) {

    }

    @Override
    public void getUserJournal(KetabukAPIResponse ketabukAPIResponse) {

    }

    @Override
    public void getAllJournals(KetabukAPIResponse ketabukAPIResponse) {

    }
}
