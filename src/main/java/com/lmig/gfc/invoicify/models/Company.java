package com.lmig.gfc.invoicify.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

// This needs to be an entity
@Entity
public class Company {

	@Id// This needs an id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// This needs a name
	@Column(nullable = false)
	private String name;
	
	// This needs a list of invoice objects named invoices as one-to-many relationship mapped by "company"
	@OneToMany(mappedBy = "company")
	private List<Invoice> invoices;

	// Lots of getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	
}
