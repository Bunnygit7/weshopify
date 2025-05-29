package com.weshopify.platform.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.weshopify.platform.beans.CategoryBean;
import com.weshopify.platform.cqrs.commands.CategoryCommand;
import com.weshopify.platform.model.Category;
import com.weshopify.platform.repo.CategoriesRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	public CategoriesRepository categoriesRepo;

	@Autowired
	private CommandGateway commandGateway;
	
	@Override
	public CategoryBean createCategory(CategoryBean categoryBean) {
		return convertEntityToBean(categoriesRepo.save(convertBeanToEntity(categoryBean)));
	}

	@Override
	public CategoryBean updateCategory(CategoryBean categoryBean) {
		CategoryBean bean=convertEntityToBean(categoriesRepo.save(convertBeanToEntity(categoryBean)));
		CategoryCommand categoryCommand =convertBeanToCommand(bean);
		CompletableFuture<CategoryCommand> future=commandGateway.send(categoryCommand);
		log.info(future.toString());
		log.info("Updating");
		if (future.isDone()) {
			log.info("Category updates were delivered to counsumer service");
		}else {
			log.error("Category updates were not delivered to counsumer service");
		}
		return bean;
	}

	@Override
	public List<CategoryBean> findAllCategories() {
		List<Category> categoriesEntityList = categoriesRepo.findAll();
		if (!CollectionUtils.isEmpty(categoriesEntityList)) {
			List<CategoryBean> categoriesBeansList = new ArrayList<>();
			categoriesEntityList.stream().forEach(list -> {
				categoriesBeansList.add(convertEntityToBean(list));
			});
			return categoriesBeansList;
		} else {
			throw new RuntimeException("No Categories Found In Database!");
		}

	}

	@Override
	public CategoryBean findCategoryById(int categoryId) {
		return convertEntityToBean(categoriesRepo.findById(categoryId).get());
	}

	@Override
	public List<CategoryBean> findAllChildCategories(int parentId) {
		List<Category> categoriesEntityList = categoriesRepo.findChildsOfParent(parentId);
		if (!CollectionUtils.isEmpty(categoriesEntityList)) {
			List<CategoryBean> categoriesBeansList = new ArrayList<>();
			categoriesEntityList.stream().forEach(list -> {
				categoriesBeansList.add(convertEntityToBean(list));
			});
			return categoriesBeansList;
		} else {
			throw new RuntimeException("No Child Categories Found For The Parent " + parentId);
		}
	}

	@Override
	public List<CategoryBean> deleteCategory(int categoryId) {
		categoriesRepo.deleteById(categoryId);
		return findAllCategories();
	}

	
	private Category convertBeanToEntity(CategoryBean categoryBean) {
		Category categoryEntity=new Category();
		categoryEntity.setName(categoryBean.getName());
		categoryEntity.setAlias(categoryBean.getAlias());
		categoryEntity.setEnabled(true);
//		categoryEntity.setImagePath(categoryBean.set);
		
		//IF parent present then retrieve and cast parent
		if (categoryBean.getParentCategory() > 0) {
			categoryEntity.setParent(categoriesRepo.findById(categoryBean.getParentCategory()).get());
		}
		// Only for update operation
		if (categoryBean.getId() > 0) {
			categoryEntity.setId(categoryBean.getId());
		}
		return categoryEntity;
	}

	private CategoryBean convertEntityToBean(Category category) {
		CategoryBean categoryBean=new CategoryBean();
		categoryBean.setId(category.getId());
		categoryBean.setName(category.getName());
		categoryBean.setAlias(category.getAlias());
		categoryBean.setEnabled(category.isEnabled());
//		categoryBean.setImagePath(category.set);
		if(category.getParent()!=null) {
			categoryBean.setParentCategory(category.getParent().getId());
		}
		
		return categoryBean;
	}
	
	private CategoryCommand convertBeanToCommand(CategoryBean bean) {
		CategoryCommand command=new CategoryCommand();
		String randomId=RandomStringUtils.randomAlphanumeric(17).toUpperCase();
		command.setEventId(randomId);
		command.setId(bean.getId());
		command.setAlias(bean.getAlias());
		command.setEnabled(bean.isEnabled());
		command.setParentCategory(bean.getParentCategory());
		command.setName(bean.getName());
		return command;
	}

}
