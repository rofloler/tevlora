package com.example.tevlora.model

import java.time.LocalTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class OpeningHours(
    @Id @GeneratedValue var id: Long = 0,
    var dayOfWeek: Int? = 0, // Use java.time.DayOfWeek enum values (1 = Monday, ... 7 = Sunday)
    var dayOfMonth: Int? = 0,
    var openTimeAm: LocalTime? = LocalTime.of(0,0,0),
    var closeTimeAm: LocalTime? = LocalTime.of(0,0,0),
    var openTimePm: LocalTime? = LocalTime.of(0,0,0),
    var closeTimePm: LocalTime? = LocalTime.of(0,0,0),
)