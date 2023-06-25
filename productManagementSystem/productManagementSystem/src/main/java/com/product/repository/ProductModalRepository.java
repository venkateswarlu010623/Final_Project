package com.product.repository;

import com.product.modal.ProductModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductModalRepository extends JpaRepository<ProductModels,String> {
}
