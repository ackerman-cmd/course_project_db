package org.spring_boot.course.project_db.service.auth;


import org.spring_boot.course.project_db.config.provider.JwtTokenProvider;
import org.spring_boot.course.project_db.controller.auth.authEntity.AuthCredentials;
import org.spring_boot.course.project_db.controller.auth.authEntity.JwtTokenResponse;
import org.spring_boot.course.project_db.controller.auth.authEntity.SIgnUpEntity;
import org.spring_boot.course.project_db.model.User;
import org.spring_boot.course.project_db.repository.user.UserRepository;
import org.spring_boot.course.project_db.structure.aspects.Log;
import org.spring_boot.course.project_db.structure.validation.exeptions.business.BusinessException;
import org.spring_boot.course.project_db.structure.validation.exeptions.business.BusinessExceptionCode;
import org.spring_boot.course.project_db.structure.validation.exeptions.validation.ValidationException;
import org.spring_boot.course.project_db.structure.validation.exeptions.validation.ValidationExceptionCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtTokenProvider provider;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthService(JwtTokenProvider provider, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.provider = provider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Log
    public JwtTokenResponse auth(AuthCredentials credentials) {
        User user = userRepository.findByUsername(credentials.username())
                .orElseThrow(() -> new BusinessException(BusinessExceptionCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(credentials.password(), user.getPassword())) {
            throw new ValidationException(ValidationExceptionCode.ACCESS_DENIED);
        }

        String token = provider.generateToken(credentials.username());

        return new JwtTokenResponse(token);

    }

    @Log
    public JwtTokenResponse signUp(SIgnUpEntity sIgnUpEntity) {
        if (userRepository.findByUsername(sIgnUpEntity.username()).isPresent()) {
            throw new BusinessException(BusinessExceptionCode.USER_ALREADY_EXIST);
        }

        userRepository.save(User.createUser(
                sIgnUpEntity.username(),
                passwordEncoder.encode(sIgnUpEntity.password()),
                sIgnUpEntity.role()
        ));

        String token = provider.generateToken(sIgnUpEntity.username());

        return new JwtTokenResponse(token);
    }
}
