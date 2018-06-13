package sbnz.ftn.uns.ac.rs.cdss.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sbnz.ftn.uns.ac.rs.cdss.exceptions.NotValidParamsException;
import sbnz.ftn.uns.ac.rs.cdss.model.AppUser;
import sbnz.ftn.uns.ac.rs.cdss.model.MedicalRecord;
import sbnz.ftn.uns.ac.rs.cdss.model.Medicine;
import sbnz.ftn.uns.ac.rs.cdss.model.MedicineIngredient;
import sbnz.ftn.uns.ac.rs.cdss.model.Patient;
import sbnz.ftn.uns.ac.rs.cdss.model.UserRole;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.AlergiesDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.MedicineDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.PatientDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.PatientDetailsDTO;
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
				throw new NotValidParamsException("There are no medicine with that name");
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
				for(Medicine mm : medicineRepository.findAll()) {
					if(mm.getIngredients().contains(mi)) {
						for (Medicine mm2 : newm.getMedicines()) {
							if (!mm2.getId().equals(mm.getId())) {
								newm.getMedicines().add(mm);
							}
						}
					}
				}
				MedicalRecord mr = recordRepository.save(newm);
				return null;
			}
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to add patient");
		}
	}

}
