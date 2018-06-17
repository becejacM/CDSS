package sbnz.ftn.uns.ac.rs.cdss.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiagnosticTherapyDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiagnosticTherapyDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiseaseDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiseaseDetailsDTO;

@Service
public interface DiagnosticProccesService {

	Page<DiagnosticTherapyDetailsDTO> getAll(String username, Pageable pageable);
	DiagnosticTherapyDetailsDTO getById(String username, Long id);
	DiagnosticTherapyDetailsDTO save(String username, DiagnosticTherapyDTO disease);
	DiagnosticTherapyDetailsDTO validate(String username, DiagnosticTherapyDTO disease);

	void sendMessage(String message);
}
