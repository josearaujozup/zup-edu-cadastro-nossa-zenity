package br.com.zup.edu.nossozenity.controller;

import java.util.List;
import java.util.stream.Collectors;

import br.com.zup.edu.nossozenity.zupper.Certificado;
import br.com.zup.edu.nossozenity.zupper.Habilidade;
import br.com.zup.edu.nossozenity.zupper.Kudo;
import br.com.zup.edu.nossozenity.zupper.Zupper;

public class ZupperResponseDTO {
	
	private String nome;
	private String cargo;
	private String tempoCasa;
	
	private List<CertificadoResponse> certificados;
	private List<KudoResponse> kudosRecebidos;
	private List<HabilidadeResponse> habilidades;
	
	public ZupperResponseDTO(Zupper zupper) {
		this.nome = zupper.getNome();
		this.cargo = zupper.getCargo();
		this.tempoCasa = zupper.getTempoCasa();
		this.certificados = zupper.getCertificados().stream().map(CertificadoResponse::new).collect(Collectors.toList());
		this.kudosRecebidos = zupper.getKudosRecebidos().stream().map(KudoResponse::new).collect(Collectors.toList());
		this.habilidades = zupper.getHabilidades().stream().map(HabilidadeResponse::new).collect(Collectors.toList());
	}
	
	public ZupperResponseDTO() {
		
	}

	public String getNome() {
		return nome;
	}

	public String getCargo() {
		return cargo;
	}

	public String getTempoCasa() {
		return tempoCasa;
	}

	public List<CertificadoResponse> getCertificados() {
		return certificados;
	}

	public List<KudoResponse> getKudosRecebidos() {
		return kudosRecebidos;
	}

	public List<HabilidadeResponse> getHabilidades() {
		return habilidades;
	}
	
}
