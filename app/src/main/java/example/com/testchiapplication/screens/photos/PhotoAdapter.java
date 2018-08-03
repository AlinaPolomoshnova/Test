package example.com.testchiapplication.screens.photos;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.com.testchiapplication.R;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private final Context mContext;
    private final OnItemClickListener mListener;
    private List<String> mImages;
    private Random mRandom = new Random();

    public PhotoAdapter(@NonNull Context context, List<String> imagePaths, OnItemClickListener listener) {
        mContext = context;
        mListener = listener;
        mImages = imagePaths;
    }


    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.photo_item, viewGroup, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        if (mImages != null) {
            holder.cardView.getLayoutParams().height = getRandomIntInRange(400, 200);
            String image = mImages.get(position);
            Glide
                    .with(mContext)
                    .load(image)
                    .into(holder.photo);
        }
    }

    private int getRandomIntInRange(int max, int min) {
        return mRandom.nextInt((max - min) + min) + min;
    }

    @Override
    public int getItemCount() {
        return mImages != null ? mImages.size() : 0;
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.photo)
        ImageView photo;
        @BindView(R.id.photo_card_view)
        CardView cardView;

        PhotoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String photoPath = mImages.get(adapterPosition);
            mListener.onItemClick(photoPath, photo, mContext.getString(R.string.photo_transition));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String item, ImageView sharedImageView, String transitionName);
    }
}

