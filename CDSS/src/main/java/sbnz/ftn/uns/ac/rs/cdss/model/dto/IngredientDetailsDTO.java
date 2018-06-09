package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import sbnz.ftn.uns.ac.rs.cdss.model.MedicineIngredient;

public class IngredientDetailsDTO {

	private Long id;
	private String name;
	
	public IngredientDetailsDTO() {
		
	}
	
	public IngredientDetailsDTO(MedicineIngredient mi) {
		this.id = mi.getId();
		this.name = mi.getName();
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
