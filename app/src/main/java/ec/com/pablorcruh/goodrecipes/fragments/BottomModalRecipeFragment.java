package ec.com.pablorcruh.goodrecipes.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

import ec.com.pablorcruh.goodrecipes.R;
import ec.com.pablorcruh.goodrecipes.constants.Constants;
import ec.com.pablorcruh.goodrecipes.viewmodel.MainViewModel;

public class BottomModalRecipeFragment extends BottomSheetDialogFragment {

    private MainViewModel mainViewModel;
    private String recipeIdDelete;
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
        if(getArguments()!=null){
            recipeIdDelete = getArguments().getString(Constants.ARG_RECIPE_ID);
        }
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_modal_recipe, container, false);
        final NavigationView nav = view.findViewById(R.id.navigation_view_bottom_recipe);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch(id){
                    case R.id.menu_action_delete_recipe:
                        Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+recipeIdDelete);
                        mainViewModel.deleteRecipe(recipeIdDelete);
                        getDialog().dismiss();
                        return true;
                }
                return false;
            }
        });

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }
}
