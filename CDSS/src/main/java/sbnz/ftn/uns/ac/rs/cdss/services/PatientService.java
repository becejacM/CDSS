package sbnz.ftn.uns.ac.rs.cdss.services;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sbnz.ftn.uns.ac.rs.cdss.model.Patient;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.AlergiesDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.MedicineDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.MedicineRecordDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.PatientDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.PatientDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.ReportDTO;


public interface PatientService {

	Page<PatientDetailsDTO> getAllPatients(String username, Pageable pageable);
	AlergiesDetailsDTO getAlergies(String username,Long id);
	PatientDetailsDTO getById(String username, Long id);
	PatientDetailsDTO save(String username, PatientDTO patient);
	PatientDetailsDTO update(String username, PatientDTO patient, Long id);
	void deletePatient(String username, Long id);
	MedicineDetailsDTO addAlergie(String username, Long id, String name);
	
	Collection<ReportDTO> getReport1(String username);
	Collection<ReportDTO> getReport2(String username);
	Collection<ReportDTO> getReport3(String username);

	Page<PatientDetailsDTO> getByFN(String username,String firstname, Pageable pageable);
	Page<PatientDetailsDTO> getByLN(String username,String lastname, Pageable pageable);
	Page<PatientDetailsDTO> getByFNLN(String username,String firstname,String lastname, Pageable pageable);

	Collection<MedicineRecordDTO> getMR(String username, Long id);
}
