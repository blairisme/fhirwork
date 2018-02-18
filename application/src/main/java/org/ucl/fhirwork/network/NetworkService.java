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

import org.ucl.fhirwork.configuration.*;
import org.ucl.fhirwork.network.ehr.server.EhrServer;
import org.ucl.fhirwork.network.empi.server.EmpiServer;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Instances of this class manage connections to the EMPI and EHR servers.
 *
 * @author Blair Butterworth
 */
@Singleton
public class NetworkService implements ConfigObserver
{
    private EmpiServer empiServer;
    private EhrServer ehrServer;
    private ConfigService configuration;

    @Inject
    public NetworkService(EmpiServer empiServer, EhrServer ehrServer, ConfigService configuration) {
        this.empiServer = empiServer;
        this.ehrServer = ehrServer;
        this.configuration = configuration;
        this.configuration.addObserver(this);
        initialize();
    }

    public EhrServer getEhrServer() {
        return ehrServer;
    }

    public EmpiServer getEmpiServer() {
        return empiServer;
    }

    @Override
    public void configurationUpdated() {
        initialize();
    }

    private void initialize() {
        initializeEhrServer(configuration.getNetworkConfig(NetworkConfigType.Ehr));
        initializeEmpiServer(configuration.getNetworkConfig(NetworkConfigType.Empi));
    }

    private void initializeEhrServer(NetworkConfigData networkProperties) {
        this.ehrServer.setConnectionDetails(
            System.getProperty("network.ehr.address", networkProperties.getAddress()),
            System.getProperty("network.ehr.username", networkProperties.getUsername()),
            System.getProperty("network.ehr.password", networkProperties.getPassword()));
    }

    private void initializeEmpiServer(NetworkConfigData networkProperties) {
        this.empiServer.setConnectionDetails(
            System.getProperty("network.empi.address", networkProperties.getAddress()),
            System.getProperty("network.empi.username", networkProperties.getUsername()),
            System.getProperty("network.empi.password", networkProperties.getPassword()));
    }
}
