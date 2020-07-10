package br.com.freitag.petz;
import javax.annotation.PostConstruct;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.freitag.petz.config.JwtTokenUtil;
import br.com.freitag.petz.controller.ApiResponse;
import br.com.freitag.petz.interceptor.JwtUser;
import br.com.freitag.petz.util.Constants;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
@Lazy(true)
public abstract class BaseTest {
	@Mock
	SecurityContext securityContext;
	@Mock
	Authentication authentication;

	@Mock
	JwtUser jwtUser;

	protected static final String DOC_USUARIO_LOGADO = "35455378000164";

	@LocalServerPort
	protected int port;

	protected TestRestTemplate restTemplate = new TestRestTemplate();

	protected String urlBase;

	protected HttpHeaders headers;

	@PostConstruct
	private void autentica() {
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getName()).thenReturn(DOC_USUARIO_LOGADO);

        urlBase = "http://localhost:"+port;

        LoginVO login = new LoginVO(DOC_USUARIO_LOGADO, "455378");
		ResponseEntity<ApiResponse<String>> responseEntity = restTemplate.exchange(urlBase + "/api/autenticacao",
																				HttpMethod.POST,
																				new HttpEntity<LoginVO>(login ),
																				new ParameterizedTypeReference<ApiResponse<String>>() {});

		String token = responseEntity.getBody().getResult();

		 jwtUser = new JwtUser();
		 jwtUser.setToken(token);

		 Mockito.when(authentication.getPrincipal()).thenReturn(jwtUser);

        headers = new HttpHeaders();
        headers.add(Constants.HEADER_STRING, token);
    }

}