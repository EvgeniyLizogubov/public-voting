package com.github.evgenylizogubov.publicvoting.mapper.suggestion;

import com.github.evgenylizogubov.publicvoting.controller.dto.suggestion.SuggestionDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.suggestion.SuggestionResponse;
import com.github.evgenylizogubov.publicvoting.mapper.BaseDtoToResponseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuggestionDtoToSuggestionResponseMapper extends BaseDtoToResponseMapper<SuggestionDto, SuggestionResponse> {
}
