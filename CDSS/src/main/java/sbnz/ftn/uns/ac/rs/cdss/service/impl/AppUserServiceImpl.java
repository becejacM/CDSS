package sbnz.ftn.uns.ac.rs.cdss.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sbnz.ftn.uns.ac.rs.cdss.exceptions.NotValidParamsException;
import sbnz.ftn.uns.ac.rs.cdss.model.AppUser;
import sbnz.ftn.uns.ac.rs.cdss.model.Disease;
import sbnz.ftn.uns.ac.rs.cdss.model.Role;
import sbnz.ftn.uns.ac.rs.cdss.model.UserRole;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.AppUserDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.AppUserDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiseaseDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.repository.AppUserRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.RoleRepository;
import sbnz.ftn.uns.ac.rs.cdss.services.AppUserService;
import sbnz.ftn.uns.ac.rs.cdss.utils.KieSessionUtil;

@Service
public class AppUserServiceImpl implements AppUserService{

	@Autowired
	AppUserRepository appUserRepository;
	
	@Autowired
	RoleRepository roleRepository;

	
	@Override
	public AppUserDetailsDTO save(String username, AppUserDTO addeduser) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.ADMIN)) {
				throw new NotValidParamsException("You must be logged in as admin to add doctor");
			}
			AppUser newu = new AppUser(addeduser);
			Role role = roleRepository.findByName("DOCTOR");
			System.out.println(role);
			newu.setRole(UserRole.DOCTOR);
			newu.getRoles().add(role);
			AppUser p = appUserRepository.save(newu);
			return new AppUserDetailsDTO(p);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to add doctor");
		}
	}

	@Override
	public AppUserDetailsDTO update(String username, AppUserDTO updateduser, Long id) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			AppUser p = appUserRepository.findById(id).get();
			if(user==null || p==null) {
				throw new NotValidParamsException("Doctor with that id doesn't exists!");
			}
			if (!user.getRole().equals(UserRole.ADMIN) && !p.getId().equals(user.getId())) {
				throw new NotValidParamsException("You must be logged in as admin to update doctor");
			}
			p.setFirstname(updateduser.getFirstname());
			p.setLastname(updateduser.getLastname());
			p.setEmail(updateduser.getEmail());
			AppUser pp = appUserRepository.save(p);
			return new AppUserDetailsDTO(pp);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to add doctor");
		}
	}

	@Override
	public AppUserDetailsDTO getById(String username, Long id) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			AppUser p = appUserRepository.findById(id).get();
			if(user==null || p==null) {
				throw new NotValidParamsException("You must be logged in to get doctor");
			}
			if (!user.getRole().equals(UserRole.ADMIN) && !p.getId().equals(user.getId())) {
				throw new NotValidParamsException("You must be logged in as admin to get doctor");
			}
			return new AppUserDetailsDTO(p);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to get doctor");
		}
	}

	@Override
	public Page<AppUserDetailsDTO> getAllByRole(String username, UserRole role, Pageable pageable) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.ADMIN)) {
				throw new NotValidParamsException("You must be logged in as admin to get doctors");
			}
			List<AppUserDetailsDTO> a = new ArrayList<>();
			Page<AppUser> page = appUserRepository.findByRole(role, pageable);
			for (AppUser p : page.getContent()) {
				a.add(new AppUserDetailsDTO(p));
			}
			return new PageImpl<>(a, pageable, page.getTotalElements());
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to get diseases");
		}
	}

	@Override
	public void delete(String username, Long id) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.ADMIN)) {
				throw new NotValidParamsException("You must be logged in as admin to delete doctor");
			}

			AppUser pat = appUserRepository.findById(id).get();
			if (pat == null) {
				throw new NotValidParamsException("Doctor with that id doesn't exists!");
			}
			appUserRepository.delete(pat);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to delete doctor");
		}
	}

}
