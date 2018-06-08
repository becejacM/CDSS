package sbnz.ftn.uns.ac.rs.cdss;

import java.io.IOException;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sbnz.ftn.uns.ac.rs.cdss.service.impl.DebugAgendaEventListener;


@SpringBootApplication
public class CdssApplication {

	@Bean
    public KieSession kieSession() throws IOException {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("CDSS-rules", "CDSS-rules-kjar", "0.0.1-SNAPSHOT"));

        KieScanner kScanner = ks.newKieScanner(kContainer);
        kScanner.start(5000);
        KieSession kieSession = kContainer.newKieSession("ksession-rules");
        kieSession.addEventListener(new DebugAgendaEventListener());

        return kieSession;
    }
	
	public static void main(String[] args) {
		SpringApplication.run(CdssApplication.class, args);
	}
}
