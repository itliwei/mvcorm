package io.github.itliwei.mvcorm.orm;

import java.util.concurrent.atomic.AtomicReference;

/**
 * NullableImmutableHolder
 * Created by cheshun on 16/12/18.
 */
public final class NullableImmutableHolder<V> {

    private final AtomicReference<V> holder = new AtomicReference<>(null);

    public V get() {
        return holder.get();
    }

    public void set(V v) {
        if (!this.holder.compareAndSet(null, v)) {
            throw new IllegalStateException("Unable to set another non-null value.");
        }
    }
}
