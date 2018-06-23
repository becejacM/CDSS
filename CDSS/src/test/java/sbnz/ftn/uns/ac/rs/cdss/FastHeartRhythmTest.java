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
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.internal.io.ResourceFactory;

import sbnz.ftn.uns.ac.rs.cdss.events.FastHeartRhythmEvent;
import sbnz.ftn.uns.ac.rs.cdss.events.HeartBeatEvent;
import sbnz.ftn.uns.ac.rs.cdss.model.Patient;

public class FastHeartRhythmTest {

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

		runPseudoClockHeartBeatFire(ksession1);
		runPseudoClockHeartBeatNotFire(ksession2);
	}

	

	private void runPseudoClockHeartBeatFire(KieSession ksession) {
		SessionPseudoClock clock = ksession.getSessionClock();
		Patient p = new Patient();
		p.setId(1L);
		for (int index = 0; index < 30; index++) {
			HeartBeatEvent beep = new HeartBeatEvent(p.getId(), clock.getCurrentTime());
			System.out.println(new Date(clock.getCurrentTime()));
			ksession.insert(beep);
			clock.advanceTime(100, TimeUnit.MILLISECONDS);
			// 30 otkucaja jedan za drugim,
		}
		ksession.getAgenda().getAgendaGroup("heart beat").setFocus();
		ksession.fireUntilHalt();
		//System.out.println("Okinuto pravila:" +num);
		Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(FastHeartRhythmEvent.class));
		System.out.println(newEvents.size());
		assertThat(newEvents.size(), equalTo(1));
		ksession.dispose();
	}

	private void runPseudoClockHeartBeatNotFire(KieSession ksession) {
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
	}
}
