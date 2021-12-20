package com.pos.integration.model.redirect_url;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RedirectUrlResponse {

    private final String url;
    private final String rawBody;

    @Builder
    public RedirectUrlResponse(final String url, final String rawBody) {
        this.url = url;
        this.rawBody = rawBody;
    }
}
