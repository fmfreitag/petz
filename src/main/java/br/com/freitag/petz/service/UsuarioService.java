package br.com.freitag.petz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.freitag.petz.exception.BusinessException;
import br.com.freitag.petz.model.Usuario;
import br.com.freitag.petz.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

	public String convertPassword(String password) {
		return bcryptEncoder.encode(password);
	}

    public void loginValidation(String cnpj, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByCnpj(cnpj);
        System.out.println(bcryptEncoder.encode("328118"));
        if(usuario.isPresent()) {
        	Boolean isMatch = bcryptEncoder.matches(password,usuario.get().getSenha());
        	if(isMatch==false)
        		throw new BusinessException(HttpStatus.BAD_REQUEST,"Senha invalida.");
        } else
            throw new BusinessException(HttpStatus.BAD_REQUEST,"CNPJ nao encontrado.");
    }

	@Override
    public UserDetails loadUserByUsername(String documento) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.findByCnpj(documento);
		if(!usuario.isPresent()){
			throw new UsernameNotFoundException("Usuario ou senha invalidos.");
		}
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_ADMIN");
		return new org.springframework.security.core.userdetails.User(usuario.get().getCnpj(), usuario.get().getSenha(), true, true, true, true, getGrantedAuthorities(roles));

	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String privilege : roles) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

}
