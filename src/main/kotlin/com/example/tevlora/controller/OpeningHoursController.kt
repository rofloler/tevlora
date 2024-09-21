package com.example.tevlora.controller

import com.example.tevlora.dto.dtofromOpeningHours
import com.example.tevlora.model.OpeningHours
import com.example.tevlora.service.OpeningHoursService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("api/openingHours")
class OpeningHoursController @Autowired constructor(val openingHoursService: OpeningHoursService) {
    
    @GetMapping
    fun getAllOpeningHours(model: Model, request: HttpServletRequest, redirectAttributes: RedirectAttributes): String{
        redirectAttributes.addAttribute("openingHours", openingHoursService.findAllOpeningHours())
        val referer = request.getHeader("Referer")
        return "redirect:$referer"
    }


    @GetMapping("/{id}")
    fun getOpeningHoursById(@PathVariable id: Long, request: HttpServletRequest, model: Model): String {
        model.addAttribute("appointment",openingHoursService.findOpeningHoursById(id))
        val referrer = request.getHeader("Referer")
        return "redirect:$referrer"
    }

    @PostMapping("")
    fun addOpeningHours(@ModelAttribute openingHours: OpeningHours, request: HttpServletRequest, model: Model): String {
        model.addAttribute("openingHours",openingHoursService.addOpeningHours(dtofromOpeningHours(openingHours)))
        val referrer = request.getHeader("Referer")
        return "redirect:$referrer"
    }

    @PostMapping("/{id}")
    fun updateOpeningHours(@PathVariable id: Long, @ModelAttribute updatedOpeningHours: OpeningHours, model: Model, request: HttpServletRequest): String {
        val existingOpeningHours = openingHoursService.findOpeningHoursById(id)
        return if (existingOpeningHours != null) {
            openingHoursService.deleteOpeningHours(dtofromOpeningHours(existingOpeningHours))
            openingHoursService.addOpeningHours(dtofromOpeningHours(updatedOpeningHours))
            val referrer = request.getHeader("Referer")
            return "redirect:$referrer"
        } else {
            return "redirect:/error"
        }
    }

    @PostMapping("/delete/{id}")
    fun deleteOpeningHours(@PathVariable id: Long, @ModelAttribute updatedOpeningHours: OpeningHours, model: Model, request: HttpServletRequest): String {
        openingHoursService.deleteOpeningHours(id)
        val referrer = request.getHeader("Referer")
        return "redirect:$referrer" }

    @DeleteMapping("")
    fun deleteOpeningHours(@RequestBody openingHours: OpeningHours, request: HttpServletRequest, model: Model): String {
        openingHoursService.deleteOpeningHours(dtofromOpeningHours(openingHours))
        val referrer = request.getHeader("Referer")
        return "redirect:$referrer"    }

    @GetMapping("/calendar")
    fun getAllOpeningHours(): ResponseEntity<List<OpeningHours>> {
        val oh = openingHoursService.findAllOpeningHours()
        return  ResponseEntity.ok(oh)
    }

}