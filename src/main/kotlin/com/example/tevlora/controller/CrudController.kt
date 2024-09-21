package com.example.tevlora.controller
import com.example.tevlora.dto.AppointmentDto
import com.example.tevlora.dto.OpeningHoursDto
import com.example.tevlora.dto.PersonDto
import com.example.tevlora.dto.ProductDto

import com.example.tevlora.service.AppointmentService
import com.example.tevlora.service.OpeningHoursService
import com.example.tevlora.service.PersonService
import com.example.tevlora.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WebController @Autowired constructor(val openingHoursService: OpeningHoursService, val personService: PersonService, val appointmentService: AppointmentService, val productService: ProductService) {

    @GetMapping("/admin-hub")
    fun showForm(model: Model): String {
        model.addAttribute("persons", personService.findAllPersons())
        model.addAttribute("appointments", appointmentService.findAllAppointments())
        model.addAttribute("products", productService.findAllProducts())
        model.addAttribute("openingHours", openingHoursService.findAllOpeningHours())
        // Add empty objects to support form for creating new entities
        model.addAttribute("newPerson", PersonDto())
        model.addAttribute("newAppointment", AppointmentDto())
        model.addAttribute("newProduct", ProductDto())
        model.addAttribute("newOpeningHour", OpeningHoursDto())

        return "admin-hub" // Name of the Thymeleaf template


    }
}