package sbnz.ftn.uns.ac.rs.cdss;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.drools.core.ClockType;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sbnz.ftn.uns.ac.rs.cdss.service.impl.DebugAgendaEventListener;
import sbnz.ftn.uns.ac.rs.cdss.service.impl.MonitoringService;
import sbnz.ftn.uns.ac.rs.cdss.services.DiagnosticProccesService;


@SpringBootApplication
public class CdssApplication {

	@Autowired
	private DiagnosticProccesService diagnosticProccessService;
	
	
	public static Map<String, KieSession> kieSessions = new HashMap<>();
	

	
	@Bean
	public KieContainer kieContainer() {
		System.out.println("tu sam");
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("CDSS-rules", "CDSS-rules-kjar", "0.0.1-SNAPSHOT"));
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(5000);
		return kContainer;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CdssApplication.class, args);
	}
}
