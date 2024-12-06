package org.spring_boot.course.project_db.controller.backup;


import lombok.RequiredArgsConstructor;
import org.spring_boot.course.project_db.service.backup.BackupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/backup")
@RequiredArgsConstructor
public class BackUpController {
    private final BackupService backupService;

    /**
     * Создание бэкапа базы данных
     *
     * @param userDetails текущий пользователь
     * @return HTTP 200 OK, если бэкап выполнен успешно
     */
    @PostMapping
    public ResponseEntity<String> backup(@AuthenticationPrincipal UserDetails userDetails) {
        backupService.backUp(userDetails);
        return ResponseEntity.ok("Backup was completed successfully");
    }
}

