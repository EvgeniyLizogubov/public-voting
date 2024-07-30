package com.github.evgenylizogubov.publicvoting.web.user;

import com.github.evgenylizogubov.publicvoting.model.User;
import com.github.evgenylizogubov.publicvoting.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractUserController {
    protected final Logger log = getLogger(getClass());
    
    @Autowired
    protected UserRepository userRepository;
    
    @Autowired
    private UniqueMailValidator emailValidator;
    
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }
    
    public User get(int id) {
        log.info("get {}", id);
        return userRepository.getExisted(id);
    }
    
    public void delete(int id) {
        log.info("delete {}", id);
        userRepository.deleteExisted(id);
    }
}
