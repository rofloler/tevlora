package com.example.tevlora.service

import com.example.tevlora.dto.AppointmentDto
import com.example.tevlora.dto.appointmentFromAppointmentDto
import com.example.tevlora.model.Appointment
import com.example.tevlora.repository.AppointmentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AppointmentServiceImpl @Autowired constructor(private val appointmentRepository: AppointmentRepository, val personService: PersonService): AppointmentService {
    override fun findAllAppointments(): List<AppointmentDto> {
        // Implementation to retrieve all appointments from the database
        // and convert them to DTOs
        return appointmentRepository.findAll().map{ AppointmentDto(it.id, it.personId, it.startDateTime, it.endDateTime, it.productId) }
    }

    override fun findAppointmentById(appointmentId: Long): AppointmentDto {
        // Implementation to retrieve an appointment by ID from the database
        // and convert it to a DTO
        return appointmentRepository.findById(appointmentId).map{AppointmentDto(it.id, it.personId, it.startDateTime, it.endDateTime, it.productId)}.get()
    }

    override fun findAppointmentByEmail(email: String): List<AppointmentDto> {
        // Implementation to retrieve an appointment by ID from the database
        // and convert it to a DTO
        val person = personService.findAllPersons().filter { it.email == email }.first()
        return  appointmentRepository.findAll().filter { it.personId == person.id}
            .map{AppointmentDto(it.id, it.personId, it.startDateTime, it.endDateTime, it.productId)}
    }

    override fun addAppointment(appointmentDto: AppointmentDto): Appointment {
        // Implementation to create a new appointment in the database
        // and convert it to a DTO
        return appointmentRepository.save(appointmentFromAppointmentDto(appointmentDto))
    }

    override fun updateAppointment(appointmentId: Long, appointmentDto: AppointmentDto): Appointment? {
        return appointmentRepository.save(appointmentFromAppointmentDto(appointmentDto))

    }

    override fun deleteAppointment(appointmentDto: AppointmentDto) {
        // Implementation to delete an appointment from the database
        return appointmentRepository.delete(appointmentFromAppointmentDto(appointmentDto))
    }

    override fun deleteAppointmentById(appointmentId: Long) {
        // Implementation to delete an appointment from the database
        return appointmentRepository.deleteById(appointmentId)
    }
}