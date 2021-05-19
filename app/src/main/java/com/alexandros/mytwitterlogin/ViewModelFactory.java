package com.alexandros.mytwitterlogin;


import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {
    String accessToken;
    String accessTokenSecret;
    public ViewModelFactory(String accessToken,String accessTokenSecret) {
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull  Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserDataViewModel.class)){
            return Objects.requireNonNull(modelClass.cast(new UserDataViewModel(accessToken, accessTokenSecret)));
        }
        return null;
    }
}
