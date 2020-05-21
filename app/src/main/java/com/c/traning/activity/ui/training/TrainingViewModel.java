package com.c.traning.activity.ui.training;

import android.util.Log;

import com.c.traning.model.TrainingModel;
import com.c.traning.rest.ApiConfig;
import com.c.traning.rest.ApiService;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainingViewModel extends ViewModel {

    public String TAG = "retrofit";
    private MutableLiveData<List<TrainingModel>> trainingModelMutableLiveData;

    public LiveData<List<TrainingModel>> getTraining() {
        if (trainingModelMutableLiveData == null) {
            trainingModelMutableLiveData = new MutableLiveData<List<TrainingModel>>();

            loadDataTraining();
        }
        return trainingModelMutableLiveData;
    }

    private void loadDataTraining() {
        ApiService apiService = ApiConfig.getApiService();
        apiService.getSatusTraining("aktif")
                .enqueue(new Callback<List<TrainingModel>>() {
                    @Override
                    public void onResponse(Call<List<TrainingModel>> call, Response<List<TrainingModel>> response) {
                        if (response.isSuccessful()) {
                            trainingModelMutableLiveData.setValue(response.body());
                            Log.d(TAG, "onResponse: " +response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TrainingModel>> call, Throwable t) {
                        Log.d(TAG, "onFailure: " +t.getLocalizedMessage() + t.getMessage());
                    }
                });
    }

}