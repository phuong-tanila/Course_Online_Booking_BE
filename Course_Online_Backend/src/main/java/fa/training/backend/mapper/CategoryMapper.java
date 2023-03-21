package fa.training.backend.mapper;

import fa.training.backend.entities.Category;
import fa.training.backend.entities.Course;
import fa.training.backend.model.CategoryModels.CategoryModel;
import fa.training.backend.model.CategoryModels.CategoryModelDetail;
import fa.training.backend.model.CourseModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface CategoryMapper {
    CategoryModel toModel(Category category);
    Category toEntity(CategoryModel categoryModel);
    CategoryModelDetail toModelDetail(Category category);
    Category toEntityDetail(CategoryModelDetail categoryModel);

    List<CategoryModel> toListModel(List<Category> category);
}
