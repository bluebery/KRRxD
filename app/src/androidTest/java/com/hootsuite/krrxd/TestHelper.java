package com.hootsuite.krrxd;

import static org.mockito.Mockito.mock;

public class TestHelper {
    public static <T> io.reactivex.Observer<T> mockObserver() {
        return mock(io.reactivex.Observer.class);
    }
}
