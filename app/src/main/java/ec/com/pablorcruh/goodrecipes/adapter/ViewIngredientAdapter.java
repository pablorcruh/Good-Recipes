package ec.com.pablorcruh.goodrecipes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ec.com.pablorcruh.goodrecipes.R;

public class ViewIngredientAdapter extends RecyclerView.Adapter<ViewIngredientAdapter.IngredientViewHolder> {

    private List<String> listIngredients;

    public ViewIngredientAdapter(List<String> listIngredients){
        this.listIngredients = listIngredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_item_ingredient, parent, false);
        IngredientViewHolder ingredientViewHolder = new IngredientViewHolder(view);
        return ingredientViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        if(listIngredients != null){
            String ingredient = listIngredients.get(position);
            holder.setData(ingredient, position);
        }else{
            holder.tvRecipeIngredient.setText("Ingredient no ready");
        }
    }

    @Override
    public int getItemCount() {
        if(listIngredients !=null){
            return listIngredients.size();
        }else{
            return 0;
        }
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        private TextView tvRecipeIngredient;
        private int mPosition;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecipeIngredient = itemView.findViewById(R.id.view_text_view_item_ingredient);
        }

        public void setData(String ingredient, int position) {
            tvRecipeIngredient.setText(ingredient);
            mPosition = position;
        }

    }
}
