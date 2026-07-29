package io.github.tbondetti.testresourceserver.infrastructure.persistence.adapter;

import io.github.tbondetti.testresourceserver.core.port.NumeroRepositoryPort;

import java.security.SecureRandom;

public class NumeroRepositoryAdapter implements NumeroRepositoryPort {

    static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String generate(final int length) {
        final StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            builder.append(ALPHANUMERIC.charAt(this.secureRandom.nextInt(ALPHANUMERIC.length())));
        }

        return builder.toString();
    }
}