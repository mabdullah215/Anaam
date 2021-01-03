package com.app.anaamapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.anaamapp.R;
import com.app.anaamapp.model.Dashboard;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder>
{
    private ArrayList<Dashboard> dataList;
    private OnItemClickListener onItemClickListener;
    Context mContext;
    int selected=0;
    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setSelected(int selected)
    {
        this.selected = selected;
    }

    public DashboardAdapter(Context context, ArrayList<Dashboard> eventList) {
        this.dataList = eventList;
        mContext=context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_dashboard, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public int getItemCount()
    {
        return dataList == null? 0: dataList.size();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        final Dashboard item = dataList.get(position);
        holder.setDetails(item,position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mHeading;
        private TextView mSubheading;
        private TextView mRoundLabel;
        private ImageView mSourceImg;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mHeading=itemView.findViewById(R.id.tv_heading);
            mSubheading=itemView.findViewById(R.id.tv_sub_heading);
            mRoundLabel=itemView.findViewById(R.id.tv_round_label);
            mSourceImg=itemView.findViewById(R.id.img_src);
            itemView.setOnClickListener(this);
        }

         public void setDetails(final Dashboard item, final int pos)
         {
             mHeading.setText(item.getHeading());
             mSubheading.setText(item.getSubHeading());
             mSourceImg.setImageResource(item.getImageres());
             mRoundLabel.setVisibility(View.GONE);
             if(!item.getLabel().isEmpty())
             {
                 mRoundLabel.setText(item.getLabel());
                 mRoundLabel.setVisibility(View.VISIBLE);
             }
         }

        @Override
        public void onClick(View v)
        {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(getAdapterPosition());
        }
    }


}


