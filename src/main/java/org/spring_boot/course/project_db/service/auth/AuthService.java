package org.spring_boot.course.project_db.service.auth;

import lombok.RequiredArgsConstructor;
import org.spring_boot.course.project_db.config.provider.JwtTokenProvider;
import org.spring_boot.course.project_db.controller.auth.authEntity.AuthCredentials;
import org.spring_boot.course.project_db.controller.auth.authEntity.JwtTokenResponse;
import org.spring_boot.course.project_db.controller.auth.authEntity.SIgnUpEntity;
import org.spring_boot.course.project_db.model.Customer;
import org.spring_boot.course.project_db.model.Role;
import org.spring_boot.course.project_db.model.User;
import org.spring_boot.course.project_db.repository.customer.CustomerRepository;
import org.spring_boot.course.project_db.repository.user.UserRepository;
import org.spring_boot.course.project_db.structure.aspects.Log;
import org.spring_boot.course.project_db.structure.validation.exeptions.business.BusinessException;
import org.spring_boot.course.project_db.structure.validation.exeptions.business.BusinessExceptionCode;
import org.spring_boot.course.project_db.structure.validation.exeptions.validation.ValidationException;
import org.spring_boot.course.project_db.structure.validation.exeptions.validation.ValidationExceptionCode;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider provider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final StringRedisTemplate redisTemplate;

    @Log
    public JwtTokenResponse auth(AuthCredentials credentials) {

        User user = userRepository.findByUsername(credentials.username())
                .orElseThrow(() -> new BusinessException(BusinessExceptionCode.USER_NOT_FOUND));


        if (!passwordEncoder.matches(credentials.password(), user.getPassword())) {
            throw new ValidationException(ValidationExceptionCode.ACCESS_DENIED);
        }


        String token = provider.generateToken(credentials.username());

        long expirationMs = provider.getJwtExpirationMs();
        redisTemplate.opsForValue().set("token:" + token, credentials.username(), expirationMs, TimeUnit.MILLISECONDS);

        String oldToken = (String) redisTemplate.opsForHash().get("user:tokens:" + credentials.username(), "active");
        if (oldToken != null) {
            provider.invalidateToken(oldToken, credentials.username());
        }
        // Сохраняем активный токен в хэше пользователя
        redisTemplate.opsForHash().put("user:tokens:" + credentials.username(), "active", token);
        redisTemplate.expire("user:tokens:" + credentials.username(), Duration.ofMillis(expirationMs));

        return new JwtTokenResponse(token);
    }

    @Log
    public JwtTokenResponse signUp(SIgnUpEntity sIgnUpEntity) {

        if (userRepository.findByUsername(sIgnUpEntity.username()).isPresent()) {
            throw new BusinessException(BusinessExceptionCode.USER_ALREADY_EXIST);
        }


        Integer id = userRepository.save(User.createUser(
                sIgnUpEntity.username(),
                passwordEncoder.encode(sIgnUpEntity.password()),
                sIgnUpEntity.role()
        ));


        if (sIgnUpEntity.role().equals(Role.USER)) {
            customerRepository.save(Customer.createNew(id, sIgnUpEntity.fullName()));
        }


        String token = provider.generateToken(sIgnUpEntity.username());


        long expirationMs = provider.getJwtExpirationMs();
        redisTemplate.opsForValue().set("token:" + token, sIgnUpEntity.username(), expirationMs, TimeUnit.MILLISECONDS);


        redisTemplate.opsForHash().put("user:tokens:" + sIgnUpEntity.username(), "active", token);
        redisTemplate.expire("user:tokens:" + sIgnUpEntity.username(), Duration.ofMillis(expirationMs));

        return new JwtTokenResponse(token);
    }
}