package org.tasktracker.tasktrackerauthservice.service;

public interface AccessTokenService<T> {

    String generateAccess(T object);
}
