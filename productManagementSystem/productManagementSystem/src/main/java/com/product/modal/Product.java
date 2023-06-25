package com.product.modal;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Product {

    @SequenceGenerator(name = "productId", initialValue = 200)
    @GeneratedValue(strategy = GenerationType.IDENTITY ,generator = "productId")
    @Id
    private int productId;

    private String productName;
    private int productPrice;
    private String productBrand;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "product")
    private Set<ProductModels> productModelsList;

    public Product() {
    }

    public Product(int productId, String productName, int productPrice, String productBrand, Set<ProductModels> productModelsList) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productBrand = productBrand;
        this.productModelsList = productModelsList;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public Set<ProductModels> getProductModelsList() {
        return productModelsList;
    }

    public void setProductModelsList(Set<ProductModels> productModelsList) {
        this.productModelsList = productModelsList;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productBrand='" + productBrand + '\'' +
                ", productModelsList=" + productModelsList +
                '}';
    }
//    @OneToMany(cascade =CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "product")
//    private List<ProductModels> productModelsList;
//
//    public Product(int productId, String productName, int productPrice, String productBrand) {
//        this.productId = productId;
//        this.productName = productName;
//        this.productPrice = productPrice;
//        this.productBrand = productBrand;
//    }
//
//    public int getProductId() {
//        return productId;
//    }
//
//    public void setProductId(int productId) {
//        this.productId = productId;
//    }
//
//    public String getProductName() {
//        return productName;
//    }
//
//    public void setProductName(String productName) {
//        this.productName = productName;
//    }
//
//    public int getProductPrice() {
//        return productPrice;
//    }
//
//    public void setProductPrice(int productPrice) {
//        this.productPrice = productPrice;
//    }
//
//    public String getProductBrand() {
//        return productBrand;
//    }
//
//    public void setProductBrand(String productBrand) {
//        this.productBrand = productBrand;
//    }
//
//    @Override
//    public String toString() {
//        return "Product{" +
//                "productId=" + productId +
//                ", productName='" + productName + '\'' +
//                ", productPrice=" + productPrice +
//                ", productBrand='" + productBrand + '\'' +
//                '}';
//    }
}
