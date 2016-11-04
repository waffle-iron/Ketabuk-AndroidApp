package com.ketabuk.android;

import com.ketabuk.android.activities.LoginActivity;
import com.ketabuk.android.dagger.DaggerMockBaseComponent;
import com.ketabuk.android.dagger.MockAppModule;
import com.ketabuk.android.presenters.LoginActivityPresenter;
import com.ketabuk.android.presenters.PresentersFactory;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;

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
 * Created by Karim Mostafa on 10/31/16.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class LoginActivityPresenterTest {

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
    public void testSuccessfulLogin() {

        final MockResponse response = new MockResponse();
        response.setResponseCode(HttpURLConnection.HTTP_OK);
        final String responseBody = "HTTP_OK";
        response.setBody(responseBody);
        server.enqueue(response);

        String email = "lolo@lolo.com";
        String password = "123456";

        LoginActivity loginActivity = Mockito.spy(LoginActivity.class);

        LoginActivityPresenter loginActivityPresenter = presentersFactory.providesLoginActivityPresenter(loginActivity);
        loginActivityPresenter.login(email, password);

        try {
            RecordedRequest recordedRequest = server.takeRequest(responseTimeLimit, TimeUnit.SECONDS);
            Assert.assertNotNull(recordedRequest);
            Assert.assertEquals(recordedRequest.getPath(), "/login");   // Probably a bad assertion as I should not make any assumptions about how the method works
            String request = recordedRequest.getBody().readUtf8();
            Assert.assertTrue(request.contains(email));
            Assert.assertTrue(request.contains(password));

            verify(loginActivity).onLoginSucessListener();
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testFailLogin() {
        final MockResponse response = new MockResponse();
        response.setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED);
        final String responseBody = "HTTP_UNAUTHORIZED";
        response.setBody(responseBody);
        server.enqueue(response);

        String email = "wrong email";
        String password = "wrong password";

        LoginActivity loginActivity = Mockito.spy(LoginActivity.class);
        LoginActivityPresenter loginActivityPresenter = presentersFactory.providesLoginActivityPresenter(loginActivity);
        loginActivityPresenter.login(email, password);

        try {
            RecordedRequest recordedRequest = server.takeRequest(responseTimeLimit, TimeUnit.SECONDS);
            Assert.assertNotNull(recordedRequest);
            Assert.assertEquals(recordedRequest.getPath(), "/login");   // Probably a bad assertion as I should not make any assumptions about how the method works
            String request = recordedRequest.getBody().readUtf8();
            Assert.assertTrue(request.contains(email));
            Assert.assertTrue(request.contains(password));

            verify(loginActivity, never()).onLoginSucessListener();
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

}
