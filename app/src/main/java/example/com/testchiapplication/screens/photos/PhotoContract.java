package example.com.testchiapplication.screens.photos;

import java.util.List;

import example.com.testchiapplication.screens.base.BasePresenter;
import example.com.testchiapplication.screens.base.BaseView;

public interface PhotoContract {
    interface View extends BaseView<PhotoContract.Presenter> {
        void setImages(List<String> imagesPaths);
    }

    interface Presenter extends BasePresenter<PhotoContract.View> {
        void getAllShownImagesPath();
    }
}
