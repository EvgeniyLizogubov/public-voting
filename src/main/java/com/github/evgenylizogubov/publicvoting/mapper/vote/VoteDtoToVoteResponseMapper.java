package com.github.evgenylizogubov.publicvoting.mapper.vote;

import com.github.evgenylizogubov.publicvoting.controller.dto.vote.VoteDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.vote.VoteResponse;
import com.github.evgenylizogubov.publicvoting.mapper.BaseDtoToResponseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoteDtoToVoteResponseMapper extends BaseDtoToResponseMapper<VoteDto, VoteResponse> {
}
