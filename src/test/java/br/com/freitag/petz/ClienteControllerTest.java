package br.com.freitag.petz;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import br.com.freitag.petz.controller.ApiResponse;
import br.com.freitag.petz.model.Cliente;

public class ClienteControllerTest extends BaseTest {

	@Test
    public void saveSucessTest() {

		Cliente cliente = mockCliente();

        ResponseEntity<Cliente> exchange = restTemplate.exchange(urlBase + "/api/v1/cliente",
                HttpMethod.POST,
                new HttpEntity<>(cliente, headers),
                new ParameterizedTypeReference<Cliente>() {
       });

        Assert.assertNotNull(exchange);
    }

    @Test
    public void ListTest() {

    	ResponseEntity<ApiResponse<List<Cliente>>> exchange = restTemplate.exchange(urlBase + "/api/v1/cliente",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<ApiResponse<List<Cliente>>>() {
                });

        Assert.assertNotNull(exchange);
    }

    @Test
    public void updateTest() {

    	Cliente cliente = mockCliente();

    	cliente.setId(21L);
    	cliente.setNome("teste update");
    	cliente.setSexo("F");

        ResponseEntity<Cliente> exchange = restTemplate.exchange(urlBase + "/api/v1/cliente",
                HttpMethod.PUT,
                new HttpEntity<>(cliente,headers),
                new ParameterizedTypeReference<Cliente>() {
                });

        Assert.assertNotNull(exchange);
    }


    @Test
    public void getById() {

        ResponseEntity<Cliente> exchange = restTemplate.exchange(urlBase + "/api/v1/cliente/1",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<Cliente>() {
                });

        Assert.assertNotNull(exchange);
    }

    private Cliente mockCliente() {

    	Cliente cliente = new Cliente();
    	cliente.setNome("Cliente teste");
    	cliente.setCpf("12312312312");
    	cliente.setSexo("M");
    	cliente.setDataNascimento(LocalDate.now());
    	cliente.setEmail("test@dominio.com");
    	cliente.setEndereco("Rua XV de novembro, 1500");
    	return cliente;
    }
}
