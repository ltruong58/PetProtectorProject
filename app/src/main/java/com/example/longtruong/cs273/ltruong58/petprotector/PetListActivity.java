package com.example.longtruong.cs273.ltruong58.petprotector;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class PetListActivity extends AppCompatActivity {

    private ImageView petImageView;
    private static final int REQUEST_CODE = 100;

    // This member variable store the URI to whatever image has been selected
    // Defaukt: none.png (R.drawable.none)
    private Uri imageURI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        petImageView = (ImageView) findViewById(R.id.petImageView);

        // Constructs a full URI to any Android resource
        imageURI = getUriToResource(this, R.drawable.none);

        // Set the imageURI of the ImageView
        petImageView.setImageURI(imageURI);
    }

    public void selectPetImage(View view) {
        ArrayList<String> permList = new ArrayList<>();

        // Start by seeing if we have permission to camera
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(cameraPermission != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.CAMERA);

        int readExtStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(readExtStoragePermission != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        int writeExtStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(writeExtStoragePermission != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);


        // If the list have items
        if(permList.size() > 0)
        {
            String[] perms = new String[permList.size()];


            ActivityCompat.requestPermissions(this, permList.toArray(perms), REQUEST_CODE);


        }
        if(cameraPermission == PackageManager.PERMISSION_GRANTED
                && readExtStoragePermission == PackageManager.PERMISSION_GRANTED
                && writeExtStoragePermission == PackageManager.PERMISSION_GRANTED)
        {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, REQUEST_CODE);
        }
        else Toast.makeText(this, "Pet Protector requires permission",
                Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Code to handle when the user closes the image gallery(by selecting at image or pressing back button)

        // The Intent data is the URI selected from image gallery
        // Decide if the user selected an image
        if(data != null && requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            // set imageURI to the data
            imageURI = data.getData();
            petImageView.setImageURI(imageURI);
        }
    }


    public static Uri getUriToResource(@NonNull Context context, @AnyRes int resId) throws Resources.NotFoundException {
        Resources res = context.getResources();

        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId)
                + '/' + res.getResourceTypeName(resId)
                + '/' + res.getResourceEntryName(resId));
    }

}
