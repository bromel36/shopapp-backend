package vn.ptithcm.shopapp.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.ptithcm.shopapp.model.request.PaymentRequestDTO;
import vn.ptithcm.shopapp.model.response.PaymentResponseDTO;
import vn.ptithcm.shopapp.service.IPaymentService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static vn.ptithcm.shopapp.util.VNPayUtil.hmacSHA512;

@Service
public class PaymentService implements IPaymentService {

    @Value("${bromel.vnpay.vnp-tmn-code}")
    private String vnp_TmnCode;

    @Value("${bromel.vnpay.vnp-hash-secret}")
    private String vnp_HashSecret;


    @Value("${bromel.vnpay.url}")
    private String vnp_PayUrl;

    @Value("${bromel.vnpay.return-url}")
    private String vnp_ReturnUrl;

    @Override
    public PaymentResponseDTO handlecreatePaymentUrl(PaymentRequestDTO dto, HttpServletRequest request) throws UnsupportedEncodingException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        String vnp_TxnRef = dto.getOrderId().toString();
        String vnp_IpAddr = request.getRemoteAddr();

        String orderInfo = "Paid for the order with id " + dto.getOrderId() + " . Total :" + dto.getAmount() + " VND";

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(dto.getAmount() * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Instant now = Instant.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                .withZone(ZoneId.of("Asia/Ho_Chi_Minh"));

        String vnp_CreateDate = formatter.format(now);

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                hashData.append(fieldName).append('=').append(fieldValue);
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII))
                        .append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (!fieldName.equals(fieldNames.get(fieldNames.size() - 1))) {
                    hashData.append('&');
                    query.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

        PaymentResponseDTO result = new PaymentResponseDTO();
        result.setPaymentUrl(queryUrl);


        return result;
    }
}
