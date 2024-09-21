package com.example.tevlora.dto


import com.example.tevlora.model.OpeningHours
import java.time.LocalTime


class OpeningHoursDto (
    var id: Long = 0,
    var dayOfWeek: Int? = 0, // Use java.time.DayOfWeek enum values (1 = Monday, ... 7 = Sunday)
    var dayOfMonth: Int? = 0 ,
    var openTimeAm: LocalTime? = LocalTime.of(0,0,0),
    var closeTimeAm: LocalTime? = LocalTime.of(0,0,0),
    var openTimePm: LocalTime? = LocalTime.of(0,0,0),
    var closeTimePm: LocalTime? = LocalTime.of(0,0,0),
)

fun dtofromOpeningHours(openingHours: OpeningHours): OpeningHoursDto {
    return OpeningHoursDto( id = openingHours.id , dayOfWeek = openingHours.dayOfWeek, dayOfMonth= openingHours.dayOfMonth, openTimeAm = openingHours.openTimeAm,  closeTimeAm = openingHours.closeTimeAm, openTimePm = openingHours.openTimePm,  closeTimePm = openingHours.closeTimePm)
}

fun openingHoursFromOpeningHoursDto(openingHours : OpeningHoursDto): OpeningHours {
    return OpeningHours( id = openingHours.id, dayOfWeek = openingHours.dayOfWeek, dayOfMonth = openingHours.dayOfMonth , openTimeAm = openingHours.openTimeAm,  closeTimeAm = openingHours.closeTimeAm, openTimePm = openingHours.openTimePm,  closeTimePm = openingHours.closeTimePm)
}
