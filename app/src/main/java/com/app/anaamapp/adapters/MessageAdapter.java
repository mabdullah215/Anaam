package com.app.anaamapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.anaamapp.R;
import com.app.anaamapp.model.Message;
import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>
{
    private ArrayList<Message> dataList;
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

    public MessageAdapter(Context context, ArrayList<Message> eventList) {
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
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
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
        final Message item = dataList.get(position);
        holder.setDetails(item,position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mText;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mText=itemView.findViewById(R.id.tv_txt);
            itemView.setOnClickListener(this);
        }

         public void setDetails(final Message item, final int pos)
         {
             mText.setText(item.getText());
         }

        @Override
        public void onClick(View v)
        {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(getAdapterPosition());
        }
    }


}


