package project.marwenpfe.response;
import com.google.gson.annotations.SerializedName;

public class AuthenticationResponse {
    @SerializedName("token")
    private String token;

    @SerializedName("id")
    private Integer id;

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("role")
    private String role;

    public AuthenticationResponse(String token, Integer id, String username, String email) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = "user"; // Set default role to "user"
    }

    // Add getters and setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
