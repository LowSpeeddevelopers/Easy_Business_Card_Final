package com.nexttech.easybusinesscard.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nexttech.easybusinesscard.Model.UserInfoModel;
import com.nexttech.easybusinesscard.R;

import java.util.ArrayList;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    Context context;
    ArrayList<UserInfoModel> cardList;

    public CardListAdapter(Context context, ArrayList<UserInfoModel> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.saved_card_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserInfoModel model = cardList.get(position);

        holder.itemName.setText(model.getName());
        holder.itemDesignation.setText(model.getDesignation());
        holder.itemCompanyName.setText(model.getCompanyName());
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemName, itemDesignation, itemCompanyName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.tv_item_name);
            itemDesignation = itemView.findViewById(R.id.tv_item_designation);
            itemCompanyName = itemView.findViewById(R.id.tv_item_company_name);
        }
    }
}
