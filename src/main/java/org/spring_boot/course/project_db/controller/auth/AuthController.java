package org.spring_boot.course.project_db.controller.auth;

import org.spring_boot.course.project_db.controller.auth.authEntity.AuthCredentials;
import org.spring_boot.course.project_db.controller.auth.authEntity.JwtTokenResponse;
import org.spring_boot.course.project_db.controller.auth.authEntity.SIgnUpEntity;
import org.spring_boot.course.project_db.service.auth.AuthService;
import org.spring_boot.course.project_db.structure.aspects.Log;
import org.springframework.web.bind.annotation.*;

@RestController
@Log
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public JwtTokenResponse auth(@RequestBody AuthCredentials authCredentials) {
        return authService.auth(authCredentials);
    }

    @PostMapping("/sign-up")
    public JwtTokenResponse signUp(@RequestBody SIgnUpEntity sIgnUpEntity) {
        return authService.signUp(sIgnUpEntity);
    }


}
