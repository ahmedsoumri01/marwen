package project.marwenpfe.services;

import project.marwenpfe.model.User;
import project.marwenpfe.request.AuthenticationRequest;
import project.marwenpfe.request.RegisterRequest;
import project.marwenpfe.response.AuthenticationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/api/v1/auth/authenticate")
    Call<User> authenticate(@Body AuthenticationRequest request);
    @POST("/api/v1/auth/register")
    Call<AuthenticationResponse> register(@Body RegisterRequest request);
}
