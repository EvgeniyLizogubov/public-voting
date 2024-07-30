package com.github.evgenylizogubov.publicvoting.web.user;

import com.github.evgenylizogubov.publicvoting.HasIdAndEmail;
import com.github.evgenylizogubov.publicvoting.repository.UserRepository;
import com.github.evgenylizogubov.publicvoting.web.AuthUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class UniqueMailValidator implements Validator {
    public static final String EXCEPTION_DUPLICATE_EMAIL = "User with this email already exists";
    
    private final UserRepository userRepository;
    private final HttpServletRequest httpServletRequest;
    
    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return HasIdAndEmail.class.isAssignableFrom(clazz);
    }
    
    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        HasIdAndEmail user = (HasIdAndEmail) target;
        if (StringUtils.hasText(user.getEmail())) {
            userRepository.findByEmailIgnoreCase(user.getEmail())
                    .ifPresent(dbUser -> {
                        if (httpServletRequest.getMethod().equals("PUT")) {
                            int dbId = dbUser.id();
                            
                            if (user.getId() != null && dbId == user.id()) {
                                return;
                            }
                            
                            String requestURI = httpServletRequest.getRequestURI();
                            if (requestURI.endsWith("/" + dbId)
                                    || (dbId == AuthUser.authId() && requestURI.contains("/profile"))) {
                                return;
                            }
                        }
                        errors.rejectValue("email", "", EXCEPTION_DUPLICATE_EMAIL);
                    });
        }
    }
}
