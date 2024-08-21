package com.github.evgenylizogubov.publicvoting.service;

import com.github.evgenylizogubov.publicvoting.error.NotFoundException;
import com.github.evgenylizogubov.publicvoting.model.Voting;
import com.github.evgenylizogubov.publicvoting.repository.VotingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VotingService {
    private final VotingRepository votingRepository;
    
    public Voting get(int id) {
        return votingRepository.findById(id).orElse(null);
    }
    
    public List<Voting> getAll() {
        return votingRepository.findAll();
    }
    
    public Voting update(Voting voting, int id) {
        if (!votingRepository.existsById(id)) {
            throw new NotFoundException("Theme with id=" + id + " not found");
        }
        
        voting.setId(id);
        return votingRepository.save(voting);
    }
    
    public int delete(int id) {
        return votingRepository.removeById(id);
    }
}
