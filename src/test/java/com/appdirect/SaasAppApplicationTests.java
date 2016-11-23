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
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaasAppApplicationTests {

	@Test
	public void contextLoads() throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
		String auth = "OAuth oauth_consumer_key=\"cloudsaas-142142\", oauth_nonce=\"3717820168993121793\", oauth_signature=\"sp7GkhLg9n%2BgE%2B8YryInGYkaPZA%3D\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"1479860919\", oauth_version=\"1.0\"";
		auth = auth.replace("OAuth ", "");
		auth = auth.replace("\"", "");
		Map<String, String> data = Arrays.asList(auth.split(","))
				.stream()
				.map(elem -> elem.split("="))
				.collect(Collectors.toMap(e -> e[0], e -> e[1]));
		System.out.println(data);

	}

}
