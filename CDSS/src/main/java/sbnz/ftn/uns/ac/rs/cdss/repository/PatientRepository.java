package sbnz.ftn.uns.ac.rs.cdss.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



import sbnz.ftn.uns.ac.rs.cdss.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{

}


