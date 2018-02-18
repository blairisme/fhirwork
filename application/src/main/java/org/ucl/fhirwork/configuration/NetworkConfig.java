/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.configuration;

/**
 * Instances of this class represent a container for all persisted network
 * configuration data.
 *
 * @author Chenghui Fan
 * @author Blair Butterworth
 */
@SuppressWarnings("UnusedDeclaration")
public class NetworkConfig
{
    private NetworkConfigData empi;
    private NetworkConfigData ehr;

    public NetworkConfig(NetworkConfigData empi, NetworkConfigData ehr) {
        this.empi = empi;
        this.ehr = ehr;
    }

    public NetworkConfigData getEmpi() {
        return empi;
    }

    public NetworkConfigData getEhr() {
        return ehr;
    }

    public NetworkConfigData getData(NetworkConfigType type){
        switch (type){
            case Ehr: return ehr;
            case Empi: return empi;
            default: throw new ConfigMissingException(type.name());
        }
    }

    public NetworkConfig setData(NetworkConfigType type, NetworkConfigData data){
        switch (type){
            case Ehr: return new NetworkConfig(empi, data);
            case Empi: return new NetworkConfig(data, ehr);
            default: throw new ConfigMissingException(type.name());
        }
    }
}
