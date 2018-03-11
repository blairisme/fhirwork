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

import com.google.inject.AbstractModule;
import org.ucl.fhirwork.network.ehr.server.BasicEhrServer;
import org.ucl.fhirwork.network.ehr.server.EhrServer;
import org.ucl.fhirwork.network.empi.server.BasicEmpiServer;
import org.ucl.fhirwork.network.empi.server.CachedEmpiServer;
import org.ucl.fhirwork.network.empi.server.ConfigEmpiServer;
import org.ucl.fhirwork.network.empi.server.EmpiServer;

/**
 * Instances of this class control the bindings used by the dependency injector
 * when generating the class hierarchy.
 *
 * @author Blair Butterworth
 */
public class ApplicationModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(EhrServer.class).to(BasicEhrServer.class);
        bind(EmpiServer.class).to(ConfigEmpiServer.class);
    }
}
