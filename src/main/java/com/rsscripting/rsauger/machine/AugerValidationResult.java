package com.rsscripting.rsauger.machine;

import java.util.List;

public class AugerValidationResult {

    private final boolean valid;

    private final AugerEndpoints endpoints;

    private final List<String> messages;

    private final MachineError primaryError;

    public AugerValidationResult(
            boolean valid,
            AugerEndpoints endpoints,
            List<String> messages,
            MachineError primaryError
    ) {

        this.valid = valid;
        this.endpoints = endpoints;
        this.messages = messages;
        this.primaryError = primaryError;

    }

    public boolean isValid() {

        return valid;

    }

    public AugerEndpoints getEndpoints() {

        return endpoints;

    }

    public List<String> getMessages() {

        return messages;

    }

    public MachineError getPrimaryError() {

        return primaryError;

    }

}