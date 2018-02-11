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

import org.ucl.fhirwork.configuration.ConfigService;
import org.ucl.fhirwork.configuration.NetworkConfigData;
import org.ucl.fhirwork.configuration.NetworkConfigType;
import org.ucl.fhirwork.network.ehr.server.EhrServer;
import org.ucl.fhirwork.network.empi.server.EmpiServer;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NetworkService
{
    private EmpiServer empiServer;
    private EhrServer ehrServer;
    private ConfigService configuration;

    @Inject
    public NetworkService(EmpiServer empiServer, EhrServer ehrServer, ConfigService configuration)
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
        NetworkConfigData networkProperties = configuration.getNetworkConfig(NetworkConfigType.Ehr);
        this.ehrServer.setAddress(System.getProperty("network.ehr.address", networkProperties.getAddress()));
        this.ehrServer.setUsername(System.getProperty("network.ehr.username", networkProperties.getUsername()));
        this.ehrServer.setPassword(System.getProperty("network.ehr.password", networkProperties.getPassword()));
    }

    private void initializeEmpiServer()
    {
        NetworkConfigData networkProperties = configuration.getNetworkConfig(NetworkConfigType.Empi);
        this.empiServer.setAddress(System.getProperty("network.empi.address", networkProperties.getAddress()));
        this.empiServer.setUsername(System.getProperty("network.empi.username", networkProperties.getUsername()));
        this.empiServer.setPassword(System.getProperty("network.empi.password", networkProperties.getPassword()));
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
