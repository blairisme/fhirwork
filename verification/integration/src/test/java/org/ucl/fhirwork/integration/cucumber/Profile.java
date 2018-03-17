/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.cucumber;

public class Profile
{
    private String id;
    private String domain;
    private String first;
    private String last;
    private String gender;
    private String birthday;

    public Profile(String id, String domain, String first, String last, String gender, String birthday) {
        this.id = id;
        this.domain = domain;
        this.first = first;
        this.last = last;
        this.gender = gender;
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public String getDomain() {
        return domain;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }
}