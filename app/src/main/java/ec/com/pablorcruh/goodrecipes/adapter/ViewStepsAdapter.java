package ec.com.pablorcruh.goodrecipes.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewStepsAdapter extends RecyclerView.Adapter<ViewStepsAdapter.StepsViewHolder> {
    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder {
        public StepsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
