package com.izaguirre.mascotas.frag;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ActionMenuView;
import androidx.fragment.app.Fragment;

import com.izaguirre.mascotas.R;
import com.izaguirre.mascotas.actividad.MainActivity;

public class BasicoFrag extends Fragment {

    private Activity prinAct;
    private ActionMenuView mAcitionMenuView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (prinAct == null){
            prinAct = getActivity();
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        mAcitionMenuView = (ActionMenuView) prinAct.findViewById(R.id.actionMV);
        if(!MainActivity.getInflated()) {
            inflater.inflate(R.menu.top_menu, mAcitionMenuView.getMenu());
            MainActivity.setInflated(true);
        }
    }


}
