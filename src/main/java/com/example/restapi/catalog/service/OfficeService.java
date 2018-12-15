package com.example.restapi.catalog.service;

import com.example.restapi.catalog.model.Office;
import com.example.restapi.catalog.model.Organization;
import com.example.restapi.catalog.rawModel.RawOffice;
import com.example.restapi.catalog.repos.OfficeRepo;
import com.example.restapi.catalog.repos.OrganizationRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.restapi.catalog.rawModel.resultResponce;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OfficeService {

    private final OrganizationRepo organizationRepo;
    private final OfficeRepo officeRepo;

    @Autowired
    public OfficeService(OrganizationRepo organizationRepo, OfficeRepo officeRepo) {
        this.organizationRepo = organizationRepo;
        this.officeRepo = officeRepo;
    }

    public List<Office> getOfficeList (Integer orgId) {
            return officeRepo.findAllByOrgId(organizationRepo.findOrgByOrgId(orgId));
    }

public Office getOne (Integer officeId){
        return officeRepo.getOne(officeId);
}

@Transactional
public resultResponce add (RawOffice office){

        Office newoffice = new Office();
        BeanUtils.copyProperties(office, newoffice, "orgId");
        newoffice.setOrganizationId(organizationRepo.findOrgByOrgId(office.getOrgId())); ///Выдергиваем организацию из номера в сырой модели, и суем уже обьект
        officeRepo.save(newoffice);
        return new resultResponce("success");
    }

@Transactional
public resultResponce update (RawOffice office){
    Office newoffice = new Office();
    BeanUtils.copyProperties(office, newoffice, "orgId");
    newoffice.setOrganizationId(organizationRepo.findOrgByOrgId(office.getOrgId())); ///Выдергиваем организацию из номера в сырой модели, и суем уже обьект
    officeRepo.save(newoffice);
return new resultResponce("success"); }

}
