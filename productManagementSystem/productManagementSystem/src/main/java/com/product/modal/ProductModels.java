package com.product.modal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ProductModels {

    @Id
    @GeneratedValue(generator = "modalId")
    @GenericGenerator(name = "modalId", strategy = "org.hibernate.id.UUIDGenerator")
    String modalId;

    String color;
    String length;
    String width;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId")
    private Product product;

    public ProductModels() {
    }

    public ProductModels(String modalId, String color, String length, String width, Product product) {
        this.modalId = modalId;
        this.color = color;
        this.length = length;
        this.width = width;
        this.product = product;
    }

    public String getModalId() {
        return modalId;
    }

    public void setModalId(String modalId) {
        this.modalId = modalId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public String toString() {
        return "ProductModels{" +
                "modalId='" + modalId + '\'' +
                ", color='" + color + '\'' +
                ", length='" + length + '\'' +
                ", width='" + width + '\'' +
                ", product=" + product +
                '}';
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
