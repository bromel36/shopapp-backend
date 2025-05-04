package vn.ptithcm.shopapp.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptithcm.shopapp.service.IEmailService;

@RestController
@Tag(name = "Home")
public class HomeController {

    private final IEmailService emailService;

    public HomeController(IEmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String home() {
        return "Hello World!!!!!";
    }


}
