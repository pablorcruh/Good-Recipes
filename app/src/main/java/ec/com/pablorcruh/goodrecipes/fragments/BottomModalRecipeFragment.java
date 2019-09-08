package ec.com.pablorcruh.goodrecipes.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.crashlytics.android.Crashlytics;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

import ec.com.pablorcruh.goodrecipes.R;
import ec.com.pablorcruh.goodrecipes.activities.EditRecipe;
import ec.com.pablorcruh.goodrecipes.common.MyApp;
import ec.com.pablorcruh.goodrecipes.constants.Constants;
import ec.com.pablorcruh.goodrecipes.firebase.CallbackGetRecipe;
import ec.com.pablorcruh.goodrecipes.model.Recipe;
import ec.com.pablorcruh.goodrecipes.viewmodel.MainViewModel;

public class BottomModalRecipeFragment extends BottomSheetDialogFragment {

    private MainViewModel mainViewModel;
    private String recipeIdSelected;
    private static final String TAG= BottomModalRecipeFragment.class.getName();

    public static BottomModalRecipeFragment newInstance(String recipeId) {
        BottomModalRecipeFragment fragment = new BottomModalRecipeFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_RECIPE_ID, recipeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if(getArguments()!=null){
                recipeIdSelected = getArguments().getString(Constants.ARG_RECIPE_ID);
            }
        }catch (Exception e){
            Crashlytics.log(e.getMessage());
            Log.e(TAG, "onCreate: ", e);
        }
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try{
            final View view = inflater.inflate(R.layout.fragment_bottom_modal_recipe, container, false);
            final NavigationView nav = view.findViewById(R.id.navigation_view_bottom_recipe);
            nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    int id = menuItem.getItemId();
                    switch(id){
                        case R.id.menu_action_delete_recipe:
                            mainViewModel.deleteRecipe(recipeIdSelected);
                            getDialog().dismiss();
                            return true;
                    }
                    return false;
                }
            });
            return view;
        }catch(Exception e){
            Crashlytics.log(e.getMessage());
            Log.e(TAG, "onCreateView: ", e);
            return null;
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

}
