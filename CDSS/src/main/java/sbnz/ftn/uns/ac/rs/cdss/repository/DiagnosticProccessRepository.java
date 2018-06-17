package sbnz.ftn.uns.ac.rs.cdss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sbnz.ftn.uns.ac.rs.cdss.model.DiagnosticTherapy;
import sbnz.ftn.uns.ac.rs.cdss.model.Disease;

public interface DiagnosticProccessRepository extends JpaRepository<DiagnosticTherapy, Long>{

}
