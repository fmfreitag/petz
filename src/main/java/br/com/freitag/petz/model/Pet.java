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
public class Pet implements Serializable {

 	private static final long serialVersionUID = -4092992253631705821L;

 	public Pet() {
   	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column
    private String nome;
    @Column
    private String especie;
    @Column
    private String raca;
    @Column
    private LocalDate dataNascimento;
    @Column
    private String peso;
    @Column
    private String sexo;
    @Column
    private String cor;
    @Column
    private String olhos;
    @Column
    private String descricao;
    @Column
    private Long id_cliente;

 }