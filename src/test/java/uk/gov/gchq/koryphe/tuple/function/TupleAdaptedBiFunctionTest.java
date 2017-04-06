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

package uk.gov.gchq.koryphe.tuple.function;

import com.google.common.collect.Sets;
import org.junit.Test;
import uk.gov.gchq.koryphe.bifunction.MockBiFunction;
import uk.gov.gchq.koryphe.tuple.Tuple;
import uk.gov.gchq.koryphe.tuple.TupleInputAdapter;
import uk.gov.gchq.koryphe.tuple.TupleReverseOutputAdapter;
import uk.gov.gchq.koryphe.tuple.bifunction.TupleAdaptedBiFunction;
import uk.gov.gchq.koryphe.util.JsonSerialiser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TupleAdaptedBiFunctionTest {

    @Test
    public void testTupleCombination() {
        String[] inputs = new String[]{"input1", "input2", "input3"};
        Set<String> output1 = Sets.newHashSet(Collections.singletonList("input1"));
        Set<String> output2 = Sets.newHashSet("input1", "input2");
        Set<String> output3 = Sets.newHashSet("input1", "input2", "input3");
        List<Set<String>> outputsArray = new ArrayList<>();
        outputsArray.addAll(Arrays.asList(output1, output2, output3));

        Tuple<String>[] tuples = new Tuple[]{mock(Tuple.class), mock(Tuple.class), mock(Tuple.class)};
        BiFunction<String, Set<String>, Set<String>> function = mock(BiFunction.class);
        TupleInputAdapter<String, String> inputAdapter = mock(TupleInputAdapter.class);
        TupleAdaptedBiFunction<String, String, Set<String>> combiner = new TupleAdaptedBiFunction<>(function);
        TupleReverseOutputAdapter<String,Set<String>> reverseOutputAdapter = mock(TupleReverseOutputAdapter.class);

        combiner.setInputAdapter(inputAdapter);
        combiner.setReverseOutputAdapter(reverseOutputAdapter);

        // set up the function
        Tuple<String> state = null;
        for (int i = 0; i < tuples.length; i++) {
            Set<String> previousOutput = null;
            if (i > 0) {
                previousOutput = outputsArray.get(i - 1);
                given(reverseOutputAdapter.apply(null)).willReturn(outputsArray.get(i - 1));
            }
            if (i < 1) {
                given(reverseOutputAdapter.apply(null)).willReturn(null);
            }
            given(inputAdapter.apply(tuples[i])).willReturn(inputs[i]);
            given(function.apply(inputs[i], previousOutput)).willReturn(outputsArray.get(i));

            state = combiner.apply(tuples[i], state);
        }

        // check the expected calls
        for (int i = 0; i < tuples.length; i++) {
            String in1 = inputs[i];
            Set<String> in2 = null;
            if (i > 0) {
                in2 = outputsArray.get(i - 1);
            }
            verify(function, times(1)).apply(in1, in2);
        }
    }

    @Test
    public void shouldJsonSerialiseAndDeserialise() throws IOException {
        MockBiFunction function = new MockBiFunction();
        TupleAdaptedBiFunction<String, String, Set<String>> combiner = new TupleAdaptedBiFunction<>(function);

        String json = JsonSerialiser.serialise(combiner);
        TupleAdaptedBiFunction<String, String, Set<String>> deserialisedBiFunction = JsonSerialiser.deserialise(json, TupleAdaptedBiFunction.class);

        // check deserialisation
        assertNotSame(combiner, deserialisedBiFunction);

        BiFunction<String, Set<String>, Set<String>> deserialisedFunction = deserialisedBiFunction.getFunction();
        assertNotSame(function, deserialisedFunction);
        assertTrue(deserialisedFunction instanceof MockBiFunction);
    }
}
