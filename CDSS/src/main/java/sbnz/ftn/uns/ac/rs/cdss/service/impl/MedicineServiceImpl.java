package sbnz.ftn.uns.ac.rs.cdss.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sbnz.ftn.uns.ac.rs.cdss.exceptions.NotValidParamsException;
import sbnz.ftn.uns.ac.rs.cdss.model.AppUser;
import sbnz.ftn.uns.ac.rs.cdss.model.Medicine;
import sbnz.ftn.uns.ac.rs.cdss.model.Symptom;
import sbnz.ftn.uns.ac.rs.cdss.model.UserRole;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.MedicineDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.MedicineDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.SymptomDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.repository.AppUserRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.MedicineRepository;
import sbnz.ftn.uns.ac.rs.cdss.services.MedicineService;

@Service
public class MedicineServiceImpl implements MedicineService{

	@Autowired
	AppUserRepository appUserRepository;
	
	@Autowired
	MedicineRepository medicineRepository;

	@Override
	public Page<MedicineDetailsDTO> getAll(String username, Pageable pageable) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null) {
				throw new NotValidParamsException("You must be logged in as admin or doctor to get medicines");
			}
			List<MedicineDetailsDTO> medicines = new ArrayList<>();
			Page<Medicine> page = medicineRepository.findAll(pageable);
			for (Medicine p : page.getContent()) {
				medicines.add(new MedicineDetailsDTO(p));
			}
			return new PageImpl<>(medicines, pageable, page.getTotalElements());
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to get medicines");
		}
	}

	@Override
	public MedicineDetailsDTO getById(String username, Long id) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null) {
				throw new NotValidParamsException("You must be logged in as admin or doctor to get medicines");
			}
			Medicine p = medicineRepository.findById(id).get();
			return new MedicineDetailsDTO(p);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to get medicines");
		}
	}

	@Override
	public MedicineDetailsDTO save(String username, MedicineDTO m) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.ADMIN)) {
				throw new NotValidParamsException("You must be logged in as admin or doctor to add medicines");
			}

			Medicine p = medicineRepository.save(new Medicine(m));
			return new MedicineDetailsDTO(p);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to add medicines");
		}
	}

	@Override
	public MedicineDetailsDTO update(String username, MedicineDTO m, Long id) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.ADMIN)) {
				throw new NotValidParamsException("You must be logged in as admin to update medicine");
			}

			Medicine pat = medicineRepository.findById(id).get();
			if (pat == null) {
				throw new NotValidParamsException("Medicine with that id doesn't exists!");
			}
			pat.setName(m.getName());
			pat.setTypeOfMedicine(m.getTypeOfMedicine());
			Medicine p = medicineRepository.save(pat);
			return new MedicineDetailsDTO(p);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to update medicine");
		}
	}

	@Override
	public void delete(String username, Long id) {
		try {
			AppUser user = this.appUserRepository.findByUsername(username);
			if (user == null || !user.getRole().equals(UserRole.ADMIN)) {
				throw new NotValidParamsException("You must be logged in as admin to delete symptoms");
			}

			Medicine pat = medicineRepository.findById(id).get();
			if (pat == null) {
				throw new NotValidParamsException("Medicine with that id doesn't exists!");
			}
			medicineRepository.delete(pat);
		} catch (NotValidParamsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new NotValidParamsException("Invalid parameters while trying to delete medicine");
		}
	}
}
