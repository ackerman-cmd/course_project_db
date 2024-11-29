package org.spring_boot.course.project_db.structure.validation.exeptions.business;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final BusinessExceptionCode code;

    public BusinessException(BusinessExceptionCode code) {
        this.code = code;
    }
}
