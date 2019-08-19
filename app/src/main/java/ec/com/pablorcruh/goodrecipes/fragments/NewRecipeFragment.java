package ec.com.pablorcruh.goodrecipes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ec.com.pablorcruh.goodrecipes.R;
import ec.com.pablorcruh.goodrecipes.adapter.IngredientsAdapter;
import ec.com.pablorcruh.goodrecipes.model.Recipe;
import ec.com.pablorcruh.goodrecipes.viewmodel.NewRecipeViewModel;

public class NewRecipeFragment extends Fragment {

    private static final String TAG = NewRecipeFragment.class.getName();

    private IngredientsAdapter adapter;
    private EditText editTextNewIngredient;
    private ImageView imageViewAddIngredient;
    private EditText editTextRecipeName;
    private Button buttonSave;
    private NewRecipeViewModel viewModel;

    private List<String> ingredientsArray;
    private List<String> stepsArray = new ArrayList<>();
    private Recipe recipe = new Recipe();

    public NewRecipeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ingredientsArray = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_recipe, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(NewRecipeViewModel.class);

        this.editTextNewIngredient = view.findViewById(R.id.edit_text_add_ingredient);
        this.editTextRecipeName = view.findViewById(R.id.edit_text_new_recipe);
        this.imageViewAddIngredient = view.findViewById(R.id.image_view_add_ingredient);
        //this.buttonSave = view.findViewById(R.id.button_save);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_ingredients);
        adapter=new IngredientsAdapter(getActivity(), ingredientsArray);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        this.imageViewAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editTextNewIngredient.getText().toString())){
                    Toast.makeText(getActivity(), "Need to add ingredient", Toast.LENGTH_SHORT).show();
                }else{
                    String ingredientAdded = editTextNewIngredient.getText().toString();
                    ingredientsArray.add(ingredientAdded);
                    adapter.setIngredients(ingredientsArray);
                    cleanIngredientEntry();
                    Toast.makeText(getActivity(), "Ingredient added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Guardar receta en firebase
        /*this.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.saveRecipe(recipe);
            }
        });*/
        return view;
    }

    public void cleanIngredientEntry(){
        this.editTextNewIngredient.setText("");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
