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
package uk.gov.gchq.koryphe.binaryoperator;

public abstract class KorypheBinaryOperator<T> implements IKorypheBinaryOperator<T> {
    @Override
    public T apply(final T a, final T b) {
        if (null == a) {
            return b;
        }

        if (null == b) {
            return a;
        }

        return _apply(a, b);
    }

    protected abstract T _apply(final T a, final T b);

    @Override
    public boolean equals(final Object other) {
        return this == other || classEquals(other);
    }

    protected boolean classEquals(final Object other) {
        return null != other && getClass().equals(other.getClass());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
