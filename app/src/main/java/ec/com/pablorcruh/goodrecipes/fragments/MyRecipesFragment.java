package ec.com.pablorcruh.goodrecipes.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.crashlytics.android.Crashlytics;

import ec.com.pablorcruh.goodrecipes.R;

public class MyRecipesFragment extends Fragment {

    public static final String TAG = MyRecipesFragment.class.getName();

    public MyRecipesFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try{
            return inflater.inflate(R.layout.fragment_my_recipes, container, false);
        }catch(Exception e){
            Crashlytics.log(e.getMessage());
            Log.e(TAG, "onCreateView: ", e);
            return null;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{


        }catch(Exception e){
            Crashlytics.log(e.getMessage());
            Log.e(TAG, "onCreate: ", e);
        }
    }
}
