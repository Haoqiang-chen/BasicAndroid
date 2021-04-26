package cc.aiknow.basicandroid.androidarch.livedata_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cc.aiknow.basicandroid.MyApplication;

/**
 * 学习LiveData与ViewModel的相关知识
 * ViewModel:用于以有生命周期意识的方式存储、管理UI的相关数据
 * 1.ViewModel实例会在配置变更时自动保留以便新建的Activity或者Fragment使用
 * @author chen
 * @version 1.0
 * @since 2019-07-18 18:32
 */
public class MyViewModel extends ViewModel {
    private MutableLiveData<String> liveData;

    public MutableLiveData<String> getLivedate() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
        }
        return liveData;
    }
}
