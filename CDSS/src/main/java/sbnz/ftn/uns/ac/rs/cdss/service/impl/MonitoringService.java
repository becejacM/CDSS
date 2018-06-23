package sbnz.ftn.uns.ac.rs.cdss.service.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.drools.core.ClockType;
import org.drools.core.time.SessionPseudoClock;
import org.hamcrest.core.IsInstanceOf;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import sbnz.ftn.uns.ac.rs.cdss.events.FastHeartRhythmEvent;
import sbnz.ftn.uns.ac.rs.cdss.events.HeartBeatEvent;
import sbnz.ftn.uns.ac.rs.cdss.events.OxygenLevelEvent;
import sbnz.ftn.uns.ac.rs.cdss.events.UrinationEvent;
import sbnz.ftn.uns.ac.rs.cdss.model.DiagnosticTherapy;
import sbnz.ftn.uns.ac.rs.cdss.model.Disease;
import sbnz.ftn.uns.ac.rs.cdss.model.MedicalRecord;
import sbnz.ftn.uns.ac.rs.cdss.model.Patient;

@Component
public class MonitoringService implements CommandLineRunner {

	//@Autowired
	//KieSession kieSession;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("CDSS-rules", "CDSS-rules-kjar", "0.0.1-SNAPSHOT"));

        KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
		kbconf.setOption(EventProcessingOption.STREAM);
		KieBase kbase = kContainer.newKieBase(kbconf);
		
        KieSessionConfiguration ksconf1 = ks.newKieSessionConfiguration();
        ksconf1.setOption(ClockTypeOption.get(ClockType.REALTIME_CLOCK.getId()));
        KieSession ksession1 = kbase.newKieSession(ksconf1, null);
        testHeartBeat(ksession1);
	}
	
	public void testHeartBeat(KieSession kieSession) {
		Thread t = new Thread() {
            @Override
            public void run() {
        		Patient p = new Patient();
        		p.setId(1L);
        		while(true) {
                	try {
                        Thread.sleep(35000);
                    } catch (InterruptedException e) {
                    }
                	for (int index = 0; index < 26; index++) {
            			HeartBeatEvent beep = new HeartBeatEvent(p.getId(), new Date().getTime());
            			kieSession.insert(beep);
            			//System.out.println(beep.getExecutionTime());
            			// 30 otkucaja jedan za drugim, razmak 100 milisec
            			try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            //do nothing
                        }
            		}
                	//System.out.println("opaljujem");
                    kieSession.getAgenda().getAgendaGroup("heart beat").setFocus();
                    int ruleCount = kieSession.fireAllRules();
                    if(ruleCount>0) {
                    	sendMessage("Alarm: U poslednjih 10 sekundi zabelezeno vise od 25 otkucaja srca");
                    }
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                    }
                }
        		
            }
        };
        t.setDaemon(true);
        t.start();
	}
	
	
	public void testOxygenLevel(KieSession kieSession) {
		Thread t = new Thread() {
            @Override
            public void run() {
        		Patient p = new Patient();
        		p.setId(1L);
        		while(true) {
                	
            		OxygenLevelEvent oxygenLevel = new OxygenLevelEvent(new Date().getTime(), p.getId(), 60);
            		kieSession.insert(oxygenLevel);

            		try {
                        Thread.sleep(1000*60*13);
                    } catch (InterruptedException e) {
                    }
            		
            		kieSession.getAgenda().getAgendaGroup("oxygen level problem").setFocus();
                    int ruleCount = kieSession.fireAllRules();
                    if(ruleCount>0) {
                    	sendMessage("Alarm: U poslednjih 15 minuta nije zabelezen rast nivoa kiseonika, a nivo je manji od 70mmHg");
                    }
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                    }
                }
        		
            }
        };
        t.setDaemon(true);
        t.start();
	}
	
	public void testDialysis(KieSession kieSession) {
		Thread t = new Thread() {
            @Override
            public void run() {
            	Patient p = new Patient();
    			p.setId(1L);
    			MedicalRecord mr = new MedicalRecord();
    			mr.setId(1L);
    			DiagnosticTherapy dt = new DiagnosticTherapy();
    			dt.setId(1L);
    			Disease d = new Disease();
    			d.setId(1L);
    			d.setName("Hronicna bubrezna bolest");
    			dt.setDisease(d);
    			mr.getTherapy().add(dt);
    			p.setMedicalRecord(mr);
        		while(true) {
                	
        			
        			for(DiagnosticTherapy dd:mr.getTherapy()) {
        				System.out.println(dd.getDisease().getName());
        			}
        			kieSession.insert(p);
        			UrinationEvent ue = new UrinationEvent(new Date().getTime(), p.getId(), 60);
        			kieSession.insert(ue);
        			try {
                        Thread.sleep(1000*60*60*11);
                    } catch (InterruptedException e) {
                    }
        			UrinationEvent ue2 = new UrinationEvent(new Date().getTime(), p.getId(), 20);
        			kieSession.insert(ue2);

        			for (int index = 0; index < 20; index++) {
        				HeartBeatEvent beep = new HeartBeatEvent(p.getId(), new Date().getTime());
        				kieSession.insert(beep);
        				try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                        }
        				// 20 otkucaja jedan za drugim,
        			}
        			kieSession.getAgenda().getAgendaGroup("disysis").setFocus();
        			int ruleCount = kieSession.fireAllRules();
            		
                    if(ruleCount>0) {
                    	sendMessage("Alarm: U poslednjih 15 minuta nije zabelezen rast nivoa kiseonika, a nivo je manji od 70mmHg");
                    }
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                    }
                }
        		
            }
        };
        t.setDaemon(true);
        t.start();
	}
	private SimpMessagingTemplate template;

	@Autowired
	private MonitoringService(SimpMessagingTemplate template) {
		this.template = template;
	}

	public void sendMessage(String message) {
		System.out.println("Tu saaaam");
		this.template.convertAndSend("/secured/monitoring", message);
	}
}
