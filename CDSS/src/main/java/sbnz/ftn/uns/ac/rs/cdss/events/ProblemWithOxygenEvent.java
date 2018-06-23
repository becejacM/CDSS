package sbnz.ftn.uns.ac.rs.cdss.events;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("30m")
public class ProblemWithOxygenEvent implements Serializable{

	private Long patientId;
	private String reason;
	
	public ProblemWithOxygenEvent() {
		
	}

	public ProblemWithOxygenEvent(Long patientId, String reason) {
		super();
		this.patientId = patientId;
		this.reason = reason;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
}
