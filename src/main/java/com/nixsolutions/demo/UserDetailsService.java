package com.nixsolutions.demo;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements SocialUserDetailsService {
    
    private UserDetailsManager userDetailsManager;
    
    public UserDetailsService(UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }



    @Override
    public SocialUserDetails loadUserByUserId(String userId)
            throws UsernameNotFoundException {
        UserDetails userDetails = userDetailsManager.loadUserByUsername(userId);
        
        SocialUserDetails socialUserDetails = new SocialUser(
                userDetails.getUsername(), 
                userDetails.getPassword(),
                userDetails.getAuthorities());
        
        return socialUserDetails;
    }

}
