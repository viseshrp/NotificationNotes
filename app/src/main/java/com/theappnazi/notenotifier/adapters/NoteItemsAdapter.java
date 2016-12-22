package com.theappnazi.notenotifier.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theappnazi.notenotifier.R;
import com.theappnazi.notenotifier.models.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by viseshprasad on 12/8/16.
 */

public class NoteItemsAdapter extends RecyclerView.Adapter<NoteItemsAdapter.ViewHolder> {
    private final ItemTapListener mItemTapListener;
    private List<Note> noteList;
    private Context mContext;

    public NoteItemsAdapter(Context mContext, ItemTapListener listener) {
        noteList = new ArrayList<>();
        mItemTapListener = listener;
        this.mContext = mContext;
    }

    public void setDataset(List<Note> noteList) {
        this.noteList = noteList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Note note = noteList.get(position);

        holder.noteDate.setText(note.getNote_date());

        holder.noteTitle.setText(note.getNotification_title());

        String noteContent = note.getNotification_content();
        if (noteContent != null && !noteContent.equals("")) {
            holder.noteContent.setText(noteContent);
        } else {
            holder.noteContent.setVisibility(View.GONE);
        }

        holder.wrapLayout.setId(position);
        holder.wrapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mItemTapListener != null) {
                    mItemTapListener.onTap(note);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public interface ItemTapListener {
        void onTap(Note note);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView noteDate;
        private TextView noteTitle;
        private TextView noteContent;
        private CardView wrapLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            wrapLayout = (CardView) itemView.findViewById(R.id.card_view);
            noteDate = (TextView) itemView.findViewById(R.id.date_value);
            noteTitle = (TextView) itemView.findViewById(R.id.title_value);
            noteContent = (TextView) itemView.findViewById(R.id.content_value);
        }
    }
}
