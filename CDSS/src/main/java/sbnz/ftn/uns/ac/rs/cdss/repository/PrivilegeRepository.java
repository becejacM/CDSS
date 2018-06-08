package sbnz.ftn.uns.ac.rs.cdss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sbnz.ftn.uns.ac.rs.cdss.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}