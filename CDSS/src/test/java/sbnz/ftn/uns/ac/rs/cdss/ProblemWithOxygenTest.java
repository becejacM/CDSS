package sbnz.ftn.uns.ac.rs.cdss;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Date;
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
import sbnz.ftn.uns.ac.rs.cdss.events.OxygenLevelEvent;
import sbnz.ftn.uns.ac.rs.cdss.events.ProblemWithOxygenEvent;
import sbnz.ftn.uns.ac.rs.cdss.model.Patient;

public class ProblemWithOxygenTest {
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

		runPseudoClockOxygenLevelFire(kbase.newKieSession(ksconf2, null));
		runPseudoClockOxygenLevelFire2(kbase.newKieSession(ksconf2, null));

		runPseudoClockOxygenLevelNotFire(kbase.newKieSession(ksconf2, null));
		runPseudoClockOxygenLevelNotFire2(kbase.newKieSession(ksconf2, null));	}

	

	private void runPseudoClockOxygenLevelFire(KieSession ksession) {
		//u poslednjih 15 minuta nije zabelezen rast kiseonika, a nivo je manji od 70mmHg -> pravilo se okida
		SessionPseudoClock clock = ksession.getSessionClock();
		Patient p = new Patient();
		p.setId(1L);
		OxygenLevelEvent oxygenLevel = new OxygenLevelEvent(clock.getCurrentTime(), p.getId(), 60);
		ksession.insert(oxygenLevel);

		clock.advanceTime(20, TimeUnit.MINUTES);
		
		ksession.getAgenda().getAgendaGroup("oxygen level problem").setFocus();
		ksession.fireAllRules();

		Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(ProblemWithOxygenEvent.class));
		System.out.println(newEvents.size());
		assertThat(newEvents.size(), equalTo(1));
		ksession.dispose();
	}

	private void runPseudoClockOxygenLevelNotFire(KieSession ksession) {
		//u poslednjih 15 minuta nije zabelezen rast kiseonika, a nivo je veci od 70mmHg -> pravilo se ne okida
		SessionPseudoClock clock = ksession.getSessionClock();
		Patient p = new Patient();
		p.setId(1L);
		OxygenLevelEvent oxygenLevel = new OxygenLevelEvent(clock.getCurrentTime(), p.getId(), 80);
		ksession.insert(oxygenLevel);

		clock.advanceTime(20, TimeUnit.MINUTES);

		
		ksession.getAgenda().getAgendaGroup("oxygen level problem").setFocus();
		ksession.fireAllRules();

		Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(ProblemWithOxygenEvent.class));
		System.out.println(newEvents.size());
		assertThat(newEvents.size(), equalTo(0));
		ksession.dispose();
	}
	
	private void runPseudoClockOxygenLevelNotFire2(KieSession ksession) {
		//u poslednjih 15 minuta zabelezen rast kiseonika, a nivo je na pocetku manji od 70mmHg -> pravilo se ne okida
		SessionPseudoClock clock = ksession.getSessionClock();
		Patient p = new Patient();
		p.setId(1L);
		OxygenLevelEvent oxygenLevel = new OxygenLevelEvent(clock.getCurrentTime(), p.getId(), 60);
		ksession.insert(oxygenLevel);

		clock.advanceTime(14, TimeUnit.MINUTES);
		OxygenLevelEvent oxygenLevel2 = new OxygenLevelEvent(clock.getCurrentTime(), p.getId(), 80);
		ksession.insert(oxygenLevel2);

		
		ksession.getAgenda().getAgendaGroup("oxygen level problem").setFocus();
		ksession.fireAllRules();

		Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(ProblemWithOxygenEvent.class));
		System.out.println(newEvents.size());
		assertThat(newEvents.size(), equalTo(0));
		ksession.dispose();
	}
	
	private void runPseudoClockOxygenLevelFire2(KieSession ksession) {
		//u poslednjih 15 minuta zabelezen pad nivoa kiseonika, a nivo je na pocetku manji od 70mmHg -> pravilo se okida
		SessionPseudoClock clock = ksession.getSessionClock();
		Patient p = new Patient();
		p.setId(1L);
		OxygenLevelEvent oxygenLevel = new OxygenLevelEvent(clock.getCurrentTime(), p.getId(), 60);
		ksession.insert(oxygenLevel);

		clock.advanceTime(14, TimeUnit.MINUTES);
		OxygenLevelEvent oxygenLevel2 = new OxygenLevelEvent(clock.getCurrentTime(), p.getId(), 55);
		ksession.insert(oxygenLevel2);

		
		ksession.getAgenda().getAgendaGroup("oxygen level problem").setFocus();
		ksession.fireAllRules();

		Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(ProblemWithOxygenEvent.class));
		System.out.println(newEvents.size());
		assertThat(newEvents.size(), equalTo(1));
		ksession.dispose();
	}
	/*private void runPseudoClockOxygenLevelNotFire(KieSession ksession) {
		SessionPseudoClock clock = ksession.getSessionClock();
		Patient p = new Patient();
		p.setId(1L);
		for (int index = 0; index < 5; index++) {
			HeartBeatEvent beep = new HeartBeatEvent(p.getId(), clock.getCurrentTime());
			ksession.insert(beep);
			clock.advanceTime(2, TimeUnit.SECONDS);
			ksession.getAgenda().getAgendaGroup("heart beat").setFocus();
			int ruleCount = ksession.fireAllRules();
			// 25 otkucaja za vise od 25*2=50 sekundi, nije okinuto pravilo
			assertThat(ruleCount, equalTo(0));
		}
		Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(FastHeartRhythmEvent.class));
		System.out.println(newEvents.size());
		assertThat(newEvents.size(), equalTo(0));
		ksession.dispose();
	}*/
}
