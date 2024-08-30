package com.github.evgenylizogubov.publicvoting.mapper.suggestion;

import com.github.evgenylizogubov.publicvoting.controller.dto.suggestion.SuggestionDto;
import com.github.evgenylizogubov.publicvoting.mapper.BaseDtoToEntityMapper;
import com.github.evgenylizogubov.publicvoting.model.Suggestion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuggestionDtoToSuggestionMapper extends BaseDtoToEntityMapper<SuggestionDto, Suggestion> {
}
