package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import sbnz.ftn.uns.ac.rs.cdss.model.Medicine;
import sbnz.ftn.uns.ac.rs.cdss.model.TypeOfMedicine;

public class MedicineDetailsDTO {

	private Long id;
	private String name;
	private TypeOfMedicine typeOfMedicine;
	
	public MedicineDetailsDTO() {
		
	}
	
	public MedicineDetailsDTO(Medicine m) {
		this.id = m.getId();
		this.name = m.getName();
		this.typeOfMedicine = m.getTypeOfMedicine();
	}

	public MedicineDetailsDTO(Long id, String name, TypeOfMedicine typeOfMedicine) {
		super();
		this.id = id;
		this.name = name;
		this.typeOfMedicine = typeOfMedicine;
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
	
	
}
