package com.saasysquad.backend_tings.model;

import java.util.UUID;

public class RefreshToken {
    private UUID tokenId;
    private String refreshToken;

    public RefreshToken() {}

    public RefreshToken(UUID tokenId, String refreshToken) {
        this.tokenId = tokenId;
        this.refreshToken = refreshToken;
    }

    public UUID getTokenId() {
        return tokenId;
    }

    public void setTokenId(UUID tokenId) {
        this.tokenId = tokenId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
