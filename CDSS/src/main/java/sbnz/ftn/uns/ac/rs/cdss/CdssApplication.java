package sbnz.ftn.uns.ac.rs.cdss;

import java.io.IOException;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sbnz.ftn.uns.ac.rs.cdss.service.impl.DebugAgendaEventListener;
import sbnz.ftn.uns.ac.rs.cdss.services.DiagnosticProccesService;


@SpringBootApplication
public class CdssApplication {

	@Autowired
	private DiagnosticProccesService diagnosticProccessService;
	
	@Bean
    public KieSession kieSession() throws IOException {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("CDSS-rules", "CDSS-rules-kjar", "0.0.1-SNAPSHOT"));

        KieScanner kScanner = ks.newKieScanner(kContainer);
        kScanner.start(10000);
        KieSession kieSession = kContainer.newKieSession("ksession-rules");
        kieSession.addEventListener(new DebugAgendaEventListener());
        kieSession.setGlobal("diagnosticProccessService", diagnosticProccessService);
        //kieSession.setGlobal("diagnosticProccessService", diagnosticProccessService);
        //System.out.println("ubacujem servis: " +kieSession.getGlobal("diagnosticProccessService"));
        return kieSession;
    }
	
	/*@Bean
	public KieContainer kieContainer() {
		System.out.println("tu sam");
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("CDSS-rules", "CDSS-rules-kjar", "0.0.1-SNAPSHOT"));
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(5000);
		return kContainer;
	}*/
	
	public static void main(String[] args) {
		SpringApplication.run(CdssApplication.class, args);
	}
}
