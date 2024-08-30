package com.github.evgenylizogubov.publicvoting.mapper.suggestion;

import com.github.evgenylizogubov.publicvoting.controller.dto.suggestion.SuggestionDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.suggestion.SuggestionRequest;
import com.github.evgenylizogubov.publicvoting.mapper.BaseRequestToDtoMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuggestionRequestToSuggestionDtoMapper extends BaseRequestToDtoMapper<SuggestionRequest, SuggestionDto> {
}
