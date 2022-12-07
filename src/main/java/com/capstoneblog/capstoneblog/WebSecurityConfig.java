package com.capstoneblog.capstoneblog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig
{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        // http.authorizeHttpRequests((requests) ->
        // requests.anyRequest().authenticated()).httpBasic(withDefaults())
        // .authenticationManager(new CustomAuthenticationManager());

        http.authorizeHttpRequests((requests) -> requests
                .antMatchers("/", "/Post/View", "/unsecure")
                .permitAll()
                .anyRequest()
                .authenticated())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll())
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    // @Bean
    // WebSecurityCustomizer webSecurityCustomizer()
    // {
    // return (web) -> web.ignoring().antMatchers("/", "/Post/View/**");
    // }

    // @Bean
    //// @Service("userDetailsService")
    // public UserDetailsService userDetailsService()
    // {
    // UserDetails admin = User.withDefaultPasswordEncoder()
    // .username("admin").password("password")
    // .roles("ADMIN", "WRITER")
    // .build();
    //
    // UserDetails writer = User.withDefaultPasswordEncoder()
    // .username("writer").password("password")
    // .roles("WRITER")
    // .build();
    //
    // return new InMemoryUserDetailsManager(admin, writer);
    // }

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
}
