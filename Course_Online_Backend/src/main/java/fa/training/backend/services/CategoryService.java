package fa.training.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import fa.training.backend.entities.Category;
import fa.training.backend.repositories.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	public List<Category> getAll() {
        return categoryRepository.findAll();
    }
	public Category getCategoryByName(String categoryName) {
        return categoryRepository.findCategoryByCategoryName(categoryName);
    }
    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }
}
