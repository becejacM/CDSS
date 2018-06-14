package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import java.util.ArrayList;
import java.util.Collection;

import sbnz.ftn.uns.ac.rs.cdss.model.Medicine;
import sbnz.ftn.uns.ac.rs.cdss.model.MedicineIngredient;
import sbnz.ftn.uns.ac.rs.cdss.model.TypeOfMedicine;

public class MedicineDTO {

	private String name;
	private TypeOfMedicine typeOfMedicine;
	private Collection<IngredientDTO> mi=new ArrayList<>();
	public MedicineDTO() {
		
	}
	
	public MedicineDTO(Medicine m) {
		this.name = m.getName();
		this.typeOfMedicine = m.getTypeOfMedicine();
		for(MedicineIngredient i : m.getIngredients()) {
			this.mi.add(new IngredientDTO(i));
		}
	}

	public MedicineDTO(String name, TypeOfMedicine typeOfMedicine, Collection<IngredientDTO> mi) {
		super();
		this.name = name;
		this.typeOfMedicine = typeOfMedicine;
		this.mi = mi;
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

	public Collection<IngredientDTO> getMi() {
		return mi;
	}

	public void setMi(Collection<IngredientDTO> mi) {
		this.mi = mi;
	}

	@Override
	public String toString() {
		return "MedicineDTO [name=" + name + ", typeOfMedicine=" + typeOfMedicine + ", mi=" + mi + "]";
	}


	
}
