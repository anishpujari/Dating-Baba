package com.example.datingbaba;

import java.util.List;

public class UserProfile {
    private String userId, fullName, gender, socializeWith, lookingFor, dob, interests, bio, branch, profilePicUrl, year, hobbies, insta;
    private long lastOnline;
    private int likesCount, matchesCount;
    private List<String> likedUsers, matchedUsers; // Store User IDs of liked/matched users

    public UserProfile() {
        // Required empty constructor for Firebase
    }

    public UserProfile(String userId, String fullName, String gender, String socializeWith, String lookingFor, String dob,
                       String interests, String bio, String branch, String profilePicUrl, String year,String hobbies, String insta,
                       long lastOnline, int likesCount, int matchesCount, List<String> likedUsers, List<String> matchedUsers) {
        this.userId = userId;
        this.fullName = fullName;
        this.gender = gender;
        this.socializeWith = socializeWith;
        this.lookingFor = lookingFor;
        this.dob = dob;
        this.interests = interests;
        this.bio = bio;
        this.branch = branch;
        this.profilePicUrl = profilePicUrl;
        this.year = year;
        this.hobbies = hobbies;
        this.insta = insta;
        this.lastOnline = lastOnline;
        this.likesCount = likesCount;
        this.matchesCount = matchesCount;
        this.likedUsers = likedUsers;
        this.matchedUsers = matchedUsers;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getSocializeWith() { return socializeWith; }
    public void setSocializeWith(String socializeWith) { this.socializeWith = socializeWith; }

    public String getLookingFor() { return lookingFor; }
    public void setLookingFor(String lookingFor) { this.lookingFor = lookingFor; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getInterests() { return interests; }
    public void setInterests(String interests) { this.interests = interests; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public String getProfilePicUrl() { return profilePicUrl; }
    public void setProfilePicUrl(String profilePicUrl) { this.profilePicUrl = profilePicUrl; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }
    public String getHobbies() { return hobbies; }
    public void setHobbies(String hobbies) { this.hobbies = hobbies; }

    public String getInsta() { return insta; }
    public void setInsta(String insta) { this.insta = insta; }

    public long getLastOnline() { return lastOnline; }
    public void setLastOnline(long lastOnline) { this.lastOnline = lastOnline; }

    public int getLikesCount() { return likesCount; }
    public void setLikesCount(int likesCount) { this.likesCount = likesCount; }

    public int getMatchesCount() { return matchesCount; }
    public void setMatchesCount(int matchesCount) { this.matchesCount = matchesCount; }

    public List<String> getLikedUsers() { return likedUsers; }
    public void setLikedUsers(List<String> likedUsers) { this.likedUsers = likedUsers; }

    public List<String> getMatchedUsers() { return matchedUsers; }
    public void setMatchedUsers(List<String> matchedUsers) { this.matchedUsers = matchedUsers; }
}
