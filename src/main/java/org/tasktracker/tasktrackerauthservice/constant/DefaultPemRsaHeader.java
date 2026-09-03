package org.tasktracker.tasktrackerauthservice.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class DefaultPemRsaHeader {

    public static final String BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----";
    public static final String END_PRIVATE_KEY = "-----END PRIVATE KEY-----";
    public static final String BEGIN_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----";
    public static final String END_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----";
    public static final String INDENTATION = "\\s+";
}
