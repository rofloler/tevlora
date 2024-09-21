package com.example.tevlora.service

import com.example.tevlora.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class PersonUserDetailsService @Autowired constructor(val personRepository: PersonRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val person = personRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User not found with email: $username")

        // Hardcode roles based on some condition, e.g., user's email
        val authorities = when (username) {
                "admin@example.com" -> listOf(SimpleGrantedAuthority("ROLE_ADMIN"))
            else -> listOf(SimpleGrantedAuthority("ROLE_USER"))
        }

        return User(person.email, person.password, authorities)
    }
}