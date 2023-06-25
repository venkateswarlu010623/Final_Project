package com.product.repository;

import com.product.modal.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    public  Product findByProductIdAndProductName(int productId, String productName);

    public   Product findByProductIdAndProductPrice(int productId, int productPrice);

    public List<Product> findByProductNameOrderByProductName(String name);

    @Query("select p FROM Product p")
    public List<Product> getAllProducts();

    @Query("select p FROM Product p WHERE p.productId = :id AND p.productBrand = :brand")
    public Product getProductByIdAndBrand(@Param("id") int id,@Param("brand") String brand);


    @Query(value = "select * from Product p where product_Id = :id",nativeQuery = true)
    public Product  getProductById(@Param("id") int productId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Product p SET p.productPrice = :productPrice WHERE p.productId IN :productIdList")
    public List<Product> updateProductPrice(@Param("productPrice") int productPrice, @Param("productIdList") List<Integer> productIdList);

}
