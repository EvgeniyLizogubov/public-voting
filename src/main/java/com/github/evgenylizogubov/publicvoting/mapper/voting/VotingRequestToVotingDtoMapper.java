package com.github.evgenylizogubov.publicvoting.mapper.voting;

import com.github.evgenylizogubov.publicvoting.controller.dto.voting.VotingDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.voting.VotingRequest;
import com.github.evgenylizogubov.publicvoting.mapper.BaseRequestToDtoMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VotingRequestToVotingDtoMapper extends BaseRequestToDtoMapper<VotingRequest, VotingDto> {
}
