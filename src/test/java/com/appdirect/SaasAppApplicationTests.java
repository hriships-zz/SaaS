package com.appdirect;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpRequest;
import oauth.signpost.signature.AuthorizationHeaderSigningStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaasAppApplicationTests {

	@Test
	public void contextLoads() throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {

		String url = "oauth_consumer_key=cloudsaas-142142&oauth_nonce=-7995465560126214428&oauth_signature=BWkbQC4WLhONUSMqadXmFK8ItxY%253D&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1480346411&oauth_version=1.0";
		String data = Arrays.asList(url.split("&"))
				.stream()
				.map(elem -> elem.replace("=", "=\""))
				.map(elem -> elem.concat("\""))
				.collect(Collectors.joining(","));
		System.out.print("OAuth realm=\"\"," + data);

	}

}
