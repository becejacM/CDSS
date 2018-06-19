package sbnz.ftn.uns.ac.rs.cdss.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

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
import javax.persistence.Transient;

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
	private Collection<Medicine> medicines = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "medicalRecord_id", nullable = false)
	protected MedicalRecord medicalRecord;
	
	private String message;
	
	private Date date;
	
	@Transient
	private Collection<Disease> posibleDiseases = new ArrayList<>();
	
	public DiagnosticTherapy() {
		
	}

	public DiagnosticTherapy(Long id, Disease disease, Collection<Symptom> symptoms, Collection<Medicine> medicines, String message) {
		super();
		this.id = id;
		this.disease = disease;
		this.symptoms = symptoms;
		this.medicines = medicines;
		this.message = message;
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

	public Collection<Medicine> getMedicines() {
		return medicines;
	}

	public void setMedicines(Collection<Medicine> medicines) {
		this.medicines = medicines;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	

	public Collection<Disease> getPosibleDiseases() {
		return posibleDiseases;
	}

	public void setPosibleDiseases(Collection<Disease> posibleDiseases) {
		this.posibleDiseases = posibleDiseases;
	}

	@Override
	public String toString() {
		return "DiagnosticTherapy [id=" + id + ", disease=" + disease + ", message=" + message + "]";
	}

	
	public boolean checkDate(Integer num, String date, String beforeOrAfter) {
		Date today = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(today);
		if(date.equals("day")) {
			cal.add(Calendar.DAY_OF_MONTH, -num);
		}
		else if(date.equals("month")) {
			cal.add(Calendar.MONTH, -num);
		}
		Date todayBefore = cal.getTime();
		if(beforeOrAfter.equals("before")) {
			if (todayBefore.before(this.date)){ return true; }
		}
		else {
			if (todayBefore.after(this.date)){ return true; }
		}
		 

		return false;
	}
	
}
