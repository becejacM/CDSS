package sbnz.ftn.uns.ac.rs.cdss.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sbnz.ftn.uns.ac.rs.cdss.model.dto.AlergiesDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.MedicineDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.MedicineDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.PatientDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.PatientDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.security.TokenUtils;
import sbnz.ftn.uns.ac.rs.cdss.services.PatientService;

@RestController
@RequestMapping("/patients")
public class PatientController {

	@Autowired
	PatientService patientService;

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	TokenUtils tokenUtils;

	@GetMapping()
	public ResponseEntity<Page<PatientDetailsDTO>> getAllPatients(Pageable pageable) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		int limit = pageable.getPageSize() <= 25 ? pageable.getPageSize() : 25;
		Pageable newPageable = new PageRequest(pageable.getPageNumber(), limit);
		return new ResponseEntity<>(this.patientService.getAllPatients(username, newPageable), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<PatientDetailsDTO> getPatient(@PathVariable Long id) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		return new ResponseEntity<>(this.patientService.getById(username, id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PatientDetailsDTO> createPatient(
			@RequestBody PatientDTO patient) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		return new ResponseEntity<>(this.patientService.save(username, patient),
				HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<PatientDetailsDTO> updatePatient(@RequestBody PatientDTO patient,
			@PathVariable Long id) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		return new ResponseEntity<>(this.patientService.update(username, patient, id),
				HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deletePatient(@PathVariable Long id) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		this.patientService.deletePatient(username, id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@GetMapping(value = "alergies/{id}")
	public ResponseEntity<AlergiesDetailsDTO> getPatientAlergies(@PathVariable Long id) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		return new ResponseEntity<>(this.patientService.getAlergies(username, id), HttpStatus.OK);
	}
	
	@PutMapping(value="/alergies/{id}/{nameAlergie}")
	public ResponseEntity<MedicineDetailsDTO> addAlergie(@PathVariable Long id,@PathVariable String nameAlergie) {
		System.out.println(nameAlergie);
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		return new ResponseEntity<MedicineDetailsDTO>(this.patientService.addAlergie(username, id, nameAlergie),
				HttpStatus.CREATED);
	}
	//Dijagnostikovanje procesa
}
