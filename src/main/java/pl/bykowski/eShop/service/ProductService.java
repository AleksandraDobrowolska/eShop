package pl.bykowski.eShop.service;

import org.springframework.stereotype.Service;
import pl.bykowski.eShop.model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ProductService {

    List<Product> productList = new ArrayList<>();

    public ProductService() {
        for (int i = 0; i < 5; i++) {
            Product product = new Product("product"+(i+1), priceGenerator());
            productList.add(product);
        }
    }

    public List<Product> getProductList() {
        return productList;
    }

    private int priceGenerator() {
        Random random = new Random();
        return random.nextInt(250)+50;
    }

    public double round(double value) {
        int precision = 2;
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(precision, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
