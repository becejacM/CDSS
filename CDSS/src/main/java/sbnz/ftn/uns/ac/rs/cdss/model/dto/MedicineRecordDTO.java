package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import sbnz.ftn.uns.ac.rs.cdss.model.DiagnosticTherapy;
import sbnz.ftn.uns.ac.rs.cdss.model.MedicalRecord;
import sbnz.ftn.uns.ac.rs.cdss.model.Medicine;
import sbnz.ftn.uns.ac.rs.cdss.model.MedicineForTherapy;
import sbnz.ftn.uns.ac.rs.cdss.model.Symptom;
import sbnz.ftn.uns.ac.rs.cdss.model.SymptomForDisease;

public class MedicineRecordDTO {

	private String diseasename;
	private String syms;
	private String meds;
	private String date;
	private String doctor;
	
	public String getDiseasename() {
		return diseasename;
	}

	public void setDiseasename(String diseasename) {
		this.diseasename = diseasename;
	}

	public String getSyms() {
		return syms;
	}

	public void setSyms(String syms) {
		this.syms = syms;
	}

	public String getMeds() {
		return meds;
	}

	public void setMeds(String meds) {
		this.meds = meds;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public MedicineRecordDTO(String diseasename, String syms, String meds, String date) {
		super();
		this.diseasename = diseasename;
		this.syms = syms;
		this.meds = meds;
		this.date = date;
	}

	public MedicineRecordDTO() {
		
	}
	
	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public MedicineRecordDTO(DiagnosticTherapy dt) {
		this.diseasename = dt.getDisease().getName();
		this.syms="";
		for(Symptom sd : dt.getSymptoms()) {
			this.syms += ",";
			this.syms += sd.getName();
		}
		if(this.syms.startsWith(",")) {
			this.syms = this.syms.substring(1);
		}
		this.meds="";
		for(MedicineForTherapy sd : dt.getMedicines()) {
			this.meds += ",";
			this.meds += sd.getMedicine().getName();
		}
		if(this.meds.startsWith(",")) {
			this.meds = this.meds.substring(1);
		}
		if(this.syms.startsWith(",")) {
			this.syms = this.syms.substring(1);
		}
		this.date=dt.getDate().toString();
		for(MedicineForTherapy sd : dt.getMedicines()) {
			this.doctor=sd.getDoctor().getFirstname()+" "+sd.getDoctor().getLastname();
			break;
		}
	}
}
