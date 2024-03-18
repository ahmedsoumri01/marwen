package project.marwenpfe.Auth;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import project.marwenpfe.MainActivity;
import project.marwenpfe.R;
import project.marwenpfe.request.RegisterRequest;
import project.marwenpfe.response.AuthenticationResponse;
import project.marwenpfe.services.AuthService;
import project.marwenpfe.utils.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InscriptionActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmpass);

        TextView loginLink = findViewById(R.id.loginlink);
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to the login activity
                startActivity(new Intent(InscriptionActivity.this, MainActivity.class));
            }
        });

        Button registerButton = findViewById(R.id.enregistrerbtn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Log the input values to check if they are received correctly
        Log.d("Registration", "Username: " + username);
        Log.d("Registration", "Email: " + email);
        Log.d("Registration", "Password: " + password);
        Log.d("Registration", "Confirm Password: " + confirmPassword);

        if (!password.equals(confirmPassword)) {
            // Passwords do not match, show an error
            showErrorAlert("Passwords do not match!");
            return;
        }

        AuthService authService = ApiService.getRetrofit().create(AuthService.class);

        RegisterRequest registerRequest = new RegisterRequest(username, email, password);

        Call<AuthenticationResponse> call = authService.register(registerRequest);
        call.enqueue(new Callback<AuthenticationResponse>() {
            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                if (response.isSuccessful()) {
                    // Registration successful
                    AuthenticationResponse authenticationResponse = response.body();
                    Log.d("Registration", "Registration successful!");

                    // Show a success alert
                    showSuccessAlert();
                } else {
                    // Registration failed
                    // Show an error message
                    showErrorAlert("Registration failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                // Network or other errors
                // Show an error message
                showErrorAlert("Registration failed: " + t.getMessage());
            }
        });
    }

    private void showSuccessAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registration Successful")
                .setMessage("You have successfully registered.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Clear the input fields
                        usernameEditText.setText("");
                        emailEditText.setText("");
                        passwordEditText.setText("");
                        confirmPasswordEditText.setText("");
                    }
                })
                .show();
    }


    private void showErrorAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", null) // No action for OK button
                .show();
    }
}
