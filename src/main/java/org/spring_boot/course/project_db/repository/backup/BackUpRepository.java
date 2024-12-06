package org.spring_boot.course.project_db.repository.backup;


import org.spring_boot.course.project_db.model.Backup;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BackUpRepository {

    private final JdbcTemplate jdbcTemplate;

    public BackUpRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void backUp(Backup backUp){
        String sql = "insert into backup(backup_name, backup_time, status, details) values(?, ?, ?, ?)";
        jdbcTemplate.update(sql,backUp.getBackupName(), backUp.getBackupTime(), backUp.getBackupStatus(), backUp.getBackupDetails());
    }
}

