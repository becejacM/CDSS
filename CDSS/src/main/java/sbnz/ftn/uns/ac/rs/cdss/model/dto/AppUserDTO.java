package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import sbnz.ftn.uns.ac.rs.cdss.model.AppUser;
import sbnz.ftn.uns.ac.rs.cdss.model.UserRole;

public class AppUserDTO {

	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private String username;
	private UserRole role;
	
	public AppUserDTO() {
		
	}
	public AppUserDTO(AppUser user) {
		super();
		this.firstname = user.getFirstname();
		this.lastname = user.getLastname();
		this.email = user.getEmail();
		this.role = user.getRole();
		this.username = user.getUsername();
		this.password = user.getPassword();
	}
	public AppUserDTO(String firstname, String lastname, String email, String password, String username,
			UserRole role) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.username = username;
		this.role = role;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "AppUserDTO [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", password="
				+ password + ", username=" + username + ", role=" + role + "]";
	}
	
	
}
