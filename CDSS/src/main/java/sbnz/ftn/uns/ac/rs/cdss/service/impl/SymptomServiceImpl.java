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
import sbnz.ftn.uns.ac.rs.cdss.model.Disease;
import sbnz.ftn.uns.ac.rs.cdss.model.Symptom;
import sbnz.ftn.uns.ac.rs.cdss.model.UserRole;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiseaseDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.SymptomDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.SymptomDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.repository.AppUserRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.SymptomRepository;
import sbnz.ftn.uns.ac.rs.cdss.services.SymptomService;

@Service
public class SymptomServiceImpl implements SymptomService {

	@Autowired
	AppUserRepository appUserRepository;
	
	@Autowired
	SymptomRepository symptomRepository;
	
	@Autowired
    private KieSessionService kieSessionService;
	
	@Override
	public Page<SymptomDetailsDTO> getAll(String username, Pageable pageable) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null) {
				throw new NotValidParamsException("You must be logged in as admin or doctor to get symptoms");
			}
			List<SymptomDetailsDTO> symptoms = new ArrayList<>();
			Page<Symptom> page = symptomRepository.findAll(pageable);
			for (Symptom p : page.getContent()) {
				symptoms.add(new SymptomDetailsDTO(p));
			}
			return new PageImpl<>(symptoms, pageable, page.getTotalElements());
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to get symptoms");
		}
	}

	@Override
	public SymptomDetailsDTO getById(String username, Long id) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null) {
				throw new NotValidParamsException("You must be logged in as admin or doctor to get symptoms");
			}
			Symptom p = symptomRepository.findById(id).get();
			return new SymptomDetailsDTO(p);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to get symptoms");
		}
	}

	@Override
	public SymptomDetailsDTO save(String username, SymptomDTO symptom) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.ADMIN)) {
				throw new NotValidParamsException("You must be logged in as admin or doctor to add symptoms");
			}

			Symptom p = symptomRepository.save(new Symptom(symptom));
			
			return new SymptomDetailsDTO(p);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to add symptom");
		}
	}

	@Override
	public SymptomDetailsDTO update(String username, SymptomDTO symptom, Long id) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.ADMIN)) {
				throw new NotValidParamsException("You must be logged in as admin to update symptom");
			}

			Symptom pat = symptomRepository.findById(id).get();
			if (pat == null) {
				throw new NotValidParamsException("Symptom with that id doesn't exists!");
			}
			pat.setName(symptom.getName());
			Symptom p = symptomRepository.save(pat);
			kieSessionService.updateSymptoms(p.getId(), p);
			return new SymptomDetailsDTO(p);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to update symptom");
		}
	}

	@Override
	public void delete(String username, Long id) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.ADMIN)) {
				throw new NotValidParamsException("You must be logged in as admin to delete symptoms");
			}

			Symptom pat = symptomRepository.findById(id).get();
			if (pat == null) {
				throw new NotValidParamsException("Symptom with that id doesn't exists!");
			}
			symptomRepository.delete(pat);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to delete symptom");
		}
	}

}
