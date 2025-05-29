package com.weshopify.platform.service;

import java.util.List;

import com.weshopify.platform.beans.BrandsBean;

public interface BrandsService {
	
	BrandsBean createBrand(BrandsBean BrandsBean);
	BrandsBean updateBrand(BrandsBean BrandsBean);
	List<BrandsBean> findAllBrands();
	BrandsBean findBrandById(String BrandsId);
	List<BrandsBean> deleteBrand(String BrandsId);
	void cleanDB();

}
