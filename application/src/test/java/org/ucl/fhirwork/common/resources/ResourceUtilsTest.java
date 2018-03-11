/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.common.resources;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class ResourceUtilsTest
{
    @Test
    public void getResourceTest() {
        File resource = ResourceUtils.getResource("configuration/cache.json");
        Assert.assertNotNull(resource);
        Assert.assertTrue(resource.exists());
    }

    @Test (expected = ResourceNotFoundException.class)
    public void getResourceMissingTest() {
        ResourceUtils.getResource("doesntExist.txt");
    }
}
