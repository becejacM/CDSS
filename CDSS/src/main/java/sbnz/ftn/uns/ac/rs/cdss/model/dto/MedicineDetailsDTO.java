package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import java.util.Collection;
import java.util.List;

import sbnz.ftn.uns.ac.rs.cdss.model.Medicine;
import sbnz.ftn.uns.ac.rs.cdss.model.MedicineIngredient;
import sbnz.ftn.uns.ac.rs.cdss.model.TypeOfMedicine;

public class MedicineDetailsDTO {

	private Long id;
	private String name;
	private TypeOfMedicine typeOfMedicine;
	private Collection<IngredientDetailsDTO> mi;
	public MedicineDetailsDTO() {
		
	}
	
	public MedicineDetailsDTO(Medicine m) {
		this.id = m.getId();
		this.name = m.getName();
		this.typeOfMedicine = m.getTypeOfMedicine();
		for(MedicineIngredient i : m.getIngredients()) {
			this.mi.add(new IngredientDetailsDTO(i));
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
	
	
}
