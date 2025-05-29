package com.weshopify.platform.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weshopify.platform.beans.BrandsBean;
import com.weshopify.platform.beans.CategoryBean;
import com.weshopify.platform.model.Brands;
import com.weshopify.platform.outbounds.CategoriesApiFeignClient;
import com.weshopify.platform.repository.BrandsRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BrandServiceImpl implements BrandsService {

	@Autowired
	BrandsRepository brandsRepo;

	/*	@Autowired
		CategoriesApiClient categoriesApiClient;*/
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	CategoriesApiFeignClient apiFeignClient;

	/*@Value("${service.categories.token}")
	private String token;*/
	
	@Override
	public BrandsBean createBrand(BrandsBean brandBean) {
		return convertEntityToBean(brandsRepo.save(convertBeanToEntity(brandBean)));
	}

	@Override
	public BrandsBean updateBrand(BrandsBean brandBean) {
		return convertEntityToBean(brandsRepo.save(convertBeanToEntity(brandBean)));
	}

	@Override
	public List<BrandsBean> findAllBrands() {
		List<Brands> brandsEntityList = brandsRepo.findAll();
		if (!CollectionUtils.isEmpty(brandsEntityList)) {
			List<BrandsBean> brandsBeanList = new ArrayList<>();
			brandsEntityList.forEach(list -> {
				brandsBeanList.add(convertEntityToBean(list));
			});
			return brandsBeanList;
		} else {
			throw new RuntimeException("NO BRANDS AVAILABLE!");
		}

	}

	@Override
	public BrandsBean findBrandById(String brandId) {
		return convertEntityToBean(brandsRepo.findById(brandId).get());
	}

	@Override
	public List<BrandsBean> deleteBrand(String brandId) {
		brandsRepo.deleteById(brandId);
		return findAllBrands();
	}

	private Brands convertBeanToEntity(BrandsBean brandBean) {
		Brands brandEntity = new Brands();
		brandEntity.setName(brandBean.getName());
		brandEntity.setLogoPath(brandEntity.getLogoPath());

		if (!CollectionUtils.isEmpty(brandBean.getCategories())) {
			Set<CategoryBean> categoriesList = new HashSet<CategoryBean>();
			Map<String, String> headers=new HashMap<>();
			String headerWithBearer=request.getHeader("Authorization");
			headers.put(HttpHeaders.AUTHORIZATION, headerWithBearer);
			brandBean.getCategories().parallelStream().forEach(cat -> {
				log.info("Invoking the Category Service");
				ResponseEntity<String> apiResponse = apiFeignClient.findCategoryById(cat.getId(), headers);
//				log.info(category);
				if(apiResponse.getStatusCode().value()==HttpStatus.OK.value() && apiResponse!=null) {
					try {
						log.info("Invoked Category service successfull!!");
						categoriesList.add(mapper.readValue(apiResponse.getBody(), CategoryBean.class));
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
				}else {
					log.info("Failed to invoke Category service");
					throw new RuntimeException("Category service failed for id: " + cat.getId());
				}
				
			});
			log.info(categoriesList.toString());
			brandEntity.setCategories(categoriesList);

		}
//		brandEntity.setCategories(brandBean.getCategories()); 
		// For Update Operation
		if (!StringUtils.isEmpty(brandBean.getId())) {
			brandEntity.setId(brandBean.getId());
		}
		log.info(brandEntity.getCategories().toString());
		return brandEntity;
	}

	private BrandsBean convertEntityToBean(Brands brandEntity) {

		BrandsBean brandBean = new BrandsBean();

		brandBean.setId(brandEntity.getId());
		brandBean.setName(brandEntity.getName());
		brandBean.setCategories(brandEntity.getCategories());
//		log.info(brandBean.getCategories().toString());
		brandBean.setLogoPath(brandEntity.getLogoPath());

		return brandBean;
	}

	@Override
	public void cleanDB() {
		brandsRepo.deleteAll();
		
	}

}
