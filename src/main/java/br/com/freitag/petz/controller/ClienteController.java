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

import br.com.freitag.petz.model.Cliente;
import br.com.freitag.petz.repository.ClienteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
@Api(value = "API Cliente", tags = "Cliente")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/cliente")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<Cliente>> save(@RequestBody Cliente cliente) {
    	return ResponseEntity.status(HttpStatus.CREATED)
    						 .body(new ApiResponse<Cliente>("Cliente gravado com sucesso.", clienteRepository.save(cliente)));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Void>> update(@RequestBody Cliente cliente) {
    	clienteRepository.save(cliente);
    	return ResponseEntity.ok(new ApiResponse<Void>("Cliente alterado com sucesso.",null));
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<ApiResponse<Optional<Cliente>>> getOne(@ApiParam(value = "Identificador Único do Cliente") @PathVariable(name =  "idCliente") Long idCliente) {
        return ResponseEntity.ok(new ApiResponse<>("Cliente obtido com sucesso.",clienteRepository.findById(idCliente)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Cliente>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>("Lista de clientes obtida com sucesso.",clienteRepository.findAll()));
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long idCliente) {
		ApiResponse<Void> response = new ApiResponse<>();

        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if(cliente.isPresent()) {
            clienteRepository.delete(cliente.get());
          	return ResponseEntity.ok(new ApiResponse<Void>("Cliente deletado com sucesso.",null));
        }
        response.setMessage("Cliente não encontrado com o ID:" + idCliente);
        return ResponseEntity.badRequest().body(response);
    }

}
