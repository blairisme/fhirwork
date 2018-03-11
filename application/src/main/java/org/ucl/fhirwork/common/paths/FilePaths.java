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

import java.io.File;

/**
 * Instances of this class contain common user directories.
 *
 * @author Blair Butterworth
 */
public class FilePaths
{
    public static File getTempDir() {
        return new File(System.getProperty("java.io.tmpdir"));
    }

    public static File getTempDir(String subDirectory) {
        return new File(getTempDir(), subDirectory);
    }

    public static File getUserDir() {
        return new File(System.getProperty("user.dir"));
    }

    public static File getUserDir(String subDirectory) {
        return new File(getUserDir(), subDirectory);
    }
}
