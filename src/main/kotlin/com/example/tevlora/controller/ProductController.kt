package com.example.tevlora.controller

import com.example.tevlora.dto.dtofromProduct
import com.example.tevlora.model.Product
import com.example.tevlora.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpServletRequest


@Controller
@RequestMapping("api/products")
class ProductController @Autowired constructor(
    val productService: ProductService
) {

    @GetMapping()
    fun getAllProducts(model: Model, request: HttpServletRequest): String {
        model.addAttribute("products",productService.findAllProducts())
        val referrer = request.getHeader("Referer")
        return "redirect:$referrer"
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long, model: Model,request: HttpServletRequest, redirectAttributes: RedirectAttributes ): String {
        redirectAttributes.addAttribute("product",productService.findProductById(id))
        val referrer = request.getHeader("Referer")
        return "redirect:$referrer"
    }

    @PostMapping
    fun addProduct(@ModelAttribute product: Product,  model: Model, request: HttpServletRequest): String {
        model.addAttribute("product",productService.addProduct(dtofromProduct(product)))
        val referrer = request.getHeader("Referer")
        return "redirect:$referrer"
    }

    @PostMapping("/{id}")
    fun updateProduct(@PathVariable id: Long, @ModelAttribute updatedProduct: Product, model: Model, request: HttpServletRequest): String {
        val existingProduct = productService.findProductById(id)
        return if (existingProduct != null) {
            productService.deleteProduct(dtofromProduct(existingProduct))
            productService.addProduct(dtofromProduct(updatedProduct))
            val referrer = request.getHeader("Referer")
            return "redirect:$referrer"
        } else {
            return "redirect:/error"
        }
    }

    @PostMapping("/delete/{id}")
    fun deleteProduct(@PathVariable id: Long, request: HttpServletRequest): String {
        productService.deleteProductById(id)
        val referrer = request.getHeader("Referer")
        return "redirect:$referrer"
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@ModelAttribute product: Product, model: Model, request: HttpServletRequest): String {
        productService.deleteProduct(dtofromProduct(product))
        val referrer = request.getHeader("Referer")
        return "redirect:$referrer"
    }

}