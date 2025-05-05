package vn.ptithcm.shopapp.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import vn.ptithcm.shopapp.model.request.PaymentRequestDTO;
import vn.ptithcm.shopapp.model.response.PaymentResponseDTO;

import java.io.UnsupportedEncodingException;

public interface IPaymentService {
    PaymentResponseDTO handlecreatePaymentUrl(@Valid PaymentRequestDTO dto, HttpServletRequest request) throws UnsupportedEncodingException;
}
