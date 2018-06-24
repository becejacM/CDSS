package sbnz.ftn.uns.ac.rs.cdss.service.impl;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.weaver.patterns.PerThisOrTargetPointcutVisitor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import sbnz.ftn.uns.ac.rs.cdss.CdssApplication;
import sbnz.ftn.uns.ac.rs.cdss.model.AppUser;
import sbnz.ftn.uns.ac.rs.cdss.model.DiagnosticTherapy;
import sbnz.ftn.uns.ac.rs.cdss.model.Disease;
import sbnz.ftn.uns.ac.rs.cdss.model.Medicine;
import sbnz.ftn.uns.ac.rs.cdss.model.MedicineForTherapy;
import sbnz.ftn.uns.ac.rs.cdss.model.MedicineIngredient;
import sbnz.ftn.uns.ac.rs.cdss.model.Patient;
import sbnz.ftn.uns.ac.rs.cdss.model.Symptom;
import sbnz.ftn.uns.ac.rs.cdss.model.SymptomForDisease;
import sbnz.ftn.uns.ac.rs.cdss.repository.AppUserRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.DiseaseRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.PatientRepository;
import sbnz.ftn.uns.ac.rs.cdss.services.DiagnosticProccesService;

@Service
public class KieSessionService {


	@Autowired
    @Lazy
    private KieContainer kieContainer;
	
	@Autowired
    private DiseaseRepository diseaseRepository;
    
    @Autowired
    private AppUserRepository userRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
	DiagnosticProccesService diagnosticProccessService;
    
	public void createSession(String username) {
		KieSession kieSession = kieContainer.newKieSession("ksession-rules");
        kieSession.addEventListener(new DebugAgendaEventListener());
        kieSession.setGlobal("diagnosticProccessService", diagnosticProccessService);
        
        
        System.out.println("ovde sam i dodajem doktora");
        AppUser user = userRepository.findByUsername(username);
        kieSession.insert(user);
        
        this.addAllDiseases(kieSession);
        this.addAllPatients(kieSession);
        CdssApplication.kieSessions.put(username, kieSession);
        //printObjects();
	}
	
	public KieSession getKieSession(String username) {
		return CdssApplication.kieSessions.get(username);
	}
	
	public void deleteKieSession(String username) {
		KieSession kieSession = CdssApplication.kieSessions.get(username);
		if(kieSession!=null) {
			System.out.println("Brisem sesiju: " +username);
			kieSession.dispose();
			CdssApplication.kieSessions.put(username, kieSession);
			this.printObjects();
		}
		
	}
	
	private void addAllDiseases(KieSession kieSession) {
		Collection<Disease> diseases = diseaseRepository.findAll();
        for(Disease d: diseases) {
        	kieSession.insert(d);
        }
	}
	
	private void addAllPatients(KieSession kieSession) {
		Collection<Patient> patients = patientRepository.findAll();
        for(Patient p: patients) {
        	kieSession.insert(p);
        }
	}
	
	public void addDiagnosticTherapy(String username, DiagnosticTherapy dt) {
		KieSession kieSession = CdssApplication.kieSessions.get(username);
		kieSession.insert(dt);
	}
	
	public void setAgendaAndFireAllRules(String username, String agenda) {
		KieSession kieSession = CdssApplication.kieSessions.get(username);
        kieSession.setGlobal("diagnosticProccessService", diagnosticProccessService);
		kieSession.getAgenda().getAgendaGroup(agenda).setFocus();
		int firedRules = kieSession.fireAllRules();
		System.out.println(firedRules);
	}
	
	public void deleteDiagnosticTherapy(String username, DiagnosticTherapy dt) {
		System.out.println("Brisem dt");
		KieSession kieSession = CdssApplication.kieSessions.get(username);
		kieSession.delete(kieSession.getFactHandle(dt));
		//printObjects();
	}
	
	public void printObjects() {
		for(KieSession ks : CdssApplication.kieSessions.values()) {
			System.out.println("Sesijaaaa");
			for(Object a : ks.getObjects()) {
				if(a instanceof AppUser){
					AppUser aa = (AppUser)a;
					System.out.println(aa.getUsername());
				}
			}
			for(Object a : ks.getObjects()) {
				if(a instanceof Patient){
					Patient aa = (Patient)a;
					System.out.println(aa.getFirstname());
				}
			}
			for(Object a : ks.getObjects()) {
				if(a instanceof Disease){
					Disease aa = (Disease)a;
					System.out.println("disease   "+aa.getName());
				}
			}
		}
	}
	
	
	// update-ovanje doktora
	public void updateDoctor(String username, AppUser doctor) {
		for(KieSession ks : CdssApplication.kieSessions.values()) {
			for(Object a : ks.getObjects()) {
				if(a instanceof AppUser){
					AppUser aa = (AppUser)a;
					if(aa.getUsername().equals(username)) {
						ks.update(ks.getFactHandle(aa), doctor);
					}
					System.out.println(aa.getUsername());
				}
			}
		}
	}
	
	public void updatePatient(Long id, Patient patient) {
		for(KieSession ks : CdssApplication.kieSessions.values()) {
			for(Object a : ks.getObjects()) {
				if(a instanceof Patient){
					Patient aa = (Patient)a;
					if(aa.getId().equals(id)) {
						ks.update(ks.getFactHandle(aa), patient);
					}
					System.out.println(aa.getId());
				}
			}
		}
	}
	
	public void updateDT(Long id, DiagnosticTherapy dt) {
		for(KieSession ks : CdssApplication.kieSessions.values()) {
			for(Object a : ks.getObjects()) {
				if(a instanceof Patient){
					Patient aa = (Patient)a;
					if(aa.getId()==id) {
						aa.getMedicalRecord().getTherapy().add(dt);
						ks.update(ks.getFactHandle(aa), aa);
					}
					
					System.out.println(aa.getId());
				}
			}
		}
	}
	public void addPatient(Patient patient) {
		for(KieSession ks : CdssApplication.kieSessions.values()) {
			ks.insert(patient);
		}
	}
	
	public void updateDisease(Long id, Disease d) {
		for(KieSession ks : CdssApplication.kieSessions.values()) {
			for(Object a : ks.getObjects()) {
				if(a instanceof Disease){
					Disease aa = (Disease)a;
					if(aa.getId().equals(id)) {
						ks.update(ks.getFactHandle(aa), d);
					}
					System.out.println(aa.getId());
				}
			}
		}
	}
	
	public void addDisease(Disease d) {
		for(KieSession ks : CdssApplication.kieSessions.values()) {
			ks.insert(d);
		}
	}
	
	public void updateSymptoms(Long id, Symptom d) {
		for(KieSession ks : CdssApplication.kieSessions.values()) {
			for(Object a : ks.getObjects()) {
				if(a instanceof Disease){
					Disease aa = (Disease)a;
					for(SymptomForDisease sfd : aa.getSymptomsForDisease()) {
						if(sfd.getSymptom().getId().equals(id)) {
							sfd.setSymptom(d);
							ks.update(ks.getFactHandle(aa), aa);
						}
					}
					
					System.out.println(aa.getId());
				}
				
				if(a instanceof Patient){
					Patient aa = (Patient)a;
					for(DiagnosticTherapy dt : aa.getMedicalRecord().getTherapy()) {
						Collection<Symptom> sym = new ArrayList<>();
						for(Symptom s : dt.getSymptoms()) {
							if(s.getId().equals(id)) {
								s = d;
								ks.update(ks.getFactHandle(aa), aa);
							}
							sym.add(s);
						}
						dt.setSymptoms(sym);
						ks.update(ks.getFactHandle(aa), aa);
					}
					
					System.out.println(aa.getId());
				}
			}
		}
	}
	

	
	public void updateIngredient(Long id, MedicineIngredient newmi) {
		for(KieSession ks : CdssApplication.kieSessions.values()) {
			for(Object a : ks.getObjects()) {
				
				if(a instanceof Patient){
					Patient aa = (Patient)a;
					for(DiagnosticTherapy dt : aa.getMedicalRecord().getTherapy()) {
						for(MedicineForTherapy mft : dt.getMedicines()) {
							Collection<MedicineIngredient> meds = new ArrayList<>();
							for(MedicineIngredient mi : mft.getMedicine().getIngredients()) {
								if(mi.getId().equals(id)) {
									mi = newmi;
								}
								meds.add(mi);
							}
							mft.getMedicine().setIngredients(meds);
							ks.update(ks.getFactHandle(aa), aa);
						}
					}
					
				}
			}
		}
	}
	
	public void updateMedicine(Long id, Medicine newmi) {
		for(KieSession ks : CdssApplication.kieSessions.values()) {
			for(Object a : ks.getObjects()) {
				
				if(a instanceof Patient){
					Patient aa = (Patient)a;
					for(DiagnosticTherapy dt : aa.getMedicalRecord().getTherapy()) {
						for(MedicineForTherapy mft : dt.getMedicines()) {
								if(mft.getMedicine().getId().equals(id)) {
									mft.setMedicine(newmi);
									ks.update(ks.getFactHandle(aa), aa);
								}
							
							
						}
					}
					
				}
			}
		}
	}
}
