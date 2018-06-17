package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import java.util.ArrayList;
import java.util.Collection;

import sbnz.ftn.uns.ac.rs.cdss.model.DiagnosticTherapy;
import sbnz.ftn.uns.ac.rs.cdss.model.Medicine;
import sbnz.ftn.uns.ac.rs.cdss.model.Symptom;

public class DiagnosticTherapyDetailsDTO {

	private Long id;
	private Collection<SymptomDetailsDTO> symptoms = new ArrayList<>();
	private String syms;
	private Collection<MedicineDetailsDTO> medicines = new ArrayList<>();
	private String meds;
	private String diseasename;
	private String message;
	
	public DiagnosticTherapyDetailsDTO() {
		
	}

	public DiagnosticTherapyDetailsDTO(DiagnosticTherapy d) {
		super();
		this.id = d.getId();
		syms="";
		meds="";
		for(Symptom s: d.getSymptoms()) {
			this.symptoms.add(new SymptomDetailsDTO(s));
			this.syms+=",";
			this.syms+=s.getName();
		}
		for(Medicine m : d.getMedicines()) {
			this.medicines.add(new MedicineDetailsDTO(m));
			this.meds+=",";
			this.meds+=m.getName();
			
		}
		if(this.syms.startsWith(",")) {
			this.syms = this.syms.substring(1);
		}
		if(this.meds.startsWith(",")) {
			this.meds = this.meds.substring(1);
		}
		this.diseasename = d.getDisease().getName();
		this.message = d.getMessage();
	}

	
	public DiagnosticTherapyDetailsDTO(Long id, Long patientId, Collection<SymptomDetailsDTO> symptoms, Collection<MedicineDetailsDTO> medicines,
			String diseasename) {
		super();
		this.id = id;
		this.symptoms = symptoms;
		this.medicines = medicines;
		this.diseasename = diseasename;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<SymptomDetailsDTO> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(Collection<SymptomDetailsDTO> symptoms) {
		this.symptoms = symptoms;
	}

	public String getSyms() {
		return syms;
	}

	public void setSyms(String syms) {
		this.syms = syms;
	}

	public Collection<MedicineDetailsDTO> getMedicines() {
		return medicines;
	}

	public void setMedicines(Collection<MedicineDetailsDTO> medicines) {
		this.medicines = medicines;
	}

	public String getMeds() {
		return meds;
	}

	public void setMeds(String meds) {
		this.meds = meds;
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
