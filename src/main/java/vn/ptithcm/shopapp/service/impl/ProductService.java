package vn.ptithcm.shopapp.service.impl;

import com.turkraft.springfilter.builder.FilterBuilder;
import com.turkraft.springfilter.converter.FilterSpecification;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import com.turkraft.springfilter.parser.FilterParser;
import com.turkraft.springfilter.parser.node.FilterNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptithcm.shopapp.converter.ProductConverter;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.*;
import vn.ptithcm.shopapp.model.request.ProductRequestDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.model.response.ProductResponseDTO;
import vn.ptithcm.shopapp.repository.ProductRepository;
import vn.ptithcm.shopapp.service.IBrandService;
import vn.ptithcm.shopapp.service.ICategoryService;
import vn.ptithcm.shopapp.service.IProductService;
import vn.ptithcm.shopapp.service.IUserService;
import vn.ptithcm.shopapp.util.PaginationUtil;
import vn.ptithcm.shopapp.util.SecurityUtil;
import vn.ptithcm.shopapp.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductService implements IProductService {


    private final FilterParser filterParser;
    private final FilterSpecificationConverter filterSpecificationConverter;
    private final FilterBuilder filterBuilder;


    private final ProductRepository productRepository;
    private final ICategoryService categoryService;
    private final IUserService userService;
    private final IBrandService brandService;
    private final ProductConverter productConverter;

    public ProductService(ProductRepository productRepository, ICategoryService categoryService, FilterParser filterParser, FilterSpecificationConverter filterSpecificationConverter, FilterBuilder filterBuilder, IUserService userService, IBrandService brandService, ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.filterParser = filterParser;
        this.filterSpecificationConverter = filterSpecificationConverter;
        this.filterBuilder = filterBuilder;
        this.userService = userService;
        this.brandService = brandService;
        this.productConverter = productConverter;
    }

    @Override
    public ProductResponseDTO handleCreateProduct(ProductRequestDTO productRequest) {

        Category category = categoryService.handleFetchCategoryById(productRequest.getCategory().getId());
        Brand brand = brandService.handleFetchBrandById(productRequest.getBrand().getId());

        productRequest.setStatus(true);
        productRequest.setSold(0);

        Product toSaveProduct = new Product();
        productConverter.convertToProduct(productRequest, toSaveProduct);

        toSaveProduct.setCategory(category);
        toSaveProduct.setBrand(brand);

        productRepository.save(toSaveProduct);

        return productConverter.convertToProductResponseDTO(toSaveProduct);
    }

    @Override
    public ProductResponseDTO handleUpdateProduct(ProductRequestDTO product) {
        Product productDB = handleFetchProductById(product.getId());

        product.setSold(productDB.getSold());



        if(product.getCategory().getId() != productDB.getCategory().getId()) {
            Category category = categoryService.handleFetchCategoryById(product.getCategory().getId());

            productDB.setCategory(category);
        }
        if(product.getBrand().getId() != productDB.getBrand().getId()) {
            Brand brand = brandService.handleFetchBrandById(product.getBrand().getId());

            productDB.setBrand(brand);
        }
        productConverter.convertToProduct(product, productDB);


        productRepository.save(productDB);


        return productConverter.convertToProductResponseDTO(productDB);
    }

    @Override
    public Product handleFetchProductById(String id) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(()-> new IdInvalidException("Product with " + id + " not found"));

        return product;
    }

    @Override
    public PaginationResponseDTO handleFetchAllProducts(Specification<Product> spec, Pageable pageable) {
        String filter ="status = 1 ";
        FilterNode filterNode =filterParser.parse(filter);

        FilterSpecification<Product> statusSpec =filterSpecificationConverter.convert(filterNode);

        Specification<Product> finalSpec = spec.and(statusSpec);

        String username = SecurityUtil.getCurrentUserLogin().orElse(null);
        if(username!=null) {
            User user = userService.handleGetUserByUsername(username);
            if(user!= null && !user.getRole().getCode().equalsIgnoreCase(SecurityUtil.ROLE_CUSTOMER)){
                finalSpec = spec;
            }
        }

        Page<Product> productPage = productRepository.findAll(finalSpec, pageable);

        PaginationResponseDTO result = PaginationUtil.handlePaginate(pageable, productPage);

        List<Product> products = productPage.getContent();

        List<ProductResponseDTO> responses = products.stream().map(it -> productConverter.convertToProductResponseDTO(it)).toList();

        result.setResult(responses);

        return result;
    }

    @Override
    public List<Product> handleFetchAllProductByIds(List<String> ids) {
        return productRepository.findByIdIn(ids);
    }

    @Override
    public ProductResponseDTO handleFetchProductResponseById(String id) {
        return productConverter.convertToProductResponseDTO(handleFetchProductById(id));
    }

    public Map<String, List<String>> getTop7DistinctProductFeatures() {
        Map<String, List<String>> result = new HashMap<>();

        result.put("cpu", productRepository.findTop7DistinctCpuOrderBySoldDesc());
        result.put("gpu", productRepository.findTop7DistinctGpuOrderBySoldDesc());
        result.put("ram", productRepository.findTop7DistinctRamOrderBySoldDesc());
        result.put("model", productRepository.findTop7DistinctModelOrderBySoldDesc());
        result.put("screen", productRepository.findTop7DistinctScreenOrderBySoldDesc());

        return result;
    }

}
