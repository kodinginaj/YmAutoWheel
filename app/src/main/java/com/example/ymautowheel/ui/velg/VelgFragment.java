package com.example.ymautowheel.ui.velg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ymautowheel.R;

public class VelgFragment extends Fragment {
    private VelgViewModel velgViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        velgViewModel =
                ViewModelProviders.of(this).get(VelgViewModel.class);
        View root = inflater.inflate(R.layout.fragment_velg, container, false);
        final TextView textView = root.findViewById(R.id.text_velg);
        velgViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
