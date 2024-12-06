package org.spring_boot.course.project_db.model;


import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("backup")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Backup {

    private String backupName;

    private LocalDateTime backupTime;

    private String backupStatus;

    private String backupDetails;
}
