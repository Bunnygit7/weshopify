package com.weshopify.platform.service;

import java.util.List;

import com.weshopify.platform.beans.CategoryBean;

public interface CategoryService {
	CategoryBean createCategory(CategoryBean categoryBean);
	CategoryBean updateCategory(CategoryBean categoryBean);
	List<CategoryBean> findAllCategories();
	CategoryBean findCategoryById(int categoryId);
	List<CategoryBean> findAllChildCategories(int parentId);
	List<CategoryBean> deleteCategory(int categoryId);
}
