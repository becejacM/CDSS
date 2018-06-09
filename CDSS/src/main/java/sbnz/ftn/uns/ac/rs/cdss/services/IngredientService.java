package sbnz.ftn.uns.ac.rs.cdss.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sbnz.ftn.uns.ac.rs.cdss.model.dto.IngredientDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.IngredientDetailsDTO;

public interface IngredientService {

	Page<IngredientDetailsDTO> getAll(String username, Pageable pageable);
	IngredientDetailsDTO getById(String username, Long id);
	IngredientDetailsDTO save(String username, IngredientDTO i);
	IngredientDetailsDTO update(String username, IngredientDTO i, Long id);
	void delete(String username, Long id);
}
