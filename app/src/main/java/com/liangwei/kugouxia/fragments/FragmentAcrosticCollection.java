package com.liangwei.kugouxia.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.liangwei.kugouxia.R;

public class FragmentAcrosticCollection extends Fragment {
    @BindView(R.id.fragment_acrostic_collection_text)
    TextView textView;
    public FragmentAcrosticCollection() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_acrostic_collection,container,false);
        ButterKnife.bind(this, view);
         Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.text_rotate);
        textView.setAnimation(animation);
        textView.startAnimation(animation);
        return view;
    }


}
