package com.example.tevlora.repository

import com.example.tevlora.model.Appointment
import org.springframework.data.jpa.repository.JpaRepository


interface AppointmentRepository : JpaRepository<Appointment, Long> {
}