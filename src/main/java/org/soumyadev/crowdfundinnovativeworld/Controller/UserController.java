package org.soumyadev.crowdfundinnovativeworld.Controller;

import org.soumyadev.crowdfundinnovativeworld.DTO.*;
import org.soumyadev.crowdfundinnovativeworld.ExceptionHandling.UserAlreadyExists;
import org.soumyadev.crowdfundinnovativeworld.ExceptionHandling.UserValidationException;
import org.soumyadev.crowdfundinnovativeworld.Model.CustomCredDetails;
import org.soumyadev.crowdfundinnovativeworld.Service.UserAuthenticationService;
import org.soumyadev.crowdfundinnovativeworld.Service.UserService;
import org.soumyadev.crowdfundinnovativeworld.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserAuthenticationService userDetailsService;

    @Autowired
    private UserService userService;

    @GetMapping({ "/hello" })
    public String firstPage() {
        return "Hello World";
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) throws Exception {
        final CustomCredDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequestDTO.getUsername()
                        , authenticationRequestDTO.getPassword());
        if(Objects.nonNull(userDetails)){
            final String jwt = jwtTokenUtil.generateToken(userDetails);
            userDetails.setJwt(jwt);
            return ResponseEntity.ok(userDetails);
        }
        throw new UserValidationException("User credential is not valid");
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

    @GetMapping("/fetchUserProfile/{id}")
    public ResponseEntity<?> getProfile(@PathVariable String id) throws Exception {
        UserProfileDTO userProfileDto = userService.getProfile(id);
        if(Objects.nonNull(userProfileDto)){
            return ResponseEntity.ok(userProfileDto);
        }
        else{
            throw new UsernameNotFoundException("User not found !!");
        }
    }

    @PostMapping("/updateUserProfile/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable String id,@RequestBody UserProfileDTO userProfileDTOInput) throws Exception {
        UserProfileDTO userProfileDto = userService.getProfile(id);
        if(Objects.nonNull(userProfileDto)){
            userService.updateProfile(id,userProfileDTOInput);
            return ResponseEntity.ok(userProfileDTOInput);
        }
        else{
            throw new UsernameNotFoundException("User not found !!");
        }
    }
}
