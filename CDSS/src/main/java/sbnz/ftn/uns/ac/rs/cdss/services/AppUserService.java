package sbnz.ftn.uns.ac.rs.cdss.services;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sbnz.ftn.uns.ac.rs.cdss.model.UserRole;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.AppUserDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.AppUserDetailsDTO;

public interface AppUserService {

	AppUserDetailsDTO save(String username, AppUserDTO user);
	AppUserDetailsDTO update(String username, AppUserDTO user, Long id);
	AppUserDetailsDTO getById(String username, Long id);
	Page<AppUserDetailsDTO> getAllByRole(String username, UserRole role, Pageable pageable);
	void delete(String username, Long id);
}
