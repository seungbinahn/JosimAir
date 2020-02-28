package com.cbnu.josimair.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cbnu.josimair.Communication;
import com.cbnu.josimair.DeviceListActivity;
import com.cbnu.josimair.MainBtmActivity;
import com.cbnu.josimair.Model.IndoorAir;
import com.cbnu.josimair.R;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private Communication communication;
    private TextView airInfoTextView;
    private TextView airQualityTextView;
    private Button btBtn;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        communication = MainBtmActivity.communication;
        btBtn = (Button) root.findViewById(R.id.btBtn);
        airInfoTextView = (TextView) root.findViewById(R.id.airInfoTextView);
        airQualityTextView = (TextView) root.findViewById(R.id.airQualityTextView);

        if(communication.enable()){
            btBtn.setText(R.string.bluetooth_enabled_btn);
        }else{
            btBtn.setText(R.string.bluetooth_connect_btn);
        }

        setCallback();
        return root;
    }

    public void setCallback(){
        btBtn.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        if(!communication.enable()) {
                            communication.showDialog();
                        }else{ // 여기서 블루투스 리스트에서 디바이스를 선택하여 연결
                            //Communication thread 시작
                            communication.start_Test_Using_RandomData();
                            //startActivityForResult(new Intent(v.getContext(), DeviceListActivity.class),Communication.RESULT_CODE_BTLIST);
                        }

                /*
                ArpltnInforInqireSvc svc = new ArpltnInforInqireSvc("서울");
                svc.setOnReceivedEvent(new ArpltnInforInqireSvc.ReceivedListener() {
                    @Override
                    public void onReceivedEvent(String xml) {
                        Log.v("result",xml);
                    }
                });
                svc.getAirInfo();
                 */
                    }
                }
        );

        communication.setReceivedCallback(new Communication.ReceivedListener() {
            @Override
            public void onReceivedEvent() {
                Log.i("HomeFragment","공기정보 수신");
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            homeViewModel.updateAirInfo(airInfoTextView,airQualityTextView,communication.getReceivedAir());
                        }
                    });
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}