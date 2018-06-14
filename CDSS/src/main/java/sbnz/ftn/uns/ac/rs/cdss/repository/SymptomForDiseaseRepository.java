package sbnz.ftn.uns.ac.rs.cdss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sbnz.ftn.uns.ac.rs.cdss.model.Privilege;
import sbnz.ftn.uns.ac.rs.cdss.model.SymptomForDisease;

public interface SymptomForDiseaseRepository extends JpaRepository<SymptomForDisease, Long>{

}
