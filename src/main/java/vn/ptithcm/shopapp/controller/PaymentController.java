package vn.ptithcm.shopapp.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.model.request.PaymentRequestDTO;
import vn.ptithcm.shopapp.model.response.PaymentResponseDTO;
import vn.ptithcm.shopapp.service.IPaymentService;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Order")
public class PaymentController {

    private final IPaymentService paymentService;

    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payments")
    public ResponseEntity<PaymentResponseDTO> createPaymentUrl(
            @Valid @RequestBody PaymentRequestDTO dto,
            HttpServletRequest request) {
        return ResponseEntity.ok(this.paymentService.handlecreatePaymentUrl(dto, request));
    }

    @GetMapping("/payments/vnpay-payment-return")
    public ResponseEntity<Void> getPaymentResult(@RequestParam Map<String,String> params){
        return ResponseEntity.ok(this.paymentService.handlePaymentResult(params));
    }

}
