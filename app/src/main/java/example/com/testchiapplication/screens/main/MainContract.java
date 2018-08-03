package example.com.testchiapplication.screens.main;

import java.util.List;

import example.com.testchiapplication.model.Hit;
import example.com.testchiapplication.model.Story;
import example.com.testchiapplication.model.UserStory;
import example.com.testchiapplication.screens.base.BasePresenter;
import example.com.testchiapplication.screens.base.BaseView;

public interface MainContract {
    interface View extends BaseView<Presenter> {
      void showStrories(List<UserStory> hitList);
      void showError(String message);
    }

    interface Presenter extends BasePresenter<View> {
        void getStories(int page);
        void getStoriesFromDB();
        void dispose();
    }
}
