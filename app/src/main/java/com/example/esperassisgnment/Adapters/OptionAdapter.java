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
import com.example.esperassisgnment.Models.Entities.Options;
import com.example.esperassisgnment.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {

    Context context;
    List<Options> options = new ArrayList<>();
    Picasso picasso;
    LayoutInflater inflater;

    public OptionAdapter(Context context){
        this.context = context;
        picasso = Picasso.get();
        inflater = LayoutInflater.from(context);
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
        holder.optionName.setText(option.getName());
        picasso.load(option.getIcon()).error(R.drawable.nature).into(holder.optionImage);

        holder.optionCard.setOnClickListener((v)->{
            clearSelections();
            holder.selectionImage.setActivated(!holder.selectionImage.isActivated());
        });

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

    public void updateOptions(List<Options> options){
        if (options==null) return;
        this.options.clear();
        this.options.addAll(options);
        notifyDataSetChanged();
    }

    public void clearSelections(){

    }
}
