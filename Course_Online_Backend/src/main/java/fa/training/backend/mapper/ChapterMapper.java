package fa.training.backend.mapper;

import fa.training.backend.entities.Chapter;
import fa.training.backend.model.ChapterModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel="spring")
public interface ChapterMapper {
    ChapterModel toModel(Chapter chapter);
    Chapter toEntity(ChapterModel chapterModel);
}
