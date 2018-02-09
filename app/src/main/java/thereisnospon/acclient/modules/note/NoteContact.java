package thereisnospon.acclient.modules.note;

import thereisnospon.acclient.base.mvp.MvpPullPresenter;
import thereisnospon.acclient.base.mvp.MvpPullView;
import thereisnospon.acclient.base.mvp.MvpPullModel;
import thereisnospon.acclient.data.NoteItem;

/**
 * @author thereisnospon
 * @// TODO: 17/3/26
 * Created by yzr on 16/9/9.
 */
public interface NoteContact {

    interface View extends MvpPullView<NoteItem>{

    }
    interface Presenter extends MvpPullPresenter<NoteItem>{

    }

    interface Model extends MvpPullModel<NoteItem> {

    }
}
