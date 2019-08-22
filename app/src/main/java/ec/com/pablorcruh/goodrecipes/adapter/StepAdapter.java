package ec.com.pablorcruh.goodrecipes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ec.com.pablorcruh.goodrecipes.R;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private Context context;
    private List<String> stepList;
    private final LayoutInflater layoutInflater;
    private OnDeleteStepClickListener onDeleteStepClickListener;

    public StepAdapter(Context context, List<String> stepList, OnDeleteStepClickListener listener){
        this.context = context;
        this.stepList = stepList;
        this.onDeleteStepClickListener = listener;
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
            holder.setListeners();
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
        private TextView tvStep;
        private int mPosition;
        private ImageView ivDeleteStep;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStep = itemView.findViewById(R.id.text_view_item_step);
            ivDeleteStep = itemView.findViewById(R.id.image_view_remove_step);
        }

        public void setData(String step, int position) {
            mPosition = position;
            tvStep.setText(step);
        }


        public void setListeners() {
            ivDeleteStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDeleteStepClickListener.onDeleteClickStepListener(stepList.get(mPosition));
                }
            });
        }
    }

    public interface OnDeleteStepClickListener{
        void onDeleteClickStepListener(String step);
    }
}
