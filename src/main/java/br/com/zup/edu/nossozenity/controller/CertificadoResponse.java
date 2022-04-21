package br.com.zup.edu.nossozenity.controller;

import br.com.zup.edu.nossozenity.zupper.Certificado;

public class CertificadoResponse {
	
	private String nome;
	private String instituicaoEmissora;
	private String link;
	private String tipo;
	 
	public CertificadoResponse(Certificado certificado) {
		this.nome = certificado.getNome();
		this.instituicaoEmissora = certificado.getInstituicaoEmissora();
		this.link = certificado.getLink();
		this.tipo = certificado.getTipo();
	}
	 
	public CertificadoResponse() {
		
	}

	public String getNome() {
		return nome;
	}

	public String getInstituicaoEmissora() {
		return instituicaoEmissora;
	}

	public String getLink() {
		return link;
	}

	public String getTipo() {
		return tipo;
	}
	
}
