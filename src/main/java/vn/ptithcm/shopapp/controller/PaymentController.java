package vn.ptithcm.shopapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Payment")
public class PaymentController {

    private final IPaymentService paymentService;

    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payments")
    @Operation(summary = "Create payment URL", description = "Generate a payment URL for the given payment request.")
    public ResponseEntity<PaymentResponseDTO> createPaymentUrl(
            @Valid @RequestBody PaymentRequestDTO dto,
            HttpServletRequest request) {
        return ResponseEntity.ok(this.paymentService.handlecreatePaymentUrl(dto, request));
    }

    @GetMapping("/payments/vnpay-payment-return")
    @Operation(summary = "Handle payment result", description = "Process the payment result returned by VNPay.")
    public ResponseEntity<Void> getPaymentResult(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(this.paymentService.handlePaymentResult(params));
    }
}
