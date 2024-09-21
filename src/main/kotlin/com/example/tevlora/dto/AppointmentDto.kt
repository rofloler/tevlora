package com.example.tevlora.dto

import com.example.tevlora.model.Appointment
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime


class AppointmentDto(
    val id : Long = 0,
    val personId: Long? = 0,
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    val startDateTime: LocalDateTime? = LocalDateTime.now(),
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    val endDateTime: LocalDateTime? = LocalDateTime.now(),
    val productId: Long? = 0,
     val isCurrentUser: Boolean = false

)

fun dtofromAppointment(appointment: Appointment): AppointmentDto {
    return AppointmentDto( personId = appointment.personId , startDateTime = appointment.startDateTime, endDateTime= appointment.endDateTime, productId = appointment.productId )
}

fun appointmentFromAppointmentDto(appointment : AppointmentDto): Appointment {
    return Appointment( id = appointment.id, personId = appointment.personId, startDateTime = appointment.startDateTime , endDateTime =  appointment.endDateTime , productId = appointment.productId)
}
