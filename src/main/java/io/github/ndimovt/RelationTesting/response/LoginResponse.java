package io.github.ndimovt.RelationTesting.response;

/**
 * The class LoginResponse
 */
public class LoginResponse {
    private String token;
    private Long expiresIn;

    /**
     * Return token
     * @return String object
     */

    public String getToken() {
        return token;
    }

    /**
     * Updates token. Returns current instance
     * @param token String object
     * @return Current LoginResponse instance
     */

    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }

    /**
     * Return expiresIn
     * @return Long object
     */
    public Long getExpiresIn() {
        return expiresIn;
    }

    /**
     * Updates espiresIn. Return current instance
     * @param expiresIn Long object
     * @return Current LoginResponse instance
     */
    public LoginResponse setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }
}
