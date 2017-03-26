package thereisnospon.acclient.modules.problem.list;

import android.util.Log;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import thereisnospon.acclient.data.HdojProblem;

/**
 * @author thereisnospon
 * 首页题目 Presenter
 * Created by yzr on 16/6/5.
 */
public class HdojPresenterImpl implements HdojContact.Presenter {

    HdojContact.Model model;
    HdojContact.View view;

    public HdojPresenterImpl(HdojContact.View view,int page) {
        this.view=view;
        model=new HdojProbelemModeImpl(page);
    }


    @Override
    public void requestRefresh() {
        Observable.just(1)
                .observeOn(Schedulers.io())
                .map(new Func1<Object, List<HdojProblem>>() {
                    @Override
                    public List<HdojProblem> call(Object object) {
                        return model.requestRefresh();
                    }
                })
                .filter(new Func1<List<HdojProblem>, Boolean>() {
                    @Override
                    public Boolean call(List<HdojProblem> hdojProblems) {
                        return hdojProblems!=null&&hdojProblems.size()>0;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<HdojProblem>>() {
                    @Override
                    public void call(List<HdojProblem> hdojProblems) {
                        view.onRefreshSuccess(hdojProblems);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.onFailure("刷新失败");
                    }
                });
    }

    @Override
    public void requestMore() {
        Observable.just(1)
                .observeOn(Schedulers.io())
                .map(new Func1<Object, List<HdojProblem>>() {
                    @Override
                    public List<HdojProblem> call(Object object) {
                        return model.requestMore();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<HdojProblem>>() {
                    @Override
                    public void call(List<HdojProblem> hdojProblems) {
                        if(hdojProblems!=null&&hdojProblems.size()>0){
                            view.onMoreSuccess(hdojProblems);
                        }else{
                            view.onFailure("获取失败");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.onFailure("获取失败");
                    }
                });
    }

    @Deprecated
    @Override
    public void loadPage(final int page) {

    }



}