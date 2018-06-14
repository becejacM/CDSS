package sbnz.ftn.uns.ac.rs.cdss.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiseaseDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiseaseDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.SymptomDetailsDTO;

public interface DiseaseService {

	Page<DiseaseDetailsDTO> getAllDiseases(String username, Pageable pageable);
	DiseaseDetailsDTO getById(String username, Long id);
	DiseaseDetailsDTO save(String username, DiseaseDTO disease);
	DiseaseDetailsDTO update(String username, DiseaseDTO disease, Long id);
	void delete(String username, Long id);
	
	SymptomDetailsDTO checkSymptom(String username, String name);
}
