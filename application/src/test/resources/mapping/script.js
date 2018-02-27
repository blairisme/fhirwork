/*
 * FHIRWork (c) 2018 - Blair Butterworth
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

/*
 * Instances of this prototype represent the constituent parts of an EHR query.
 * These will be used to generate a full AQL when combined with query
 * statements required by the FHIRWork engine.
 */
function Query(selectors, archetype)
{
    this.selectors = selectors;
    this.archetype = archetype;
}

/*
 * Instances of this prototype represent a FHIR observation quantity.
 *
 * @param value a floating point number containing the quantities value.
 * @param unit  a unit of measurement. E.g., kg for kilograms.
 */
function Quantity(value, unit)
{
    this.value = value;
    this.unit = unit;
}

/*
 * Provides an AQL query that when made will return all skeletal age
 * observations contained in a given heath record.
 *
 * @return  a Query instance.
 */
function getQuery(ehrId)
{
    var archetype = "openEHR-EHR-OBSERVATION.skeletal_age.v0";
    var selectors = ["data[at0001]/events[at0002]/data[at0003]/items[at0005]/value/value as value"];
    return new Query(selectors, archetype);
}

/*
 * Provides a Quantity instance, containing a value for the given query result.
 *
 * @return  a Quantity instance.
 */
function getQuantity(queryResult)
{
    load('https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.20.1/moment.min.js');

    var value = queryResult.get("value");
    var duration = moment.duration(value);
    var months = duration.asMonths();
    var unit = "Months";

    return new Quantity(months, unit);
}