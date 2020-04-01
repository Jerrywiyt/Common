package com.lujunyu.juice.best;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.ToString;

@Singleton
@ToString
public class Service {
    public Service(){
       // InjectorInstance.injector.injectMembers(this);
    }
    @Inject
    private Biz biz;
}
