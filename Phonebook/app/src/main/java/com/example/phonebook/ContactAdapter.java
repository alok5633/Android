package com.example.phonebook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phonebook.ContactModel;
import com.example.phonebook.R;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>{

    private List<ContactModel> data;
    private Context context;
    private clickListener listener;


    public ContactAdapter(List<ContactModel> data, Context context,clickListener listener) {
        this.data = data;
        this.context = context;
        this.listener=listener;
    }

    interface clickListener {
        void onUpdateClick(ContactModel model);
        void onDeleteClick(ContactModel model);
        void onNameClick(ContactModel model);
    }



    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.contacts_info,viewGroup,false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder contactViewHolder, int i) {
        contactViewHolder.logo.setImageResource(R.drawable.photo);
        contactViewHolder.name_p.setText(data.get(i).getContact_name());
        contactViewHolder.no_p.setText(data.get(i).getContact_no());
        contactViewHolder.update.setImageResource(R.drawable.update);
        contactViewHolder.delete.setImageResource(R.drawable.delete);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        ImageView logo,update,delete;
        TextView  name_p,no_p;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            logo=itemView.findViewById(R.id.image_logo);
            name_p=itemView.findViewById(R.id.tv_contact_name);
            no_p=itemView.findViewById(R.id.tv_contact_no);
            update=itemView.findViewById(R.id.image_update);
            delete=itemView.findViewById(R.id.image_delete);

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onUpdateClick(data.get(getAdapterPosition()));
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteClick(data.get(getAdapterPosition()));
                }
            });

            name_p.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNameClick(data.get(getAdapterPosition()));
                }
            });

        }
    }
}



