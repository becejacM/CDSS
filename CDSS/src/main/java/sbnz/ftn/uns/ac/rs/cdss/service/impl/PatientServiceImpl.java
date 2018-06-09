package sbnz.ftn.uns.ac.rs.cdss.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sbnz.ftn.uns.ac.rs.cdss.exceptions.NotValidParamsException;
import sbnz.ftn.uns.ac.rs.cdss.model.AppUser;
import sbnz.ftn.uns.ac.rs.cdss.model.Patient;
import sbnz.ftn.uns.ac.rs.cdss.model.UserRole;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.PatientDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.PatientDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.repository.AppUserRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.PatientRepository;
import sbnz.ftn.uns.ac.rs.cdss.services.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	PatientRepository patientRepository;

	@Autowired
	AppUserRepository appUserRepository;

	@Override
	public Page<PatientDetailsDTO> getAllPatients(String username, Pageable pageable) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null) {
				throw new NotValidParamsException("You must be logged in as admin or doctor to get patients");
			}
			List<PatientDetailsDTO> patients = new ArrayList<>();
			Page<Patient> page = patientRepository.findAll(pageable);
			for (Patient p : page.getContent()) {
				patients.add(new PatientDetailsDTO(p));
			}
			return new PageImpl<>(patients, pageable, page.getTotalElements());
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to get patients");
		}
	}

	@Override
	public PatientDetailsDTO getById(String username, Long id) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null) {
				throw new NotValidParamsException("You must be logged in as admin or doctor to get patients");
			}
			Patient p = patientRepository.findById(id).get();
			return new PatientDetailsDTO(p);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to get patients");
		}
	}

	@Override
	public PatientDetailsDTO save(String username, PatientDTO patient) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null) {
				throw new NotValidParamsException("You must be logged in as admin or doctor to add patients");
			}
			
			Patient p = patientRepository.save(new Patient(patient));
			return new PatientDetailsDTO(p);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to add patient");
		}
	}


	@Override
	public PatientDetailsDTO update(String username, PatientDTO patient, Long id) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.ADMIN)) {
				throw new NotValidParamsException("You must be logged in as admin to update patients");
			}
			
			Patient pat = patientRepository.findById(id).get();
			if(pat==null) {
				throw new NotValidParamsException("Patient with that id doesn't exists!");
			}
			pat.setFirstname(patient.getFirstname());
			pat.setLastname(patient.getLastname());
			pat.setAddress(patient.getAddress());
			Patient p = patientRepository.save(pat);
			return new PatientDetailsDTO(p);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to update patient");
		}
	}
	
	@Override
	public void deletePatient(String username, Long id) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.ADMIN)) {
				throw new NotValidParamsException("You must be logged in as admin to delete patients");
			}
			
			Patient pat = patientRepository.findById(id).get();
			if(pat==null) {
				throw new NotValidParamsException("Patient with that id doesn't exists!");
			}
			patientRepository.delete(pat);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to delete patient");
		}
	}


}
