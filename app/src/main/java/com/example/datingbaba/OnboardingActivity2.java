package com.example.datingbaba;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OnboardingActivity2 extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText editHobbies, editInstagram, editBio;
    private ImageView imageProfile;
    private Button btnUploadPhoto, btnFinish;
    private Uri imageUri;
    private String profilePicUrl = "";

    // Firebase references
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private StorageReference storageRef;

    // Data passed from part one
    private String name, branch, year, birthDate, gender, socializeWith, lookingFor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relativelayout3); // Ensure this matches your XML file name

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference("profile_pics");

        editHobbies = findViewById(R.id.edit_hobbies);
        editInstagram = findViewById(R.id.edit_instagram);
        editBio = findViewById(R.id.edit_bio);
        imageProfile = findViewById(R.id.image_profile);
        btnUploadPhoto = findViewById(R.id.btn_upload_photo);
        btnFinish = findViewById(R.id.btn_finish);

        // Retrieve data from part one
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        branch = intent.getStringExtra("branch");
        year = intent.getStringExtra("year");
        birthDate = intent.getStringExtra("birthDate");
        gender = intent.getStringExtra("gender");
        socializeWith = intent.getStringExtra("socializeWith");
        lookingFor = intent.getStringExtra("lookingFor");

        btnUploadPhoto.setOnClickListener(v -> openFileChooser());
        btnFinish.setOnClickListener(v -> finishOnboarding());
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageProfile.setImageURI(imageUri);
            uploadImage();
        }
    }

    private void uploadImage() {
        if(imageUri != null) {
            StorageReference fileRef = storageRef.child(mAuth.getCurrentUser().getUid() + ".jpg");
            fileRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot ->
                            fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                profilePicUrl = uri.toString();
                                Toast.makeText(OnboardingActivity2.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                            })
                    )
                    .addOnFailureListener(e -> Toast.makeText(OnboardingActivity2.this, "Upload Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void finishOnboarding() {
        // Get hobbies and Instagram details
        String hobbies = editHobbies.getText().toString().trim();
        String instagram = editInstagram.getText().toString().trim();
        String bio = editBio.getText().toString().trim();

        UserProfile userProfile = new UserProfile();
        userProfile.setFullName(name);
        userProfile.setGender(gender);
        userProfile.setProfilePicUrl(profilePicUrl);
        userProfile.setDob(birthDate);
        userProfile.setSocializeWith(socializeWith);
        userProfile.setLookingFor(lookingFor);
        userProfile.setBranch(branch);
        userProfile.setYear(year);
        userProfile.setHobbies(hobbies);
        userProfile.setInsta(instagram);
        userProfile.setBio(bio);

        // Save the user profile to Firestore under "users/{userId}"
        String userId = mAuth.getCurrentUser().getUid();
        db.collection("users").document(userId)
                .set(userProfile)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(OnboardingActivity2.this, "Profile Saved Successfully!", Toast.LENGTH_SHORT).show();
                    // Redirect to HomeActivity (create one as your main app screen)
                    startActivity(new Intent(OnboardingActivity2.this, HomeActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(OnboardingActivity2.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
