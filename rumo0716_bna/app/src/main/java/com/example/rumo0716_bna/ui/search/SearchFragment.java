package com.example.rumo0716_bna.ui.search;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.rumo0716_bna.MainActivity;
import com.example.rumo0716_bna.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SearchFragment extends Fragment implements View.OnClickListener{

    Button btnSearch;
    EditText editSearch;

    public static Editable tex;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        btnSearch = root.findViewById(R.id.button);
        editSearch = root.findViewById(R.id.textView);
        //  editSearch.setText("");

        tex=editSearch.getText();
        btnSearch.setOnClickListener(this);
        View root1 = root;



        return root1;

    }


    @Override
    public void onClick(View root) {
        if (!editSearch.getText().toString().isEmpty()){

            Navigation.findNavController(root).navigate(R.id.action_navigation_search_to_fragment_search_result);
        }
        else{
            Toast.makeText(getContext(),"請輸入文字",Toast.LENGTH_SHORT).show();
        }

    }




}
