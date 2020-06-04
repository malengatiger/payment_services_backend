package com.nps.api;

import com.google.api.client.util.Sleeper;
import com.nps.api.controllers.OzowController;
import com.nps.api.controllers.PayPalController;
import com.nps.api.controllers.PayfastController;
import com.nps.api.services.PublisherService;
import com.nps.api.services.SubscriberService;
import com.nps.api.util.Emoji;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class PaymentServicesApplication implements ApplicationListener<ApplicationReadyEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServicesApplication.class);

	@Value(value = "${databaseUrl}")
	private String databaseUrl;

	@Value(value = "${guru}")
	private String guru;

	@Value(value = "${skill}")
	private String skill;

	@Value(value = "${spring.cloud.config.uri}")
	private String configUri;

	@Autowired
	private OzowController ozowController;

	@Autowired
	private PayfastController payfastController;

	@Autowired
	private PayPalController payPalController;


	@Autowired
	private PublisherService publisherService;

	@Autowired
	private SubscriberService subscriberService;


	public static void main(String[] args) {
		LOGGER.info("\uD83D\uDC7D\uD83D\uDC7D\uD83D\uDC7D\uD83D\uDC7D\uD83D\uDC7D ".concat("PaymentServicesApplication starting ..."));
		SpringApplication.run(PaymentServicesApplication.class, args);
		LOGGER.info("\uD83D\uDC7D\uD83D\uDC7D\uD83D\uDC7D\uD83D\uDC7D\uD83D\uDC7D ".concat("PaymentServicesApplication started OK  \uD83E\uDD1F\uD83C\uDFFD"));
	}

	@Override
	public void onApplicationEvent(@NotNull ApplicationReadyEvent applicationReadyEvent) {
		LOGGER.info("\uD83C\uDF38 \uD83C\uDF38 \uD83C\uDF38 onApplicationEvent: databaseUrl = ".concat(databaseUrl));

		try {
			LOGGER.info("\uD83C\uDF38 \uD83C\uDF38 \uD83C\uDF38 onApplicationEvent: calling Publisher Service ");
			String msg = Emoji.PEAR.concat(Emoji.PEAR).concat("Network Payment Services started and ready to serve ".concat(Emoji.CAT)
			.concat(new Date().toString().concat(" ".concat(Emoji.BLUE_BIRD.concat(Emoji.BLUE_BIRD)))));

			publisherService.publish(msg, "ozow-success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		LOGGER.info("\uD83C\uDFB2 \uD83C\uDFB2 \uD83C\uDFB2 Network Payment Services Configuration Server URI: ".concat(configUri));

		ozowController.printOzowCallbacks();
		payfastController.printPayfastCallbacks();
		payPalController.printPayPalCallbacks();

		LOGGER.info("\uD83D\uDC4C\uD83C\uDFFD Resident Guru: \uD83D\uDC4C\uD83C\uDFFD\uD83D\uDC7D ".concat(guru).concat(" ".concat("\uD83C\uDFBD \uD83C\uDFBD")));
		LOGGER.info("\uD83D\uDC4C\uD83C\uDFFD Guru Skills: \uD83E\uDD6C\uD83E\uDD6C\uD83E\uDD6C ".concat(skill).concat(" ".concat("\uD83C\uDFBD \uD83C\uDFBD")));
	}
}
