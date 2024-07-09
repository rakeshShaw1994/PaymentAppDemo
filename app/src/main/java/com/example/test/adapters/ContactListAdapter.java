package com.example.test.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.ui_models.ContactDataModel;
import com.example.test.ui_models.PromoDiscountModel;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    public interface ContactItemClick {
        void onItemClick(ContactDataModel model);
    }

    private ArrayList<ContactDataModel> mContactList = new ArrayList<>();
    private ContactItemClick mListener;

    public ContactListAdapter(ArrayList<ContactDataModel> contactList, ContactItemClick listener) {
        this.mContactList = contactList;
        this.mListener = listener;
    }

    public void add(ContactDataModel item) {
        mContactList.add(item);
        notifyItemInserted(mContactList.size() - 1);
    }

    public void remove(String item) {
        int position = mContactList.indexOf(item);
        mContactList.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contactItem = inflater.inflate(R.layout.item_contact_list, parent, false);
        return new ContactViewHolder(contactItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        int index = holder.getAdapterPosition();
        //ToDo: Glide/Piccasso Library can be used to render server image urls
        holder.mImageView.setImageResource(mContactList.get(position).getProfilePhoto());
        holder.contactName.setText(mContactList.get(position).getName());
        holder.contactDesc.setText(mContactList.get(position).getAccountNumber());
        holder.mView.setOnClickListener(v -> mListener.onItemClick(mContactList.get(position)));
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView contactName;
        TextView contactDesc;

        View mView;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_pic);
            contactName = itemView.findViewById(R.id.tv_contact_name);
            contactDesc = itemView.findViewById(R.id.tv_contact_desc);
            mView = itemView;
        }
    }
}
