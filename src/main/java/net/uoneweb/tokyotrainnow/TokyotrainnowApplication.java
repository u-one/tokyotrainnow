package net.uoneweb.tokyotrainnow;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import net.uoneweb.tokyotrainnow.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Slf4j
@SpringBootApplication
public class TokyotrainnowApplication {

	public static void main(String[] args) {
		SpringApplication.run(TokyotrainnowApplication.class, args);
	}

	@Autowired
	TrainService trainService;

	@Bean
	public ApplicationRunner initialize() {
		return (args) -> {
			log.info("[initialize]");
			trainService.initialize();
		};
	}

	WireMockServer wireMockServer;

	@Profile("debug")
	@PostConstruct
	public void debugInitialize() {
		log.info("[debugInitialize]");

		wireMockServer = new WireMockServer(8081);
		wireMockServer.start();
		configureFor("localhost", 8081);

		stubFor(get(urlMatching("/odpt:Train\\?acl:consumerKey=TEST-KEY&odpt:operator=odpt.Operator:JR-East&odpt:railway=odpt.Railway:JR-East.SobuRapid"))
				.willReturn(aResponse()
						.withStatus(200)
						.withBodyFile("train_soburapid.jsonld")
						.withHeader("Content-Type", "application/json")
				));

		stubFor(get(urlMatching("/odpt:Station.json\\?acl:consumerKey=TEST-KEY"))
				.willReturn(aResponse()
						.withStatus(200)
						.withBodyFile("stations.jsonld")
						.withHeader("Content-Type", "application/json")
				));

		stubFor(get(urlMatching("/odpt:Operator.json\\?acl:consumerKey=TEST-KEY"))
				.willReturn(aResponse()
						.withStatus(200)
						.withBodyFile("operator.jsonld")
						.withHeader("Content-Type", "application/json")
				));

		stubFor(get(urlMatching("/odpt:Railway.json\\?acl:consumerKey=TEST-KEY"))
				.willReturn(aResponse()
						.withStatus(200)
						.withBodyFile("railway.jsonld")
						.withHeader("Content-Type", "application/json")
				));

		stubFor(get(urlMatching("/odpt:TrainType.json\\?acl:consumerKey=TEST-KEY"))
				.willReturn(aResponse()
						.withStatus(200)
						.withBodyFile("traintypes.jsonld")
						.withHeader("Content-Type", "application/json")
				));
	}

}
