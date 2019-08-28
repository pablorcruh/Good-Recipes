package ec.com.pablorcruh.goodrecipes.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ec.com.pablorcruh.goodrecipes.R;
import ec.com.pablorcruh.goodrecipes.adapter.RecipeAdapter;
import ec.com.pablorcruh.goodrecipes.model.Recipe;
import ec.com.pablorcruh.goodrecipes.viewmodel.MainViewModel;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getName();

    private LiveData<QuerySnapshot> liveData;
    private MainViewModel mainViewModel;
    private RecipeAdapter adapterRecipe;
    private List<Recipe> recipeList;


    public HomeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        liveData = mainViewModel.getAllRecipes();
        liveData.observe(getActivity(), new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(QuerySnapshot documentSnapshots) {
                recipeList = new ArrayList<>();
                for(QueryDocumentSnapshot recipeSnapshot:documentSnapshots){
                    Recipe recipe = recipeSnapshot.toObject(Recipe.class);
                    recipe.setId(recipeSnapshot.getId().toString());
                    recipeList.add(recipe);
                }
                adapterRecipe.setRecipes(recipeList);
            }
        });

        RecyclerView recyclerViewRecipes = view.findViewById(R.id.recycler_view_recipes);
        adapterRecipe = new RecipeAdapter(getActivity(),getActivity(),recipeList);
        recyclerViewRecipes.setAdapter(adapterRecipe);
        recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

}
