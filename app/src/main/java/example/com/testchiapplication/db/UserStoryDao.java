package example.com.testchiapplication.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import example.com.testchiapplication.model.UserStory;
import io.reactivex.Single;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserStoryDao {
    @Query("SELECT * FROM userStory")
    Single<List<UserStory>> getAllStories();

    @Insert(onConflict = REPLACE)
    List<Long> insertUserStories(List<UserStory> stories);

    @Query("DELETE FROM userStory")
    int deleteAllStories();
}

