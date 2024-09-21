package com.example.tevlora.service

import com.example.tevlora.dto.*
import com.example.tevlora.model.OpeningHours
import com.example.tevlora.repository.OpeningHoursRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OpeningHoursServiceImpl @Autowired constructor(
    val openingHoursRepository: OpeningHoursRepository
) : OpeningHoursService {

    override fun addOpeningHours(openingHours: OpeningHoursDto): OpeningHours {
        return openingHoursRepository.save(openingHoursFromOpeningHoursDto(openingHours))
    }

    override fun findAllOpeningHours(): List<OpeningHours> {
        return openingHoursRepository.findAll().sortedBy { it.dayOfWeek }.sortedBy { it.dayOfMonth }
    }

    override fun findOpeningHoursById(id: Long): OpeningHours? {
        return openingHoursRepository.findById(id).get()
    }

    override fun findOpeningHoursByDayOfWeek(weekDay: Long): List<OpeningHours> {
        return openingHoursRepository.findAll().filter { weekDay.equals(it.dayOfWeek) }
    }

    override fun findOpeningHoursByDayOfMonth(monthDay: Long): OpeningHours? {
        return openingHoursRepository.findAll().filter { monthDay.equals(it.dayOfMonth) }.first()
    }

    override fun updateOpeningHoursById(id: Long, openingHours: OpeningHoursDto): OpeningHours? {
        return openingHoursRepository.save(openingHoursFromOpeningHoursDto(openingHours))
    }

    override fun deleteOpeningHours(openingHours: OpeningHoursDto) {
        return openingHoursRepository.delete(openingHoursFromOpeningHoursDto(openingHours))
    }

    override fun deleteOpeningHours(id: Long) {
        return openingHoursRepository.deleteById(id)
    }

}