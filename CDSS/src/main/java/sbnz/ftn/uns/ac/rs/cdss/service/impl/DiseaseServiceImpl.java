package sbnz.ftn.uns.ac.rs.cdss.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import sbnz.ftn.uns.ac.rs.cdss.model.Medicine;
import sbnz.ftn.uns.ac.rs.cdss.model.MedicineIngredient;
import sbnz.ftn.uns.ac.rs.cdss.model.Patient;
import sbnz.ftn.uns.ac.rs.cdss.model.Symptom;
import sbnz.ftn.uns.ac.rs.cdss.model.SymptomForDisease;
import sbnz.ftn.uns.ac.rs.cdss.model.TypeOfSymptoms;
import sbnz.ftn.uns.ac.rs.cdss.model.UserRole;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiagnosticTherapyDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiseaseDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiseaseDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.IngredientDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.IngredientDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.ListOfSymbolsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.PatientDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.ReportDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.ResonerDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.SymptomDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.SymptomDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.repository.AppUserRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.DiseaseRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.PatientRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.SymptomForDiseaseRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.SymptomRepository;
import sbnz.ftn.uns.ac.rs.cdss.services.DiagnosticProccesService;
import sbnz.ftn.uns.ac.rs.cdss.services.DiseaseService;

@Service
public class DiseaseServiceImpl implements DiseaseService {

	@Autowired
	DiseaseRepository diseaseRepository;

	@Autowired
	SymptomRepository symptomRepository;
	
	@Autowired
	SymptomForDiseaseRepository sfdRepository;
	
	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	private KieSession kieSession;
	
	@Autowired
	DiagnosticProccesService diagnosticProccessService;

	@Autowired
	PatientRepository patientRepository;
	
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
			ex.printStackTrace();
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
			Disease newd = diseaseRepository.findByName(disease.getName());
			if(newd!=null) {
				throw new NotValidParamsException("Disease with that name allready exists");
			}
			Symptom news = symptomRepository.findByName(disease.getName());
			if(news!=null) {
				throw new NotValidParamsException("Symptom with that name exists! The names of diseases and symptom can not be the same");
			}
			Disease newdisease = new Disease(disease);
			Disease p = diseaseRepository.save(newdisease);

			for(SymptomDTO i : disease.getSymptoms()) {
				Symptom symptom= symptomRepository.findByName(i.getName());
				SymptomForDisease sfd = new SymptomForDisease();
				sfd.setDisease(p);
				sfd.setSymptom(symptom);
				sfd.setTypeOfSymptoms(TypeOfSymptoms.GENERAL);
				sfdRepository.save(sfd);
			}
			return new DiseaseDetailsDTO(p);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
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

	@Override
	public SymptomDetailsDTO checkSymptom(String username, String name) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null) {
				throw new NotValidParamsException("You must be logged in as admin to check symptom");
			}
			System.out.println(name);
			Symptom news = symptomRepository.findByName(name);
			if(news==null) {
				throw new NotValidParamsException("Symptom with that name doesn't exists!");
			}
			return new SymptomDetailsDTO(news);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to check symptom");
		}
	}

	@Override
	public DiagnosticTherapyDetailsDTO getDiagnose(String username, ListOfSymbolsDTO listOfSymbols) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.DOCTOR)) {
				throw new NotValidParamsException("You must be logged in as doctor to get disease");
			}
			Patient p = patientRepository.findById(listOfSymbols.getPatientid()).get();
			//kieSession.insert(p);
			DiagnosticTherapy d = new DiagnosticTherapy();
			d.setMedicalRecord(p.getMedicalRecord());
			Date date = new Date();
			long ltime=date.getTime()-60*24*60*60*1000; //oduzimanje 60 dana
			Date todayminus60=new Date(ltime);
			d.setDate(todayminus60);

			for(SymptomDTO s : listOfSymbols.getSymptoms()) {
				Symptom symptom = symptomRepository.findByName(s.getName());
				System.out.println("tu saaam: "+symptom.toString());
				d.getSymptoms().add(symptom);
				//kieSession.insert(symptom);
			}
			kieSession.insert(d);
			kieSession.getAgenda().getAgendaGroup("diagnose").setFocus();
			kieSession.fireAllRules();
			/*for(SymptomDTO s : listOfSymbols.getSymptoms()) {
				Symptom symptom = symptomRepository.findByName(s.getName());
				if(kieSession.getFactHandle(symptom)!=null) {
					System.out.println("brisem");
					kieSession.delete(kieSession.getFactHandle(symptom));
				}
			}*/
			kieSession.delete(kieSession.getFactHandle(d));
			System.out.println(d.toString());
			return new DiagnosticTherapyDetailsDTO(d);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new NotValidParamsException("Invalid parameters while trying to update disease");
		}
	}

	@Override
	public Collection<DiseaseDetailsDTO> getDiagnoseList(String username, ListOfSymbolsDTO listOfSymbols) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.DOCTOR)) {
				throw new NotValidParamsException("You must be logged in as doctor to get disease");
			}
			List<Symptom> symptoms = new ArrayList<>();
			ResonerDTO resoner = new ResonerDTO();
			for(SymptomDTO s : listOfSymbols.getSymptoms()) {
				Symptom sym = symptomRepository.findByName(s.getName());
				symptoms.add(sym);
			}
			resoner.setSymptoms(symptoms);
			kieSession.insert(resoner);
			kieSession.getAgenda().getAgendaGroup("resoner").setFocus();
			kieSession.fireAllRules();
			QueryResults results = kieSession.getQueryResults( "resoner: sve bolesti povezane sa 1 ili vise simptoma", new Object[] { symptoms } );
			System.out.println( "we have " + results.size());

			Collection<DiseaseDetailsDTO> diseases = new ArrayList<>();
			Map<Disease, Long> mapa = new HashMap<>();
			for ( QueryResultsRow row : results ) {
			    Disease disease = ( Disease ) row.get( "d" );
			    Long num = (Long) row.get("numOfSym");
			    mapa.put(disease, num);
			}
			
			Map<Disease, Long> result = mapa.entrySet().stream()
	                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
	                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
	                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
			for(Disease d: result.keySet()) {
			    diseases.add(new DiseaseDetailsDTO(d));
			}
			/*Collection<DiseaseDetailsDTO> diseases = new ArrayList<>();
			for(Disease d : resoner.getMapa().keySet()) {
			    diseases.add(new DiseaseDetailsDTO(d));
			}
			kieSession.delete(kieSession.getFactHandle(resoner));*/
			return diseases;
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new NotValidParamsException("Invalid parameters while trying to update disease");
		}
	}

	@Override
	public Collection<SymptomDetailsDTO> getSymptomList(String username, String name) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.DOCTOR)) {
				throw new NotValidParamsException("You must be logged in as doctor to get disease");
			}
			Disease d = diseaseRepository.findByName(name);
			if (d==null) {
				throw new NotValidParamsException("Disease with that name doesn't exist");
			}
			QueryResults results = kieSession.getQueryResults( "resoner: svi simptomi bolesti", new Object[] { d } );
			System.out.println( "we have " + results.size());

			Collection<SymptomDetailsDTO> symptoms = new ArrayList<>();
			Map<Symptom, Long> mapa = new HashMap<>();
			for ( QueryResultsRow row : results ) {
				Collection<Symptom> generalSymptoms = ( Collection<Symptom> ) row.get( "generalSymptoms" );
				Collection<Symptom> specificSymptoms = ( Collection<Symptom> ) row.get( "specificSymptoms" );
				for(Symptom s : generalSymptoms) {
				    mapa.put(s, 1L);
				}
				for(Symptom s : specificSymptoms) {
				    mapa.put(s, 2L);
				}
			}
			
			Map<Symptom, Long> result = mapa.entrySet().stream()
	                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
	                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
	                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
			for(Symptom ss: result.keySet()) {
			    symptoms.add(new SymptomDetailsDTO(ss));
			}
			return symptoms;
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new NotValidParamsException("Invalid parameters while trying to update disease");
		}
	}

}
