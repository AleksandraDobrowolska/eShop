package pl.bykowski.eShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import pl.bykowski.eShop.model.Product;
import pl.bykowski.eShop.service.ProductService;

@Controller
@Profile("START")
public class StartVersionController implements ProductController {

    private final ProductService productService;

    @Autowired
    public StartVersionController(ProductService productService) {
        this.productService = productService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void printProductList() {
        System.out.println("Shop version: START");
        int totalPrice = 0;
        for (int i = 0; i < 5; i++) {
            Product product = productService.getProductList().get(i);
            System.out.println(product);
            totalPrice += product.getProductPrice();
        }
        System.out.println("Total price equals: " + totalPrice);
        System.out.println();
        productService.addProduct();
        System.out.println("Amount to pay: " + productService.getCartValue());
    }

}
