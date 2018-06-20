package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import java.util.ArrayList;
import java.util.Collection;

import sbnz.ftn.uns.ac.rs.cdss.model.Patient;

public class ReportDTO {

	private PatientDTO patient;
	private String diseasename;
	private int numberOfTimes;
	
	public ReportDTO() {
		
	}

	

	public PatientDTO getPatient() {
		return patient;
	}



	public void setPatient(PatientDTO patient) {
		this.patient = patient;
	}



	public String getDiseasename() {
		return diseasename;
	}

	public void setDiseasename(String diseasename) {
		this.diseasename = diseasename;
	}

	public int getNumberOfTimes() {
		return numberOfTimes;
	}

	public void setNumberOfTimes(int numberOfTimes) {
		this.numberOfTimes = numberOfTimes;
	}

	
	
}
