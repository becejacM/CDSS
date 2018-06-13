package sbnz.ftn.uns.ac.rs.cdss.model;

import java.util.ArrayList;
import java.util.Collection;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "diagnosticTherapy")
public class DiagnosticTherapy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disease_id") 
	private Disease disease;
	
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE
    })
    @JoinTable(name = "therapy_symptoms",
            joinColumns = { @JoinColumn(name = "therapy_id") },
            inverseJoinColumns = { @JoinColumn(name = "symptom_id") })
	private Collection<Symptom> symptoms = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE
            })
    @JoinTable(name = "therapy_medicines",
            joinColumns = { @JoinColumn(name = "therapy_id") },
            inverseJoinColumns = { @JoinColumn(name = "medicine_id") })
	private Collection<Medicine> cures = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "medicalRecord_id", nullable = false)
	protected MedicalRecord medicalRecord;
	
	public DiagnosticTherapy() {
		
	}

	public DiagnosticTherapy(Long id, Disease disease, Collection<Symptom> symptoms, Collection<Medicine> cures) {
		super();
		this.id = id;
		this.disease = disease;
		this.symptoms = symptoms;
		this.cures = cures;
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

	public Collection<Symptom> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(Collection<Symptom> symptoms) {
		this.symptoms = symptoms;
	}

	public Collection<Medicine> getCures() {
		return cures;
	}

	public void setCures(Collection<Medicine> cures) {
		this.cures = cures;
	}
	
	
}
