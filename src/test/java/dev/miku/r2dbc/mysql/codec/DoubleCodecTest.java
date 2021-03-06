/*
 * Copyright 2018-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.miku.r2dbc.mysql.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Unit tests for {@link DoubleCodec}.
 */
class DoubleCodecTest implements CodecTestSupport<Double> {

    private final Double[] doubles = {
        0.0,
        -0.0,
        1.0,
        -1.0,
        1.101,
        -1.101,
        Double.MAX_VALUE,
        Double.MIN_VALUE,
        Double.MIN_NORMAL,
        -Double.MIN_NORMAL,
        // Following should not be permitted by MySQL server (i.e. the SQL standard), but also test.
        Double.NaN,
        Double.POSITIVE_INFINITY,
        Double.NEGATIVE_INFINITY,
    };

    @Override
    public DoubleCodec getCodec(ByteBufAllocator allocator) {
        return new DoubleCodec(allocator);
    }

    @Override
    public Double[] originParameters() {
        return doubles;
    }

    @Override
    public Object[] stringifyParameters() {
        return doubles;
    }

    @Override
    public ByteBuf[] binaryParameters(Charset charset) {
        return Arrays.stream(doubles)
            .map(it -> Unpooled.buffer(Double.BYTES, Double.BYTES).writeDoubleLE(it))
            .toArray(ByteBuf[]::new);
    }

    @Override
    public ByteBuf sized(ByteBuf value) {
        return value;
    }
}
