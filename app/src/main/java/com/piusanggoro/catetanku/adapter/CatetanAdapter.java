package com.piusanggoro.catetanku.adapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piusanggoro.catetanku.FormActivity;
import com.piusanggoro.catetanku.R;
import com.piusanggoro.catetanku.model.Catetan;

import static com.piusanggoro.catetanku.db.DatabaseContract.CONTENT_URI;

public class CatetanAdapter extends RecyclerView.Adapter<CatetanAdapter.NoteViewholder> {
    private Cursor listNotes;
    private Activity activity;

    public CatetanAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListNotes(Cursor listNotes) {
        this.listNotes = listNotes;
    }

    @Override
    public NoteViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewholder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewholder holder, int position) {
        final Catetan catetan = getItem(position);
        holder.tvTitle.setText(catetan.getTitle());
        holder.tvDate.setText(catetan.getDate());
        holder.tvDescription.setText(catetan.getDescription());
        holder.tvInfo.setText(catetan.getInfo());
        holder.cvNote.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, FormActivity.class);

                // Set intent dengan data uri row catetan by id
                // content://com.dicoding.mynotesapp/catetan/id
                Uri uri = Uri.parse(CONTENT_URI + "/" + catetan.getId());
                intent.setData(uri);
                //intent.putExtra(FormActivity.EXTRA_POSITION, position);
                //intent.putExtra(FormActivity.EXTRA_NOTE, catetan);
                activity.startActivityForResult(intent, FormActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        if (listNotes == null) return 0;
        return listNotes.getCount();
    }

    private Catetan getItem(int position) {
        if (!listNotes.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Catetan(listNotes);
    }

    class NoteViewholder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, tvDate,tvInfo;
        CardView cvNote;

        NoteViewholder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_item_title);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_item_description);
            tvDate = (TextView) itemView.findViewById(R.id.tv_item_date);
            tvInfo = (TextView)itemView.findViewById(R.id.tv_item_info);
            cvNote = (CardView) itemView.findViewById(R.id.cv_item_note);
        }
    }

    static class CustomOnItemClickListener implements View.OnClickListener {
        private int position;
        private OnItemClickCallback onItemClickCallback;

        public CustomOnItemClickListener(int position, OnItemClickCallback onItemClickCallback) {
            this.position = position;
            this.onItemClickCallback = onItemClickCallback;
        }

        @Override
        public void onClick(View view) {
            onItemClickCallback.onItemClicked(view, position);
        }

        public interface OnItemClickCallback {
            void onItemClicked(View view, int position);
        }
    }
}
