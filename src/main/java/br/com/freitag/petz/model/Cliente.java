package br.com.freitag.petz.model;

import java.io.Serializable;
import java.time.LocalDate;

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
public class Cliente implements Serializable {

 	private static final long serialVersionUID = 208875095644364836L;

 	public Cliente() {
   	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column
    private String nome;
    @Column
    private String endereco;
    @Column
    private String email;
    @Column
    private LocalDate dataNascimento;
    @Column
    private String cpf;
    @Column
    private String sexo;
 }