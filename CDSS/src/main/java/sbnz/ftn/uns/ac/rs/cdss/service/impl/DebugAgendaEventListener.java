package sbnz.ftn.uns.ac.rs.cdss.service.impl;

import org.kie.api.definition.rule.Rule;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DebugAgendaEventListener extends DefaultAgendaEventListener {
    // private final static Logger LOGGER = Logger.getLogger(DebugAgendaEventListener.class.getName());
    private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DebugAgendaEventListener.class.getName());

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        Rule rule = event.getMatch().getRule();
        LOGGER.info("Rule fired: " + rule.getName());


    }
}
