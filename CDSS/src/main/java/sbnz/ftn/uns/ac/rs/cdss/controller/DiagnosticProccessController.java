package sbnz.ftn.uns.ac.rs.cdss.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiagnosticTherapyDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiagnosticTherapyDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiseaseDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiseaseDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.security.TokenUtils;
import sbnz.ftn.uns.ac.rs.cdss.services.DiagnosticProccesService;
import sbnz.ftn.uns.ac.rs.cdss.services.DiseaseService;

@RestController
@RequestMapping("/diagnostic_proccess")
public class DiagnosticProccessController {

	@Autowired
	DiagnosticProccesService dpService;

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	TokenUtils tokenUtils;
	
	@GetMapping()
	public ResponseEntity<Page<DiagnosticTherapyDetailsDTO>> getAll(Pageable pageable) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		int limit = pageable.getPageSize() <= 25 ? pageable.getPageSize() : 25;
		Pageable newPageable = new PageRequest(pageable.getPageNumber(), limit);
		return new ResponseEntity<>(this.dpService.getAll(username, newPageable), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<DiagnosticTherapyDetailsDTO> getOne(@PathVariable Long id) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		return new ResponseEntity<>(this.dpService.getById(username, id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<DiagnosticTherapyDetailsDTO> createDiagnosticProcces(
			@RequestBody DiagnosticTherapyDTO dt) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		return new ResponseEntity<>(this.dpService.save(username, dt),
				HttpStatus.CREATED);
	}
	
	@PostMapping(value="validate")
	public ResponseEntity<DiagnosticTherapyDetailsDTO> validateProccess(
			@RequestBody DiagnosticTherapyDTO dt) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		return new ResponseEntity<>(this.dpService.validate(username, dt),
				HttpStatus.CREATED);
	}
	
}
