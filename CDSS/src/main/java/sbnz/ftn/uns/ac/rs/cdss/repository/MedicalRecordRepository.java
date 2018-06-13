package sbnz.ftn.uns.ac.rs.cdss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sbnz.ftn.uns.ac.rs.cdss.model.MedicalRecord;
import sbnz.ftn.uns.ac.rs.cdss.model.Patient;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long>{

}
