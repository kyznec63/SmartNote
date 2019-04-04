package prb.com.altarix.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import io.reactivex.annotations.NonNull;
import prb.com.altarix.CallBack;
import prb.com.altarix.model.Repository;

/**
 * Created by Босс on 30.03.2019.
 */

public class MainViewModel extends AndroidViewModel {

    private final Repository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getsInstance(application);
    }


    public void loadAllData(CallBack dataCallBack){
        mRepository.loadDataObserver(dataCallBack);
    }

}
