package ec.com.pablorcruh.goodrecipes.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ec.com.pablorcruh.goodrecipes.R;
import ec.com.pablorcruh.goodrecipes.common.MyApp;
import ec.com.pablorcruh.goodrecipes.common.SharedPreferencesManager;
import ec.com.pablorcruh.goodrecipes.constants.Constants;
import ec.com.pablorcruh.goodrecipes.firebase.Callback;
import ec.com.pablorcruh.goodrecipes.model.Recipe;
import ec.com.pablorcruh.goodrecipes.viewmodel.MainViewModel;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private static final String TAG  =  RecipeAdapter.class.getName();
    private List<Recipe> recipeList;
    private String user;
    private MainViewModel mainViewModel;
    private Context context;
    private LifecycleOwner lifecycleOwner;
    private String author;
    private List<String> followerList;
    private int followerCounter;

    public RecipeAdapter(Context context, LifecycleOwner lifecycleOwner, List<Recipe> recipeList) {
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.recipeList = recipeList;
        user = SharedPreferencesManager.getSomeStringValue(Constants.PREF_EMAIL);
        mainViewModel = ViewModelProviders.of((FragmentActivity) lifecycleOwner).get(MainViewModel.class);
        followerCounter =0;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent,false);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(view);
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder holder, int position) {
        if(recipeList != null){
            Recipe recipe = recipeList.get(position);
            holder.mRecipe = recipe;
            holder.setData(recipe, position);
            holder.ivRecipeAction.setVisibility(View.GONE);
            holder.tvFollowers.setVisibility(View.VISIBLE);
            if(holder.mRecipe.getAuthor().equals(user)){
                holder.ivRecipeAction.setVisibility(View.VISIBLE);
                holder.tvFollowers.setVisibility(View.GONE);
            }
            holder.ivRecipeAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainViewModel.openDialogRecipeMenu(context,holder.mRecipe.getId());
                }
            });
        }else{
            Toast.makeText(MyApp.getContext(), "No data available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        if(recipeList!=null){
            return recipeList.size();
        }else{
            return 0;
        }
    }

    public void setRecipes(List<Recipe> recipes){
        recipeList = recipes;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        private TextView tvRecipeName;
        private TextView tvRecipeDescription;
        private ImageView ivRecipeImage;
        private ImageView ivRecipeAction;
        private Recipe mRecipe;
        private TextView tvFollowers;
        private TextView tvAuthor;
        private int mPosition;
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecipeName = itemView.findViewById(R.id.label_item_recipe_name);
            tvRecipeDescription = itemView.findViewById(R.id.label_item_recipe_description);
            ivRecipeImage = itemView.findViewById(R.id.image_view_item_recipe_image);
            ivRecipeAction = itemView.findViewById(R.id.image_view_recipe_action);
            tvFollowers = itemView.findViewById(R.id.text_view_follow);
            tvAuthor = itemView.findViewById(R.id.label_recipe_author);
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

        public void setData(Recipe recipe, int position) {
            mPosition = position;
            tvRecipeName.setText(recipe.getName());
            tvRecipeDescription.setText(recipe.getDescription());
            tvAuthor.setText(recipe.getAuthor());
            author = tvAuthor.getText().toString();
            Glide.with(context)
                    .load(recipe.getRecipeImageUrl())
                    .into(ivRecipeImage);
        }
    }

}
