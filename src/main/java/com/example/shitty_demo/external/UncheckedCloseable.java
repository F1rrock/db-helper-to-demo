package com.example.shitty_demo.external;

public interface UncheckedCloseable extends Runnable, AutoCloseable {
    default void run() {
        try {
            close();
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    static UncheckedCloseable wrap(AutoCloseable c) {
        return c::close;
    }
    default UncheckedCloseable nest(AutoCloseable c) {
        return () -> {
            try (
                    UncheckedCloseable ignored = this
            ) {
                c.close();
            }
        };
    }
}
