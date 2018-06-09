package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import java.util.ArrayList;
import java.util.List;

import sbnz.ftn.uns.ac.rs.cdss.model.Disease;
import sbnz.ftn.uns.ac.rs.cdss.model.SymptomForDisease;
import sbnz.ftn.uns.ac.rs.cdss.model.TypeOfDisease;

public class DiseaseDetailsDTO {

	private Long id;
	private String name;
	private TypeOfDisease typeOfDisease;
	private List<SymptomDetailsDTO> symptoms = new ArrayList<SymptomDetailsDTO>();
	
	public DiseaseDetailsDTO() {
		
	}
	
	public DiseaseDetailsDTO(Disease d) {
		this.id = d.getId();
		this.name = d.getName();
		this.typeOfDisease = d.getTypeOfDisease();
		for(SymptomForDisease sd : d.getSymptomsForDisease()) {
			this.symptoms.add(new SymptomDetailsDTO(sd.getSymptom()));
		}
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

	public TypeOfDisease getTypeOfDisease() {
		return typeOfDisease;
	}

	public void setTypeOfDisease(TypeOfDisease typeOfDisease) {
		this.typeOfDisease = typeOfDisease;
	}

	public List<SymptomDetailsDTO> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(List<SymptomDetailsDTO> symptoms) {
		this.symptoms = symptoms;
	}
	
	
}

