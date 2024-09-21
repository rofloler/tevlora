package com.example.tevlora.controller

import com.example.tevlora.dto.dtofromAppointment
import com.example.tevlora.model.Appointment
import com.example.tevlora.service.AppointmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("api/appointments")
class AppointmentController @Autowired constructor(
    val appointmentService: AppointmentService) {


    @GetMapping()
    fun getAllAppointments(model: Model, request: HttpServletRequest, redirectAttributes: RedirectAttributes): String {
        redirectAttributes.addAttribute("appointments",appointmentService.findAllAppointments())
        val referrer = request.getHeader("Referer")
        return "redirect:$referrer"
    }


    @GetMapping("/{id}")
    fun getAppointmentById(@PathVariable id: Long, request: HttpServletRequest, model: Model): String {
        model.addAttribute("appointment",appointmentService.findAppointmentById(id))
        val referrer = request.getHeader("Referer")
        return "redirect:$referrer"
    }

    @PostMapping("")
    fun addAppointment(@ModelAttribute appointment: Appointment, request: HttpServletRequest, redirectAttributes: RedirectAttributes): String {
        val savedAppointment = appointmentService.addAppointment(dtofromAppointment(appointment))
        redirectAttributes.addFlashAttribute("appointment",savedAppointment)
        val referrer = request.getHeader("Referer")
        return "redirect:$referrer"
    }


    @PostMapping("/{id}")
    fun updateAppointment(@PathVariable id: Long, @ModelAttribute updatedAppointment: Appointment, request: HttpServletRequest, redirectAttributes: RedirectAttributes): String {
        val existingAppointment = appointmentService.findAppointmentById(id)
        return if (existingAppointment != null) {
            appointmentService.deleteAppointment(existingAppointment)
            appointmentService.addAppointment(dtofromAppointment(updatedAppointment))
            redirectAttributes.addFlashAttribute("appointment", updatedAppointment)
            val referrer = request.getHeader("Referer")
            return "redirect:$referrer"
        } else {
            return "redirect:/error"
        }
    }

    @PostMapping("/delete/{id}")
    fun deleteAppointment(@PathVariable id: Long, @ModelAttribute updatedAppointment: Appointment,  model: Model, request: HttpServletRequest): String {
        appointmentService.deleteAppointmentById(id)
        val referrer = request.getHeader("Referer")
        return "redirect:$referrer"    }

    @DeleteMapping("")
    fun deleteAppointment(@RequestBody appointment: Appointment, request: HttpServletRequest, model: Model): String {
        appointmentService.deleteAppointment(dtofromAppointment(appointment))
        val referrer = request.getHeader("Referer")
        return "redirect:$referrer"    }



}