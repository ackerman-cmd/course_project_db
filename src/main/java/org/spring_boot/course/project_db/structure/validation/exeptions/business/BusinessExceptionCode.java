package org.spring_boot.course.project_db.structure.validation.exeptions.business;

public enum BusinessExceptionCode {
    USER_NOT_FOUND("Пользователь не найден"),
    USER_ALREADY_EXIST("Пользователь уже существует"),
    ROLE_NOT_FOUND("Роль не найдена"),
    BOOKING_ERROR("Ошибка при бронировании полета"),
    PAYMENT_ERROR("Ошибка при проведении платежа"),
    ;


     final String value;

    BusinessExceptionCode(String value) {
        this.value = value;
    }
}
