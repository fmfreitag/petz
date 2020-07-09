package br.com.freitag.petz.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.freitag.petz.model.Pet;
import br.com.freitag.petz.repository.PetRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
@Api(value = "API Pet", tags = "Pet")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/pet")
public class PetController {

	@Autowired
	private PetRepository petRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<Pet>> save(@RequestBody Pet Pet) {
    	return ResponseEntity.status(HttpStatus.CREATED)
    						 .body(new ApiResponse<Pet>("Pet gravado com sucesso.", petRepository.save(Pet)));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Void>> update(@RequestBody Pet Pet) {
    	petRepository.save(Pet);
    	return ResponseEntity.ok(new ApiResponse<Void>("Pet alterado com sucesso.",null));
    }

    @GetMapping("/{idPet}")
    public ResponseEntity<ApiResponse<Optional<Pet>>> getOne(@ApiParam(value = "Identificador Único do Pet") @PathVariable(name =  "idPet") Long idPet) {
        return ResponseEntity.ok(new ApiResponse<>("Pet obtido com sucesso.",petRepository.findById(idPet)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Pet>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>("Lista de Pets obtida com sucesso.",petRepository.findAll()));
    }

    @DeleteMapping("/{idPet}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long idPet) {
		ApiResponse<Void> response = new ApiResponse<>();

        Optional<Pet> pet = petRepository.findById(idPet);
        if(pet.isPresent()) {
            petRepository.delete(pet.get());
          	return ResponseEntity.ok(new ApiResponse<Void>("Pet deletado com sucesso.",null));
        }
        response.setMessage("Pet não encontrado com o ID:" + idPet);
        return ResponseEntity.badRequest().body(response);
    }

}
