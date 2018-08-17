package com.rmz.second.backendRunner;

import com.rmz.common.BackendRunner;
import com.rmz.second.SecondApplication;

public class SecondServiceBackendRunner extends BackendRunner{
	public SecondServiceBackendRunner() {
        super(SecondApplication.class, CustomizationBean.class);
    }
}
