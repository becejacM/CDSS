package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sbnz.ftn.uns.ac.rs.cdss.model.Medicine;
import sbnz.ftn.uns.ac.rs.cdss.model.MedicineIngredient;
import sbnz.ftn.uns.ac.rs.cdss.model.TypeOfMedicine;

public class MedicineDetailsDTO {

	private Long id;
	private String name;
	private TypeOfMedicine typeOfMedicine;
	private Collection<IngredientDetailsDTO> mi=new ArrayList<>();
	private String ingredients;
	
	public MedicineDetailsDTO() {
		
	}
	
	public MedicineDetailsDTO(Medicine m) {
		this.id = m.getId();
		this.name = m.getName();
		this.typeOfMedicine = m.getTypeOfMedicine();
		this.ingredients="";
		for(MedicineIngredient i : m.getIngredients()) {
			this.mi.add(new IngredientDetailsDTO(i));
			this.ingredients+=",";
			this.ingredients+=i.getName();
		}
		if(this.ingredients.startsWith(",")) {
			this.ingredients = this.ingredients.substring(1);
		}
		
	}

	

	public MedicineDetailsDTO(Long id, String name, TypeOfMedicine typeOfMedicine, Collection<IngredientDetailsDTO> mi) {
		super();
		this.id = id;
		this.name = name;
		this.typeOfMedicine = typeOfMedicine;
		this.mi = mi;
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

	public TypeOfMedicine getTypeOfMedicine() {
		return typeOfMedicine;
	}

	public void setTypeOfMedicine(TypeOfMedicine typeOfMedicine) {
		this.typeOfMedicine = typeOfMedicine;
	}

	public Collection<IngredientDetailsDTO> getMi() {
		return mi;
	}

	public void setMi(Collection<IngredientDetailsDTO> mi) {
		this.mi = mi;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	
	
}
