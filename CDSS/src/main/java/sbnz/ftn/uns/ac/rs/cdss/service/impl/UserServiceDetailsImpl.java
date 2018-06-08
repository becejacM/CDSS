package sbnz.ftn.uns.ac.rs.cdss.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sbnz.ftn.uns.ac.rs.cdss.CdssApplication;
import sbnz.ftn.uns.ac.rs.cdss.model.AppUser;
import sbnz.ftn.uns.ac.rs.cdss.repository.AppUserRepository;
import sbnz.ftn.uns.ac.rs.cdss.security.SecurityUser;


@Service
public class UserServiceDetailsImpl implements UserExtendedService {
    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(CdssApplication.class);

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUser user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return new SecurityUser(user);
        }
    }

    @Transactional
    @Override
    public boolean changePassword(String oldPass,String newPass, String username) {
        UsernamePasswordAuthenticationToken t = new UsernamePasswordAuthenticationToken(
                username, oldPass);
        try {
            this.authenticationManager.authenticate(t);
            newPass = new BCryptPasswordEncoder().encode(newPass);
            this.userRepository.changePassword(newPass,username);
            return true;
        } catch (AuthenticationException e) {
            logger.warn(String.format("Someone try to change password but unsuccessfully," +
                            " wrong current password for username %s",
                    username));
            return false;
        }
    }


    
}