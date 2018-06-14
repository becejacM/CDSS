package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import java.util.ArrayList;
import java.util.Collection;

public class ListOfSymbolsDTO {

	Collection<SymptomDTO> symptoms = new ArrayList<>();
	
	public ListOfSymbolsDTO() {
		
	}

	public ListOfSymbolsDTO(Collection<SymptomDTO> symptoms) {
		super();
		this.symptoms = symptoms;
	}


	public Collection<SymptomDTO> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(Collection<SymptomDTO> symptoms) {
		this.symptoms = symptoms;
	}
	
	
}
