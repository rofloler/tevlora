package com.example.tevlora.controller

import com.example.tevlora.dto.AppointmentDto
import com.example.tevlora.dto.PersonDto
import com.example.tevlora.dto.ProductDto
import com.example.tevlora.service.AppointmentService
import com.example.tevlora.service.OpeningHoursService
import com.example.tevlora.service.PersonService
import com.example.tevlora.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping


@Controller
class OverviewController  @Autowired constructor(val openingHoursService: OpeningHoursService, val personService: PersonService, val appointmentService: AppointmentService, val productService: ProductService) {
    @GetMapping("/index")
    fun showForm(model: Model): String {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        if (authentication.getPrincipal() is UserDetails) {
            val userDetails = authentication.getPrincipal()
            val email = authentication.name
            model.addAttribute("currentUser", personService.findPersonByEmail(email))
            model.addAttribute("currentUserAppointments", appointmentService.findAppointmentByEmail(email) )
        }
        model.addAttribute("persons", personService.findAllPersons())
        model.addAttribute("appointments", appointmentService.findAllAppointments())
        model.addAttribute("products", productService.findAllProducts())
        model.addAttribute("openingHours", openingHoursService.findAllOpeningHours())
        // Add empty objects to support form for creating new entities
        model.addAttribute("newPerson", PersonDto(password = "password", email = "email@email.ch"))
        model.addAttribute("newAppointment", AppointmentDto())
        model.addAttribute("newAppointment", AppointmentDto())
        model.addAttribute("newProduct", ProductDto())


        return "index" // Name of the Thymeleaf template
    }
}