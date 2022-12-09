package com.capstoneblog.capstoneblog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig
{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests((requests) -> requests
                .antMatchers("/", "/Post/Tags", "/Post/Tag/**", "/Post/{postID:[0-9]+}")
                .permitAll()
                .anyRequest()
                .authenticated())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll())
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService()
    {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("Admin").password("password")
                .roles("ADMIN", "WRITER")
                .build();

        UserDetails writer = User.withDefaultPasswordEncoder()
                .username("Writer").password("password")
                .roles("WRITER")
                .build();

        return new InMemoryUserDetailsManager(admin, writer);
    }

    @Bean
    public SpringSecurityDialect SpringSecurityDialect()
    {
        return new SpringSecurityDialect();
    }
}
