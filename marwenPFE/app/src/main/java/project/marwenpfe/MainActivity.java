package project.marwenpfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import project.marwenpfe.Auth.InscriptionActivity;
import project.marwenpfe.Technicien.TechnicienDashboardActivity;
import project.marwenpfe.admin.AdminDashboardActivity;
import project.marwenpfe.model.User;
import project.marwenpfe.request.AuthenticationRequest;
import project.marwenpfe.services.AuthService;
import project.marwenpfe.utils.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.pwd);

        Button loginButton = findViewById(R.id.connexion);
        TextView inscriptionLink = findViewById(R.id.registerlink);
        TextView forgetPassLink = findViewById(R.id.forgetpass);
        AuthService authService = ApiService.getRetrofit().create(AuthService.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(authService);
            }
        });
        forgetPassLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to the registration activity
                //startActivity(new Intent(MainActivity.this, ForgetPasswordActivity.class));
            }
        });
        inscriptionLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to the registration activity
                startActivity(new Intent(MainActivity.this, InscriptionActivity.class));
            }
        });
    }

    private void loginUser(AuthService authService) {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, password);

        Call<User> call = authService.authenticate(authenticationRequest);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Authentication successful
                    User user = response.body();
                    if (user != null) {
                        // Show a success Toast
                        showSuccessToast();
                        Log.d("MainActivity", "user: " + user.getId());

                        // Check for null before accessing the ID
                      if (user.getId() != null) {
                            // Store the user ID in SharedPreferences
                            SharedPreferences sp = getSharedPreferences("mypref", MODE_PRIVATE);
                            SharedPreferences.Editor e = sp.edit();
                            int id = user.getId();
                            e.putInt("userId", id);
                           e.commit(); // Use commit() for immediate changes

                            // Redirect based on user role
                            redirectToDashboard(user.getRole());
                        } else {
                            // Handle the case where the user ID is null
                            Log.e("MainActivity", "User ID is null in the response.");
                            // Show an error message if needed
                      }
                    } else {
                        // Authentication failed
                        // Log the error message
                        Log.e("MainActivity", "Authentication failed. Error code: " + response.code());

                        // Show an error message if needed
                    }
                } else {
                    // Handle the case where the response is not successful
                    Log.e("MainActivity", "Authentication response is not successful. Error code: " + response.code());
                    // Show an error message if needed
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Network or other errors
                // Show an error message if needed
                Log.e("MainActivity", "Authentication failed. Error: " + t.getMessage());
            }
        });
    }

    private void showSuccessToast() {
        // Display a Toast message for successful login
        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
    }

    private void redirectToDashboard(String role) {
        Intent intent;

        if ("ADMIN".equals(role)) {
            intent = new Intent(MainActivity.this, AdminDashboardActivity.class);
        } else if ("Technicien".equals(role)) {
            intent = new Intent(MainActivity.this, TechnicienDashboardActivity.class);
        } else {
            // Handle other roles or unexpected values
            // You can show an error message or redirect to a common dashboard
            // For now, redirect to a common dashboard (MainActivity)
            intent = new Intent(MainActivity.this, MainActivity.class);
        }

        // Add the FLAG_ACTIVITY_CLEAR_TOP flag to clear the activity stack
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Start the dashboard activity
        startActivity(intent);
    }
}