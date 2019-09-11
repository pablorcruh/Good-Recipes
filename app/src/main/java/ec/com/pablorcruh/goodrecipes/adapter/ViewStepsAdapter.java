package ec.com.pablorcruh.goodrecipes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ec.com.pablorcruh.goodrecipes.R;

public class ViewStepsAdapter extends RecyclerView.Adapter<ViewStepsAdapter.StepsViewHolder> {

    private List<String> listSteps;

    public ViewStepsAdapter(List<String> listSteps){
        this.listSteps = listSteps;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_item_steps, parent, false);
        StepsViewHolder stepsViewHolder = new StepsViewHolder(view);
        return stepsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        if(listSteps != null){
            String ingredient = listSteps.get(position);
            holder.setData(ingredient, position);
        }else{
            holder.tvStep.setText("Ingredient no ready");
        }
    }

    @Override
    public int getItemCount() {
        if(listSteps !=null){
            return listSteps.size();
        }else{
            return 0;
        }
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder {
        private TextView tvStep;
        private int mPosition;

        public StepsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStep = itemView.findViewById(R.id.view_text_view_item_step);
        }

        public void setData(String ingredient, int position) {
            tvStep.setText(ingredient);
            mPosition = position;
        }
    }
}
