package com.weshopify.platform.cqrs.handler;

import java.util.HashSet;
import java.util.Set;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.weshopify.platform.beans.CategoryBean;
import com.weshopify.platform.cqrs.domainevent.CategoryEvent;
import com.weshopify.platform.repository.BrandsRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CategoriesEventHandler {
	
	@Autowired
	BrandsRepository brandRepo;
	
	/*private String eventId;
	private int id;
	private String name;
	private String alias;
	private int parentCategory;
	private boolean enabled;
	*/
	@EventHandler
	public CategoryEvent event(CategoryEvent event) {
		Set<CategoryBean> updatedCategorySet=new HashSet<CategoryBean>();
		brandRepo.findAll().stream().forEach(brand->{
			if(!CollectionUtils.isEmpty(brand.getCategories())) {
				brand.getCategories().forEach(category->{
					if(event.getId()==category.getId()) {
						CategoryBean updatedCategory=new CategoryBean();
						updatedCategory.setName(event.getName());
						updatedCategory.setAlias(event.getAlias());
						updatedCategory.setEnabled(event.isEnabled());
						updatedCategory.setId(event.getId());
						updatedCategory.setParentCategory(event.getParentCategory());
						updatedCategorySet.add(updatedCategory);
						log.info(updatedCategory.toString());
					}else {
						updatedCategorySet.add(category);
					}
				});
				brand.setCategories(updatedCategorySet);
				log.info("list"+updatedCategorySet.toString());
				brandRepo.save(brand);
				updatedCategorySet.clear();
			}
		});
		
		log.info(event.toString());
		return null;
	}

}
