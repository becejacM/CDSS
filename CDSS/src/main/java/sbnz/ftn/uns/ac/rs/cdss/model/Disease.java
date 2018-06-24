package sbnz.ftn.uns.ac.rs.cdss.model;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.drools.core.common.ClassAwareObjectStore.SingleClassStore;
import org.junit.Ignore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiseaseDTO;

@Entity
@Table(name = "disease")
public class Disease {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(name = "name", nullable = true)
	protected String name;
	
	@Column(name = "typeOfDisease", nullable = false)
	@Enumerated(EnumType.STRING)
	protected TypeOfDisease typeOfDisease;
	
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "disease")
	private List<SymptomForDisease> symptomsForDisease = new ArrayList<>();
	
	
	
	public Disease() {
		
	}

	public Disease(DiseaseDTO d) {
		this.name = d.getName();
		this.typeOfDisease = d.getTypeOfDisease();
		//this.symptomsForDisease = new ArrayList<SymptomForDisease>();
	}
	
	public Disease(Long id, String name, TypeOfDisease typeOfDisease, List<SymptomForDisease> symptomsForDisease) {
		super();
		this.id = id;
		this.name = name;
		this.typeOfDisease = typeOfDisease;
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

	public TypeOfDisease getTypeOfDisease() {
		return typeOfDisease;
	}

	public void setTypeOfDisease(TypeOfDisease typeOfDisease) {
		this.typeOfDisease = typeOfDisease;
	}


	public List<SymptomForDisease> getSymptomsForDisease() {
		return symptomsForDisease;
	}

	public void setSymptomsForDisease(List<SymptomForDisease> symptomsForDisease) {
		this.symptomsForDisease = symptomsForDisease;
	}

	@Override
	public String toString() {
		return "Disease [id=" + id + ", name=" + name + ", typeOfDisease=" + typeOfDisease ;
	}
	
	public Collection<Symptom> getSyms() {
		Collection<Symptom> syms = new ArrayList<>();
		for(SymptomForDisease s : this.symptomsForDisease) {
			syms.add(s.getSymptom());
		}
		return syms;
	}
	
	public void setSyms() {
		Collection<Symptom> syms = new ArrayList<>();
		for(SymptomForDisease s : this.symptomsForDisease) {
			syms.add(s.getSymptom());
		}
	}


}
