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
import sbnz.ftn.uns.ac.rs.cdss.model.UserRole;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiseaseDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiseaseDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.repository.AppUserRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.DiseaseRepository;
import sbnz.ftn.uns.ac.rs.cdss.services.DiseaseService;

@Service
public class DiseaseServiceImpl implements DiseaseService {

	@Autowired
	DiseaseRepository diseaseRepository;

	@Autowired
	AppUserRepository appUserRepository;

	@Override
	public Page<DiseaseDetailsDTO> getAllDiseases(String username, Pageable pageable) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null) {
				throw new NotValidParamsException("You must be logged in as admin or doctor to get diseases");
			}
			List<DiseaseDetailsDTO> diseases = new ArrayList<>();
			Page<Disease> page = diseaseRepository.findAll(pageable);
			for (Disease p : page.getContent()) {
				diseases.add(new DiseaseDetailsDTO(p));
			}
			return new PageImpl<>(diseases, pageable, page.getTotalElements());
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to get diseases");
		}
	}

	@Override
	public DiseaseDetailsDTO getById(String username, Long id) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null) {
				throw new NotValidParamsException("You must be logged in as admin or doctor to get diseases");
			}
			Disease p = diseaseRepository.findById(id).get();
			return new DiseaseDetailsDTO(p);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to get diseases");
		}
	}

	@Override
	public DiseaseDetailsDTO save(String username, DiseaseDTO disease) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.ADMIN)) {
				throw new NotValidParamsException("You must be logged in as admin to add disease");
			}

			Disease p = diseaseRepository.save(new Disease(disease));
			return new DiseaseDetailsDTO(p);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to add disease");
		}
	}

	@Override
	public DiseaseDetailsDTO update(String username, DiseaseDTO disease, Long id) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.ADMIN)) {
				throw new NotValidParamsException("You must be logged in as admin to update disease");
			}

			Disease pat = diseaseRepository.findById(id).get();
			if (pat == null) {
				throw new NotValidParamsException("Disease with that id doesn't exists!");
			}
			pat.setName(disease.getName());
			pat.setTypeOfDisease(disease.getTypeOfDisease());
			Disease p = diseaseRepository.save(pat);
			return new DiseaseDetailsDTO(p);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to update disease");
		}
	}

	@Override
	public void delete(String username, Long id) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.ADMIN)) {
				throw new NotValidParamsException("You must be logged in as admin to delete patients");
			}

			Disease pat = diseaseRepository.findById(id).get();
			if (pat == null) {
				throw new NotValidParamsException("Disease with that id doesn't exists!");
			}
			diseaseRepository.delete(pat);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to delete disease");
		}
	}

}
