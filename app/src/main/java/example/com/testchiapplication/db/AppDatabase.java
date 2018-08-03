package example.com.testchiapplication.db;

import example.com.testchiapplication.model.UserStory;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = {UserStory.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class, ListConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserStoryDao userStoryDao();
}
