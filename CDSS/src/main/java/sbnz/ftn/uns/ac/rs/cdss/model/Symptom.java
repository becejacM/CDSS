package sbnz.ftn.uns.ac.rs.cdss.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import sbnz.ftn.uns.ac.rs.cdss.model.dto.SymptomDTO;

@Entity
@Table(name = "symptom")
public class Symptom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;

	@Column(name = "name", nullable = true)
	protected String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "symptom")
	protected List<SymptomForDisease> symptomsForDisease;

	public Symptom() {

	}

	public Symptom(SymptomDTO s) {
		this.name = s.getName();
		this.symptomsForDisease = new ArrayList<SymptomForDisease>();
	}

	public Symptom(Long id, String name, List<SymptomForDisease> symptomsForDisease) {
		super();
		this.id = id;
		this.name = name;
		this.symptomsForDisease = symptomsForDisease;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SymptomForDisease> getSymptomsForDisease() {
		return symptomsForDisease;
	}

	public void setSymptomsForDisease(List<SymptomForDisease> symptomsForDisease) {
		this.symptomsForDisease = symptomsForDisease;
	}

	@Override
	public String toString() {
		return "Symptom [id=" + id + ", name=" + name + ", symptomsForDisease=" + symptomsForDisease + "]";
	}

}
