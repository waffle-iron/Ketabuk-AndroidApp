package com.ketabuk.android;

import com.ketabuk.android.activities.RegisterActivity;
import com.ketabuk.android.dagger.DaggerMockBaseComponent;
import com.ketabuk.android.dagger.MockAppModule;
import com.ketabuk.android.presenters.PresentersFactory;
import com.ketabuk.android.presenters.RegisterActivityPresenter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by Karim Mostafa on 11/5/16.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class RegisterActivityPresenterTest {

    @Inject
    MockWebServer server;

    @Inject
    PresentersFactory presentersFactory;

    Integer responseTimeLimit = 5; // In Seconds

    @Before
    public void setUp() {
        DaggerMockBaseComponent.builder()
                .mockAppModule(new MockAppModule(RuntimeEnvironment.application))
                .build()
                .inject(this);
    }

    @Test
    public void testSuccessfulRegister() {

        final MockResponse response = new MockResponse();
        response.setResponseCode(HttpURLConnection.HTTP_OK);
        final String responseBody = "HTTP_OK";
        response.setBody(responseBody);
        server.enqueue(response);

        String name = "user";
        String email = "lolo@lolo.com";
        String password = "123456";
        String journalName = "user journal";


        RegisterActivity registerActivity = Mockito.spy(RegisterActivity.class);

        RegisterActivityPresenter registerActivityPresenter = presentersFactory.providesRegisterActivityPresenter(registerActivity);
        registerActivityPresenter.register(name, email, password, journalName);

        try {
            RecordedRequest recordedRequest = server.takeRequest(responseTimeLimit, TimeUnit.SECONDS);
            Assert.assertNotNull(recordedRequest);
            Assert.assertEquals(recordedRequest.getPath(), "/Register");
            String request = recordedRequest.getBody().readUtf8();
            Assert.assertTrue(request.contains(email));
            Assert.assertTrue(request.contains(password));

            verify(registerActivity).onRegisterSuccessListener();
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testFailRegister() {
        final MockResponse response = new MockResponse();
        response.setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED);
        final String responseBody = "HTTP_UNAUTHORIZED";
        response.setBody(responseBody);
        server.enqueue(response);

        String name = "user";
        String email = "lolo@lolo.com";
        String password = "123456";
        String journalName = "user journal";


        RegisterActivity registerActivity = Mockito.spy(RegisterActivity.class);

        RegisterActivityPresenter registerActivityPresenter = presentersFactory.providesRegisterActivityPresenter(registerActivity);
        registerActivityPresenter.register(name, email, password, journalName);
        try {
            RecordedRequest recordedRequest = server.takeRequest(responseTimeLimit, TimeUnit.SECONDS);
            Assert.assertNotNull(recordedRequest);
            Assert.assertEquals(recordedRequest.getPath(), "/register");
            String request = recordedRequest.getBody().readUtf8();
            Assert.assertTrue(request.contains(email));
            Assert.assertTrue(request.contains(password));

            verify(registerActivity, never()).onRegisterSuccessListener();
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
    }


    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }
}
