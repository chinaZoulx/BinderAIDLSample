// IRemoteService.aidl
package com.example.binderclient;

// Declare any non-default types here with import statements

interface IRemoteService {

    int getPid();
    void method0();
    int method1(int a, int b);
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}