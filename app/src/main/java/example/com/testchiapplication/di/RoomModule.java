package example.com.testchiapplication.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.com.testchiapplication.db.AppDatabase;
import example.com.testchiapplication.db.UserStoryDao;

@Module
public class RoomModule {

    @Singleton
    @Provides
    AppDatabase provideAppDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "testchi-db")
                .build();
    }

    @Singleton
    @Provides
    UserStoryDao providesUserDao(AppDatabase appDatabase) {
        return appDatabase.userStoryDao();
    }
}
