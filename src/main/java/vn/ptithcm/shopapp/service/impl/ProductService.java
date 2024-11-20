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
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.Category;
import vn.ptithcm.shopapp.model.entity.Product;
import vn.ptithcm.shopapp.model.entity.Role;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.repository.ProductRepository;
import vn.ptithcm.shopapp.service.ICategoryService;
import vn.ptithcm.shopapp.service.IProductService;
import vn.ptithcm.shopapp.util.PaginationUtil;
import vn.ptithcm.shopapp.util.SecurityUtil;
import vn.ptithcm.shopapp.util.StringUtil;

import java.util.List;


@Service
public class ProductService implements IProductService {


    private final FilterParser filterParser;
    private final FilterSpecificationConverter filterSpecificationConverter;
    private final FilterBuilder filterBuilder;


    private final ProductRepository productRepository;
    private final ICategoryService categoryService;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, ICategoryService categoryService, FilterParser filterParser, FilterSpecificationConverter filterSpecificationConverter, FilterBuilder filterBuilder, UserService userService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.filterParser = filterParser;
        this.filterSpecificationConverter = filterSpecificationConverter;
        this.filterBuilder = filterBuilder;
        this.userService = userService;
    }

    @Override
    public Product handleCreateProduct(Product product) {

        if(product.getCategory()==null || !StringUtil.isValid(product.getCategory().getId())) {
            throw new IdInvalidException("Category must be filled");
        }

        Category category = categoryService.handleFetchCategoryById(product.getCategory().getId());

        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public Product handleUpdateProduct(Product product) {
        Product productDB = handleFetchProductById(product.getId());

        if(product.getCategory()==null || !StringUtil.isValid(product.getCategory().getId())) {
            throw new IdInvalidException("Category must be filled");
        }

        if(!product.getCategory().getId().equals(productDB.getCategory().getId())) {
            Category category = categoryService.handleFetchCategoryById(product.getCategory().getId());

            productDB.setCategory(category);
        }

        productDB.setName(product.getName());
        productDB.setBrand(product.getBrand());
        productDB.setModel(product.getModel());
        productDB.setCpu(product.getCpu());
        productDB.setRam(product.getRam());
        productDB.setMemory(product.getMemory());
        productDB.setMemoryType(product.getMemoryType());
        productDB.setGpu(product.getGpu());
        productDB.setScreen(product.getScreen());
        productDB.setTouch(product.getTouch());
        productDB.setPrice(product.getPrice());
        productDB.setDescription(product.getDescription());
        productDB.setThumbnail(product.getThumbnail());
        productDB.setStatus(product.getStatus());
        productDB.setWeight(product.getWeight());
        productDB.setQuantity(product.getQuantity());
        productDB.setTag(product.getTag());

        return productRepository.save(productDB);
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

        result.setResult(productPage.getContent());

        return result;
    }

    @Override
    public List<Product> handleFetchAllProductByIds(List<String> ids) {
        return productRepository.findByIdIn(ids);
    }


}
