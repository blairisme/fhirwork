/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.common.reflect;

import org.junit.Assert;
import org.junit.Test;

public class TypeUtilsTest
{
    @Test
    public void newInstanceTest() {
        String instance = TypeUtils.newInstance(String.class);
        Assert.assertNotNull(instance);
    }

    @Test (expected = ReflectionException.class)
    public void noDefaultConstructionTest() {
        TypeUtils.newInstance(TestClass.class);
    }

    private static class TestClass {
        public TestClass(Object parameter) {
            parameter.toString();
        }
    }
}
