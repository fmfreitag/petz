package br.com.freitag.petz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.freitag.petz.service.AuthenticationService;
import br.com.freitag.petz.vo.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
@Api(value = "API Autenticação", tags = "Autenticação")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/autenticacao")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @ApiOperation(value = "Gera o token a partir do documento e senha.", response = String.class)
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Ok"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error") })
    @PostMapping
    public ResponseEntity<ApiResponse<String>> register(@RequestBody LoginVO login) {
            return ResponseEntity.ok(new ApiResponse<>("sucesso", authenticationService.getToken(login)));
    }

}
