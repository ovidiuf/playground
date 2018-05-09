/*
 * Copyright (c) 2016 Nova Ordis LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.novaordis.playground.java.jmh;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 11/27/16
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value=2, jvmArgs={"-Xms4G", "-Xmx4G"})
public class ParallelStreamBenchmark {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final long N = 20_000_000L;

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

//    @Benchmark
//    public long sequentialSumNoBoxing() {
//
//        return LongStream.iterate(1L, i -> i + 1).limit(N).reduce(0L, Long::sum);
//    }

    @Benchmark
    public long parallelSumNoBoxing() {

        return LongStream.rangeClosed(1L, N).parallel().reduce(0L, Long::sum);
    }


//    @Benchmark
//    public long parallelSum() {
//
//        return Stream.iterate(1L, i -> i + 1).limit(N).parallel().reduce(0L, Long::sum);
//    }

//    @Benchmark
//    public long parallelSum2() {
//
//        return LongStream.rangeClosed(1L, N).parallel().reduce(0L, Long::sum);
//    }

//    @Benchmark
//    public long iterativeSum() {
//
//        long sum = 0L;
//
//        for(long i = 1; i < N; i ++) {
//
//            sum += i;
//        }
//
//        return sum;
//    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------


}
