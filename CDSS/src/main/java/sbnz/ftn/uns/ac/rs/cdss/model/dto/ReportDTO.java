package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import java.util.ArrayList;
import java.util.Collection;

import sbnz.ftn.uns.ac.rs.cdss.model.Patient;

public class ReportDTO {

	private PatientDTO patient;
	private String diseasename;
	private String painkiller;
	private String doctor;
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

	
	public String getPainkiller() {
		return painkiller;
	}



	public void setPainkiller(String painkiller) {
		this.painkiller = painkiller;
	}



	public int getNumberOfTimes() {
		return numberOfTimes;
	}

	public void setNumberOfTimes(int numberOfTimes) {
		this.numberOfTimes = numberOfTimes;
	}



	public String getDoctor() {
		return doctor;
	}



	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	
	
}
