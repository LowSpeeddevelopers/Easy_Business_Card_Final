package com.nexttech.easybusinesscard.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.nexttech.easybusinesscard.Activity.SplashActivity;
import com.nexttech.easybusinesscard.Activity.ViewActivity;
import com.nexttech.easybusinesscard.DB.BusinessCardDb;
import com.nexttech.easybusinesscard.Model.CollectionCardModel;
import com.nexttech.easybusinesscard.Model.UserInfoModel;
import com.nexttech.easybusinesscard.R;

import java.util.ArrayList;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    Context context;
    ArrayList<CollectionCardModel> cardList;

    BusinessCardDb businessCardDb;

    public CardListAdapter(Context context, ArrayList<CollectionCardModel> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.saved_card_item, parent, false);

        businessCardDb = new BusinessCardDb(context);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final CollectionCardModel model = cardList.get(position);

        holder.itemName.setText(model.getName());
        holder.itemDesignation.setText(model.getDesignation());
        holder.itemCompanyName.setText(model.getCompanyName());

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)
                        // set message & title
                        .setTitle("Delete")
                        .setMessage("Do you want to Delete")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                businessCardDb.deleteCardData(model.getId());
                                cardList.remove(position);
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }

                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        })
                        .create();
                myQuittingDialogBox.show();
                Button btnPositive = myQuittingDialogBox.getButton(AlertDialog.BUTTON_POSITIVE);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
                layoutParams.leftMargin=20;
                btnPositive.setLayoutParams(layoutParams);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(context, ViewActivity.class);
                i.putExtra("user_id",model.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemName, itemDesignation, itemCompanyName;
        ImageView ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.tv_item_name);
            itemDesignation = itemView.findViewById(R.id.tv_item_designation);
            itemCompanyName = itemView.findViewById(R.id.tv_item_company_name);
            ivDelete = itemView.findViewById(R.id.iv_delete);
        }
    }
}
