package com.example.shaker.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shaker.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentResults#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentResults extends Fragment {

    private TextView xTextView, yTextView, zTextView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public static FragmentResults newInstance(String param1, String param2) {
        FragmentResults fragment = new FragmentResults();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentResults() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        xTextView = view.findViewById(R.id.xTextView);
        yTextView = view.findViewById(R.id.yTextView);
        zTextView = view.findViewById(R.id.zTextView);
        return view;
    }

    public void setXYZ(float x, float y, float z) {
        xTextView.setText("x: " + x);
        yTextView.setText("y: " + y);
        zTextView.setText("z: " + z);
    }
}