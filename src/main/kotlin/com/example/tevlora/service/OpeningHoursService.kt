package com.example.tevlora.service

import com.example.tevlora.dto.OpeningHoursDto
import com.example.tevlora.model.OpeningHours

interface OpeningHoursService {

    fun addOpeningHours(openingHoursDto: OpeningHoursDto) : OpeningHours
    fun findAllOpeningHours() : List<OpeningHours>
    fun findOpeningHoursById(id : Long) : OpeningHours?
    fun findOpeningHoursByDayOfWeek(weekDay : Long) : List<OpeningHours>
    fun findOpeningHoursByDayOfMonth(monthDay : Long) : OpeningHours?
    fun updateOpeningHoursById(id : Long, openingHours: OpeningHoursDto) : OpeningHours?
    fun deleteOpeningHours(openingHours: OpeningHoursDto)
    fun deleteOpeningHours(id: Long)

}