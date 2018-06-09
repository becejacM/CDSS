package sbnz.ftn.uns.ac.rs.cdss.controller;

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

import sbnz.ftn.uns.ac.rs.cdss.model.dto.IngredientDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.IngredientDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.security.TokenUtils;
import sbnz.ftn.uns.ac.rs.cdss.services.IngredientService;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

	@Autowired
	IngredientService iService;

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	TokenUtils tokenUtils;
	
	@GetMapping()
	public ResponseEntity<Page<IngredientDetailsDTO>> getAllMedicineIngredients(Pageable pageable) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		int limit = pageable.getPageSize() <= 25 ? pageable.getPageSize() : 5;
		Pageable newPageable = new PageRequest(pageable.getPageNumber(), limit);
		return new ResponseEntity<>(this.iService.getAll(username, newPageable), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<IngredientDetailsDTO> getMedicineIngredient(@PathVariable Long id) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		return new ResponseEntity<>(this.iService.getById(username, id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<IngredientDetailsDTO> createMedicineIngredient(
			@RequestBody IngredientDTO i) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		return new ResponseEntity<>(this.iService.save(username, i),
				HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<IngredientDetailsDTO> updateMedicineIngredient(@RequestBody IngredientDTO i,
			@PathVariable Long id) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		return new ResponseEntity<>(this.iService.update(username, i, id),
				HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteMedicineIngredient(@PathVariable Long id) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		this.iService.delete(username, id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
