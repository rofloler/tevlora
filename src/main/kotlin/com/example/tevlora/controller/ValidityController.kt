package com.example.tevlora.controller

import com.example.tevlora.dto.AppointmentDto
import com.example.tevlora.dto.appointmentFromAppointmentDto
import com.example.tevlora.dto.dtofromAppointment
import com.example.tevlora.model.Appointment
import com.example.tevlora.model.OpeningHours
import com.example.tevlora.service.AppointmentService
import com.example.tevlora.service.OpeningHoursService
import com.example.tevlora.service.PersonService
import com.example.tevlora.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpServletRequest

@Controller
class ValidityController @Autowired constructor(val openingHoursService: OpeningHoursService, val appointmentService: AppointmentService, val productService: ProductService, val personService: PersonService) {

    @PostMapping("/addWithChecks")
    fun addAppointmentWithValidityChecks(@ModelAttribute appointment: Appointment,
                                         request: HttpServletRequest,
                                         redirectAttributes: RedirectAttributes
    ): String {

        // Fetch opening hours and appointments
        val openingHours = openingHoursService.findAllOpeningHours()
        val appointments = appointmentService.findAllAppointments()



        // Check if the appointment is within opening hours
        if (!isAppointmentWithinOpeningHours(appointment, openingHours)) {
            redirectAttributes.addFlashAttribute("error", "Appointment time is outside opening hours.")
            println("not within openning hours")
            return "redirect:${request.getHeader("Referer")}"
        }

        // Check for overlapping appointments
        if (doesAppointmentOverlap(appointment,  appointments.map{ appointmentFromAppointmentDto(it) })) {
            redirectAttributes.addFlashAttribute("error", "Appointment overlaps with an existing appointment.")
            println("overlap")
            return "redirect:${request.getHeader("Referer")}"
        }

        // If checks pass, add the appointment

        appointmentService.addAppointment(dtofromAppointment(appointment))
        redirectAttributes.addFlashAttribute("success", "Appointment added successfully.")
        println("stored")
        return "redirect:${request.getHeader("Referer")}"
    }



    @GetMapping("/api/appointments/calendar")
    fun getAppointmentsForCalendar(): ResponseEntity<List<Map<String, Any>>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val username = authentication.name // Directly get username (email) from authentication

        // Fetch the current user based on user name
        val person = personService.findPersonByEmail(username)
        val id = person.id

        val listForCalendarExport = appointmentService.findAllAppointments().map { appointment ->
            AppointmentDto(
                id = appointment.id,
                startDateTime = appointment.startDateTime,
                endDateTime = appointment.endDateTime,
                productId = appointment.productId,
                // Check if the appointment belongs to the current user
                isCurrentUser = appointment.personId == id
            )
        }.map{
            appointment -> mapOf(
            "title" to "Placeholder", // Assuming you have a personName field or similar
            "start" to appointment.startDateTime!!.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            "end" to appointment.endDateTime!!.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            "color" to if (appointment.isCurrentUser) "#499eb8" else "#49b89b"// Conditional coloring
            )
        }

        // Fetch all appointments and map them to DTOs, adding the isCurrentUser flag
          return ResponseEntity.ok(listForCalendarExport)

    }

}


fun isAppointmentWithinOpeningHours(appointment: Appointment, openingHours: List<OpeningHours>): Boolean {
    val dayOfWeek = appointment.startDateTime?.dayOfWeek?.value ?: return false
    val appointmentStart = appointment.startDateTime?.toLocalTime()
    val appointmentEnd = appointment.endDateTime?.toLocalTime()

    // Find opening hours for the day of the appointment
    val dayOpeningHours = openingHours.filter { it.dayOfWeek == dayOfWeek }

    // Check each opening period of the day
    return dayOpeningHours.any { oh ->
        // Morning check
        val isMorningValid = oh.openTimeAm != null && oh.closeTimeAm != null &&
                appointmentStart != null && appointmentEnd != null &&
                appointmentStart >= oh.openTimeAm && appointmentEnd <= oh.closeTimeAm

        // Afternoon check
        val isAfternoonValid = oh.openTimePm != null && oh.closeTimePm != null &&
                appointmentStart != null && appointmentEnd != null &&
                appointmentStart >= oh.openTimePm && appointmentEnd <= oh.closeTimePm

        isMorningValid || isAfternoonValid
    }
}

fun doesAppointmentOverlap(newAppointment: Appointment, existingAppointments: List<Appointment>): Boolean {
    val newStart = newAppointment.startDateTime
    val newEnd = newAppointment.endDateTime

    return existingAppointments.any { existing ->
        val existingStart = existing.startDateTime
        val existingEnd = existing.endDateTime

        // Check for overlap
        newStart != null && newEnd != null && existingStart != null && existingEnd != null &&
                (newStart < existingEnd && newEnd > existingStart)
    }
}
