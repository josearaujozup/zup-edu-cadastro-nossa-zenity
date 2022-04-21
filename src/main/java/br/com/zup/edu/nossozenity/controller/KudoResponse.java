package br.com.zup.edu.nossozenity.controller;

import java.time.format.DateTimeFormatter;

import br.com.zup.edu.nossozenity.zupper.Kudo;
import br.com.zup.edu.nossozenity.zupper.TipoKudo;

public class KudoResponse {
	
	private TipoKudo nome;
	private String criadoEm;
    private String enviadoPor;
    
	public KudoResponse(Kudo kudo) {
		this.nome = kudo.getNome();
		this.criadoEm = kudo.getCriadoEm().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		this.enviadoPor = kudo.getEnviado().getNome();
	}
    
	public KudoResponse() {
		
	}

	public TipoKudo getNome() {
		return nome;
	}

	public String getCriadoEm() {
		return criadoEm;
	}

	public String getEnviadoPor() {
		return enviadoPor;
	}
	
}
