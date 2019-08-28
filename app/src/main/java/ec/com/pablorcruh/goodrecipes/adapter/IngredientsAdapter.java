package ec.com.pablorcruh.goodrecipes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ec.com.pablorcruh.goodrecipes.R;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private static final String TAG = IngredientsAdapter.class.getName();


    private List<String> listIngredients;

    private OnDeleteIngredientClickListener onDeleteIngredientClickListener;

    public IngredientsAdapter(List<String> listIngredients, OnDeleteIngredientClickListener listener) {
        this.listIngredients = listIngredients;
        this.onDeleteIngredientClickListener = listener;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ingredient, parent, false);
        IngredientViewHolder ingredientViewHolder = new IngredientViewHolder(view);
        return ingredientViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        if(listIngredients != null){
            String ingredient = listIngredients.get(position);
            holder.setData(ingredient, position);
            holder.setListener();
        }else{
            holder.tvIngredient.setText("Ingredient no ready");
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

        private TextView tvIngredient;
        private ImageView ivDeleteIngredient;
        private int mPosition;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIngredient = itemView.findViewById(R.id.text_view_item_ingredient);
            ivDeleteIngredient = itemView.findViewById(R.id.image_view_remove_item);
        }

        public void setData(String ingredient, int position) {
            tvIngredient.setText(ingredient);
            mPosition = position;
        }

        public void setListener() {
            ivDeleteIngredient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onDeleteIngredientClickListener !=null){
                        onDeleteIngredientClickListener.onDeleteIngredientClickListener(listIngredients.get(mPosition));
                    }
                }
            });
        }


    }

    public interface OnDeleteIngredientClickListener {
        void onDeleteIngredientClickListener(String ingredient);
    }
}
