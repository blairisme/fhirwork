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

import org.ucl.fhirwork.common.network.Rest.RestException;
import org.ucl.fhirwork.common.network.exception.ResourceMissingException;
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
     * Returns the {@link HealthRecord} belonging to the patient with the given
     * identifier.
     *
     * @param id        the identifier of a patient.
     * @param namespace the namespace the given identifier belongs to.
     * @return          a {@code HealthRecord}.
     * @throws RestException            thrown if an error occurs whilst
     *                                  communicating with the EHR server.
     * @throws ResourceMissingException thrown if a matching {@code HealthRecord}
     *                                  isn't found.
     */
    HealthRecord getHealthRecord(String id, String namespace) throws RestException, MissingRecordException;

    /**
     * Executes the given AQL query and returns the results serialized into
     * the given class.
     *
     * @param query an AQL query.
     * @param type
     * @param <T>
     * @return
     * @throws RestException            thrown if an error occurs whilst
     *                                  communicating with the EHR server.
     */
    <T extends QueryBundle> T query(String query, Class<T> type) throws RestException;
}

