package com.ketabuk.android.network;

import com.ketabuk.android.interfaces.KetabukAPIInterface;

import javax.inject.Inject;


/**
 * Created by Karim Mostafa on 10/8/16.
 */

public class KetabukAPI implements KetabukAPIInterface {

    @Inject
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
