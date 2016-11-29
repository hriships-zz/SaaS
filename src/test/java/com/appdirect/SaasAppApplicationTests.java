package com.appdirect;

import com.appdirect.subscriptions.operations.domain.entities.AccountStatusEnum;
import com.appdirect.subscriptions.operations.domain.entities.Subscription;
import com.appdirect.subscriptions.operations.services.SubscriptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaasAppApplicationTests {

	@Autowired
	SubscriptionService subscriptionService;

	private Subscription subscription;

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

	@Test
	public void createSubscription() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("create.json").getFile());
		Subscription dummySubscription = mapper.readValue(file, Subscription.class);
		subscription = subscriptionService.create(dummySubscription);

		Assert.assertTrue(subscription.getId() != null && subscription.getPayload().getAccount().getAccountIdentifier() != null);
	}

	@Test
	public void changeSubscription() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("change.json").getFile());
		Subscription dummySubscription = mapper.readValue(file, Subscription.class);
		subscription = subscriptionService.create(dummySubscription);
		dummySubscription.getPayload().setAccount(subscription.getPayload().getAccount());
		dummySubscription = subscriptionService.update(dummySubscription);

		Assert.assertTrue(dummySubscription.getId() != null && dummySubscription.getId().equals(subscription.getId()));
	}

	@Test
	public void cancelSubscription() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("cancel.json").getFile());
		Subscription dummySubscription = mapper.readValue(file, Subscription.class);
		subscription = subscriptionService.create(dummySubscription);
		dummySubscription.getPayload().setAccount(subscription.getPayload().getAccount());
		dummySubscription = subscriptionService.cancel(dummySubscription);

		Assert.assertTrue(dummySubscription.getPayload().getAccount().getStatus().equals(AccountStatusEnum.INACTIVE));
	}
}
