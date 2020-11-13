package com.example.rumo0716_bna.ui.analysis;

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

import com.example.rumo0716_bna.R;

public class AnalysisFragment extends Fragment {

    private AnalysisViewModel analysisViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        analysisViewModel =
                ViewModelProviders.of(this).get(AnalysisViewModel.class);
        View root = inflater.inflate(R.layout.fragment__analysis, container, false);

        return root;
    }
}
