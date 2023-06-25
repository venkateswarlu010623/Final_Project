package com.product.controller;

import com.product.modal.Product;
import com.product.modal.ProductModels;
import com.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    //1.create products
    @PostMapping("create/product")
    public Product createProduct(@RequestBody Product product)
    {
        return productService.saveProduct(product);
    }

    @PostMapping("create/productmodals/{id}")
    public Product createProductModals(@RequestBody Set<ProductModels> productModelsSet,@PathVariable int id)
    {
        return productService.saveProductModel(productModelsSet,id);
    }

    @GetMapping("get/product/{id}")
    public Product getOneProduct(@PathVariable int id)
    {
        return productService.getOneProductById(id);
    }

    @GetMapping("get/product")
    public List<Product> retrieveAllProducts()
    {
        return productService.getProducts();
    }

    @PutMapping("update/product/productid-{id}")
    public Product updateProduct(@RequestBody Product product,@PathVariable int id)
    {
        return productService.updateProduct(product,id);
    }

    @DeleteMapping("delete/product/productid-{id}")
    public void deleteProduct(@PathVariable int id)
    {
        productService.removeProduct(id);
    }

    @GetMapping("get/product/by/{id}/and/{name}")
    public Product getProductListByIdAndName(@PathVariable int id,@PathVariable String name)
    {
        return productService.getProductListByIdAndName(id, name);
    }

    @GetMapping("get/product/by/{id}/-and-/{price}")
    public Product getProductsByProductIdAndPrice(@PathVariable int id,@PathVariable int price)
    {
        return productService.getProductListByIdAndPrice(id,price);
    }

    @GetMapping("get/all/product/list")
    public List<Product> getAllProductList()
    {
        return productService.getAllProducts();
    }


    @GetMapping("get/product/by/id-{id}/and/brand-{brand}")
    public Product getProductByIdAndBrand(@PathVariable int id,@PathVariable String brand)
    {
        return productService.getProductByIdAndBrand(id,brand);
    }

    @GetMapping("get/product/by/id-{id}")
    public Product getProductById(@PathVariable int id)
    {
        return productService.getOneProductById(id);
    }


    @PostMapping("create/product/modal")
    public ProductModels createProductModal(@RequestBody ProductModels productModels)
    {
        return productService.saveProductModal(productModels);
    }

    @PatchMapping("update/products/price/with/{price}")
    public List<Product> updateProducts(@PathVariable int price, List<Integer> productsid)
    {
        productsid.forEach(System.out::println);
        return productService.setProductPrice(price,productsid);
    }
}
