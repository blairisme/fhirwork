/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.ehr.server;

import org.ucl.fhirwork.common.http.RestException;
import org.ucl.fhirwork.network.ehr.data.HealthRecord;
import org.ucl.fhirwork.network.ehr.data.QueryBundle;
import org.ucl.fhirwork.network.ehr.exception.MissingRecordException;

/**
 * Instances of this class represent an EHR server. Methods exists to create,
 * read, update and delete composition and health record data.
 *
 * @author Blair Butterworth
 * @author Xiaolong Chen
 * @author Jiaming Zhou
 */
public interface EhrServer
{
    /**
     * Sets the address and authentication information used to connect to the
     * EHR server.
     *
     * @param address   the URL of an EHR server.
     * @param username  the name of an account on the EMPI server.
     * @param password  the password of an EMPI account.
     */
    void setConnectionDetails(String address, String username, String password);

    /**
     *
     * @param id
     * @param namespace
     * @return
     * @throws RestException
     * @throws MissingRecordException
     */
    HealthRecord getHealthRecord(String id, String namespace) throws RestException, MissingRecordException;

    /**
     *
     * @param query
     * @param type
     * @param <T>
     * @return
     * @throws RestException
     */
    <T extends QueryBundle> T query(String query, Class<T> type) throws RestException;
}

