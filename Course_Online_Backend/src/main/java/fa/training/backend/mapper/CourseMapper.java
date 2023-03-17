package fa.training.backend.mapper;

import fa.training.backend.entities.Course;
import fa.training.backend.model.CourseModel;
//import org.apache.commons.lang3.StringEscapeUtils;
//import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import java.util.List;

@Mapper(componentModel="spring")
public interface CourseMapper {

    @Mapping(target="feedbacks", ignore = true)
    CourseModel toModel(Course course);
    Course toEntity(CourseModel courseModel);

//    @AfterMapping
//    default void decodeHTMLCharacter(CourseModel courseModel){
//        courseModel.suitable = StringEscapeUtils.escapeHtml4(courseModel.suitable);
//        courseModel.description = StringEscapeUtils.escapeHtml4(courseModel.description);
//        courseModel.objective = StringEscapeUtils.escapeHtml4(courseModel.objective);
//        courseModel.chapters.forEach(c -> c.setChapterName(StringEscapeUtils.escapeHtml4(c.chapterName)));
//    }

    List<CourseModel> toListModel(List<Course> courses);
}
