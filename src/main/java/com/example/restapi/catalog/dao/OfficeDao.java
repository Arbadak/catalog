package com.example.restapi.catalog.dao;


import com.example.restapi.catalog.model.Office;

import java.util.List;

/**
 * Datasource access object for processing Office
 */
public interface OfficeDao {

    /**
     * Get all offices by orgId
     * @param orgId - Mandatory
     */

    List<Office> getByOrgId(Integer orgId);

    /**
     * Get single Office by officeId
     * @param officeId -Mandatory
     */

    Office summonById (Integer officeId);

    /**
     * Update specific office information
     * @param officeId  - Mandatory
     * @param orgId  - Mandatory
     * @param officeName - Mandatory
     * @param  address - Mandatory
     * @param  phone - Optional
     * @param  isActive - Optional
     * @param  isMain - Optional
     */

    void updateOffice(Integer officeId, Integer orgId, String officeName, String address, Integer phone, Boolean isActive, Boolean isMain);

    /**
     * Add a new office
     * @param orgId  - Mandatory
     * @param officeName - Mandatory
     * @param  address - Mandatory
     * @param  phone - Optional
     * @param  isActive - Optional
     * @param  isMain - Optional
     */
    void addOffice(Integer orgId, String officeName, String address, Integer phone, Boolean isActive, Boolean isMain);

}
