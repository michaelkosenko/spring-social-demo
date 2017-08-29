package com.nixsolutions.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@EnableSocial
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //@formatter:off
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/index", "/login", "/auth/**").permitAll()
                .antMatchers("/user/**").hasAnyAuthority("USER")
            .and()
                .formLogin().loginPage("/login").failureUrl("/login?loginError").successForwardUrl("/user/index")
            .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/index")
            .and()
                .apply(new SpringSocialConfigurer());
    }
    //@formatter:on
    
    @Bean
    public UserDetailsManager userDetailsManager() {
        return new InMemoryUserDetailsManager();
    }

    @Bean
    @Autowired
    public UsersConnectionRepository usersConnectionRepository(
            ConnectionFactoryLocator connectionFactoryLocator,
            AccountConnectionSignup accountConnectionSignup) {
        
        InMemoryUsersConnectionRepository usersConnectionRepository = new InMemoryUsersConnectionRepository(
                connectionFactoryLocator);
        usersConnectionRepository.setConnectionSignUp(accountConnectionSignup);
        return usersConnectionRepository;
    }
}
