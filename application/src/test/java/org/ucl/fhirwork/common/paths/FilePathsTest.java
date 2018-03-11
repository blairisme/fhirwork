/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.common.paths;

import org.junit.Assert;
import org.junit.Test;
import org.ucl.fhirwork.common.paths.FilePaths;

import java.io.File;

public class FilePathsTest
{
    @Test
    public void getUserDirTest() {
        File userDir = FilePaths.getUserDir();
        Assert.assertNotNull(userDir);
        Assert.assertTrue(userDir.exists());
    }

    @Test
    public void getUserSubDirTest() {
        File userDir = FilePaths.getUserDir();
        File subDir = FilePaths.getUserDir("test");
        Assert.assertNotNull(subDir);
        Assert.assertEquals(new File(userDir, "test"), subDir);
    }

    @Test
    public void getTempDirTest() {
        File tempDir = FilePaths.getTempDir();
        Assert.assertNotNull(tempDir);
        Assert.assertTrue(tempDir.exists());
    }

    @Test
    public void getTempSubDirTest() {
        File tempDir = FilePaths.getTempDir();
        File subDir = FilePaths.getTempDir("test");
        Assert.assertNotNull(subDir);
        Assert.assertEquals(new File(tempDir, "test"), subDir);
    }
}
