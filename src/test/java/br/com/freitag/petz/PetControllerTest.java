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
import br.com.freitag.petz.model.Pet;

public class PetControllerTest extends BaseTest {

	@Test
    public void saveSucessTest() {

		Pet pet = mockPet();

        ResponseEntity<Pet> exchange = restTemplate.exchange(urlBase + "/api/v1/pet",
                HttpMethod.POST,
                new HttpEntity<>(pet, headers),
                new ParameterizedTypeReference<Pet>() {
       });

        Assert.assertNotNull(exchange);
    }

    @Test
    public void ListTest() {

    	ResponseEntity<ApiResponse<List<Pet>>> exchange = restTemplate.exchange(urlBase + "/api/v1/pet",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<ApiResponse<List<Pet>>>() {
                });

        Assert.assertNotNull(exchange);
    }

    @Test
    public void updateTest() {

    	Pet pet = mockPet();

    	pet.setId(21L);
    	pet.setNome("teste update");
    	pet.setSexo("F");
    	pet.setCor("Preta");

        ResponseEntity<Pet> exchange = restTemplate.exchange(urlBase + "/api/v1/pet",
                HttpMethod.PUT,
                new HttpEntity<>(pet,headers),
                new ParameterizedTypeReference<Pet>() {
                });

        Assert.assertNotNull(exchange);
    }


    @Test
    public void getById() {

        ResponseEntity<Pet> exchange = restTemplate.exchange(urlBase + "/api/v1/pet/1",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<Pet>() {
                });

        Assert.assertNotNull(exchange);
    }

    private Pet mockPet() {

    	Pet pet = new Pet();
    	pet.setNome("Pet teste");
    	pet.setDescricao("Dog Alemao");
    	pet.setSexo("M");
    	pet.setDataNascimento(LocalDate.now());
    	pet.setCor("Caramelo");
    	pet.setEspecie("Cachorro");
    	pet.setRaca("Rusky");
    	pet.setOlhos("Azuis");
    	pet.setId_cliente(1L);
    	pet.setPeso("1.5 kg");
      	return pet;
    }
}
