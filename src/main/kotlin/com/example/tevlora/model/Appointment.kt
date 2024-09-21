package com.example.tevlora.model

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Appointment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val personId: Long? = 0,
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    val startDateTime: LocalDateTime? = LocalDateTime.now(),
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    val endDateTime: LocalDateTime? = LocalDateTime.now(),
    val productId: Long? = null
)