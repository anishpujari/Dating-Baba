package com.example.datingbaba;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    // UI Elements
    private TextView matchName, matchInfo, matchGender, matchInstagram, matchBio, matchHobbies, matchLookingFor;
    private ImageView matchProfilePic;
    private Button btnChat, btnPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Ensure this layout is created

        // Initialize Firebase Firestore and Auth
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Bind UI elements
        matchName = findViewById(R.id.nameTextView);
        matchInfo = findViewById(R.id.yearBranchTextView);
        matchProfilePic = findViewById(R.id.myImageView);
        matchGender = findViewById(R.id.genderTextView);
        matchInstagram = findViewById(R.id.instagramTextView);
        matchBio = findViewById(R.id.interestsTextView);
        matchHobbies = findViewById(R.id.hobbiesTextView);
        matchLookingFor = findViewById(R.id.lookingForTextView);
        btnChat = findViewById(R.id.likeButton);
        btnPass = findViewById(R.id.passButton);

        // Load a match for the current user
        loadMatch();

        // Optional: Set click listeners for Chat and Pass buttons


        btnPass.setOnClickListener(v -> {
            // Load a new match when user passes on the current one
            loadMatch();
        });
    }

    private void loadMatch() {
        String currentUserId = auth.getCurrentUser().getUid();
        // First, retrieve current user's profile to get their gender preferences
        db.collection("users").document(currentUserId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Assume "socializeWith" is stored as a comma-separated string e.g., "Men,Women"
                        String socializeWith = documentSnapshot.getString("socializeWith");
                        List<String> preferredGenders = new ArrayList<>();
                        if (socializeWith != null && !socializeWith.isEmpty()) {
                            String[] parts = socializeWith.split(",");
                            for (String s : parts) {
                                preferredGenders.add(s.trim());
                            }
                        } else {
                            Toast.makeText(HomeActivity.this, "No gender preference set.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Query Firestore for users whose "gender" is in the preferredGenders list
                        db.collection("users")
                                .whereIn("gender", preferredGenders)
                                .get()
                                .addOnSuccessListener(queryDocumentSnapshots -> {
                                    List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                                    // Exclude the current user from the results
                                    Iterator<DocumentSnapshot> iterator = docs.iterator();
                                    while (iterator.hasNext()) {
                                        DocumentSnapshot doc = iterator.next();
                                        if (doc.getId().equals(currentUserId)) {
                                            iterator.remove();
                                        }
                                    }

                                    if (docs.size() > 0) {
                                        // Pick a random match from the remaining documents
                                        int randomIndex = new Random().nextInt(docs.size());
                                        DocumentSnapshot matchDoc = docs.get(randomIndex);

                                        // Extract data from matchDoc
                                        String matchFullName = matchDoc.getString("fullName");
                                        String matchBranch = matchDoc.getString("branch");
                                        String matchYear = matchDoc.getString("year");
                                        String matchPicUrl = matchDoc.getString("profilePicUrl");
                                        String matchbio = matchDoc.getString("bio");
                                        String matchhobbies = matchDoc.getString("hobbies");
                                        String matchlookingfor = matchDoc.getString("lookingFor");
                                        String matchgender = matchDoc.getString("gender");
                                        String matchinstagram = matchDoc.getString("insta");

                                        // Set UI elements
                                        matchName.setText(matchFullName);
                                        matchGender.setText("Gender: " + matchgender);
                                        matchInfo.setText("Branch: " + matchBranch + "| Year: " + matchYear);
                                        matchInstagram.setText("Instagram: @" + matchinstagram);
                                        matchBio.setText(matchbio);
                                        matchHobbies.setText(matchhobbies);
                                        matchLookingFor.setText(matchlookingfor);

                                        // Load image using Glide (or any image loading library)
                                        Glide.with(HomeActivity.this)
                                                .load(matchPicUrl)
//                                                .placeholder(R.drawable.placeholder) // optional placeholder image
                                                .into(matchProfilePic);
                                        String matchInstagram = matchDoc.getString("insta");
                                        btnChat.setOnClickListener(v -> {
                                            // Assume you have retrieved the match's Instagram handle from Firestore and stored it in a variable.
                                            // For this example, let's say the variable is matchInstagram.
                                            if(matchInstagram == null || matchInstagram.isEmpty()){
                                                Toast.makeText(HomeActivity.this, "Instagram ID not available", Toast.LENGTH_SHORT).show();
                                                return;
                                            }

                                            // Build the URI for Instagram. This URI format opens the Instagram app directly.
                                            Uri uri = Uri.parse("http://instagram.com/_u/" + matchInstagram);
                                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                            intent.setPackage("com.instagram.android");

                                            try {
                                                startActivity(intent);
                                            } catch (android.content.ActivityNotFoundException e) {
                                                // If Instagram app is not installed, open the URL in the browser.
                                                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                                        Uri.parse("http://instagram.com/" + matchInstagram));
                                                startActivity(webIntent);
                                            }
                                        });

                                    } else {
                                        matchName.setText("No Match Found");
                                        matchInfo.setText("");
                                        matchProfilePic.setImageResource(R.drawable.placeholder);
                                    }
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(HomeActivity.this, "Error fetching match: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                                );
                    } else {
                        Toast.makeText(HomeActivity.this, "User profile not found.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(HomeActivity.this, "Error retrieving user data: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}
