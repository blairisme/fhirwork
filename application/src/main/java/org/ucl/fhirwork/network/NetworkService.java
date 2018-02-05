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
import org.ucl.fhirwork.network.ehr.server.EhrServer;
import org.ucl.fhirwork.network.empi.server.EmpiServer;

import javax.inject.Inject;
import java.util.Collection;

public class NetworkService
{
    private EmpiServer empiServer;
    private EhrServer ehrServer;
    private ConfigurationService configuration;

    @Inject
    public NetworkService(EmpiServer empiServer, EhrServer ehrServer, ConfigurationService configuration)
    {
        this.empiServer = empiServer;
        this.ehrServer = ehrServer;
        this.configuration = configuration;

        initialize();
    }

    public void initialize()
    {
        initializeEhrServer();
        initializeEmpiServer();
    }

    private void initializeEhrServer()
    {
        NetworkConfiguration networkProperties = configuration.getConfiguration(Configuration.Ehr);
        this.ehrServer.setAddress(networkProperties.getAddress());
        this.ehrServer.setUsername(networkProperties.getUsername());
        this.ehrServer.setPassword(networkProperties.getPassword());
    }

    private void initializeEmpiServer()
    {
        NetworkConfiguration networkProperties = configuration.getConfiguration(Configuration.Empi);
        this.empiServer.setAddress(networkProperties.getAddress());
        this.empiServer.setUsername(networkProperties.getUsername());
        this.empiServer.setPassword(networkProperties.getPassword());
    }

    public EhrServer getEhrServer()
    {
        return ehrServer;
    }

    public EmpiServer getEmpiServer()
    {
        return empiServer;
    }
}
