package ec.com.pablorcruh.goodrecipes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ec.com.pablorcruh.goodrecipes.R;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private static final String TAG = IngredientsAdapter.class.getName();

    private Context context;

    private List<String> listIngredients;

    private final LayoutInflater layoutInflater;

    public IngredientsAdapter(Context context, List<String> listIngredients) {
        this.context = context;
        this.listIngredients = listIngredients;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =layoutInflater.inflate(R.layout.list_view_item_ingredient, parent, false);
        IngredientViewHolder ingredientViewHolder = new IngredientViewHolder(view);
        return ingredientViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        if(listIngredients != null){
            String ingredient = listIngredients.get(position);
            holder.setData(ingredient, position);
        }else{
            holder.etIngredient.setText("Ingredient no ready");
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


    public void setIngredients(List<String> ingredients){
        listIngredients = ingredients;
        notifyDataSetChanged();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        private EditText etIngredient;
        private int mPosition;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            etIngredient = itemView.findViewById(R.id.edit_text_item_ingredient);
        }

        public void setData(String ingredient, int position) {
            etIngredient.setText(ingredient);
            mPosition = position;
        }
    }
}
