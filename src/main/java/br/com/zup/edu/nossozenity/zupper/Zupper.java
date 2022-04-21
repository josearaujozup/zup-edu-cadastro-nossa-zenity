package br.com.zup.edu.nossozenity.zupper;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Zupper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cargo;


    @Column(nullable = false)
    private LocalDate dataAdmissao;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "dono")
    private List<Certificado> certificados= new ArrayList<>();

    @OneToMany(mappedBy = "recebido")
    private List<Kudo> kudosRecebidos= new ArrayList<>();

    @OneToMany(mappedBy = "donoHabilidade")
    private List<Habilidade> habilidades= new ArrayList<>();

    public Zupper(String nome, String cargo, LocalDate dataAdmissao, String email) {
        this.nome = nome;
        this.cargo = cargo;
        this.dataAdmissao = dataAdmissao;
        this.email = email;
    }


    /**
     * @deprecated construtor de uso exclusivo para o hibernate
     */
    @Deprecated
    public Zupper() {
    }

    public Long getId() {
        return id;
    }

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}


	public String getNome() {
		return nome;
	}


	public String getCargo() {
		return cargo;
	}


	public List<Certificado> getCertificados() {
		return certificados;
	}


	public List<Kudo> getKudosRecebidos() {
		return kudosRecebidos;
	}
	
	
	public List<Habilidade> getHabilidades() {
		return habilidades;
	}
	
	
	public String getTempoCasa() {
		Period periodo = Period.between(dataAdmissao, LocalDate.now());
		return "Anos: " + periodo.getYears();
	}
	
}
