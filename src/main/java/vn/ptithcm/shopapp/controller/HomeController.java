package vn.ptithcm.shopapp.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Home")
public class HomeController {


     @GetMapping("/")
     public String home() {
        return "Hello World!!!!!";
     }
}
