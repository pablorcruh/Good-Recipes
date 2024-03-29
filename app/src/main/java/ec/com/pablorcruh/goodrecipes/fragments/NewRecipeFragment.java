package ec.com.pablorcruh.goodrecipes.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import ec.com.pablorcruh.goodrecipes.R;
import ec.com.pablorcruh.goodrecipes.adapter.IngredientsAdapter;
import ec.com.pablorcruh.goodrecipes.adapter.StepAdapter;
import ec.com.pablorcruh.goodrecipes.common.SharedPreferencesManager;
import ec.com.pablorcruh.goodrecipes.common.Util;
import ec.com.pablorcruh.goodrecipes.constants.Constants;
import ec.com.pablorcruh.goodrecipes.model.Recipe;
import ec.com.pablorcruh.goodrecipes.viewmodel.NewRecipeViewModel;

import static android.app.Activity.RESULT_OK;

public class NewRecipeFragment extends Fragment implements IngredientsAdapter.OnDeleteIngredientClickListener, StepAdapter.OnDeleteStepClickListener {

    private static final String TAG = NewRecipeFragment.class.getName();

    private IngredientsAdapter adapterIngredients;
    private StepAdapter adapterSteps;
    private EditText editTextNewIngredient;
    private ImageView imageViewAddIngredient;
    private ImageView imageViewAddStep;
    private EditText editTextRecipeName;
    private EditText editTextNewStep;
    private Button buttonSaveRecipe;
    private TextView tvAddImage;
    private ImageView ivShowRecipeImage;
    private NewRecipeViewModel viewModel;
    private Uri uriImage;

    private List<String> ingredientsArray = new ArrayList<>();
    private List<String> stepsArray = new ArrayList<>();

    private Recipe recipe;
    private String recipeName;
    private EditText etDescription;
    private String recipeDescription;

    private static final int PICK_IMAGE_REQUEST = 1;
    private int STORAGE_PERMISSION_CODE = 1;

    public NewRecipeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(NewRecipeViewModel.class);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       try{
           View view = inflater.inflate(R.layout.fragment_new_recipe, container, false);

           this.editTextNewIngredient = view.findViewById(R.id.edit_text_add_ingredient);
           this.editTextRecipeName = view.findViewById(R.id.edit_text_new_recipe);
           this.imageViewAddIngredient = view.findViewById(R.id.image_view_add_ingredient);
           this.editTextNewStep = view.findViewById(R.id.edit_text_add_step);
           this.imageViewAddStep = view.findViewById(R.id.image_view_add_step);
           this.buttonSaveRecipe = view.findViewById(R.id.button_save_recipe);
           this.tvAddImage = view.findViewById(R.id.text_view_add_image);
           this.ivShowRecipeImage = view.findViewById(R.id.image_view_recipe_image);
           this.etDescription = view.findViewById(R.id.edit_text_recipe_description);


           RecyclerView recyclerViewIngredients = view.findViewById(R.id.recycler_view_ingredients);
           adapterIngredients = new IngredientsAdapter(ingredientsArray, this);
           recyclerViewIngredients.setAdapter(adapterIngredients);
           recyclerViewIngredients.setLayoutManager(new LinearLayoutManager(getActivity()));


           RecyclerView recyclerViewSteps = view.findViewById(R.id.recycler_view_steps);
           adapterSteps = new StepAdapter(getActivity(), stepsArray, this);
           recyclerViewSteps.setAdapter(adapterSteps);
           recyclerViewSteps.setLayoutManager(new LinearLayoutManager(getActivity()));

           this.imageViewAddIngredient.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if (TextUtils.isEmpty(editTextNewIngredient.getText().toString())) {
                       Toast.makeText(getActivity(), "Need to add ingredient", Toast.LENGTH_SHORT).show();
                   } else {
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
                   if (TextUtils.isEmpty(editTextNewStep.getText().toString())) {
                       Toast.makeText(getActivity(), "Need to add step", Toast.LENGTH_SHORT).show();
                   } else {
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
               public void onClick(final View view) {
                   if (TextUtils.isEmpty(editTextRecipeName.getText().toString())) {
                       Toast.makeText(getActivity(), "Must enter recipe name", Toast.LENGTH_SHORT).show();
                   } else if (stepsArray.isEmpty()) {
                       Toast.makeText(getActivity(), "Must enter one step", Toast.LENGTH_SHORT).show();
                   } else if (ingredientsArray.isEmpty()) {
                       Toast.makeText(getActivity(), "Must enter one ingredient", Toast.LENGTH_SHORT).show();
                   } else if (TextUtils.isEmpty(etDescription.getText().toString())) {
                       Toast.makeText(getActivity(), "Must enter Description", Toast.LENGTH_SHORT).show();
                   } else {
                       recipeDescription = etDescription.getText().toString();
                       recipeName = editTextRecipeName.getText().toString();
                       recipe = new Recipe(SharedPreferencesManager.getSomeStringValue(Constants.PREF_EMAIL), ingredientsArray, stepsArray, recipeName, "", recipeDescription, Util.getCurrentDate());
                       viewModel.saveRecipe(recipe);
                       if (uriImage != null) {
                           viewModel.uploadPhotoStorage(uriImage);
                       } else {
                           viewModel.uploadPhotoStorage(Uri.parse(Constants.EMPTY_RECIPE_URL));
                           Toast.makeText(getActivity(), "No image was loaded", Toast.LENGTH_SHORT).show();
                       }
                       FragmentManager fragmentManager = getFragmentManager();
                       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                       fragmentTransaction
                               .replace(R.id.fragment_container, new HomeFragment(), "home")
                               .addToBackStack("home")
                               .commit();
                   }
               }
           });
           this.tvAddImage.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                       openFileChooser();
                   }else{
                       requestStoragePermission();
                   }
               }
           });
           return view;
       }catch(Exception e){
           Crashlytics.log(e.getMessage());
           Log.e(TAG, "onCreateView: ", e);
           return null;
       }
    }

    private void requestStoragePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){
             new AlertDialog.Builder(getActivity())
                    .setTitle("Permission needed")
                    .setMessage("this permission is needed to upload image")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            uriImage = data.getData();
            Glide.with(getActivity()).load(uriImage).into(ivShowRecipeImage);
        }
    }


    @Override
    public void onDeleteIngredientClickListener(String ingredient) {
        ingredientsArray.remove(ingredient);
        adapterIngredients.notifyDataSetChanged();
    }

    @Override
    public void onDeleteClickStepListener(String step) {
        stepsArray.remove(step);
        adapterSteps.notifyDataSetChanged();
    }

}
