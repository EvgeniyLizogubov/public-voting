package com.github.evgenylizogubov.publicvoting.util;

import com.github.evgenylizogubov.publicvoting.model.Role;
import com.github.evgenylizogubov.publicvoting.model.User;
import com.github.evgenylizogubov.publicvoting.to.UserTo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UsersUtil {
    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getFirstName(), userTo.getLastName(), userTo.getEmail(), userTo.getPassword(), 0, null, Role.USER);
    }
    
    public static User updateFromTo(User user, UserTo userTo) {
        user.setFirstName(userTo.getFirstName());
        user.setLastName(userTo.getLastName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }
}
