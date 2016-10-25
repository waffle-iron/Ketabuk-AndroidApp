package com.ketabuk.android.interfaces;

/**
 * Created by Karim Mostafa on 10/8/16.
 */

public interface KetabukAPIInterface {
    void login(String email, String password, KetabukAPIResponse ketabukAPIResponse);

    void register(String name, String email, String password, String journalName, KetabukAPIResponse ketabukAPIResponse);

    void getUserJournal(KetabukAPIResponse ketabukAPIResponse);

    void getAllJournals(KetabukAPIResponse ketabukAPIResponse);

    interface KetabukAPIResponse {
        void onSuccess(String result);

        void onError(String error);
    }
}
