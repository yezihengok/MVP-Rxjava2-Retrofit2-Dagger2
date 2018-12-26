package com.demo.mvp_dagger2.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.demo.mvp.R;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class Fragment01 extends Fragment {

    private View mView;

    private TextView tv;

    @Inject
    Student mStudent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.activity_dag, container, false);
        Log.d("hbj--",mStudent.toString());
        tv=(TextView)mView.findViewById(R.id.texts);
        tv.setText(mStudent.getName());
        return mView;
    }


}
