package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import java.util.ArrayList;
import java.util.Collection;

import sbnz.ftn.uns.ac.rs.cdss.model.Disease;
import sbnz.ftn.uns.ac.rs.cdss.model.Symptom;
import sbnz.ftn.uns.ac.rs.cdss.model.SymptomForDisease;
import sbnz.ftn.uns.ac.rs.cdss.model.TypeOfDisease;

public class DiseaseDTO {

	private String name;
	private TypeOfDisease typeOfDisease;
	private Collection<SymptomDTO> symptoms = new ArrayList<>();
	
	public DiseaseDTO() {
		
	}
	public DiseaseDTO(Disease d) {
		this.name = d.getName();
		this.typeOfDisease = d.getTypeOfDisease();
		for(SymptomForDisease sfd : d.getSymptomsForDisease()) {
			this.symptoms.add(new SymptomDTO(sfd.getSymptom()));
		}
	}
	public DiseaseDTO(String name, TypeOfDisease typeOfDisease) {
		super();
		this.name = name;
		this.typeOfDisease = typeOfDisease;
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
	public Collection<SymptomDTO> getSymptoms() {
		return symptoms;
	}
	public void setSymptoms(Collection<SymptomDTO> symptoms) {
		this.symptoms = symptoms;
	}
	
	
}
