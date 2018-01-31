/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network;

import org.ucl.fhirwork.configuration.Configuration;
import org.ucl.fhirwork.configuration.ConfigurationService;
import org.ucl.fhirwork.configuration.NetworkConfiguration;
import org.ucl.fhirwork.network.empi.server.EmpiServer;

import javax.inject.Inject;
import java.util.Collection;

public class NetworkService
{
    private EmpiServer empiServer;

    @Inject
    public NetworkService(EmpiServer empiServer, ConfigurationService configurationService)
    {
        NetworkConfiguration configuration = configurationService.getConfiguration(Configuration.Empi);

        this.empiServer = empiServer;
        this.empiServer.setAddress(configuration.getAddress());
        this.empiServer.setUsername(configuration.getUsername());
        this.empiServer.setPassword(configuration.getPassword());
    }

    public EmpiServer getEmpiServer()
    {
        return empiServer;
    }
}
