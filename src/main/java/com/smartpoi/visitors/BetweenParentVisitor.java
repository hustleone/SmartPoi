package com.smartpoi.visitors;

import com.smartpoi.stream.spliterator.AbstractBetweenSpliterator;
import com.smartpoi.stream.spliterator.BetweenSpliterator;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Getter(value = AccessLevel.PROTECTED)
public class BetweenParentVisitor<Parent extends Iterable<Child>, Child> extends ChildVisitor<Parent, Child>
        implements Consumer<Parent> {

    private final Predicate<Child> leftCondition;
    private final Predicate<Child> rightCondition;
    private final Boolean inclusiveLeft;
    private final Boolean inclusiveRight;

    public BetweenParentVisitor(Consumer<Child> visitor,
                                Predicate<Child> leftCondition,
                                Predicate<Child> rightCondition,
                                boolean inclusiveLeft,
                                boolean inclusiveRight) {
        super(visitor);
        this.leftCondition = leftCondition;
        this.rightCondition = rightCondition;
        this.inclusiveLeft = inclusiveLeft;
        this.inclusiveRight = inclusiveRight;
    }

    public BetweenParentVisitor(Consumer<Child> cellVisitor,
                                Predicate<Child> leftCondition,
                                Predicate<Child> rightCondition) {
        super(cellVisitor);
        this.leftCondition = leftCondition;
        this.rightCondition = rightCondition;
        this.inclusiveLeft = null;
        this.inclusiveRight = null;
    }

    @Override
    public void accept(Parent parent) {
        Spliterator<Child> spliterator = buildSpliterator(parent.spliterator());
        fromSpliterator(spliterator).forEach(visitor);
    }

    @Override
    public AbstractBetweenSpliterator<Child> buildSpliterator(Spliterator<Child> source) {
        if (inclusiveLeft == null || inclusiveRight == null) {
            return new BetweenSpliterator<>(source, leftCondition, rightCondition);
        }
        return new BetweenSpliterator<>(source, leftCondition, rightCondition, inclusiveLeft, inclusiveRight);
    }
}
