package prb.com.altarix.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import prb.com.altarix.R;
import prb.com.altarix.model.Note;

/**
 * Created by Босс on 30.03.2019.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.TaskViewHolder> {



    final private ItemClickListener mItemClickListener;
    private List<Note> notes;
    private Context mContext;


    public NoteAdapter(ItemClickListener mItemClickListener, Context mContext) {
        this.mItemClickListener = mItemClickListener;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.note_item,parent,false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        Note note = notes.get(position);
        String description = note.getTitle();
        int priority = note.getPriority();
        holder.noteTitle.setText(description);

        byte[] sImage = note.getImage();
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(sImage);
        Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
        holder.image.setImageBitmap(bitmap);

        if(priority == 1){
            holder.priorityView.setBackgroundColor(Color.RED);
        }
        if(priority == 2){
            holder.priorityView.setBackgroundColor(Color.YELLOW);
        }
        if (priority == 3){
            holder.priorityView.setBackgroundColor(Color.GREEN);
        }

    }

    @Override
    public int getItemCount() {
        return (notes == null) ? 0 : notes.size();
    }


    public List<Note> getmTaskEntries() {
        return notes;
    }

    public void setTasks(List<Note> taskEntries){
        notes = taskEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener{
        void onItemClickListener(int id);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.noteTitle)
        TextView noteTitle;

        @BindView(R.id.priorityView)
        View priorityView;

        @BindView(R.id.imageView)
        ImageView image;



        public TaskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int elementId = notes.get(getAdapterPosition()).getId();
                    mItemClickListener.onItemClickListener(elementId);
                }
            });
        }
    }
}

