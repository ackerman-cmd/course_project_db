package org.spring_boot.course.project_db.structure.validation.exeptions.validation;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
    private final ValidationExceptionCode code;

    public ValidationException(ValidationExceptionCode code) {
        this.code = code;
    }
}
