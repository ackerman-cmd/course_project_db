package org.spring_boot.course.project_db.utils.exeptions;

public class DuplicateNameUserExc extends RuntimeException{
    public DuplicateNameUserExc(String message) {
        super(message);
    }
}
