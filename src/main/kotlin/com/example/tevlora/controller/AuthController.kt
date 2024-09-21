package com.example.tevlora.controller

import com.example.tevlora.dto.PersonDto
import com.example.tevlora.dto.dtofromPerson
import com.example.tevlora.model.Person
import com.example.tevlora.service.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest

@Controller
class AuthController @Autowired constructor(
    private val personService: PersonService, // Assume this is your service class for handling Person entities
    private val passwordEncoder: PasswordEncoder
) {




    @GetMapping("/register")
    fun showRegistrationForm(model: Model): ModelAndView {
        model.addAttribute("newPerson", PersonDto())
        return ModelAndView("register")
    }

    @PostMapping("/register")
    fun registerUserAccount(@ModelAttribute("person") personDto: PersonDto, httpServletRequest: HttpServletRequest): ModelAndView {

        val newPerson = Person(
            // Assuming Person class has id, name, email, and password fields. Adapt accordingly.
            name = personDto.name.toString(),
            lastName = personDto.lastName.toString(),
            age = personDto.age,
            email = personDto.email,
            password = passwordEncoder.encode(personDto.password)
        )
        val referer = httpServletRequest.getHeader("referer")
        personService.addPerson(dtofromPerson(newPerson))
        return ModelAndView("redirect:$referer")
    }
}




