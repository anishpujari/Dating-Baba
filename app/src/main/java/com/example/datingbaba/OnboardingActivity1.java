package com.example.datingbaba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class OnboardingActivity1 extends AppCompatActivity {

    private EditText editName;
    private Spinner spinnerBranch, spinnerYear;
    private DatePicker datePicker;
    private RadioGroup radioGroupGender;
    private CheckBox checkMen, checkWomen, checkOthers;
    private CheckBox checkCasual, checkLongTerm, checkFriends, checkNetworking, checkStudyBuddy, checkActivityPartner;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relativelayout); // Ensure this layout name matches your XML

        // Initialize views
        editName = findViewById(R.id.editName);
        spinnerBranch = findViewById(R.id.spinnerBranch);
        spinnerYear = findViewById(R.id.spinnerYear);
        datePicker = findViewById(R.id.datePicker);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        checkMen = findViewById(R.id.checkMen);
        checkWomen = findViewById(R.id.checkWomen);
        checkOthers = findViewById(R.id.checkOthers);
        checkCasual = findViewById(R.id.checkCasual);
        checkLongTerm = findViewById(R.id.checkLongTerm);
        checkFriends = findViewById(R.id.checkFriends);
        checkNetworking = findViewById(R.id.checkNetworking);
        checkStudyBuddy = findViewById(R.id.checkStudyBuddy);
        checkActivityPartner = findViewById(R.id.checkActivityPartner);
        btnNext = findViewById(R.id.btn_next);

        String yearOptions[] = {"First Year", "Second Year", "Third Year", "Fourth Year"};
        String branchOptions[] = {"CSE", "IT", "ECE", "EEE", "MECH", "CIVIL", "ENCS", "AIML", "DS", "Cyber"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yearOptions);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, branchOptions);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBranch.setAdapter(adapter2);

        btnNext.setOnClickListener(v -> goToPartTwo());
    }

    private void goToPartTwo() {



        String name = editName.getText().toString().trim();
        String branch = spinnerBranch.getSelectedItem().toString();
        String year = spinnerYear.getSelectedItem().toString();

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int yearDate = datePicker.getYear();
        String birthDate = day + "/" + month + "/" + yearDate;

        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        RadioButton selectedGenderButton = findViewById(selectedGenderId);
        String gender = selectedGenderButton.getText().toString();

        StringBuilder socializeWithBuilder = new StringBuilder();
        if (checkMen.isChecked()) socializeWithBuilder.append("Male,");
        if (checkWomen.isChecked()) socializeWithBuilder.append("Female,");
        if (checkOthers.isChecked()) socializeWithBuilder.append("Others,");
        String socializeWith = (socializeWithBuilder.length() > 0)
                ? socializeWithBuilder.substring(0, socializeWithBuilder.length() - 1)
                : "";

        StringBuilder lookingForBuilder = new StringBuilder();
        if (checkCasual.isChecked()) lookingForBuilder.append("Casual Dating,");
        if (checkLongTerm.isChecked()) lookingForBuilder.append("Long-term Relationship,");
        if (checkFriends.isChecked()) lookingForBuilder.append("Just Friends,");
        if (checkNetworking.isChecked()) lookingForBuilder.append("Professional Networking,");
        if (checkStudyBuddy.isChecked()) lookingForBuilder.append("Study Buddy,");
        if (checkActivityPartner.isChecked()) lookingForBuilder.append("Activity Partner,");
        String lookingFor = (lookingForBuilder.length() > 0)
                ? lookingForBuilder.substring(0, lookingForBuilder.length() - 1)
                : "";

        Intent intent = new Intent(OnboardingActivity1.this, OnboardingActivity2.class);
        intent.putExtra("name", name);
        intent.putExtra("branch", branch);
        intent.putExtra("year", year);
        intent.putExtra("birthDate", birthDate);
        intent.putExtra("gender", gender);
        intent.putExtra("socializeWith", socializeWith);
        intent.putExtra("lookingFor", lookingFor);
        startActivity(intent);
    }
}
