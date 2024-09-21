package com.example.tevlora.config


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Enable method-level security
class SecurityConfig(val userDetailsService: UserDetailsService) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            // Permit all GET requests
            .antMatchers("/index").permitAll()
            .antMatchers("/register").permitAll()
            .antMatchers("/https://").permitAll()
            .antMatchers("https://code.jquery.com/jquery-3.5.1.slim.min.js").permitAll()
            .antMatchers("https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.jsr").permitAll()
            .antMatchers("https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js").permitAll()
            .antMatchers("/api/appointments/calendar", "/api/openingHours/calendar").permitAll()
            .antMatchers("/css/**/**","/scss/**", "/js/**", "/images/**", "/webjars/**", "/fonts/**").permitAll()
            .antMatchers(HttpMethod.GET, "api/products/**", "api/openinghours/**", "api/appointments/**", "api/persons/**").permitAll()
            .antMatchers(HttpMethod.GET,"api/persons/**").hasAnyRole("USER", "ADMIN")
            .antMatchers(HttpMethod.POST,"api/appointments/**").hasAnyRole("USER", "ADMIN")
            .antMatchers(HttpMethod.POST,"addWithChecks/**").hasAnyRole("USER", "ADMIN")
            // Require authentication for any other requests
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login") // Customize the login page
            .defaultSuccessUrl("/index", true) // Redirect after successful login
            .permitAll()
            .and()
            .logout()
            .permitAll(); // Allow logout for all users
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}