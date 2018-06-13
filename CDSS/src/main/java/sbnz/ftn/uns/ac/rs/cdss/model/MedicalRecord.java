package sbnz.ftn.uns.ac.rs.cdss.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "medicalRecord")
public class MedicalRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "medicalRecord")
    private Collection<DiagnosticTherapy> therapy = new ArrayList<DiagnosticTherapy>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;
    
    @ManyToMany
    @JoinTable(name = "medicine_alergies_table", joinColumns = @JoinColumn(name = "medicalRecord_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "medicine_id", referencedColumnName = "id"))
    private Collection<Medicine> medicines= new ArrayList<Medicine>();;
    
    @ManyToMany
    @JoinTable(name = "ingredient_alergies_table", joinColumns = @JoinColumn(name = "medicalRecord_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id"))
    private Collection<MedicineIngredient> ingredients=new ArrayList<MedicineIngredient>();;
    
    public MedicalRecord() {
    	
    }


	public MedicalRecord(Long id, Set<DiagnosticTherapy> therapy, Patient patient) {
		super();
		this.id = id;
		this.therapy = therapy;
		this.patient = patient;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Collection<DiagnosticTherapy> getTherapy() {
		return therapy;
	}


	public void setTherapy(Set<DiagnosticTherapy> therapy) {
		this.therapy = therapy;
	}


	public Patient getPatient() {
		return patient;
	}


	public void setPatient(Patient patient) {
		this.patient = patient;
	}


	public Collection<Medicine> getMedicines() {
		return medicines;
	}


	public void setMedicines(Collection<Medicine> medicines) {
		this.medicines = medicines;
	}


	public Collection<MedicineIngredient> getIngredients() {
		return ingredients;
	}


	public void setIngredients(Collection<MedicineIngredient> ingredients) {
		this.ingredients = ingredients;
	}
    
	
    
}
