package sbnz.ftn.uns.ac.rs.cdss.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sbnz.ftn.uns.ac.rs.cdss.model.dto.MedicineDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.MedicineDetailsDTO;

public interface MedicineService {

	Page<MedicineDetailsDTO> getAll(String username, Pageable pageable);
	MedicineDetailsDTO getById(String username, Long id);
	MedicineDetailsDTO save(String username, MedicineDTO symptom);
	MedicineDetailsDTO update(String username, MedicineDTO symptom, Long id);
	void delete(String username, Long id);
}
