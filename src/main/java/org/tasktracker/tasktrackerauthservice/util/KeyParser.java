package org.tasktracker.tasktrackerauthservice.util;

import jakarta.validation.constraints.NotBlank;

public interface KeyParser <PUBLIC, SECRET>{

    PUBLIC parsePublicKey (@NotBlank String key, @NotBlank String algorithm);
    SECRET parsePrivateKey(@NotBlank String key, @NotBlank String algorithm);
}
