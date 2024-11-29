package org.spring_boot.course.project_db.structure.validation.exeptions.invocation;

import lombok.Getter;

@Getter
public class InvocationException extends RuntimeException {

    private final InvocationExceptionCode code;

    public InvocationException(InvocationExceptionCode code) {
        this.code = code;
    }
}
