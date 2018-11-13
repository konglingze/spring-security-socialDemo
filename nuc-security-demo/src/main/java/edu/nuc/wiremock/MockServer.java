package edu.nuc.wiremock;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import com.github.tomakehurst.wiremock.client.WireMock;

public class MockServer {

	public static void main(String[] args) throws Exception {

		WireMock.configureFor("127.0.0.1",8062);
		WireMock.removeAllMappings();// 清空所有以前的配置

		mock("/order/1","01");

	}

	private static void mock(String url, String file) throws Exception {
		// TODO Auto-generated method stub
		ClassPathResource resource = new ClassPathResource("/resources/mock/response/"+file+".txt");		
		String content  = FileUtils.readFileToString(resource.getFile(),"Utf-8");		
		WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(url))
				//.willReturn(WireMock.aResponse().withBody("{\"id\":1}").withStatus(200)));
				.willReturn(WireMock.aResponse().withBody(content).withStatus(200)));
		
	}
}
