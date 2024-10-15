package celzoo.project.bloood.donation.model;

import lombok.Getter;

@Getter
public class AuthRequest {
    // Getters e Setters
    private String username;
    private String password;

    // Construtores
    public AuthRequest() {}

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}