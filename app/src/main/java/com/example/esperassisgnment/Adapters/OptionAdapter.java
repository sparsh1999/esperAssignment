package com.example.esperassisgnment.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esperassisgnment.Helpers.SelectionManager;
import com.example.esperassisgnment.Models.Entities.Options;
import com.example.esperassisgnment.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {

    Context context;
    List<Options> options = new ArrayList<>();
    Picasso picasso;
    LayoutInflater inflater;
    SelectionManager manager;
    Set<Integer> exclusions = new HashSet<>();
    int selectedOptionId;

    public OptionAdapter(Context context, SelectionManager manager){
        this.context = context;
        picasso = Picasso.get();
        inflater = LayoutInflater.from(context);
        this.manager = manager;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.feature_layout, parent, false);
        return new OptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        Options option = options.get(position);

        // if this options is not in exclusion set , it means it is available for selection
        boolean isAvaialble = !exclusions.contains(option.getId());

        // if this option is previously selected , then set selected Image
        holder.selectionImage.setActivated(option.getId()==selectedOptionId);
        holder.optionName.setText(option.getName());
        picasso.load(option.getIcon()).error(R.drawable.nature).into(holder.optionImage);

        // reset the visibility of card to full, we will set it in next steps
        holder.optionCard.setAlpha(1f);

        if (isAvaialble) {
            // option is available so , allow user to select it
            holder.optionCard.setOnClickListener((v) -> {
                // if previously selected option is empty or previous and current selections are different
                // then remove previous selection and select new option
                // manager's addSelection method handles this internally
                if (selectedOptionId==-1 || selectedOptionId!=option.getId()){
                    manager.addSelection(option.getFeatureId(), option.getId());
                    int previousSelectionOptionId = selectedOptionId;
                    selectedOptionId = option.getId();
                    clearSelections(previousSelectionOptionId);
                }
                // if both are same , then just deselect it
                else if (selectedOptionId==option.getId()){
                    manager.removeSelection(option.getFeatureId(), selectedOptionId);
                    selectedOptionId=-1;
                }
                holder.selectionImage.setActivated(!holder.selectionImage.isActivated());
            });
        }
        else{
            // if this option is unavailable then stop onClickListeners and reduce visibility
            holder.optionCard.setOnClickListener(null);
            holder.optionCard.setAlpha(0.2f);
        }
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    class OptionViewHolder extends RecyclerView.ViewHolder{

        ImageView optionImage, selectionImage;
        TextView optionName;
        CardView optionCard;

        OptionViewHolder(@NonNull View itemView) {
            super(itemView);
            optionCard = itemView.findViewById(R.id.generic_card_view);
            optionImage = itemView.findViewById(R.id.generic_card_image);
            optionName = itemView.findViewById(R.id.generic_card_supertitle);
            selectionImage = itemView.findViewById(R.id.generic_card_select_img);
        }
    }

    /**
     * utility function to update options , used when user clicks on different feature (Tabs)
     * @param options
     * @param featureId
     */
    public void updateOptions(List<Options> options, int featureId){
        if (options==null) return;
        this.options.clear();
        this.options.addAll(options);
        sanitize(featureId);
        notifyDataSetChanged();
    }

    /**
     * If an option is already selected, then populate selectedOptionId and get all options which needs
     * to be excluded due to other selections
     * @param featureId
     */
    private void sanitize(int featureId){

        // add already selected options
        selectedOptionId = manager.getSelections(featureId);

        // also populate exclusion List
        Set<Integer> exclusionSet = manager.getExclusions(featureId);
        this.exclusions.clear();
        this.exclusions.addAll(exclusionSet);

    }

    /**
     * clear the previous selection with id as oldOption
     * @param oldOption
     */
    private void clearSelections(int oldOption) {
        if (oldOption==-1) return;
        Options option;
        for (int i = 0; i < options.size(); i++) {
            option = options.get(i);
            if (option.getId() == oldOption) {
                notifyItemChanged(i);
                break;
            }
        }
    }
}
