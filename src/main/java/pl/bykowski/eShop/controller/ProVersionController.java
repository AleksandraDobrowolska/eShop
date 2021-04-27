package pl.bykowski.eShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import pl.bykowski.eShop.model.Product;
import pl.bykowski.eShop.service.ProductService;

@Controller
@Profile("PRO")
public class ProVersionController implements ProductController {

    private final ProductService productService;

    @Value("${price.VAT}")
    private double VAT;
    @Value("${price.discount}")
    private double discount;

    @Autowired
    public ProVersionController(ProductService productService) {
        this.productService = productService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void printProductList() {
        System.out.println("Shop version: PRO");
        double totalPrice = 0;
        for (int i = 0; i < 5; i++) {
            Product product = productService.getProductList().get(i);
            double productPriceWithDiscount = productService.getPriceWithDiscount(product);
            product.setProductPrice(productService.round(productPriceWithDiscount));
            System.out.println(product);
            totalPrice += productPriceWithDiscount;
            totalPrice = productService.round(totalPrice);
        }
        System.out.println("Total price equals: " + totalPrice);
        System.out.println();
        productService.addProduct();
        System.out.println("Amount to pay with added VAT and after discount: " + productService.addDiscounttoTotalValueOfShopping());
    }
}
