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

package uk.gov.gchq.koryphe.bifunction;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class MockBiFunction implements BiFunction<String, Set<String>, Set<String>> {
    @Override
    public Set<String> apply(String input, Set<String> state) {
        Set<String> newState = state == null ? new HashSet<>() : state;
        newState.add(input);
        return newState;
    }
}