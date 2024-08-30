package com.github.evgenylizogubov.publicvoting.mapper.voting;

import com.github.evgenylizogubov.publicvoting.controller.dto.voting.VotingDto;
import com.github.evgenylizogubov.publicvoting.mapper.BaseDtoToEntityMapper;
import com.github.evgenylizogubov.publicvoting.model.Voting;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VotingDtoToVotingMapper extends BaseDtoToEntityMapper<VotingDto, Voting> {
}
