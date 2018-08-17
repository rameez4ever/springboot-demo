package com.rmz.first.backendRunner;

import com.rmz.common.BackendRunner;
import com.rmz.first.FirstApplication;

public class FirstServiceBackendRunner extends BackendRunner{
	public FirstServiceBackendRunner() {
        super(FirstApplication.class, CustomizationBean.class);
    }
}
