package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import java.util.ArrayList;
import java.util.Collection;

public class AlergiesDetailsDTO {

	private Collection<MedicineDetailsDTO> medicines = new ArrayList<>();
	private Collection<IngredientDetailsDTO> ingredients = new ArrayList<>();

	public AlergiesDetailsDTO() {
		
	}

	public AlergiesDetailsDTO(Collection<MedicineDetailsDTO> medicines, Collection<IngredientDetailsDTO> ingredients) {
		super();
		this.medicines = medicines;
		this.ingredients = ingredients;
	}

	public Collection<MedicineDetailsDTO> getMedicines() {
		return medicines;
	}

	public void setMedicines(Collection<MedicineDetailsDTO> medicines) {
		this.medicines = medicines;
	}

	public Collection<IngredientDetailsDTO> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Collection<IngredientDetailsDTO> ingredients) {
		this.ingredients = ingredients;
	}
	
	
	
}
