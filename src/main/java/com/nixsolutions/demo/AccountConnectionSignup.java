package com.nixsolutions.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountConnectionSignup implements ConnectionSignUp {

    private UserDetailsManager userDetailsManager;
    
    public AccountConnectionSignup(UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    @Override
    public String execute(Connection<?> connection) {
        log.info("Creating new user");
        UserProfile profile = connection.fetchUserProfile();
        String username = profile.getEmail();
        String password = randomPassword();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        UserDetails user = new User(username, password, Arrays.asList(authority));
        log.debug("User: {}", user);
        userDetailsManager.createUser(user);
        return user.getUsername();
    }

    private String randomPassword() {
        return KeyGenerators.string().generateKey();
    }
}
