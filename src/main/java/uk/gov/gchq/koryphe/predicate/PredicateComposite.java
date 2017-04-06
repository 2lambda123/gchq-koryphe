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

package uk.gov.gchq.koryphe.predicate;

import uk.gov.gchq.koryphe.composite.Composite;
import java.util.List;
import java.util.function.Predicate;


public class PredicateComposite<T> extends Composite<Predicate<T>> implements IKoryphePredicate<T> {

    public PredicateComposite() {
        super();
    }

    public PredicateComposite(final List<Predicate<T>> functions) {
        super(functions);
    }

    @Override
    public boolean test(final T input) {
        for (final Predicate<T> predicate : getFunctions()) {
            if (!predicate.test(input)) {
                return false;
            }
        }
        return true;
    }
}
