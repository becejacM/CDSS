package sbnz.ftn.uns.ac.rs.cdss.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "medicineIngredient")
public class MedicineIngredient {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "name", nullable = true)
	protected String name;
	
	@ManyToMany(mappedBy = "ingredients")
    private Collection<Medicine> medicines;
	
	public MedicineIngredient() {
		
	}

	public MedicineIngredient(Long id, String name, Collection<Medicine> medicines) {
		super();
		this.id = id;
		this.name = name;
		this.medicines = medicines;
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

	public Collection<Medicine> getMedicines() {
		return medicines;
	}

	public void setMedicines(Collection<Medicine> medicines) {
		this.medicines = medicines;
	}

	@Override
	public String toString() {
		return "MedicineIngredient [id=" + id + ", name=" + name + ", medicines=" + medicines + "]";
	}

	
	
}
