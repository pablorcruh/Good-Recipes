package ec.com.pablorcruh.goodrecipes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ec.com.pablorcruh.goodrecipes.R;
import ec.com.pablorcruh.goodrecipes.model.Ingredient;

public class IngredientsAdapter extends BaseAdapter {

    private static final String TAG = IngredientsAdapter.class.getName();

    private Context context;

    private int layout;

    private List<Ingredient> list;

    public IngredientsAdapter(Context context, int layout, List<Ingredient> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Ingredient getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        IngredientViewHolder holder;
        if(view == null){
            view = LayoutInflater.from(context).inflate(layout, null);
            holder = new IngredientViewHolder();
            holder.ingredient = view.findViewById(R.id.edit_text_item_ingredient);
            view.setTag(holder);
        }else{
            holder =(IngredientViewHolder) view.getTag();
        }

        final Ingredient currentIngredient = getItem(position);
        holder.ingredient.setText(currentIngredient.getDescription());
        return view;
    }
    static class IngredientViewHolder{
        private TextView ingredient;

    }
}
