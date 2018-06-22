package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import sbnz.ftn.uns.ac.rs.cdss.model.Symptom;
import sbnz.ftn.uns.ac.rs.cdss.model.TypeOfSymptoms;

public class SymptomDetailsDTO {

	private Long id;
	private String name;
	
	public SymptomDetailsDTO() {
		
	}
	
	public SymptomDetailsDTO(Symptom s) {
		this.id = s.getId();
		this.name = s.getName();
	}

	
	public SymptomDetailsDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
