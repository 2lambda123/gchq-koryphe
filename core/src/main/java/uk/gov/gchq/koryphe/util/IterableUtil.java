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
package uk.gov.gchq.koryphe.util;

import java.util.Iterator;
import java.util.function.Function;

/**
 * An {@code IterableUtil} is a utility class for lazily applying a {@link Function}
 * to each element of an {@link Iterable}
 */
public abstract class IterableUtil {
    public static <I_ITEM, O_ITEM> Iterable<O_ITEM> applyFunction(final Iterable<I_ITEM> input, final Function<I_ITEM, O_ITEM> function) {
        return () -> new Iterator<O_ITEM>() {
            Iterator<? extends I_ITEM> iterator = input.iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public O_ITEM next() {
                return function.apply(iterator.next());
            }
        };
    }
}
