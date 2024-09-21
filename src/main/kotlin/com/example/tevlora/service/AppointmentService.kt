package com.example.tevlora.service

import com.example.tevlora.dto.AppointmentDto
import com.example.tevlora.model.Appointment

interface AppointmentService {
    fun findAllAppointments(): List<AppointmentDto>
    fun findAppointmentById(appointmentId: Long): AppointmentDto
    fun addAppointment(appointmentDto: AppointmentDto): Appointment
    fun updateAppointment(appointmentId: Long, appointmentDto: AppointmentDto): Appointment?
    fun deleteAppointment(appointmentDto: AppointmentDto)
    fun deleteAppointmentById(appointmentId: Long)
    fun findAppointmentByEmail(email: String): List<AppointmentDto>}