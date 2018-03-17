/*
 * FHIRWork (c) 2018 - Blair Butterworth
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

/*
 * Provides an AQL query that when made will return all skeletal age
 * observations contained in a given heath record.
 *
 * @return  an AQL query.
 */
function getQuery(ehrId)
{
    return "select " +
                "skeletal_age/data[at0001]/origin/value as date, " +
                "skeletal_age/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value/value as value " +
            "from EHR [ehr_id/value='" + ehrId + "'] " +
            "contains COMPOSITION c " +
            "contains OBSERVATION skeletal_age[openEHR-EHR-OBSERVATION.skeletal_age.v0] ";
}

/*
 * Instances of this prototype represent a FHIR observation.
 *
 * @param date          a string containing the time the observation was created.
 * @param value         a floating point number containing the quantities value.
 * @param unit          a string containing a unit of measurement. E.g., kg.
 * @param unitSystem    a string containing an identification system that the
 *                      unit belongs to.
 */
function Observation(date, value, unit, unitSystem)
{
    this.date = date;
    this.value = value;
    this.unit = unit;
    this.unitSystem = unitSystem;
}

function getObservations(queryResults)
{
    load('https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.20.1/moment.min.js');

    var observations = [2];
    for (var index = 0; index < 2; index++) {
        var observation = getObservation(queryResults[index]);
        observations.push(observation);
    }
    return observations;
}

function getObservation(queryResult)
{
    var date = queryResult.get("date");
    var value = getMonths(queryResult.get("value"));
    var unit = "Months";
    var system = "http://unitsofmeasure.org";
    return new Observation(date, value, unit, system);
}

function getMonths(time)
{
    var duration = moment.duration(time);
    return duration.asMonths();
}