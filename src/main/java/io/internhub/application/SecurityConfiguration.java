package io.internhub.application;

import io.internhub.application.services.UserDetailsLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usersLoader) // How to find users by their username
                .passwordEncoder(passwordEncoder()) // How to encode and verify passwords
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
                .authorizeRequests()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/jobs/**", "interns/profile/**").access("((hasRole('INTERN') or hasRole('EMPLOYER')) and hasRole('ISAPPROVED')) or hasRole('ADMIN')")
                .antMatchers("/interns/applied-index", "/interns/index").access("hasRole('INTERN') and hasRole('ISAPPROVED')")
                .antMatchers("/employers/add-job", "/employers/index").access("hasRole('EMPLOYER') and hasRole('ISAPPROVED')")
                .and()
//                .authorizeRequests()
//                .antMatchers("/admin/*")
//                .hasAnyRole("INTERN")
//                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard") // user's home page, it can be any URL
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403")// Anyone can go to the login page
        ;
    }
}
