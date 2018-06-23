package sbnz.ftn.uns.ac.rs.cdss.events;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("20m")
public class OxygenLevelEvent implements Serializable{

	private Date executionTime;
    private Long patientId;
    private Integer oxygenLevel;
    
    public OxygenLevelEvent() {
    	
    }

	public OxygenLevelEvent(Long date, Long patientId, Integer oxygenLevel) {
		super();
		this.executionTime = new Date(date);
		this.patientId = patientId;
		this.oxygenLevel = oxygenLevel;
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

	public Integer getOxygenLevel() {
		return oxygenLevel;
	}

	public void setOxygenLevel(Integer oxygenLevel) {
		this.oxygenLevel = oxygenLevel;
	}
    
    
}
