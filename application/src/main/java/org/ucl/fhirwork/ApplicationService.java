/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.ucl.fhirwork.common.framework.Operation;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Instances of this class facilitate the execution of {@link Operation}s.
 *
 * @author Blair Butterworth
 */
public class ApplicationService
{
    private static ApplicationService instance;

    public static synchronized ApplicationService instance() {
        if (instance == null){
            instance = new ApplicationService();
        }
        return instance;
    }

    private Injector injector;

    private ApplicationService() {
        injector = Guice.createInjector(new ApplicationModule());
    }

    public <T> T get(Class<T> type) {
        return injector.getInstance(type);
    }
}
