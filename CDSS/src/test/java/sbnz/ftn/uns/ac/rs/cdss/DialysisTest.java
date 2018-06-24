package sbnz.ftn.uns.ac.rs.cdss;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.drools.core.ClockType;
import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;

import sbnz.ftn.uns.ac.rs.cdss.events.FastHeartRhythmEvent;
import sbnz.ftn.uns.ac.rs.cdss.events.HeartBeatEvent;
import sbnz.ftn.uns.ac.rs.cdss.events.UrgentDialysis;
import sbnz.ftn.uns.ac.rs.cdss.events.UrinationEvent;
import sbnz.ftn.uns.ac.rs.cdss.model.DiagnosticTherapy;
import sbnz.ftn.uns.ac.rs.cdss.model.Disease;
import sbnz.ftn.uns.ac.rs.cdss.model.MedicalRecord;
import sbnz.ftn.uns.ac.rs.cdss.model.Patient;

public class DialysisTest {

	@Test
	public void testCEPConfigThroughCode() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				.newKieContainer(ks.newReleaseId("CDSS-rules", "CDSS-rules-kjar", "0.0.1-SNAPSHOT"));

		KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
		kbconf.setOption(EventProcessingOption.STREAM);
		KieBase kbase = kContainer.newKieBase(kbconf);

		KieSessionConfiguration ksconf2 = ks.newKieSessionConfiguration();
		ksconf2.setOption(ClockTypeOption.get(ClockType.PSEUDO_CLOCK.getId()));
		KieSession ksession1 = kbase.newKieSession(ksconf2, null);
		KieSession ksession2 = kbase.newKieSession(ksconf2, null);

		runPseudoClockDialysisFire(ksession1);
	}

	

	private void runPseudoClockDialysisFire(KieSession ksession) {
		SessionPseudoClock clock = ksession.getSessionClock();
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
		for(DiagnosticTherapy dd:mr.getTherapy()) {
			System.out.println(dd.getDisease().getName());
		}
		ksession.insert(p);
		UrinationEvent ue = new UrinationEvent(clock.getCurrentTime(), p.getId(), 60);
		ksession.insert(ue);
		clock.advanceTime(11, TimeUnit.HOURS);
		UrinationEvent ue2 = new UrinationEvent(clock.getCurrentTime(), p.getId(), 20);
		ksession.insert(ue2);

		for (int index = 0; index < 20; index++) {
			HeartBeatEvent beep = new HeartBeatEvent(p.getId(), clock.getCurrentTime());
			System.out.println(new Date(clock.getCurrentTime()));
			ksession.insert(beep);
			clock.advanceTime(100, TimeUnit.MILLISECONDS);
			// 20 otkucaja jedan za drugim,
		}

		ksession.getAgenda().getAgendaGroup("disysis").setFocus();
		ksession.fireAllRules();
		//System.out.println("Okinuto pravila:" +num);
		Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(UrgentDialysis.class));
		System.out.println(newEvents.size());
		assertThat(newEvents.size(), equalTo(1));
		ksession.dispose();
	}
}
