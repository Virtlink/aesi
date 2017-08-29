package com.virtlink.aesi.debugging.mock;

import com.virtlink.aesi.debugging.IAesiThread;

public class MockThread implements IAesiThread {
    @Override
    public String getName() {
        return "Mock Thread";
    }
}
