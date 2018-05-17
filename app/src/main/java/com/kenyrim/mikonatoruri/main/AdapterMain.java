package com.kenyrim.mikonatoruri.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.kenyrim.mikonatoruri.R;

import java.util.List;


public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolder> {
    public Context context;
    private List<Event> posts;

    public AdapterMain(Context context, List<Event> posts) {
        this.posts = posts;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = posts.get(position);

        holder.title.setText(event.getTitle());
        holder.coefficien.setText(event.getCoefficient());
        holder.time.setText(event.getTime());
        holder.place.setText(event.getPlace());
        holder.preview.setText(event.getPreview());

        holder.url = event.getArticle();



        setAnimation(holder.itemView, position);

    }

    @Override
    public int getItemCount() {
        if (posts == null)
            return 0;
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, coefficien, time, place, preview;
        public String url;


        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id._title);
            coefficien = itemView.findViewById(R.id._coefficien);
            time = itemView.findViewById(R.id._time);
            place = itemView.findViewById(R.id._place);
            preview = itemView.findViewById(R.id._preview);

        }
    }
    private void setAnimation(View view, int position) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(500);
            view.startAnimation(anim);
    }
}
