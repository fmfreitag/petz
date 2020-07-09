package br.com.freitag.petz;
import javax.annotation.PostConstruct;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
@Lazy(true)
public abstract class BaseTest {

	@LocalServerPort
	protected int port;

	protected TestRestTemplate restTemplate = new TestRestTemplate();

	protected String urlBase;

	protected HttpHeaders headers;

	@PostConstruct
	private void autentica() {
        urlBase = "http://localhost:"+port;
        headers = new HttpHeaders();
    }


}