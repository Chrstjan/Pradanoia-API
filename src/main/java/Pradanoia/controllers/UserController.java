package Pradanoia.controllers;

import Pradanoia.entities.UserInfo;
import Pradanoia.services.JwtService;
import Pradanoia.services.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserInfoService userInfoService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome, this endpoint is not secure";
    }

    @GetMapping("/user/")
    public String user() {
        return "Welcome User";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return userInfoService.addUser(userInfo);
    }

    @PostMapping("/generateToken")
    public String generateToken(@RequestBody UserInfo userInfo) {
        try {
            authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                           userInfo.getEmail(),
                           userInfo.getPassword()
                   )
            );

            return jwtService.generateToken(userInfo.getEmail());
        }
        catch (AuthenticationException e) {
            return "Invalid username/password";
        }
    }

}
