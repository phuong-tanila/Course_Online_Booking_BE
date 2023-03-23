package fa.training.backend.services;

import java.util.List;
import java.util.Optional;

import fa.training.backend.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fa.training.backend.entities.Category;
import fa.training.backend.repositories.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	public Category getCategoryByName(String categoryName) {
        return categoryRepository.findCategoryByCategoryName(categoryName);
    }
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
    public Category findById(int id) throws RecordNotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        } else {
            throw new RecordNotFoundException("No course exist for given id");
        }
    }
    public List<Category> getCategoryCategoryName(String categoryName) {
//		return categoryRepository.findByCategoryNameIgnoreCaseContaining(categoryName);
        return categoryRepository.findByCategoryName(categoryName);
    }

    public List<Category> getCategoriesByCourseId(int id){
        return categoryRepository.findCategoriesByCoursesId(id);
    }
}
