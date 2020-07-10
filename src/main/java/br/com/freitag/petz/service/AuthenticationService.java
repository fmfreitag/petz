package br.com.freitag.petz.service;


import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import br.com.freitag.petz.config.JwtTokenUtil;
import br.com.freitag.petz.interceptor.JwtUser;
import br.com.freitag.petz.model.Usuario;
import br.com.freitag.petz.repository.UsuarioRepository;
import br.com.freitag.petz.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthenticationService {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String getToken(LoginVO login) {

    	usuarioService.loginValidation(login.getDocumento(), login.getPassword());

    	Optional<Usuario> usuario = usuarioRepository.findByCnpj(login.getDocumento());
        if(usuario.isPresent() == false){
            throw new UsernameNotFoundException("Usuario ou senha invalidos.");
        }

        String token = new JwtTokenUtil().generateToken(login.getDocumento(), "ROLE_ADMIN");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);

    	return jwtTokenUtil.generateToken(login.getDocumento(), "ROLE_ADMIN");

    }

    public void salvarAuthenticacao(HttpServletRequest req, String documento, String authToken) {
    	UserDetails userDetails =  userDetailsService.loadUserByUsername(documento);
        if (jwtTokenUtil.validateToken(authToken, userDetails)) {
            gravarContextoAuthenticacao(req, userDetails, authToken);
            log.info("Usuario autenticado " + documento + ", setando o contexto de seguranca.");
        }
    }

    private void gravarContextoAuthenticacao(HttpServletRequest req, UserDetails userDetails, String authToken){
    	JwtUser user = new JwtUser();
    	user.setAuthorities(userDetails.getAuthorities());
    	user.setPassword(userDetails.getPassword());
    	user.setUsername(userDetails.getUsername());
    	user.setToken(authToken);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}