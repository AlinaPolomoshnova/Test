package example.com.testchiapplication.screens.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.com.testchiapplication.Constants;
import example.com.testchiapplication.R;
import example.com.testchiapplication.model.UserStory;
import example.com.testchiapplication.utils.Utils;

class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {

    private final Context mContext;
    private List<UserStory> mStrories;
    private UserStoryType mType;

    public StoryAdapter(@NonNull Context context, UserStoryType type, List<UserStory> userStories) {
        mContext = context;
        mType = type;
        mStrories = userStories;
    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.story_item, viewGroup, false);
        return new StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StoryViewHolder holder, int position) {
        if (mStrories != null) {
            UserStory userStory = mStrories.get(position);
            switch (mType) {
                case STORY_TYPE:
                    holder.title.setText(userStory.getTitle());
                    holder.subtitle.setText(userStory.getAuthor());
                    holder.date.setText(Utils.setFormatedData(Constants.HIT_DATE_FORMAT,
                            userStory.getStoryDate()));
                   if (userStory.getTags() != null && !userStory.getTags().isEmpty()) {
                       holder.tags.setSelected(true);
                       List<String> tags = new ArrayList<>();
                       tags.addAll(userStory.getTags());
                       StringBuilder builder = new StringBuilder();
                       for (int i = 0; i < tags.size(); i++) {
                           if (i != 0) {
                               builder.append(", ");
                           }
                           builder.append(tags.get(i));
                           holder.tags.setText(builder.toString());
                       }
                   }

                    break;

                case USER_TYPE:
                    holder.title.setText(userStory.getUsername());
                    StringBuilder builder = new StringBuilder();
                    builder.append(mContext.getResources().getString(R.string.karma));
                    builder.append(" ");
                    builder.append(String.valueOf(userStory.getKarma()));
                    holder.subtitle.setText(builder.toString());
                    holder.date.setText(Utils.setFormatedData(Constants.USER_DATE_FORMAT, userStory.getStoryDate()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mStrories != null ? mStrories.size() : 0;
    }

    class StoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.subtitle)
        TextView subtitle;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.tags)
        TextView tags;

        StoryViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
