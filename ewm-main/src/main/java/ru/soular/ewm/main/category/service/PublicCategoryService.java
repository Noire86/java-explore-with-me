package ru.soular.ewm.main.category.service;

import ru.soular.ewm.main.category.dto.CategoryDto;

import java.util.List;

public interface PublicCategoryService {

    CategoryDto get(Long id);

    List<CategoryDto> getAll(Integer from, Integer size);

}
