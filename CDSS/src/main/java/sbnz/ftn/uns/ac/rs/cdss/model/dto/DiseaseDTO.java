package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import sbnz.ftn.uns.ac.rs.cdss.model.Disease;
import sbnz.ftn.uns.ac.rs.cdss.model.TypeOfDisease;

public class DiseaseDTO {

	private String name;
	private TypeOfDisease typeOfDisease;
	
	public DiseaseDTO() {
		
	}
	public DiseaseDTO(Disease d) {
		this.name = d.getName();
		this.typeOfDisease = d.getTypeOfDisease();
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
	
	
}
