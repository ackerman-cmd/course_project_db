package org.spring_boot.course.project_db.structure.validation.exeptions.invocation;

public enum InvocationExceptionCode {
    USER_NOT_FOUND("Ошибка при поиске пользователя в date_base.users"),
    ;
   final String value;

    InvocationExceptionCode(String value) {
        this.value = value;
    }
}
