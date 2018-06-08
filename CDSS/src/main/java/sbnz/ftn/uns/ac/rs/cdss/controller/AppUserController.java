package sbnz.ftn.uns.ac.rs.cdss.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import sbnz.ftn.uns.ac.rs.cdss.CdssApplication;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.AuthenticationRequestDto;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.AuthenticationResponseDto;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.ChangePasswordDto;
import sbnz.ftn.uns.ac.rs.cdss.security.SecurityUser;
import sbnz.ftn.uns.ac.rs.cdss.security.TokenUtils;
import sbnz.ftn.uns.ac.rs.cdss.service.impl.UserExtendedService;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class AppUserController {
    private static final Logger logger = LoggerFactory.getLogger(CdssApplication.class);

    @Value("${cdss.token.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserExtendedService userDetailsService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequestDto authenticationRequest) {
        // Perform the authentication
        Authentication authentication = null;
        UsernamePasswordAuthenticationToken t = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword());
        try {
            authentication = this.authenticationManager.authenticate(t);
        } catch (AuthenticationException e) {
            logger.warn(String.format("Invalid login with username: %s",
                    authenticationRequest.getUsername()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-authentication so we can generate token
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());


        SecurityUser su = (SecurityUser) userDetails;
        String token = this.tokenUtils.generateToken(userDetails);
        // Return the token
        AuthenticationResponseDto authResponse = new AuthenticationResponseDto(token, su.getId(), su.getUsername(),su.getRole(),
                su.getAuthorities().toString(), su.isEnabled(), su.getUsername(), su.getFirstname(), su.getLastname());
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }


    @PutMapping(value = "/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        boolean changedPassword = this.userDetailsService.changePassword
                (changePasswordDto.getCurrentPassword()
                        , changePasswordDto.getNewPassword(),
                        changePasswordDto.getUsername());
        if(changedPassword) {
            return new ResponseEntity<>("Password has been successfully changed",HttpStatus.OK);
        }
        return new ResponseEntity<>("Current password is not valid",HttpStatus.BAD_REQUEST);
    }

//    public static String hashPassword(String password_plaintext) {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String s = bCryptPasswordEncoder.encode(password_plaintext);
//        return (s);
//    }
}
