package org.spring_boot.course.project_db.utils.exeptions;

public class DuplicatePasswordExc extends RuntimeException {
    public DuplicatePasswordExc(String message) {
        super(message);
    }
}
