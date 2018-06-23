package sbnz.ftn.uns.ac.rs.cdss.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sbnz.ftn.uns.ac.rs.cdss.exceptions.NotValidParamsException;
import sbnz.ftn.uns.ac.rs.cdss.model.AppUser;
import sbnz.ftn.uns.ac.rs.cdss.model.Medicine;
import sbnz.ftn.uns.ac.rs.cdss.model.MedicineIngredient;
import sbnz.ftn.uns.ac.rs.cdss.model.UserRole;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.IngredientDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.IngredientDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.MedicineDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.repository.AppUserRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.MedicineIngredientRepository;
import sbnz.ftn.uns.ac.rs.cdss.services.IngredientService;

@Service
public class IngredientServiceImpl implements IngredientService{

	@Autowired
	AppUserRepository appUserRepository;
	
	@Autowired
	MedicineIngredientRepository miRepository;
	
	@Autowired
    private KieSessionService kieSessionService;
	
	@Override
	public Page<IngredientDetailsDTO> getAll(String username, Pageable pageable) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null) {
				throw new NotValidParamsException("You must be logged in as admin or doctor to get medicine ingredients");
			}
			List<IngredientDetailsDTO> medicines = new ArrayList<>();
			Page<MedicineIngredient> page = miRepository.findAll(pageable);
			for (MedicineIngredient p : page.getContent()) {
				medicines.add(new IngredientDetailsDTO(p));
			}
			return new PageImpl<>(medicines, pageable, page.getTotalElements());
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to get medicine ingredients");
		}
	}

	@Override
	public IngredientDetailsDTO getById(String username, Long id) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null) {
				throw new NotValidParamsException("You must be logged in as admin or doctor to get medicine ingredient");
			}
			MedicineIngredient p = miRepository.findById(id).get();
			return new IngredientDetailsDTO(p);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to get medicine ingredient");
		}
	}

	@Override
	public IngredientDetailsDTO save(String username, IngredientDTO i) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.ADMIN)) {
				throw new NotValidParamsException("You must be logged in as admin or doctor to add medicine ingredient");
			}

			MedicineIngredient p = miRepository.save(new MedicineIngredient(i));
			return new IngredientDetailsDTO(p);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to add medicine ingredients");
		}
	}

	@Override
	public IngredientDetailsDTO update(String username, IngredientDTO i, Long id) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.ADMIN)) {
				throw new NotValidParamsException("You must be logged in as admin to update medicine ingredient");
			}

			MedicineIngredient pat = miRepository.findById(id).get();
			if (pat == null) {
				throw new NotValidParamsException("Medicine ingredient with that id doesn't exists!");
			}
			pat.setName(i.getName());
			MedicineIngredient p = miRepository.save(pat);
			kieSessionService.updateIngredient(p.getId(), p);
			return new IngredientDetailsDTO(p);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to update medicine ingredient");
		}
	}

	@Override
	public void delete(String username, Long id) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.ADMIN)) {
				throw new NotValidParamsException("You must be logged in as admin to delete medicine ingredient");
			}

			MedicineIngredient pat = miRepository.findById(id).get();
			if (pat == null) {
				throw new NotValidParamsException("Medicine ingredient with that id doesn't exists!");
			}
			miRepository.delete(pat);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to delete medicine ingredient");
		}
	}

}
