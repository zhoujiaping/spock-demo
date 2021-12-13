package com.example.demo;

import java.util.function.Supplier;

public class Fns {
    public static <T> T invoke(Supplier<T> fn){
        return fn.get();
    }
}
