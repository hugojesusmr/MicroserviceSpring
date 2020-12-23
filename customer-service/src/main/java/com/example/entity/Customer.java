package com.example.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name="tbl_customers")
public class Customer implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="El numero de documento no puede ser vacio")
	@Size(min=8, max=8, message = "El tamaño del documeto debe de ser de 8")
	@Column(name="number_id", unique=true, length = 8, nullable = false)
	private String numberID;
	
	@NotEmpty(message = "El nombre no puede ser vacio")
	@Column(name="first_name", nullable = false)
	private String firstName;
	
	@NotEmpty(message = "El apellido no puede ser vacio")
	@Column(name="last_name", nullable = false)
	private String lastName;
	
	@NotEmpty(message = "El email no puede ser vacio")
	@Email(message = "No es una dirección de correo bien formada")
	@Column(unique = true, nullable = false)
	private String email;
	
	@NotNull(message = "La region no puede ser vacia")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Region region;
	
	private String state;
}
