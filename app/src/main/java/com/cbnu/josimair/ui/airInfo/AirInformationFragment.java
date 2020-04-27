package com.cbnu.josimair.ui.airInfo;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.cbnu.josimair.Model.IndoorAir;
import com.cbnu.josimair.ui.MainActivity;
import com.cbnu.josimair.R;

public class AirInformationFragment extends Fragment {

    private AirInformationViewModel airInformationViewModel;
    private TextView airInfoTextView;
    private TextView airQualityTextView;

    private ImageView faceImageView;
    private ImageView dustImageView;
    private ImageView microdustImageView;
    private ImageView NO2ImageView;

    private TextView dustTextView;
    private TextView microDustTextView;
    private TextView no2TextView;
    private TextView mangNameTextView;
    private TextView dateTextView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        airInformationViewModel =
                ViewModelProviders.of(this).get(AirInformationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_airinformation, container, false);
        airInfoTextView = (TextView) root.findViewById(R.id.airInfoTextView);
        airQualityTextView = (TextView) root.findViewById(R.id.airQualityTextView);

        faceImageView = (ImageView) root.findViewById(R.id.air_face);
        dustImageView = (ImageView) root.findViewById(R.id.dust);
        microdustImageView = (ImageView) root.findViewById(R.id.micro_dust);
        NO2ImageView = (ImageView) root.findViewById(R.id.NO2);

        dustTextView = (TextView) root.findViewById(R.id.dustValueTextView);
        microDustTextView = (TextView) root.findViewById(R.id.microDustValueTextView);
        no2TextView = (TextView) root.findViewById(R.id.No2ValueTextView);
        mangNameTextView = (TextView) root.findViewById(R.id.mangName);
        dateTextView = (TextView) root.findViewById(R.id.date);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        ViewGroup.LayoutParams face_params = (ViewGroup.LayoutParams)  faceImageView.getLayoutParams();
        ViewGroup.LayoutParams dust_params = (ViewGroup.LayoutParams)  dustImageView.getLayoutParams();
        ViewGroup.LayoutParams microdust_params = (ViewGroup.LayoutParams)  microdustImageView.getLayoutParams();
        ViewGroup.LayoutParams NO2_params = (ViewGroup.LayoutParams)  NO2ImageView.getLayoutParams();

        face_params.height = metrics.heightPixels/5;
        face_params.width = face_params.height;

        dust_params.height = metrics.heightPixels/15;
        dust_params.width = dust_params.height;

        microdust_params.height = metrics.heightPixels/15;
        microdust_params.width = dust_params.height;

        NO2_params.height = metrics.heightPixels/15;
        NO2_params.width = dust_params.height;

        updateIndoorAirInfo(MainActivity.indoorAir);
        updateOutdoorAirInfo();

        MainActivity.fragment = this;
        return root;
    }

    public void updateIndoorAirInfo(final IndoorAir indoorAir){
        airInformationViewModel.updateAirInfo(airInfoTextView, airQualityTextView, indoorAir, faceImageView);
    }

    public void updateOutdoorAirInfo(){
        airInformationViewModel.updateOutdoorAirInfo(dustTextView, microDustTextView, no2TextView,mangNameTextView, dateTextView, MainActivity.outdoorAir);
    }
}