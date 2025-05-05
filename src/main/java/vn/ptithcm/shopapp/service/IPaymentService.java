package vn.ptithcm.shopapp.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import vn.ptithcm.shopapp.model.request.PaymentRequestDTO;
import vn.ptithcm.shopapp.model.response.PaymentResponseDTO;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface IPaymentService {
    PaymentResponseDTO handlecreatePaymentUrl(@Valid PaymentRequestDTO dto, HttpServletRequest request);

    Void handlePaymentResult(Map<String, String> params);
}
