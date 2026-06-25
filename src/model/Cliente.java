package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Cliente {
	
	private String nome;
	private String telefone;
	private String email;
	private String sexo;
	private LocalDate data;
	private int id;
	
	public Cliente(String nome, String telefone, String email, String sexo,LocalDate data) {
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.sexo = sexo;
		this.data = data;
	}
	
	
	
	/**
	 * @param nome
	 * @param telefone
	 * @param email
	 * @param sexo
	 * @param data
	 * @param id
	 */

	public Cliente(int id, String nome, 
			String telefone, String email, String sexo,LocalDate data) {
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.sexo = sexo;
		this.data = data;
		this.id = id;
	}
	public LocalDate getData() {
		return this.data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public String formatar_data() {
		
		DateTimeFormatter formato =
				DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String dataFormatada = this.data.format(formato);
		return dataFormatada;
	}
	
	
	public int getId() {
		return this.id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	

}
