package example.com.testchiapplication.screens.base;

public interface BasePresenter<T> {

    void takeView(T view);

    void dropView();

}
