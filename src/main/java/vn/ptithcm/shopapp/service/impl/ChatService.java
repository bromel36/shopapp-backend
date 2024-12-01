package vn.ptithcm.shopapp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.ptithcm.shopapp.converter.ProductConverter;
import vn.ptithcm.shopapp.model.entity.Product;
import vn.ptithcm.shopapp.model.response.ChatResponse;
import vn.ptithcm.shopapp.model.response.ProductResponseDTO;
import vn.ptithcm.shopapp.model.response.PythonApiResponse;
import vn.ptithcm.shopapp.repository.ProductRepository;
import vn.ptithcm.shopapp.service.IChatService;
import vn.ptithcm.shopapp.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class ChatService implements IChatService {

    private final RestTemplate restTemplate;

    private final ProductRepository productRepository; // Repository để query database
    private final ProductConverter productConverter;

    public ChatService(RestTemplateBuilder restTemplateBuilder, ProductRepository productRepository, ProductConverter productConverter) {
        this.restTemplate = restTemplateBuilder.build();
        this.productRepository = productRepository;
        this.productConverter = productConverter;
    }


    public ChatResponse handleMessage(String message) {
        // Gọi API Python
        String pythonApiUrl = "http://localhost:5000/get?msg=" + message;
        ResponseEntity<PythonApiResponse> pythonResponse = restTemplate.exchange(pythonApiUrl, HttpMethod.GET, null, PythonApiResponse.class);

        PythonApiResponse fields = pythonResponse.getBody();

        ChatResponse response = new ChatResponse();

        response.setMessage(fields.getMessage());

        List<String> brands = fields.getBrand();
        List<Double> prices = fields.getPrice();
        String tag = fields.getTag();


        if (!isValidList(brands) && !isValidList(prices) && !StringUtil.isValid(tag)) {
            return response;
        }

        List<Product> products = productRepository.find(tag,brands, prices);
//        List<Product> products = productRepository.findByTagContainingIgnoreCaseAndBrandNameInAndPriceInOrderBySoldDesc(tag,brands, prices, pageable);
        if (!isValidList(products)){
            response.setMessage("Không có sản phẩm phù hợp với nhu cầu của bạn!");
        }
        else{
            List<ProductResponseDTO> responseDTOS = products.stream().map(it -> productConverter.convertToProductResponseDTO(it)).toList();

            response.setProducts(responseDTOS);
        }
        return response;
    }

    private boolean isValidList(List list){
        return list != null && !list.isEmpty();
    }
}

