package com.weshopify.platform.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.weshopify.platform.model.Category;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Integer> {
	
	@Query("from Category c where c.parent.id=:parentId")
	public List<Category> findChildsOfParent(int parentId);

}
