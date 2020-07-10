package br.com.freitag.petz.interceptor;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.freitag.petz.config.JwtTokenUtil;
import br.com.freitag.petz.service.AuthenticationService;
import br.com.freitag.petz.util.Constants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(Constants.HEADER_STRING);
        String documento = null;
        String authToken = null;
        if (header != null) {
            authToken = header.replace(Constants.TOKEN_PREFIX,"");
            try {
                documento = jwtTokenUtil.getDocumentoFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("Ocorreu um erro ao pegar documento do token.", e);
            } catch (ExpiredJwtException e) {
                logger.warn("O token esta expirado.", e);
            } catch(SignatureException e){
                logger.error("Authenticacao falhou. Usuario ou senha invalidos.");
            }
        } else {
            logger.warn("String bearer nao encontrada, sera ignorado o header.");
        }
        if (documento != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            authenticationService.salvarAuthenticacao(req, documento, authToken);
        }
        chain.doFilter(req, res);
    }

}
