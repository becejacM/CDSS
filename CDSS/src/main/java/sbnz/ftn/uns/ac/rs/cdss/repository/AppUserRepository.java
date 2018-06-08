package sbnz.ftn.uns.ac.rs.cdss.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sbnz.ftn.uns.ac.rs.cdss.model.AppUser;

import javax.transaction.Transactional;


@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Page<AppUser> findAll(Pageable pageable);

    AppUser findByUsername(String username);

    Page<AppUser> findByVerified(Pageable pageable, boolean verified);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE AppUser user set user.password = ?1 where user.username = ?2")
    void changePassword(String password, String username);
}
