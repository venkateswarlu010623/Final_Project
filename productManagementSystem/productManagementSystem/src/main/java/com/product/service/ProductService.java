package com.product.service;

import com.product.modal.Product;
import com.product.modal.ProductModels;
import com.product.repository.ProductModalRepository;
import com.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {


    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductModalRepository productModalRepository;


    public Product saveProduct(Product product)
    {
        product.setProductModelsList(new HashSet<>());
        return productRepository.save(product);
    }


    public Product saveProductModel(Set<ProductModels> productModels, int id)
    {
        Product product = productRepository.findById(id).get();
        for (ProductModels productModels1 :  productModels)
        {
            productModels1.setProduct(product);
        }
        product.getProductModelsList().addAll(productModels);

         return productRepository.save(product);
    }


    public List<Product> getProducts()
    {
        return productRepository.findAll();
    }


    public Product updateProduct(Product product,int id)
    {
        Product updatedProduct = productRepository.findById(id).get();

        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName(product.getProductName());
        updatedProduct.setProductPrice(product.getProductPrice());
        updatedProduct.setProductBrand(product.getProductBrand());


        return updatedProduct;
    }


    public void removeProduct(int id)
    {
        productRepository.deleteById(id);
    }

    public Product getProductListByIdAndName(int productId,String productName)
    {
        return productRepository.findByProductIdAndProductName(productId,productName);
    }

    public Product getProductListByIdAndPrice(int productId,int productPrice)
    {
        return productRepository.findByProductIdAndProductPrice(productId,productPrice);
    }

    public List<Product> getProductListByName(String productName)
    {
        return productRepository.findByProductNameOrderByProductName(productName);
    }

    public List<Product> getAllProducts()
    {
        return productRepository.getAllProducts();
    }


    public Product getProductByIdAndBrand(int id,String brand)
    {
        return productRepository.getProductByIdAndBrand(id,brand);
    }

    public Product getOneProductById(int id)
    {
        return productRepository.getProductById(id);
    }

    public List<Product> setProductPrice(int price,List<Integer> productList)
    {
        List<Integer> productId = new ArrayList<>(productList);
        return productRepository.updateProductPrice(price,productId);

    }

//for checking String sequence

    public ProductModels saveProductModal(ProductModels productModels)
    {
        return productModalRepository.save(productModels);
    }
}


//        Product users = new Product();
//        if (optionalUser.isPresent()) {
//            users = optionalUser.get();
//            products.setUser(users);
//            users.getProducts().add(products);
//            userRepo.save(users);
//        }
