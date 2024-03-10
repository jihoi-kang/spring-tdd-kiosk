package sample.springtddkiosk.spring.api.service.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import sample.springtddkiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import sample.springtddkiosk.spring.api.service.product.response.ProductResponse;
import sample.springtddkiosk.spring.domain.product.Product;
import sample.springtddkiosk.spring.domain.product.ProductRepository;
import sample.springtddkiosk.spring.domain.product.ProductSellingStatus;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductNumberFactory productNumberFactory;

    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        String nextProductNumber = productNumberFactory.createNextProductNumber();
        final Product product = request.toEntity(nextProductNumber);
        productRepository.save(product);
        return ProductResponse.of(product);
    }

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());
        return products.stream()
            .map(ProductResponse::of)
            .collect(Collectors.toList());
    }

}
