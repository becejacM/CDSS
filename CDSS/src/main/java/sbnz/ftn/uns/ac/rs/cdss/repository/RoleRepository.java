package sbnz.ftn.uns.ac.rs.cdss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sbnz.ftn.uns.ac.rs.cdss.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role role);

}