package com.example.pingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NetworkAdapterCard extends RecyclerView.Adapter<NetworkAdapterCard.ViewHolder> {
    ArrayList<NetworkCard> myNetwork;

    public NetworkAdapterCard(ArrayList<NetworkCard> myNetwork) {
        this.myNetwork = myNetwork;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.data_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(myNetwork.get(i));
    }

    @Override
    public int getItemCount() {
        return myNetwork == null ? 0 : myNetwork.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView hstname,addrs;
        private String fn_hostname,fn_address;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hstname = itemView.findViewById(R.id.tv_hostname);
            addrs = itemView.findViewById(R.id.tv_address);
            itemView.setOnClickListener(this);
        }
        void bind(NetworkCard networkCard){
            hstname.setText(networkCard.getHostname());
            addrs.setText(networkCard.getAddress());
            fn_hostname = networkCard.getHostname();
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(itemView.getContext(),HomeActivity.class);
            intent.putExtra("nama_hostname",fn_hostname);
            itemView.getContext().startActivity(intent);
        }
    }
}
