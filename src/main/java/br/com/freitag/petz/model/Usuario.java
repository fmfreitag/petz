package br.com.freitag.petz.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter @Builder @AllArgsConstructor
public class Usuario implements Serializable {

   	private static final long serialVersionUID = 658441295140176882L;

   	public Usuario() {
   	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario", updatable = false, nullable = false)
    private Long idUsuario;
    @Column
    private String nome;
    @Column
    private String login;
    @Column
    private String idPerfil;
    @Column
    private LocalDateTime dthrUltimaAlteracao;
    @Column
    private String cnpj;
    @Column
    private String senha;

}