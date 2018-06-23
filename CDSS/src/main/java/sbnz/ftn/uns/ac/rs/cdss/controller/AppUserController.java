package sbnz.ftn.uns.ac.rs.cdss.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import sbnz.ftn.uns.ac.rs.cdss.model.AppUser;
import sbnz.ftn.uns.ac.rs.cdss.model.Disease;
import sbnz.ftn.uns.ac.rs.cdss.model.Patient;
import sbnz.ftn.uns.ac.rs.cdss.model.UserRole;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.AppUserDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.AppUserDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.AuthenticationRequestDto;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.AuthenticationResponseDto;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.ChangePasswordDto;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiseaseDTO;
import sbnz.ftn.uns.ac.rs.cdss.model.dto.DiseaseDetailsDTO;
import sbnz.ftn.uns.ac.rs.cdss.repository.AppUserRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.DiseaseRepository;
import sbnz.ftn.uns.ac.rs.cdss.repository.PatientRepository;
import sbnz.ftn.uns.ac.rs.cdss.security.SecurityUser;
import sbnz.ftn.uns.ac.rs.cdss.security.TokenUtils;
import sbnz.ftn.uns.ac.rs.cdss.service.impl.DebugAgendaEventListener;
import sbnz.ftn.uns.ac.rs.cdss.service.impl.UserExtendedService;
import sbnz.ftn.uns.ac.rs.cdss.services.AppUserService;
import sbnz.ftn.uns.ac.rs.cdss.services.DiagnosticProccesService;

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
	HttpServletRequest httpServletRequest;

	@Autowired
	TokenUtils tokenUtils;

    @Autowired
    private UserExtendedService userDetailsService;

    @Autowired
    private AppUserService userService;

    @Autowired
    @Lazy
    private KieContainer kieContainer;
    //private KieSession kieSession;
    
    @Autowired
    private DiseaseRepository diseaseRepository;
    
    @Autowired
    private AppUserRepository userRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
	DiagnosticProccesService diagnosticProccessService;
    
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

        KieSession kieSession = kieContainer.newKieSession("ksession-rules");
        kieSession.addEventListener(new DebugAgendaEventListener());
        kieSession.setGlobal("diagnosticProccessService", diagnosticProccessService);
        
        
        System.out.println("ovde sam");
        AppUser user = userRepository.findByUsername(userDetails.getUsername());
        kieSession.insert(user);
        Collection<Disease> diseases = diseaseRepository.findAll();
        for(Disease d: diseases) {
        	kieSession.insert(d);
        }
        Collection<Patient> patients = patientRepository.findAll();
        for(Patient p: patients) {
        	kieSession.insert(p);
        }
        
        CdssApplication.kieSessions.put(user.getUsername(), kieSession);
        SecurityUser su = (SecurityUser) userDetails;
        String token = this.tokenUtils.generateToken(userDetails);
        // Return the token
        AuthenticationResponseDto authResponse = new AuthenticationResponseDto(token, su.getId(), su.getUsername(),su.getRole(),
                su.getAuthorities().toString(), su.isEnabled(), su.getUsername(), su.getFirstname(), su.getLastname());
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/logout")
    public ResponseEntity<?> logout() {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
    	System.out.println("odjeeeeeeeeeeeeeeeeeeeeeeeee"+username);

		this.userService.logout(username);
		return new ResponseEntity<>( HttpStatus.OK);
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
    
    @GetMapping()
	public ResponseEntity<Page<AppUserDetailsDTO>> getAllDiseases(Pageable pageable) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		int limit = pageable.getPageSize() <= 25 ? pageable.getPageSize() : 25;
		Pageable newPageable = new PageRequest(pageable.getPageNumber(), limit);
		return new ResponseEntity<>(this.userService.getAllByRole(username, UserRole.DOCTOR, newPageable), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<AppUserDetailsDTO> getUser(@PathVariable Long id) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		return new ResponseEntity<>(this.userService.getById(username, id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<AppUserDetailsDTO> createUser(
			@RequestBody AppUserDTO u) {
		u.setPassword(this.hashPassword(u.getPassword()));
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		return new ResponseEntity<>(this.userService.save(username, u),
				HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<AppUserDetailsDTO> updateUser(@RequestBody AppUserDTO u,
			@PathVariable Long id) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		return new ResponseEntity<>(this.userService.update(username, u, id),
				HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		this.userService.delete(username, id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

    public static String hashPassword(String password_plaintext) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String s = bCryptPasswordEncoder.encode(password_plaintext);
        return (s);
    }
}
