package ec.com.pablorcruh.goodrecipes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ec.com.pablorcruh.goodrecipes.R;
import ec.com.pablorcruh.goodrecipes.common.MyApp;
import ec.com.pablorcruh.goodrecipes.model.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context context;
    private List<Recipe> recipeList;
    private final LayoutInflater layoutInflater;

    public RecipeAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_recipe, parent,false);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(view);
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        if(recipeList != null){
            Recipe recipe = recipeList.get(position);
            holder.setData(recipe, position);
        }else{
            Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();
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
        private int mPosition;
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecipeName = itemView.findViewById(R.id.label_item_recipe_name);
            tvRecipeDescription = itemView.findViewById(R.id.label_item_recipe_description);
            ivRecipeImage = itemView.findViewById(R.id.image_view_item_recipe_image);
        }

        public void setData(Recipe recipe, int position) {
            mPosition = position;
            tvRecipeName.setText(recipe.getName());
            tvRecipeDescription.setText(recipe.getDescription());
            Glide.with(context)
                    .load(recipe.getRecipeImageUrl())
                    .into(ivRecipeImage);
        }
    }
}
