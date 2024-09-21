package com.example.tevlora.repository

import com.example.tevlora.model.OpeningHours
import org.springframework.data.jpa.repository.JpaRepository

interface OpeningHoursRepository: JpaRepository<OpeningHours,Long> {
}