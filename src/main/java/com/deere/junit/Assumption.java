package com.deere.junit;

import org.hamcrest.Matcher;
import org.junit.internal.AssumptionViolatedException;

public class Assumption {
    public static <T> void assume(final T actual, final Matcher<? extends T> matcher) {
        if (!matcher.matches(actual)) {
            throw new AssumptionViolatedException(actual, matcher);
        }
    }
}
