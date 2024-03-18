package project.marwenpfe.Technicien;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import project.marwenpfe.MainActivity;
import project.marwenpfe.R;

public class TechnicienDashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        CardView userProfileCard = findViewById(R.id.userProfileCard);
        CardView userPointageCard = findViewById(R.id.checkPointageCard);
        CardView logoutCard = findViewById(R.id.logoutCard);
        // Set OnClickListener for the userProfileCard
        userProfileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to UserProfileActivity
                //startActivity(new Intent(UserDashboardActivity.this, UserProfileActivity.class));
            }
        });

        // Set OnClickListener for the userProfileCard
        userPointageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to UserProfileActivity
                //startActivity(new Intent(UserDashboardActivity.this, UserPointageActivity.class));
            }
        });
        // Set OnClickListener for the logoutCard
        logoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle logout
                logoutUser();
            }
        });
    }
    private void logoutUser() {
        // Clear the user ID from SharedPreferences
        SharedPreferences sp = getSharedPreferences("mypref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("userId");
        editor.apply();

        // Redirect to MainActivity
        startActivity(new Intent(TechnicienDashboardActivity.this, MainActivity.class));

        // Show a Toast message for successful logout
        Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
        // Finish the current activity
        finish();
    }
}
