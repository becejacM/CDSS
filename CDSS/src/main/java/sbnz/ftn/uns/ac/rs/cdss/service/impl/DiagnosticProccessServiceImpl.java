package sbnz.ftn.uns.ac.rs.cdss.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import sbnz.ftn.uns.ac.rs.cdss.CdssApplication;
import sbnz.ftn.uns.ac.rs.cdss.exceptions.NotValidParamsException;
import sbnz.ftn.uns.ac.rs.cdss.model.AppUser;
import sbnz.ftn.uns.ac.rs.cdss.model.DiagnosticTherapy;
import sbnz.ftn.uns.ac.rs.cdss.model.Disease;
import sbnz.ftn.uns.ac.rs.cdss.model.Medicine;
import sbnz.ftn.uns.ac.rs.cdss.model.MedicineForTherapy;
import sbnz.ftn.uns.ac.rs.cdss.model.MedicineIngredient;
import sbnz.ftn.uns.ac.rs.cdss.model.Patient;
import sbnz.ftn.uns.ac.rs.cdss.model.Symptom;
import sbnz.ftn.uns.ac.rs.cdss.model.SymptomForDisease;
import sbnz.ftn.uns.ac.rs.cdss.model.TypeOfSymptoms;
import sbnz.ftn.uns.ac.rs.cdss.model.UserRole;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiagnosticTherapyDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiagnosticTherapyDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiseaseDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.MedicineDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.SymptomDTO;
import sbnz.ftn.uns.ac.rs.cdss.repository.AppUserRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.DiagnosticProccessRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.DiseaseRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.MedicalRecordRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.MedicineForTherapyRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.MedicineIngredientRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.MedicineRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.PatientRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.SymptomForDiseaseRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.SymptomRepository;
import sbnz.ftn.uns.ac.rs.cdss.services.DiagnosticProccesService;

@Service
public class DiagnosticProccessServiceImpl implements DiagnosticProccesService {

	@Autowired
	DiseaseRepository diseaseRepository;

	@Autowired
	MedicineRepository medicineRepository;

	@Autowired
	MedicineIngredientRepository miRepository;
	
	@Autowired
	DiagnosticProccessRepository dpRepository;

	@Autowired
	SymptomRepository symptomRepository;

	@Autowired
	SymptomForDiseaseRepository sfdRepository;

	@Autowired
	PatientRepository patientRepository;

	@Autowired
	MedicalRecordRepository mrRepository;

	@Autowired
	MedicineForTherapyRepository medicineForTherapyRepository;
	
	@Autowired
	AppUserRepository appUserRepository;

	//@Autowired
	//private KieSession kieSession;

	@Override
	public Page<DiagnosticTherapyDetailsDTO> getAll(String username, Pageable pageable) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null) {
				throw new NotValidParamsException(
						"You must be logged in as admin or doctor to get diagnostic proccess");
			}
			List<DiagnosticTherapyDetailsDTO> d = new ArrayList<>();
			Page<DiagnosticTherapy> page = dpRepository.findAll(pageable);
			for (DiagnosticTherapy p : page.getContent()) {
				d.add(new DiagnosticTherapyDetailsDTO(p));
			}
			return new PageImpl<>(d, pageable, page.getTotalElements());
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new NotValidParamsException("Invalid parameters while trying to get proccesses");
		}
	}

	@Override
	public DiagnosticTherapyDetailsDTO getById(String username, Long id) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null) {
				throw new NotValidParamsException("You must be logged in as admin or doctor to get proccess");
			}
			DiagnosticTherapy p = dpRepository.findById(id).get();
			return new DiagnosticTherapyDetailsDTO(p);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to get proccess");
		}
	}

	@Override
	public DiagnosticTherapyDetailsDTO save(String username, DiagnosticTherapyDTO d) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.DOCTOR)) {
				throw new NotValidParamsException("You must be logged in as doctor to add disease");
			}
			DiagnosticTherapy newd = new DiagnosticTherapy();
			newd.setDate(new Date());
			//newd.setDoctor(user);
			newd.setMedicalRecord(mrRepository
					.findById((patientRepository.getOne(d.getPatientId()).getMedicalRecord().getId())).get());
			Disease disease = diseaseRepository.findByName(d.getDiseasename());
			if (disease == null) {
				throw new NotValidParamsException("There are no disease with thatt name");
			}
			newd.setDisease(disease);
			newd.setMessage(d.getMessage());
			Collection<Symptom> syms = new ArrayList<>();
			for (SymptomDTO s : d.getSymptoms()) {
				System.out.println("ubacujem: "+s.getName());
				syms.add(symptomRepository.findByName(s.getName()));
			}
			newd.setSymptoms(syms);
			DiagnosticTherapy p = dpRepository.save(newd);

			for (MedicineDTO m : d.getMedicines()) {
				Medicine medicine = medicineRepository.findByName(m.getName());
				MedicineForTherapy mft = new MedicineForTherapy();
				mft.setDoctor(user);
				mft.setMedicine(medicine);
				mft.setDiagnostictherapys(newd);
				mft.setDoctor(user);
				medicineForTherapyRepository.save(mft);
			}
			return new DiagnosticTherapyDetailsDTO(p);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new NotValidParamsException("Invalid parameters while trying to add therapy");
		}
	}

	@Override
	public DiagnosticTherapyDetailsDTO validate(String username, DiagnosticTherapyDTO d) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.DOCTOR)) {
				throw new NotValidParamsException("You must be logged in as doctor to validate distherapyease");
			}
			Patient p = patientRepository.findById(d.getPatientId()).get();
			KieSession kieSession = CdssApplication.kieSessions.get(user.getUsername());
			//kieSession.insert(p.getMedicalRecord());
			kieSession.insert(p.getMedicalRecord());
			for (MedicineDTO m : d.getMedicines()) {
				Medicine newmed = medicineRepository.findByName(m.getName());
				kieSession.insert(newmed);
				System.out.println(newmed.toString());
				for(MedicineIngredient mi : newmed.getIngredients()) {
					kieSession.insert(mi);
				}
			}
	        kieSession.setGlobal("diagnosticProccessService", this);
			kieSession.getAgenda().getAgendaGroup("alergies").setFocus();
			kieSession.fireAllRules();
			for (MedicineDTO m : d.getMedicines()) {
				Medicine newmed = medicineRepository.findByName(m.getName());
				if(kieSession.getFactHandle(newmed)!=null) {
					kieSession.delete(kieSession.getFactHandle(newmed));
					for(MedicineIngredient mi : newmed.getIngredients()) {
						kieSession.delete(kieSession.getFactHandle(mi));
					}
				}
			}
			//kieSession.delete(kieSession.getFactHandle(p));
			kieSession.delete(kieSession.getFactHandle(p.getMedicalRecord()));

			/*
			 * Collection<Symptom> syms = new ArrayList<>(); for (SymptomDTO s :
			 * d.getSymptoms()) { syms.add(symptomRepository.findByName(s.getName()));
			 * System.out.println("stavljam: " + s.getName()); }
			 */
			// sendMessage("poruka");
			return null;
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new NotValidParamsException("Invalid parameters while trying to validate therapy");
		}
	}

	private SimpMessagingTemplate template;

	@Autowired
	private DiagnosticProccessServiceImpl(SimpMessagingTemplate template) {
		this.template = template;
	}

	@Override
	public void sendMessage(String message) {
		System.out.println("Tu saaaam");
		this.template.convertAndSend("/secured/alergies", message);
	}

}
