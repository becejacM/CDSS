package sbnz.ftn.uns.ac.rs.cdss.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;

import sbnz.ftn.uns.ac.rs.cdss.model.dto.PatientDTO;

@Entity
@Table(name = "patient")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;
	
	@Column(name = "email", unique = true, nullable = false)
	@Email
	protected String email;

	@Column(name = "firstname", nullable = true)
	protected String firstname;

	@Column(name = "lastname", nullable = true)
	protected String lastname;
	
	@Column(name = "address", nullable = true)
	protected String address;

	@Column(name = "medicalCardNumber", nullable = true)
	protected String medicalCardNumber;
	
	public Patient() {
		
	}
	
	public Patient(PatientDTO p) {
		this.firstname = p.getFirstname();
		this.lastname = p.getLastname();
		this.email = p.getEmail();
		this.address = p.getAddress();
		this.medicalCardNumber = p.getMedicalCardNumber();
	}
	public Patient(Long id, @Email String email, String firstname, String lastname, String address,
			String medicalCardNumber) {
		super();
		this.id = id;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
		this.medicalCardNumber = medicalCardNumber;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public String toString() {
		return "Patient [id=" + id + ", email=" + email + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", address=" + address + ", medicalCardNumber=" + medicalCardNumber + "]";
	}
	
	
}
