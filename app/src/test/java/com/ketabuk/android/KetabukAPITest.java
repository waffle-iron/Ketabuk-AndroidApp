package com.ketabuk.android;

import android.app.Application;

import com.ketabuk.android.dagger.DaggerMockBaseComponent;
import com.ketabuk.android.dagger.MockAppModule;
import com.ketabuk.android.interfaces.KetabukAPIInterface;
import com.ketabuk.android.network.KetabukAPI;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

/**
 * Created by Karim Mostafa on 10/9/16.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class KetabukAPITest {

    @Inject
    KetabukAPI ketabukAPI;

    @Inject
    Application application;

    @Inject
    MockWebServer server;

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
        final CountDownLatch signal = new CountDownLatch(1);

        final MockResponse response = new MockResponse();
        response.setResponseCode(HttpURLConnection.HTTP_OK);
        final String responseBody = "OK";
        response.setBody(responseBody);
        server.enqueue(response);

        String email = "lolo@lolo.com";
        String password = "123456";

        ketabukAPI.login(email, password, new KetabukAPIInterface.KetabukAPIResponse() {
            @Override
            public void onSuccess(String result) {
                Assert.assertEquals(responseBody, result);
                signal.countDown(); // notify the count down latch
            }

            @Override
            public void onError(String error) {
                Assert.fail(error); // Request should be accepted
                signal.countDown(); // notify the count down latch
            }
        });

        try {
            Boolean timeout = signal.await(responseTimeLimit, TimeUnit.SECONDS); // wait for callback
            Assert.assertTrue(timeout);

            RecordedRequest recordedRequest = server.takeRequest();
            String request = recordedRequest.getBody().readUtf8();
            Assert.assertTrue(request.contains(email));
            Assert.assertTrue(request.contains(password));
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }

    }

    @Test
    public void testFailLogin(){
        final CountDownLatch signal = new CountDownLatch(1);

        final MockResponse response = new MockResponse();
        response.setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED);
        final String responseBody = "Unauthorized";
        response.setBody(responseBody);
        server.enqueue(response);

        String email = "wrong username";
        String password = "wrong password";

        ketabukAPI.login(email, password, new KetabukAPIInterface.KetabukAPIResponse() {
            @Override
            public void onSuccess(String result) {
                Assert.fail("Wrong credentials accepted!"); // Request should not be accepted
                signal.countDown(); // notify the count down latch
            }

            @Override
            public void onError(String error) {
                Assert.assertEquals(error, responseBody);
                signal.countDown(); // notify the count down latch
            }
        });

        try {
            Boolean timeout = signal.await(responseTimeLimit, TimeUnit.SECONDS); // wait for callback
            Assert.assertTrue(timeout);

            RecordedRequest recordedRequest = server.takeRequest();
            String request = recordedRequest.getBody().readUtf8();
            Assert.assertTrue(request.contains(email));
            Assert.assertTrue(request.contains(password));
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testSuccessfulRegistration(){
        final CountDownLatch signal = new CountDownLatch(1);

        final MockResponse response = new MockResponse();
        response.setResponseCode(HttpURLConnection.HTTP_OK);
        final String responseBody = "OK";
        response.setBody(responseBody);
        server.enqueue(response);

        String name = "name";
        String journalName = "journalName";
        String email = "email";
        String password = "password";

        ketabukAPI.register(name, email, password, journalName, new KetabukAPIInterface.KetabukAPIResponse() {
            @Override
            public void onSuccess(String result) {
                Assert.assertEquals(responseBody, result);
                signal.countDown(); // notify the count down latch
            }

            @Override
            public void onError(String error) {
                Assert.fail(error); // Request should be accepted
                signal.countDown(); // notify the count down latch
            }
        });

        try {
            Boolean timeout = signal.await(responseTimeLimit, TimeUnit.SECONDS); // wait for callback
            Assert.assertTrue(timeout);

            RecordedRequest recordedRequest = server.takeRequest();
            String request = recordedRequest.getBody().readUtf8();

            Assert.assertTrue(request.contains(name));
            Assert.assertTrue(request.contains(email));
            Assert.assertTrue(request.contains(password));
            Assert.assertTrue(request.contains(journalName));
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testFailRegistration(){
        final CountDownLatch signal = new CountDownLatch(1);

        final MockResponse response = new MockResponse();
        response.setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED);
        final String responseBody = "Unauthorized";
        response.setBody(responseBody);
        server.enqueue(response);

        String name = "wrong name";
        String journalName = "wrong journalName";
        String email = "wrong email";
        String password = "wrong password";

        ketabukAPI.register(name, email, password, journalName, new KetabukAPIInterface.KetabukAPIResponse() {
            @Override
            public void onSuccess(String result) {
                Assert.fail("Invalid parameters accepted!"); // Request should not be accepted
                signal.countDown(); // notify the count down latch
            }

            @Override
            public void onError(String error) {
                Assert.assertEquals(error, responseBody);
                signal.countDown(); // notify the count down latch
            }
        });
        try {
            Boolean timeout = signal.await(responseTimeLimit, TimeUnit.SECONDS); // wait for callback
            Assert.assertTrue(timeout);

            RecordedRequest recordedRequest = server.takeRequest();
            String request = recordedRequest.getBody().readUtf8();

            Assert.assertTrue(request.contains(name));
            Assert.assertTrue(request.contains(email));
            Assert.assertTrue(request.contains(password));
            Assert.assertTrue(request.contains(journalName));

        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testSuccessfullyGettingJournal(){
        final CountDownLatch signal = new CountDownLatch(1);

        final MockResponse response = new MockResponse();
        response.setResponseCode(HttpURLConnection.HTTP_OK);
        final String responseBody = "OK";
        response.setBody(responseBody);
        server.enqueue(response);

        ketabukAPI.getUserJournal(new KetabukAPIInterface.KetabukAPIResponse() {
            @Override
            public void onSuccess(String result) {
                Assert.assertEquals(responseBody, result);
                signal.countDown(); // notify the count down latch
            }

            @Override
            public void onError(String error) {
                Assert.fail(error);
                signal.countDown(); // notify the count down latch
            }
        });

        try {
            Boolean timeout = signal.await(responseTimeLimit, TimeUnit.SECONDS); // wait for callback
            Assert.assertTrue(timeout);

        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testSuccessfullyGettingAllJournal(){
        final CountDownLatch signal = new CountDownLatch(1);

        final MockResponse response = new MockResponse();
        response.setResponseCode(HttpURLConnection.HTTP_OK);
        final String responseBody = "OK";
        response.setBody(responseBody);
        server.enqueue(response);

        ketabukAPI.getAllJournals(new KetabukAPIInterface.KetabukAPIResponse() {
            @Override
            public void onSuccess(String result) {
                Assert.assertEquals(responseBody, result);
                signal.countDown(); // notify the count down latch
            }

            @Override
            public void onError(String error) {
                Assert.fail(error);
                signal.countDown(); // notify the count down latch
            }
        });

        try {
            Boolean timeout = signal.await(responseTimeLimit, TimeUnit.SECONDS); // wait for callback
            Assert.assertTrue(timeout);

        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }
}
