package com.example.ymautowheel.ui.ban;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ymautowheel.R;
import com.example.ymautowheel.adapter.AdapterMerekBan;
import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.MerekBanModel;
import com.example.ymautowheel.model.ResponseModelBan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BanFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) { ;
        View root = inflater.inflate(R.layout.fragment_ban, container, false);

        return root;
    }
}