package celzoo.project.bloood.donation.model;


import lombok.Getter;

@Getter
public class AuthResponse {

    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
