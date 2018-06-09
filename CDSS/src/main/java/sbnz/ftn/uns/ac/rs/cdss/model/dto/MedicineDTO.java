package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import sbnz.ftn.uns.ac.rs.cdss.model.Medicine;
import sbnz.ftn.uns.ac.rs.cdss.model.TypeOfMedicine;

public class MedicineDTO {

	private String name;
	private TypeOfMedicine typeOfMedicine;
	
	public MedicineDTO() {
		
	}
	
	public MedicineDTO(Medicine m) {
		this.name = m.getName();
		this.typeOfMedicine = m.getTypeOfMedicine();
	}

	public MedicineDTO( String name, TypeOfMedicine typeOfMedicine) {
		super();
		this.name = name;
		this.typeOfMedicine = typeOfMedicine;
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
	
}
