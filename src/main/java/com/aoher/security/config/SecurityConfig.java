package com.aoher.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.aoher.security.util.SecurityConstants.ADMIN_ROLE;
import static com.aoher.security.util.SecurityConstants.USER_ROLE;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Authentication : User -> Roles
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1").password("secret1").roles(USER_ROLE)
                .and()
                .withUser("admin1").password("secret1").roles(USER_ROLE, ADMIN_ROLE);
    }

    // Authorization : Role -> Access
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests()
                .antMatchers("/students/**").hasRole(USER_ROLE)
                .antMatchers("/users/**").hasRole(USER_ROLE)
                .antMatchers("/**").hasRole(ADMIN_ROLE)
                .and()
                .csrf().disable()
                .headers().frameOptions().disable();
    }
}
