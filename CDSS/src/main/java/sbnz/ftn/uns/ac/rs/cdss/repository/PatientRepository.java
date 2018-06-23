package sbnz.ftn.uns.ac.rs.cdss.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sbnz.ftn.uns.ac.rs.cdss.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{

	Page<Patient> findByFirstnameStartsWith(String firstname, Pageable page);
	
	Page<Patient> findByLastnameStartsWith(String lastname, Pageable page);
    
   	Page<Patient> findByFirstnameStartsWithOrLastnameStartsWith(String firstname, String lastname, Pageable page);
}


