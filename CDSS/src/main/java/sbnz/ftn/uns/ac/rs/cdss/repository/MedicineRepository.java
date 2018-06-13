package sbnz.ftn.uns.ac.rs.cdss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sbnz.ftn.uns.ac.rs.cdss.model.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long>{

	Medicine findByName(String name);
}
