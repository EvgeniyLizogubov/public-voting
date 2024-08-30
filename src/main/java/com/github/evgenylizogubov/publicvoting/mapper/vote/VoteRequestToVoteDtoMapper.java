package com.github.evgenylizogubov.publicvoting.mapper.vote;

import com.github.evgenylizogubov.publicvoting.controller.dto.vote.VoteDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.vote.VoteRequest;
import com.github.evgenylizogubov.publicvoting.mapper.BaseRequestToDtoMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoteRequestToVoteDtoMapper extends BaseRequestToDtoMapper<VoteRequest, VoteDto> {
}
