package ec.com.pablorcruh.goodrecipes.fragments;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import ec.com.pablorcruh.goodrecipes.R;
import ec.com.pablorcruh.goodrecipes.adapter.IngredientsAdapter;
import ec.com.pablorcruh.goodrecipes.model.Ingredient;
import ec.com.pablorcruh.goodrecipes.model.Recipe;
import ec.com.pablorcruh.goodrecipes.viewmodel.NewRecipeViewModel;

public class NewRecipeFragment extends Fragment {

    private static final String TAG = NewRecipeFragment.class.getName();
    private ListView listViewIngredient;
    private IngredientsAdapter adapter;
    private EditText editTextNewIngredient;
    private ImageView imageViewAddIngredient;
    private List<Ingredient> ingredients = new ArrayList<>();
    private EditText editTextRecipeName;
    private Ingredient ingredient;
    private Button buttonSave;
    private NewRecipeViewModel viewModel;


    private List<String> ingredientsArray = new ArrayList<>();

    private List<String> stepsArray = new ArrayList<>();

    private Recipe recipe = new Recipe();

    public NewRecipeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {




        View view = inflater.inflate(R.layout.fragment_new_recipe, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(NewRecipeViewModel.class);

        this.listViewIngredient = view.findViewById(R.id.list_view_ingredients);
        this.editTextNewIngredient = view.findViewById(R.id.edit_text_add_ingredient);
        this.editTextRecipeName = view.findViewById(R.id.edit_text_new_recipe);
        this.imageViewAddIngredient = view.findViewById(R.id.image_view_add_ingredient);
        this.adapter = new IngredientsAdapter(getActivity(), R.layout.list_view_item_ingredient, ingredients);
        this.buttonSave = view.findViewById(R.id.button_save);
        this.listViewIngredient.setAdapter(adapter);
        this.imageViewAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ingredientAdded = editTextNewIngredient.getText().toString();
                ingredient = new Ingredient(ingredientAdded);
                ingredients.add(ingredient);
                adapter.notifyDataSetChanged();
            }
        });

        this.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.saveRecipe(recipe);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
