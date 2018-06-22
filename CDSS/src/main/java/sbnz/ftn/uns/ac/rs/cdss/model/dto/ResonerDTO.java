package sbnz.ftn.uns.ac.rs.cdss.model.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import sbnz.ftn.uns.ac.rs.cdss.model.Disease;
import sbnz.ftn.uns.ac.rs.cdss.model.Symptom;

public class ResonerDTO {

	private Collection<Symptom> symptoms = new ArrayList<>();
	private Collection<Disease> disease = new ArrayList<>();
	private Map<Disease, Long> mapa = new HashMap<>();
	public ResonerDTO() {
		
	}

	public Collection<Symptom> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(Collection<Symptom> symptoms) {
		this.symptoms = symptoms;
	}

	public Collection<Disease> getDisease() {
		return disease;
	}

	public void setDisease(Collection<Disease> disease) {
		this.disease = disease;
	}

	public Map<Disease, Long> getMapa() {
		return mapa;
	}

	public void setMapa(Map<Disease, Long> mapa) {
		this.mapa = mapa;
	}

	public void sort() {
		Map<Disease, Long> result = this.mapa.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
	}
}
