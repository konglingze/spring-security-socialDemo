package edu.nuc.wiremock;

import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MTest {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(8062); // No-args constructor defaults to port 8080

	@Test
	public void exampleTest() {
		stubFor(get(urlEqualTo("/my/resource")).withHeader("Accept", equalTo("text/xml")).willReturn(aResponse()
				.withStatus(200).withHeader("Content-Type", "text/xml").withBody("<response>Some content</response>")));


		verify(postRequestedFor(urlMatching("/my/resource/[a-z0-9]+"))
				.withRequestBody(matching(".*<message>1234</message>.*"))
				.withHeader("Content-Type", notMatching("application/json")));
	}
}
