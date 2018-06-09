package sbnz.ftn.uns.ac.rs.cdss.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sbnz.ftn.uns.ac.rs.cdss.model.Patient;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.PatientDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.PatientDetailsDTO;


public interface PatientService {

	Page<PatientDetailsDTO> getAllPatients(String username, Pageable pageable);
	PatientDetailsDTO getById(String username, Long id);
	PatientDetailsDTO save(String username, PatientDTO patient);
	PatientDetailsDTO update(String username, PatientDTO patient, Long id);
	void deletePatient(String username, Long id);
}
