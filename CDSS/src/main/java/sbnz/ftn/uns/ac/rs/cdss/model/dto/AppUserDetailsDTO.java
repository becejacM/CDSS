package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import sbnz.ftn.uns.ac.rs.cdss.model.AppUser;
import sbnz.ftn.uns.ac.rs.cdss.model.UserRole;

public class AppUserDetailsDTO {

	private Long id;
	private String firstname;
	private String lastname;
	private String email;
	private UserRole role;
	
	public AppUserDetailsDTO() {
		
	}
	public AppUserDetailsDTO(AppUser user) {
		super();
		this.id = user.getId();
		this.firstname = user.getFirstname();
		this.lastname = user.getLastname();
		this.email = user.getEmail();
		this.role = user.getRole();
	}
	public AppUserDetailsDTO(Long id, String firstname, String lastname, String email, UserRole role) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.role = role;
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
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	
	
	
}
