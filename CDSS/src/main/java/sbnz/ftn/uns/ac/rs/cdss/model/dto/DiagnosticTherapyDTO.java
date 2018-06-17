package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import java.util.ArrayList;
import java.util.Collection;

import sbnz.ftn.uns.ac.rs.cdss.model.DiagnosticTherapy;
import sbnz.ftn.uns.ac.rs.cdss.model.Medicine;
import sbnz.ftn.uns.ac.rs.cdss.model.Symptom;

public class DiagnosticTherapyDTO {

	private Long patientId;
	private Collection<SymptomDTO> symptoms = new ArrayList<>();
	private Collection<MedicineDTO> medicines = new ArrayList<>();
	private String diseasename;
	private String message;
	public DiagnosticTherapyDTO() {
		
	}

	public DiagnosticTherapyDTO(DiagnosticTherapy d) {
		super();
		this.patientId = d.getMedicalRecord().getPatient().getId();
		
		for(Symptom s: d.getSymptoms()) {
			this.symptoms.add(new SymptomDTO(s));
		}
		for(Medicine m : d.getMedicines()) {
			this.medicines.add(new MedicineDTO(m));
			
		}
		this.diseasename = d.getDisease().getName();
		this.message = d.getMessage();
	}
	public DiagnosticTherapyDTO(Long patientId, Collection<SymptomDTO> symptoms, Collection<MedicineDTO> medicines,
			String diseasename) {
		super();
		this.patientId = patientId;
		this.symptoms = symptoms;
		this.medicines = medicines;
		this.diseasename = diseasename;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Collection<SymptomDTO> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(Collection<SymptomDTO> symptoms) {
		this.symptoms = symptoms;
	}

	public Collection<MedicineDTO> getMedicines() {
		return medicines;
	}

	public void setMedicines(Collection<MedicineDTO> medicines) {
		this.medicines = medicines;
	}

	public String getDiseasename() {
		return diseasename;
	}

	public void setDiseasename(String diseasename) {
		this.diseasename = diseasename;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
