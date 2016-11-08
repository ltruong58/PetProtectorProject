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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for input new pet and show it on the list view
 */
public class PetListActivity extends AppCompatActivity {

    private ImageView petImageView;
    private static final int REQUEST_CODE = 100;

    private DBHelper database;
    private List<Pet> petList;
    private PetListAdapter petListAdapter;

    private EditText petNameEditText;
    private EditText petDetailsEditText;
    private EditText petPhoneEditText;

    private ListView petListView;

    // This member variable store the URI to whatever image has been selected
    // Default: none.png (R.drawable.none)
    private Uri imageURI;

    /**
     * Create the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        petImageView = (ImageView) findViewById(R.id.petImageView);
        petListView = (ListView) findViewById(R.id.petListView);
        petNameEditText = (EditText) findViewById(R.id.nameListEditText);
        petDetailsEditText = (EditText) findViewById(R.id.petDetailsListEditText);
        petPhoneEditText = (EditText) findViewById(R.id.phoneListEditText);

        // Constructs a full URI to any Android resource
        imageURI = getUriToResource(this, R.drawable.none);

        // Set the imageURI of the ImageView
        petImageView.setImageURI(imageURI);

        // Make a DBHelper
        database = new DBHelper(this);
       // database.deleteAllPets();
        petList = database.getAllPets();

        // Create custom task list adapter
        // Associate the adapter with context, the layout and the List
        petListAdapter = new PetListAdapter(this, R.layout.pet_list_item, petList);

        // Associate the adapter w list view
        petListView.setAdapter(petListAdapter);

    }

    /**
     * select a pet image for gallery or camera
     * @param view
     */
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

    /**
     * update pet image view
     * @param requestCode
     * @param resultCode
     * @param data
     */
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

    /**
     * get Uri
     * @param context
     * @param resId
     * @return
     * @throws Resources.NotFoundException
     */
    public static Uri getUriToResource(@NonNull Context context, @AnyRes int resId) throws Resources.NotFoundException {
        Resources res = context.getResources();

        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId)
                + '/' + res.getResourceTypeName(resId)
                + '/' + res.getResourceEntryName(resId));
    }

    /**
     * add new pet to the list view
     * @param view
     */
    public void addPet(View view)
    {
        String name = petNameEditText.getText().toString();
        String details = petDetailsEditText.getText().toString();
        String phone = petPhoneEditText.getText().toString();

        if(name.isEmpty() || details.isEmpty() ){
            Toast.makeText(this, "Pet's name and details can't be empty.", Toast.LENGTH_SHORT).show();
        }
        else {
            Pet newPet = new Pet(name, details, phone, imageURI);
            database.askPet(newPet);
            petListAdapter.add(newPet);
            petNameEditText.setText("");
            petDetailsEditText.setText("");
            petPhoneEditText.setText("");
        }

    }

    /**
     * go to PetDetailsActivity when user click item on list view
     * @param view
     */
    public void viewPetDetails(View view)
    {
        if(view instanceof LinearLayout) {
            LinearLayout selectedLinearLayout = (LinearLayout) view;
            Pet selectedPet = (Pet) selectedLinearLayout.getTag();

            Intent detailsIntent = new Intent(this, PetDetailsActivity.class);

            detailsIntent.putExtra("Name", selectedPet.getName());
            detailsIntent.putExtra("Details",selectedPet.getDetails());
            detailsIntent.putExtra("Phone", selectedPet.getPhone());
            detailsIntent.putExtra("ImageUri",selectedPet.getImageURI().toString());
            startActivity(detailsIntent);
        }
    }

}
