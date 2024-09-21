package com.example.tevlora.controller

import com.example.tevlora.dto.dtofromPerson
import com.example.tevlora.model.Person
import com.example.tevlora.service.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("api/persons")
class PersonController @Autowired constructor(
val personService: PersonService) {


    //GET
    @GetMapping()
    fun getAllPersons(model: Model, request: HttpServletRequest): String {
        model.addAttribute("persons",personService.findAllPersons())
        val referrer = request.getHeader("Referer")
        return "redirect:$referrer"
    }

    @GetMapping("/{id}")
    fun getPersonById(@PathVariable id: Long,request: HttpServletRequest, model: Model): String {
        model.addAttribute("person",personService.findPersonById(id))
        val referrer = request.getHeader("Referer")
        return "redirect:$referrer"
    }

    //REQUEST BODY POST
  /*  @PostMapping("")
    fun addPerson(@RequestBody person: Person, model: Model): String {
        model.addAttribute("person",personService.addPerson(dtofromPerson(person)))
        return "redirect:/"
    }
*/

    //ModelAttribute
    @PostMapping("")
    fun addPerson(@ModelAttribute person: Person, request: HttpServletRequest, redirectAttributes: RedirectAttributes): String {
        val savedPerson = personService.addPerson(dtofromPerson(person))
        redirectAttributes.addFlashAttribute("person", savedPerson)
        val referrer = request.getHeader("Referer")
        return "redirect:$referrer"
    }


    @PostMapping("/{id}")
    fun updatePerson(@PathVariable id: Long, request: HttpServletRequest, @ModelAttribute updatedPerson: Person,  redirectAttributes: RedirectAttributes): String {
        val existingPerson = personService.findPersonById(id)
        return if (existingPerson != null) {
            personService.deletePerson(existingPerson)
            personService.addPerson( dtofromPerson(updatedPerson))
            redirectAttributes.addFlashAttribute("person", updatedPerson)
            val referrer = request.getHeader("Referer")
            return "redirect:$referrer"
        } else {
            return "redirect:/error"
        }
    }

    @PostMapping("/delete/{id}")
    fun deletePerson(@PathVariable id: Long, request: HttpServletRequest, redirectAttributes: RedirectAttributes): String {
        val existingPerson = personService.findPersonById(id)
        if (existingPerson != null) {
            personService.deletePerson(existingPerson)
            redirectAttributes.addFlashAttribute("successMessage", "Person deleted successfully")
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Person not found")
        }
        val referrer = request.getHeader("Referer")
        return "redirect:$referrer"
    }



    @DeleteMapping("")
    fun deletePerson(@RequestBody person: Person, request: HttpServletRequest,  model: Model): String {
        personService.deletePerson(dtofromPerson(person))
        val referrer = request.getHeader("Referer")
        return "redirect:$referrer"
    }

}