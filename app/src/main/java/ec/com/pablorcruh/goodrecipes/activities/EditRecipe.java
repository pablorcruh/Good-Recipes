package ec.com.pablorcruh.goodrecipes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ec.com.pablorcruh.goodrecipes.R;
import ec.com.pablorcruh.goodrecipes.adapter.ViewIngredientAdapter;
import ec.com.pablorcruh.goodrecipes.adapter.ViewStepsAdapter;
import ec.com.pablorcruh.goodrecipes.common.MyApp;
import ec.com.pablorcruh.goodrecipes.common.SharedPreferencesManager;
import ec.com.pablorcruh.goodrecipes.constants.Constants;
import ec.com.pablorcruh.goodrecipes.firebase.Callback;
import ec.com.pablorcruh.goodrecipes.model.Recipe;
import ec.com.pablorcruh.goodrecipes.viewmodel.MainViewModel;

public class EditRecipe extends AppCompatActivity {

    public static final String TAG = EditRecipe.class.getSimpleName();
    private ViewIngredientAdapter ingredientAdapter;
    private ViewStepsAdapter stepAdapter;
    private List<String> ingredientList;
    private List<String> stepList;

    private TextView tvRecipeTitle;
    private TextView tvRecipeDescription;
    private ImageView ivRecipePicture;
    private TextView tvFollowers;
    private List<String> followerList;
    private int followerCounter;
    private MainViewModel mainViewModel;
    private String author;
    private String user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        user = SharedPreferencesManager.getSomeStringValue(Constants.PREF_EMAIL);
        this.getSupportActionBar().setTitle("Recipe Detail");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final Recipe recipe = intent.getParcelableExtra("recipe");
        ingredientList = recipe.getIngredientes();
        author = recipe.getAuthor();
        stepList = recipe.getSteps();
        tvRecipeTitle = findViewById(R.id.text_view_recipe_title);
        tvRecipeDescription = findViewById(R.id.text_view_view_recipe_description);
        ivRecipePicture = findViewById(R.id.image_view_recipe_picture);
        tvFollowers = findViewById(R.id.text_view_follow);

        tvFollowers.setVisibility(View.VISIBLE);
        if(recipe.getAuthor().equals(user)){
            tvFollowers.setVisibility(View.GONE);
        }

        tvRecipeTitle.setText(recipe.getName());
        tvRecipeDescription.setText(recipe.getDescription());
        Glide.with(EditRecipe.this)
                .load(recipe.getRecipeImageUrl())
                .error(Glide.with(ivRecipePicture).load(R.drawable.empty_recipe))
                .into(ivRecipePicture);



        RecyclerView recyclerViewIngredients = findViewById(R.id.view_recycler_view_ingredients);
        ingredientAdapter = new ViewIngredientAdapter(ingredientList);
        recyclerViewIngredients.setAdapter(ingredientAdapter);
        recyclerViewIngredients.setLayoutManager(new LinearLayoutManager(this));


        RecyclerView recyclerViewSteps = findViewById(R.id.view_recycler_view_steps);
        stepAdapter = new ViewStepsAdapter(stepList);
        recyclerViewSteps.setAdapter(stepAdapter);
        recyclerViewSteps.setLayoutManager(new LinearLayoutManager(this));



        tvFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.getFollowers(author, new Callback() {
                    @Override
                    public void passStringList(List<String> followers) {
                        followerList = new ArrayList<>();
                        followerList = followers;

                        for (String s : followerList){
                            if(s.equals(SharedPreferencesManager.getSomeStringValue(Constants.PREF_EMAIL))){
                                followerCounter ++;
                            }
                        }
                        if(followerCounter==0){
                            followers.add(SharedPreferencesManager.getSomeStringValue(Constants.PREF_EMAIL));
                            mainViewModel.addFollower(followers, author);
                        }else{
                            Toast.makeText(MyApp.getContext(), "User already follower", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
