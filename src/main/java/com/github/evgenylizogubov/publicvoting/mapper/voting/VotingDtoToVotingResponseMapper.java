package com.github.evgenylizogubov.publicvoting.mapper.voting;

import com.github.evgenylizogubov.publicvoting.controller.dto.voting.VotingDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.voting.VotingResponse;
import com.github.evgenylizogubov.publicvoting.mapper.BaseDtoToResponseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VotingDtoToVotingResponseMapper extends BaseDtoToResponseMapper<VotingDto, VotingResponse> {
}
