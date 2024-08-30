package com.github.evgenylizogubov.publicvoting.mapper.vote;

import com.github.evgenylizogubov.publicvoting.controller.dto.vote.VoteDto;
import com.github.evgenylizogubov.publicvoting.mapper.BaseDtoToEntityMapper;
import com.github.evgenylizogubov.publicvoting.model.Vote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoteDtoToVoteMapper extends BaseDtoToEntityMapper<VoteDto, Vote> {
}
