package com.appdirect;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaasAppApplicationTests {

	@Test
	public void contextLoads() throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
		String event = "https://marketplace.appdirect.com/api/integration/v1/events/e0121e5c-e4a6-4269-a328-93dd5748b5dee";
		OAuthConsumer consumer = new DefaultOAuthConsumer("cloudsaas-142142", "tkosyYzFHXNY");
		URL url = new URL(event);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		consumer.sign(request);
		request.connect();
		System.out.print(request.getResponseCode());
	}

}
