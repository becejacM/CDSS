package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import sbnz.ftn.uns.ac.rs.cdss.model.Patient;

public class PatientDetailsDTO {

	private Long id;
	private String firstname;
	private String lastname;
	private String email;
	private String address;
	private String medicalCardNumber;
	
	public PatientDetailsDTO() {
		super();
	}
	
	public PatientDetailsDTO(Patient p) {
		this.id = p.getId();
		this.firstname = p.getFirstname();
		this.lastname = p.getLastname();
		this.email = p.getEmail();
		this.address = p.getAddress();
		this.medicalCardNumber = p.getMedicalCardNumber();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMedicalCardNumber() {
		return medicalCardNumber;
	}

	public void setMedicalCardNumber(String medicalCardNumber) {
		this.medicalCardNumber = medicalCardNumber;
	}
	
	
	
}
