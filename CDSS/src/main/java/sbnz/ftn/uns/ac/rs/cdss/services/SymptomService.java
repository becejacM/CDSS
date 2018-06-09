package sbnz.ftn.uns.ac.rs.cdss.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sbnz.ftn.uns.ac.rs.cdss.model.dto.SymptomDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.SymptomDetailsDTO;

public interface SymptomService {

	Page<SymptomDetailsDTO> getAll(String username, Pageable pageable);
	SymptomDetailsDTO getById(String username, Long id);
	SymptomDetailsDTO save(String username, SymptomDTO symptom);
	SymptomDetailsDTO update(String username, SymptomDTO symptom, Long id);
	void delete(String username, Long id);
}
