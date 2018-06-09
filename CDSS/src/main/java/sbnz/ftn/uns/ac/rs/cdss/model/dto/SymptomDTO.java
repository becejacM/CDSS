package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import sbnz.ftn.uns.ac.rs.cdss.model.Symptom;

public class SymptomDTO {

	private String name;

	public SymptomDTO() {

	}

	public SymptomDTO(Symptom s) {
		this.name = s.getName();
	}

	public SymptomDTO(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
