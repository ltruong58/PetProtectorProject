package com.example.longtruong.cs273.ltruong58.petprotector;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Show details activity for a pet that picked from the list view in PetListActivity
 */
public class PetDetailsActivity extends AppCompatActivity {

    private TextView petNameTextView;
    private TextView petDetailsTextView;
    private TextView petPhoneTextView;
    private ImageView petImageView;

    /**
     * Create the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        petNameTextView = (TextView) findViewById(R.id.nameDetailsTextView);
        petDetailsTextView = (TextView) findViewById(R.id.petDetailsTextView);
        petPhoneTextView = (TextView) findViewById(R.id.phoneDetailsTextView);
        petImageView = (ImageView) findViewById(R.id.petDetailsImageView);

        Intent intentFromPetListActivity = getIntent();

        String name = intentFromPetListActivity.getStringExtra("Name");
        String details = intentFromPetListActivity.getStringExtra("Details");
        String phone = intentFromPetListActivity.getStringExtra("Phone");
        String imageUri = intentFromPetListActivity.getStringExtra("ImageUri");

        petNameTextView.setText(name);
        petDetailsTextView.setText(details);
        petPhoneTextView.setText(phone);
        petImageView.setImageURI(Uri.parse(imageUri));
    }
}
