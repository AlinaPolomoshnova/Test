package example.com.testchiapplication.net;

import example.com.testchiapplication.model.Story;
import example.com.testchiapplication.model.User;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IRestClient {

    @GET("v1/search_by_date")
    Observable<Story> getStories(@Query("page") int page,
                                 @Query("tags") String tag);

    @GET("v1/users/{username}")
    Observable<User> getUser(@Path("username") String userName);
}
