package com.example.first.Account;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.first.Profile;

public class AccountViewModel extends AndroidViewModel {
    private MediatorLiveData<ProfileData> userProfile = new MediatorLiveData<>();
    private MediatorLiveData<Bitmap> userImage = new MediatorLiveData<>();

    public LiveData<ProfileData> getProfileData() {
        return userProfile;
    }

    public LiveData<Bitmap> getUserImage() {
        return userImage;
    }

    public AccountViewModel(@NonNull Application application) {
        super(application);

        LiveData profileLiveData = AccountCash.getInstance().getAccountRepo().getProfile();
        userProfile.addSource(profileLiveData, new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                ProfileData profileData = new ProfileData();
                profileData.setName(profile.getName());
                profileData.setBreed(profile.getBreed());
                profileData.setCity(profile.getCity());

                profileData.setAge(profile.getAge() + " y.o");
                String str = profile.getPhone();
                String phone;
                if (str.length() == 11) {
                    phone = str.charAt(0) + " ("
                            + str.charAt(1) + str.charAt(2) + str.charAt(3) + ") - "
                            + str.charAt(4) + str.charAt(5) + str.charAt(6) + " - "
                            + str.charAt(7) + str.charAt(8) + " - "
                            + str.charAt(9) + str.charAt(10);
                }
                else
                    phone = str;

                profileData.setPhone(phone);

                userProfile.postValue(profileData);
            }
        });

        LiveData imageLiveData = AccountCash.getInstance().getAccountRepo().getImage();
        userImage.addSource(imageLiveData, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                userImage.postValue(bitmap);
            }
        });
    }

    public void exit() {
        AccountCash.getInstance().getAccountRepo().exit();
    }

    class ProfileData {
        private String name;
        private String phone;
        private String breed;
        private String age;
        private String city;

        public ProfileData(String name, String phone, String breed, String age, String city) {
            this.name = name;
            this.phone = phone;
            this.breed = breed;
            this.age = age;
            this.city = city;
        }

        public  ProfileData() {
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }

        public String getBreed() {
            return breed;
        }

        public String getAge() {
            return age;
        }

        public String getCity() {
            return city;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setBreed(String breed) {
            this.breed = breed;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}
