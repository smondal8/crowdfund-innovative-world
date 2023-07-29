package org.soumyadev.crowdfundinnovativeworld.Controller;

import org.soumyadev.crowdfundinnovativeworld.DTO.*;
import org.soumyadev.crowdfundinnovativeworld.ExceptionHandling.UserAlreadyExists;
import org.soumyadev.crowdfundinnovativeworld.Service.UserService;
import org.soumyadev.crowdfundinnovativeworld.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.management.MBeanRegistrationException;
import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

//    @GetMapping({ "/hello" })
//    public String firstPage() {
//        return "Hello World";
//    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getUsername(), authenticationRequestDTO.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            //throw new ValidationException("Incorrect username or password", e);
            return new ResponseEntity(new ValidationException("Incorrect username or password", e), HttpStatus.UNAUTHORIZED);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequestDTO.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponseDTO(jwt,"Soumya Mondal","soumya","FundRaiser"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequestDTO registrationRequestDTO) throws Exception {
        userService.registerUser(registrationRequestDTO);
        return ResponseEntity.ok(new GenericResponseDTO<String>(HttpStatus.OK.toString(),"User registered","OK"));
    }

    @GetMapping("/checkUserId/{id}")
    public ResponseEntity<?> checkUserId(@PathVariable String id) throws Exception {
        if(!userService.checkUser(id)){
            return ResponseEntity.ok(new GenericResponseDTO<String>(HttpStatus.OK.toString(),"No user found","OK"));
        }
        else{
            throw new UserAlreadyExists("Username already exist please choose something else!");
        }
    }

}
