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

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private Context context;
    private List<String> stepList;
    private final LayoutInflater layoutInflater;

    public StepAdapter(Context context, List<String> stepList){
        this.context = context;
        this.stepList = stepList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item_step, parent, false);
        StepViewHolder stepViewHolder= new StepViewHolder(view);
        return stepViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        if(stepList != null){
            String step = stepList.get(position);
            holder.setData(step, position);
        }
    }

    @Override
    public int getItemCount() {
        if(stepList!=null){
            return stepList.size();
        }else{
            return 0;
        }
    }

    public void setSteps(List<String> steps){
        stepList = steps;
        notifyDataSetChanged();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder {
        private EditText etStep;
        private int mPosition;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            etStep = itemView.findViewById(R.id.edit_text_item_step);
        }

        public void setData(String step, int position) {
            mPosition = position;
            etStep.setText(step);
        }
    }
}
