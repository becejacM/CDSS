package sbnz.ftn.uns.ac.rs.cdss.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "symptomForDisease")
public class SymptomForDisease {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    protected Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "disease_id")
	protected Disease disease;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "symptom_id")
	protected Symptom symptom;
	
	@Column(name = "typeOfSymptom", nullable = false)
	@Enumerated(EnumType.STRING)
	protected TypeOfSymptoms typeOfSymptoms;
	
	public SymptomForDisease() {
		
	}

	public SymptomForDisease(Long id, Disease disease, Symptom symptom, TypeOfSymptoms typeOfSymptoms) {
		super();
		this.id = id;
		this.disease = disease;
		this.symptom = symptom;
		this.typeOfSymptoms = typeOfSymptoms;
	}


	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Disease getDisease() {
		return disease;
	}

	public void setDisease(Disease disease) {
		this.disease = disease;
	}

	public Symptom getSymptom() {
		return symptom;
	}

	public void setSymptom(Symptom symptom) {
		this.symptom = symptom;
	}

	public TypeOfSymptoms getTypeOfSymptoms() {
		return typeOfSymptoms;
	}

	public void setTypeOfSymptoms(TypeOfSymptoms typeOfSymptoms) {
		this.typeOfSymptoms = typeOfSymptoms;
	}

	@Override
	public String toString() {
		return "SymptomForDisease [id=" + id + ", disease=" + disease + ", symptom=" + symptom + ", typeOfSymptoms="
				+ typeOfSymptoms + "]";
	}
	
	
}
