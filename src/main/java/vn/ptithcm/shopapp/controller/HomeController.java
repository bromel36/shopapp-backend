package vn.ptithcm.shopapp.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptithcm.shopapp.service.IEmailService;

@RestController
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
