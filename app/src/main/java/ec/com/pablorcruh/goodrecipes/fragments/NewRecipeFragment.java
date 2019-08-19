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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ec.com.pablorcruh.goodrecipes.R;
import ec.com.pablorcruh.goodrecipes.adapter.IngredientsAdapter;
import ec.com.pablorcruh.goodrecipes.adapter.StepAdapter;
import ec.com.pablorcruh.goodrecipes.model.Recipe;
import ec.com.pablorcruh.goodrecipes.viewmodel.NewRecipeViewModel;

public class NewRecipeFragment extends Fragment {

    private static final String TAG = NewRecipeFragment.class.getName();

    private IngredientsAdapter adapterIngredients;
    private StepAdapter adapterSteps;
    private EditText editTextNewIngredient;
    private ImageView imageViewAddIngredient;
    private ImageView imageViewAddStep;
    private EditText editTextRecipeName;
    private EditText editTextNewStep;
    private Button buttonSaveRecipe;
    private NewRecipeViewModel viewModel;

    private List<String> ingredientsArray= new ArrayList<>();
    private List<String> stepsArray = new ArrayList<>();
    private Recipe recipe;

    public NewRecipeFragment() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_recipe, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(NewRecipeViewModel.class);

        this.editTextNewIngredient = view.findViewById(R.id.edit_text_add_ingredient);
        this.editTextRecipeName = view.findViewById(R.id.edit_text_new_recipe);
        this.imageViewAddIngredient = view.findViewById(R.id.image_view_add_ingredient);
        this.editTextNewStep = view.findViewById(R.id.edit_text_add_step);
        this.imageViewAddStep = view.findViewById(R.id.image_view_add_step);
        this.buttonSaveRecipe = view.findViewById(R.id.button_save_recipe);

        RecyclerView recyclerViewIngredients = view.findViewById(R.id.recycler_view_ingredients);
        adapterIngredients=new IngredientsAdapter(getActivity(), ingredientsArray);
        recyclerViewIngredients.setAdapter(adapterIngredients);
        recyclerViewIngredients.setLayoutManager(new LinearLayoutManager(getActivity()));


        RecyclerView recyclerViewSteps = view.findViewById(R.id.recycler_view_steps);
        adapterSteps = new StepAdapter(getActivity(), stepsArray);
        recyclerViewSteps.setAdapter(adapterSteps);
        recyclerViewSteps.setLayoutManager(new LinearLayoutManager(getActivity()));

        this.imageViewAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editTextNewIngredient.getText().toString())){
                    Toast.makeText(getActivity(), "Need to add ingredient", Toast.LENGTH_SHORT).show();
                }else{
                    String ingredientAdded = editTextNewIngredient.getText().toString();
                    ingredientsArray.add(ingredientAdded);
                    adapterIngredients.setIngredients(ingredientsArray);
                    editTextNewIngredient.setText("");
                    Toast.makeText(getActivity(), "Ingredient added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.imageViewAddStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editTextNewStep.getText().toString())){
                    Toast.makeText(getActivity(), "Need to add step", Toast.LENGTH_SHORT).show();
                }else{
                    String stepAdded = editTextNewStep.getText().toString();
                    stepsArray.add(stepAdded);
                    adapterSteps.setSteps(stepsArray);
                    editTextNewStep.setText("");
                    Toast.makeText(getActivity(), "Step Added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.buttonSaveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editTextRecipeName.getText().toString())){
                    Toast.makeText(getActivity(), "Must enter recipe name", Toast.LENGTH_SHORT).show();
                }else if(stepsArray.isEmpty()){
                    Toast.makeText(getActivity(), "Must enter one step", Toast.LENGTH_SHORT).show();
                }else if(ingredientsArray.isEmpty()){
                    Toast.makeText(getActivity(), "Must enter one ingredient", Toast.LENGTH_SHORT).show();
                }else{
                    String recipeName = editTextRecipeName.getText().toString();
                    recipe = new Recipe("", ingredientsArray, stepsArray,recipeName );
                    viewModel.saveRecipe(recipe);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new HomeFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
        return view;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
