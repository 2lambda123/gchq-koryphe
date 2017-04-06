/*
 * Copyright 2017 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.koryphe.impl.filter;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import uk.gov.gchq.koryphe.predicate.KoryphePredicate;
import java.util.function.Predicate;

/**
 * A {@link java.util.function.Predicate} that returns the inverse of the wrapped function.
 *
 * @param <I> Type of input to be validated
 */
public final class Not<I> extends KoryphePredicate<I> {
    private Predicate<I> function;

    public Not() {
    }

    public Not(final Predicate<I> function) {
        setFunction(function);
    }

    public void setFunction(final Predicate<I> function) {
        this.function = function;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
    public Predicate<I> getFunction() {
        return function;
    }

    @Override
    public boolean test(final I input) {
        return null != function && !function.test(input);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (null == o || !getClass().equals(o.getClass())) {
            return false;
        }

        final Not not = (Not) o;
        return new EqualsBuilder()
                .append(function, not.function)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(function)
                .toHashCode();
    }
}
