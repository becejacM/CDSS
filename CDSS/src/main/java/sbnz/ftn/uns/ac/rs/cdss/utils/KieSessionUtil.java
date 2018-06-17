package sbnz.ftn.uns.ac.rs.cdss.utils;

import java.io.IOException;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import sbnz.ftn.uns.ac.rs.cdss.service.impl.DebugAgendaEventListener;
import sbnz.ftn.uns.ac.rs.cdss.services.DiagnosticProccesService;

public class KieSessionUtil {

	/*@Autowired 
    KieContainer kContainer;
	
	@Autowired
	DiagnosticProccesService diagnosticService;
	
	
	private KieSession kieSession;
	
	public KieSession createKieSession() throws IOException {
       
        kieSession = kContainer.newKieSession("ksession-rules");
        kieSession.addEventListener(new DebugAgendaEventListener());
        kieSession.setGlobal("diagnosticService", diagnosticService);

        return kieSession;
    }*/
}
