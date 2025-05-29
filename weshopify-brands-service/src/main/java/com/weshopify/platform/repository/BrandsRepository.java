package com.weshopify.platform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.weshopify.platform.model.Brands;

@Repository
public interface BrandsRepository extends MongoRepository<Brands, String> {

}
