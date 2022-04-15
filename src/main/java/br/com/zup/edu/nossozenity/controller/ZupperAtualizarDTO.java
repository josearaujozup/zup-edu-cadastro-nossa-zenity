package br.com.zup.edu.nossozenity.controller;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ZupperAtualizarDTO {
	
    @NotBlank
    private String nome;

    @NotBlank
    private String cargo;

	public ZupperAtualizarDTO(@NotNull Long id, @NotBlank String nome, @NotBlank String cargo) {
		this.nome = nome;
		this.cargo = cargo;
	}

	public ZupperAtualizarDTO() {
	}
	
	public String getNome() {
		return nome;
	}

	public String getCargo() {
		return cargo;
	}

}
