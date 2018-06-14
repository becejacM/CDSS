package sbnz.ftn.uns.ac.rs.cdss.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.id.insert.InsertGeneratedIdentifierDelegate;

import sbnz.ftn.uns.ac.rs.cdss.model.dto.IngredientDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.MedicineDTO;

@Entity
@Table(name = "medicine")
public class Medicine {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "name", nullable = true)
	protected String name;
	
	@Column(name = "typeOfMedicine", nullable = false)
	@Enumerated(EnumType.STRING)
	protected TypeOfMedicine typeOfMedicine;
	
	@ManyToMany
    @JoinTable(name = "medicine_ingredient_table", joinColumns = @JoinColumn(name = "medicine_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "medicineIngredient_id", referencedColumnName = "id"))
    private Collection<MedicineIngredient> ingredients = new ArrayList<>();
	
	@ManyToMany(mappedBy = "medicines")
    private Collection<MedicalRecord> medicalRecords;
	
	public Medicine() {
		
	}

	public Medicine(MedicineDTO m) {
		this.name = m.getName();
		this.typeOfMedicine = m.getTypeOfMedicine();
		/*for(IngredientDTO i : m.getMi()) {
			this.ingredients.add(new MedicineIngredient(i));
		}*/
	}
	public Medicine(Long id, String name, TypeOfMedicine typeOfMedicine,
			Collection<MedicineIngredient> ingredients) {
		super();
		this.id = id;
		this.name = name;
		this.typeOfMedicine = typeOfMedicine;
		this.ingredients = ingredients;
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

	public Collection<MedicineIngredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Collection<MedicineIngredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	

	public Collection<MedicalRecord> getMedicalRecords() {
		return medicalRecords;
	}

	public void setMedicalRecords(Collection<MedicalRecord> medicalRecords) {
		this.medicalRecords = medicalRecords;
	}

	@Override
	public String toString() {
		return "Medicine [id=" + id + ", name=" + name + ", typeOfMedicine=" + typeOfMedicine + ", ingredients="
				+ ingredients + ", medicalRecords=" + medicalRecords + "]";
	}

	
	
	
}
