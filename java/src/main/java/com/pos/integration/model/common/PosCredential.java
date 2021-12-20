package com.pos.integration.model.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PosCredential {
    private final String username;
    private final String password;
    private final String clientId;
}
