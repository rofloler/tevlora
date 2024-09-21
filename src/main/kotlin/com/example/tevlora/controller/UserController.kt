package com.example.tevlora.controller

import com.example.tevlora.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class UserController@Autowired constructor(val personRepository: PersonRepository){

@GetMapping("/user/profile")
fun userProfile(redirectAttributes: RedirectAttributes): String {
    val authentication: Authentication = SecurityContextHolder.getContext().authentication
    val email = authentication.name // Get the username (email in this case)

    // Assuming you have a service to find user by email
    val person = personRepository.findByEmail(email)

    redirectAttributes.addAttribute("person", person)
    return "userProfile" // Return the view name
}
}