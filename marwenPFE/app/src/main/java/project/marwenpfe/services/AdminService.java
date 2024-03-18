package project.marwenpfe.services;

import java.util.List;

import project.marwenpfe.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AdminService {  @GET("api/v1/admin/getMyProfile/{id}")
Call<User> getAdminInformation(@Path("id") int adminId);

    @GET("api/v1/admin/allusers")
    Call<List<User>> getAllUsers();


    @DELETE("api/v1/admin/{id}")
    Call<Void> deleteUser(@Path("id") int userId);

    @PUT("api/v1/admin/update-user/{id}")
    Call<Void> updateUser(@Path("id") int userId, @Body User user);
}
