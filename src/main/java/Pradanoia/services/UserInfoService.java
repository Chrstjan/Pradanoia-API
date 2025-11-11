package Pradanoia.services;

import Pradanoia.entities.UserInfo;
import Pradanoia.repositories.UserInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {
    private final UserInfoRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserInfoService(UserInfoRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    //Loads user details by username (email)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = repository.findByEmail(username);

        if (userInfo.isEmpty())
            throw new UsernameNotFoundException("User with email: " + username + " was not found");

        //Converts into user entity model
        UserInfo user = userInfo.get();
        return new User(user.getEmail(), user.getPassword(),
                user.getRoles());
    }

    // #region user registering methods
    public String addUser(UserInfo userInfo) {
        //Encrypts password before saving
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User added successfully!";
    }
    // #endregion user registering methods
}
