package sbnz.ftn.uns.ac.rs.cdss.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sbnz.ftn.uns.ac.rs.cdss.exceptions.NotValidParamsException;
import sbnz.ftn.uns.ac.rs.cdss.model.AppUser;
import sbnz.ftn.uns.ac.rs.cdss.model.DiagnosticTherapy;
import sbnz.ftn.uns.ac.rs.cdss.model.Disease;
import sbnz.ftn.uns.ac.rs.cdss.model.MedicalRecord;
import sbnz.ftn.uns.ac.rs.cdss.model.Medicine;
import sbnz.ftn.uns.ac.rs.cdss.model.MedicineIngredient;
import sbnz.ftn.uns.ac.rs.cdss.model.Patient;
import sbnz.ftn.uns.ac.rs.cdss.model.Symptom;
import sbnz.ftn.uns.ac.rs.cdss.model.UserRole;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.AlergiesDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiagnosticTherapyDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.IngredientDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.MedicineDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.PatientDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.PatientDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.ReportDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.SymptomDTO;
import sbnz.ftn.uns.ac.rs.cdss.repository.AppUserRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.MedicalRecordRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.MedicineIngredientRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.MedicineRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.PatientRepository;
import sbnz.ftn.uns.ac.rs.cdss.services.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	PatientRepository patientRepository;

	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	MedicalRecordRepository recordRepository;

	@Autowired
	MedicineRepository medicineRepository;

	@Autowired
	MedicineIngredientRepository ingredientRepository;

	@Autowired
	private KieSession kieSession;
	
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

			Patient pat = patientRepository.save(new Patient(patient));
			MedicalRecord newm = new MedicalRecord();
			newm.setPatient(pat);
			MedicalRecord m = recordRepository.save(newm);
			return new PatientDetailsDTO(pat);
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
			if (pat == null) {
				throw new NotValidParamsException("Patient with that id doesn't exists!");
			}
			pat.setFirstname(patient.getFirstname());
			pat.setLastname(patient.getLastname());
			pat.setAddress(patient.getAddress());
			pat.setMedicalCardNumber(patient.getMedicalCardNumber());
			pat.setEmail(patient.getEmail());
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
			if (pat == null) {
				throw new NotValidParamsException("Patient with that id doesn't exists!");
			}
			patientRepository.delete(pat);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to delete patient");
		}
	}

	@Override
	public AlergiesDetailsDTO getAlergies(String username, Long id) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null) {
				throw new NotValidParamsException("You must be logged in as admin or doctor to get patients");
			}
			Patient p = patientRepository.findById(id).get();
			MedicalRecord m = recordRepository.findById(p.getMedicalRecord().getId()).get();
			AlergiesDetailsDTO a = new AlergiesDetailsDTO();
			Collection<MedicineDetailsDTO> meds = new ArrayList<>();
			for (Medicine med : m.getMedicines()) {
				meds.add(new MedicineDetailsDTO(med));
			}
			a.setMedicines(meds);
			Collection<IngredientDetailsDTO> medings = new ArrayList<>();
			for (MedicineIngredient meding : m.getIngredients()) {
				medings.add(new IngredientDetailsDTO(meding));
			}
			a.setIngredients(medings);
			a.setMedicines(meds);
			return a;
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new NotValidParamsException("Invalid parameters while trying to get alergies");
		}
	}

	@Override
	public MedicineDetailsDTO addAlergie(String username, Long id, String name) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null) {
				throw new NotValidParamsException("You must be logged in as admin or doctor to add patients");
			}

			Patient pat = patientRepository.findById(id).get();
			MedicalRecord newm = pat.getMedicalRecord();
			System.out.println(name);
			Medicine m = medicineRepository.findByName(name);
			MedicineIngredient mi = ingredientRepository.findByName(name);
			if (m == null && mi == null) {
				throw new NotValidParamsException("There are no medicine or ingredient with that name");
			}
			if (m != null) {
				for (Medicine mm : newm.getMedicines()) {
					if (mm.getId().equals(m.getId())) {
						throw new NotValidParamsException("Pacient is already alergic to that medicine");
					}
				}
				newm.getMedicines().add(m);
				MedicalRecord mr = recordRepository.save(newm);
				return new MedicineDetailsDTO(m);
			}
			else {
				if(newm.getIngredients().contains(mi)) {
					throw new NotValidParamsException("Pacient is already alergic to that medicine ingredient");
				}
				for(Medicine mm : medicineRepository.findAll()) {//prodjem kroz sve lekove, ako neki od njih ima prosledjeni sastojak
					if(mm.getIngredients().contains(mi)) {			
						if(!newm.getMedicines().contains(mm)) {  // ako pacijent vec nije alergican na taj lek, a lek ima sastojak na koji je alergican
							Medicine alergicMedicine = medicineRepository.findByName(mm.getName());
							newm.getMedicines().add(alergicMedicine);		 // dodaje ga u listu alergija
						}
						
					}
				}
				
				newm.getIngredients().add(mi);
				MedicalRecord mr = recordRepository.save(newm);
				return null;
			}
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new NotValidParamsException("Invalid parameters while trying to add patient");
		}
	}

	@Override
	public Collection<ReportDTO> getReport1(String username) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.DOCTOR)) {
				throw new NotValidParamsException("You must be logged in as doctor to get report");
			}

			//ReportDTO r = new ReportDTO();
			//kieSession.insert(r);
			//kieSession.getAgenda().getAgendaGroup("report1").setFocus();
			//kieSession.fireAllRules();
			//kieSession.delete(kieSession.getFactHandle(r));
			
			QueryResults results = kieSession.getQueryResults( "report 1 : Pacinet sa mogucim hronicnim oboljenjima" );
			System.out.println( "we have " + results.size());

			Collection<ReportDTO> reports1 = new ArrayList<>();
			for ( QueryResultsRow row : results ) {
			    Patient p = ( Patient ) row.get( "p" );
			    Disease d = ( Disease ) row.get( "d" );
			    ReportDTO r= new ReportDTO();
			    r.setDiseasename(d.getName());
			    r.setPatient(new PatientDTO(p));
			    reports1.add(r);
			}
			return reports1;
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new NotValidParamsException("Invalid parameters while trying to update disease");
		}
	}

	@Override
	public Collection<ReportDTO> getReport2(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ReportDTO> getReport3(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
