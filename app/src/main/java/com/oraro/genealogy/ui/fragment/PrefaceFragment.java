package com.oraro.genealogy.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oraro.genealogy.R;
import com.oraro.genealogy.ui.activity.MainActivity;
import com.oraro.genealogy.ui.view.smartTab.utilsV4.FragmentPagerItem;

/**
 * Created by Administrator on 2016/10/9.
 */

public class PrefaceFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("PrefaceFragment", "onCreateView");
        return inflater.inflate(R.layout.fragment_preface, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("PrefaceFragment", "onViewCreated..."+getArguments());
        int position = FragmentPagerItem.getPosition(getArguments());
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(" "+this.getClass().getSimpleName());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        ((MainActivity) getActivity()).setActionBarTitle(getActivity().getString(R.string.preface));
    }
}
