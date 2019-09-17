package ec.com.pablorcruh.goodrecipes.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
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

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> implements Filterable {




    public interface OnRecipeClickListener {
        void onRecipeClick(int position);
    }

    public void setOnRecipeClickListener(OnRecipeClickListener listener){
        recipeListener = listener;
    }

    private static final String TAG  =  RecipeAdapter.class.getName();
    private List<Recipe> recipeList;
    private List<Recipe> recipeListSearch;

    private String user;
    private MainViewModel mainViewModel;
    private Context context;
    private LifecycleOwner lifecycleOwner;
    private OnRecipeClickListener recipeListener;


    public RecipeAdapter(Context context, LifecycleOwner lifecycleOwner, List<Recipe> recipeList) {
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.recipeList = recipeList;
        this.recipeListSearch = new ArrayList<>(recipeList);
        user = SharedPreferencesManager.getSomeStringValue(Constants.PREF_EMAIL);
        mainViewModel = ViewModelProviders.of((FragmentActivity) lifecycleOwner).get(MainViewModel.class);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent,false);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(view, recipeListener);
        this.recipeListSearch = new ArrayList<>(recipeList);
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder holder, int position) {
        if(recipeList != null){
            recipeListSearch = new ArrayList<>(this.recipeList);
            Recipe recipe = recipeList.get(position);
            holder.mRecipe = recipe;
            holder.setData(recipe, position);
            holder.ivRecipeAction.setVisibility(View.GONE);
            if(holder.mRecipe.getAuthor().equals(user)){
                holder.ivRecipeAction.setVisibility(View.VISIBLE);
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
        private TextView tvAuthor;
        private int mPosition;
        public RecipeViewHolder(@NonNull View itemView, final OnRecipeClickListener listener) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener !=null){
                        int position= getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onRecipeClick(position);
                        }
                    }
                }
            });

            tvRecipeName = itemView.findViewById(R.id.label_item_recipe_name);
            tvRecipeDescription = itemView.findViewById(R.id.label_item_recipe_description);
            ivRecipeImage = itemView.findViewById(R.id.image_view_item_recipe_image);
            ivRecipeAction = itemView.findViewById(R.id.image_view_recipe_action);
            tvAuthor = itemView.findViewById(R.id.label_recipe_author);

        }

        public void setData(Recipe recipe, int position) {
            mPosition = position;
            tvRecipeName.setText(recipe.getName());
            tvRecipeDescription.setText(recipe.getDescription());
            tvAuthor.setText(recipe.getAuthor());
            Glide.with(context)
                    .load(recipe.getRecipeImageUrl())
                    .error(Glide.with(ivRecipeImage).load(R.drawable.empty_recipe))
                    .into(ivRecipeImage);
        }
    }

    @Override
    public Filter getFilter() {
        return recipeFilter;
    }

    private Filter recipeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Recipe> filteredList = new ArrayList<>();
            if(charSequence == null || charSequence.length()==0){
                filteredList.addAll(recipeListSearch);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(Recipe item : recipeListSearch){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            recipeList.clear();
            recipeList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

}
