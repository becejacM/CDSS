package sbnz.ftn.uns.ac.rs.cdss.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sbnz.ftn.uns.ac.rs.cdss.security.TokenUtils;

@RestController
@RequestMapping("/reporting")
public class ReportingController {

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	TokenUtils tokenUtils;
	
	@PostMapping
	public ResponseEntity<?> startReporting() {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		// TODO pocni izvestavanje i salji izvestaje
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
