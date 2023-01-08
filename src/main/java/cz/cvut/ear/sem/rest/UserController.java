package cz.cvut.ear.sem.rest;

import cz.cvut.ear.sem.security.JwtService;
import cz.cvut.ear.sem.security.model.AuthenticationRequest;
import cz.cvut.ear.sem.security.model.AuthenticationResponse;
import cz.cvut.ear.sem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/login")
    public AuthenticationResponse createToken(@RequestBody AuthenticationRequest authenticationRequest) {
        UserDetails userDetails = userService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        String token = jwtService.createToken(userDetails);
        return new AuthenticationResponse(token);
    }
}
