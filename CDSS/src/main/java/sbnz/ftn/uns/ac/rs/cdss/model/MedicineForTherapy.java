package sbnz.ftn.uns.ac.rs.cdss.model;

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
@Table(name = "medicineForTherapy")
public class MedicineForTherapy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    protected Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "therapy_id")
	protected DiagnosticTherapy diagnostictherapys;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "medicine_id")
	protected Medicine medicine;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "doctor_id")
	protected AppUser doctor;
	
	
	public MedicineForTherapy() {
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public DiagnosticTherapy getDiagnostictherapys() {
		return diagnostictherapys;
	}


	public void setDiagnostictherapys(DiagnosticTherapy diagnostictherapys) {
		this.diagnostictherapys = diagnostictherapys;
	}


	public Medicine getMedicine() {
		return medicine;
	}


	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}


	public AppUser getDoctor() {
		return doctor;
	}


	public void setDoctor(AppUser doctor) {
		this.doctor = doctor;
	}

	
	
	
}
