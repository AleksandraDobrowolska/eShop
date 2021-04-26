package pl.bykowski.eShop.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.bykowski.eShop.model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Service
public class ProductService {

    List<Product> productList = new ArrayList<>();

    Product product = new Product();

    @Value("${price.VAT}")
    private double VAT;
    @Value("${price.discount}")
    private double discount;

    double cartValue = 0;

    public ProductService() {
        for (int i = 0; i < 5; i++) {
            Product product = new Product("product" + (i + 1), priceGenerator());
            productList.add(product);
        }
    }

    public List<Product> getProductList() {
        return productList;
    }

    private int priceGenerator() {
        Random random = new Random();
        return random.nextInt(250) + 50;
    }

    public double getPriceWithVAT(Product product) {
        double productPrice = product.getProductPrice();
        double productPriceWithVAT = round((productPrice * VAT) + productPrice);
        return productPriceWithVAT;
    }

    public double getPriceWithDiscount(Product product) {
        double productPriceWithVAT = getPriceWithVAT(product);
        double discountValue = round(productPriceWithVAT * discount);
        double productPriceWithDiscount = productPriceWithVAT - discountValue;
        return productPriceWithDiscount;
    }

    public double round(double value) {
        int precision = 2;
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(precision, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public void addProduct() {
        List<Product> shoppingCart = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter product name you want to buy or enter EXIT to finish your shopping.");
            String input = "";
            input = scanner.next();
            while (!input.equalsIgnoreCase("EXIT")) {
                product.setProductName(input);
                product.setProductPrice(priceGenerator());
                shoppingCart.add(product);
                cartValue += product.getProductPrice();
                System.out.println("Current value of shopping cart equals: " + cartValue);
                System.out.println("Enter the next product.");
                input = scanner.next();
            }
            scanner.close();
            System.out.println("Thank you for the shopping.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getCartValue() {
        return cartValue;
    }

    public double addVATToTotalValueOfShopping() {
        return round((cartValue * VAT) + cartValue);
    }

    public double addDiscounttoTotalValueOfShopping() {
        double discountValue = addVATToTotalValueOfShopping() * discount;
        return round(addVATToTotalValueOfShopping() - discountValue);
    }
}