package ProiectLaborator.mapper;


import java.util.List;

import ProiectLaborator.request.CategoryRequest;
import org.mapstruct.Mapper;
import ProiectLaborator.entity.Category;
import ProiectLaborator.response.CategoryResponse;


@Mapper
public interface CategoryMapper {

    List<CategoryResponse> toResponse(List<Category> entities);

    CategoryResponse toResponse(Category entry);

    Category toEntry(CategoryRequest request);

}