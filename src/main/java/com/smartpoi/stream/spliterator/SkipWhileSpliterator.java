package com.smartpoi.stream.spliterator;

import java.util.Spliterator;
import java.util.function.Predicate;

public class SkipWhileSpliterator<T> extends AbstractBetweenSpliterator<T> {

    public SkipWhileSpliterator(Spliterator<T> source,
                                Predicate<T> condition) {
        super(source, condition, condition.negate(), true, false);
    }

    @Override
    protected boolean shouldBeVisited(T value) {
        return isRightRelative(value);
    }
}
