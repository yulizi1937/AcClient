package thereisnospon.acclient.modules.discuss;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import thereisnospon.acclient.data.DiscussItem;
import thereisnospon.acclient.utils.LoadListCallback;

/**
 * Created by yzr on 16/9/9.
 */
public class DiscussPresenter implements DiscussContact.Presenter {


    DiscussContact.Model model;
    DiscussContact.View view;


    public DiscussPresenter(DiscussContact.View view) {
        this.view=view;
        this.model=new DiscussModel();
    }

    public DiscussPresenter(DiscussContact.View view, String pid) {
        this.view = view;
        model=new DiscussModel(pid);
    }


    @Override
    public void requestRefresh() {
        Observable.just(1)
                .observeOn(Schedulers.io())
                .map(new Func1<Integer, List<DiscussItem>>() {
                    @Override
                    public List<DiscussItem> call(Integer integer) {
                        return model.requestRefresh();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadListCallback<List<DiscussItem>>() {
                    @Override
                    public void onSuccess(List<DiscussItem> discussItems) {
                        view.onRefreshSuccess(discussItems);
                    }
                    @Override
                    public void onFailure(String err) {
                        view.onFailure(err);
                    }
                });
    }

    @Override
    public void requestMore() {
        Observable.just(1)
                .observeOn(Schedulers.io())
                .map(new Func1<Integer, List<DiscussItem>>() {
                    @Override
                    public List<DiscussItem> call(Integer integer) {
                        return model.requestMore();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadListCallback<List<DiscussItem>>() {
                    @Override
                    public void onSuccess(List<DiscussItem> discussItems) {
                        view.onMoreSuccess(discussItems);
                    }
                    @Override
                    public void onFailure(String err) {
                        view.onFailure(err);
                    }
                });
    }


}
