package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import sbnz.ftn.uns.ac.rs.cdss.model.MedicineIngredient;

public class IngredientDTO {
	
	private String name;
	
	public IngredientDTO() {
		
	}
	
	public IngredientDTO(MedicineIngredient mi) {
		this.name = mi.getName();
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
