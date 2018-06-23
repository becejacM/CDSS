package sbnz.ftn.uns.ac.rs.cdss.events;

import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("15h")
public class UrinationEvent {

	private Date executionTime;
    private Long patientId;
    private Integer urinationSum;
    
    public UrinationEvent() {
    	
    }

	public UrinationEvent(Long date, Long patientId, Integer urinationSum) {
		super();
		this.executionTime = new Date(date);
		this.patientId = patientId;
		this.urinationSum = urinationSum;
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

	public Integer getUrinationSum() {
		return urinationSum;
	}

	public void setUrinationSum(Integer urinationSum) {
		this.urinationSum = urinationSum;
	}
    
    
}
