/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.test;

import org.ucl.fhirwork.network.NetworkService;
import org.ucl.fhirwork.network.ehr.server.EhrServer;
import org.ucl.fhirwork.network.empi.server.EmpiServer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockNetworkService
{
    public static NetworkService get()
    {
        EmpiServer empiServer = mock(EmpiServer.class);
        EhrServer ehrServer = mock(EhrServer.class);
        NetworkService networkService = mock(NetworkService.class);
        when(networkService.getEhrServer()).thenReturn(ehrServer);
        when(networkService.getEmpiServer()).thenReturn(empiServer);
        return networkService;
    }
}
