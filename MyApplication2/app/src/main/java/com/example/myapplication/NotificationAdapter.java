package com.example.myapplication;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    ArrayList<NotiItem> notis = new ArrayList<NotiItem>();


    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    // 리스너 객체 참조를 저장하는 변수
    private RecyclerViewAdapter.OnItemClickListener mListener = null ;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notification_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.date.setText(notis.get(position).date);
        String notiText = notis.get(position).user + "님이 게시물에 댓글을 달았습니다.";
        if(notiText.length() > 33){
            notiText = notiText.substring(0, 33) + " ...";
        }
        holder.text.setText(notiText);
    }

    public NotiItem getItem(int pos){
        return notis.get(pos);
    }

    @Override
    public int getItemCount() {
        return notis.size();
    }

    public void addItem(NotiItem notiItem){
        Log.d("AddNoti", "Item");
        notis.add(notiItem);
        notifyDataSetChanged();
    }

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(RecyclerViewAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public void remove_all(){
        notis.removeAll(notis);
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        TextView text;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.noti_date);
            text = itemView.findViewById(R.id.noti_text);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
        }
    }
}
