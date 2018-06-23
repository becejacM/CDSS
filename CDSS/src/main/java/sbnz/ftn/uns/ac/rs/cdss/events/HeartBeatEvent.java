package sbnz.ftn.uns.ac.rs.cdss.events;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("10m")
public class HeartBeatEvent implements Serializable{

    private Date executionTime;
    private Long patientId;
    
    public HeartBeatEvent() {
    	
    }

	public HeartBeatEvent(Long patientId, Long date) {
		super();
		this.patientId = patientId;
		this.executionTime = new Date(date);
	}

	public Date getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
    
	
    
}
