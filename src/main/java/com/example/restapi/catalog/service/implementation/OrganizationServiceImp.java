package com.example.restapi.catalog.service.implementation;


import com.example.restapi.catalog.model.Office;
import com.example.restapi.catalog.model.Organization;
import com.example.restapi.catalog.rawmodel.RawOffice;
import com.example.restapi.catalog.rawmodel.RawOrganization;
import com.example.restapi.catalog.rawmodel.ResultResponse;
import com.example.restapi.catalog.repos.OfficeRepo;
import com.example.restapi.catalog.repos.OrganizationRepo;
import com.example.restapi.catalog.service.OfficeService;
import com.example.restapi.catalog.service.OrganizationService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class OrganizationServiceImp implements OrganizationService {

    private final OrganizationRepo organizationRepo;
    private final OfficeRepo officeRepo;
    private final OfficeService officeService;

    @Autowired
    public OrganizationServiceImp(OrganizationRepo organizationRepo, OfficeRepo officeRepo, OfficeService officeService) {
        this.organizationRepo = organizationRepo;
        this.officeRepo = officeRepo;
        this.officeService=officeService;
    }

    public List<RawOrganization> getOrgList(RawOrganization rawOrganization) {
        List<Organization> all = organizationRepo.findOrgByName(rawOrganization.getName()); //находим все по обязательному полю
        List<RawOrganization> result = new ArrayList<>();  //лист который вернется пользователю
        if (rawOrganization.getInn() != null) {  ///Если указан ИНН
            all.removeIf(x -> !rawOrganization.getInn().equals(x.getInn())); ///Выкидываем всех у кого не такой ИНН как указан
        }

        for (Iterator<Organization> iterableOrg = all.iterator(); iterableOrg.hasNext(); ) { ///цикл для из списка с учетом фильторв имя и инн, в список который вернется пользователю с учетом поля isActive
            Organization currentOrg = iterableOrg.next();
            Office bindedOffice = officeRepo.findByOrganizationAndIsMain(currentOrg, true);
            if (rawOrganization.getIsActive() != null) { // Если задан isActive
                if (!bindedOffice.getIsActive().equals(rawOrganization.getIsActive())) {  ///Если статусы несопадают
                    iterableOrg.remove(); //выкидываем из списка и на следующую итерацию
                    continue;
                }
            }
            result.add(new RawOrganization(currentOrg.getOrgId(), currentOrg.getName(), bindedOffice.getIsActive()));
        }
        return result; ///Возвращаем крокодила пользователю
    }

    @Transactional
    public ResultResponse add(RawOrganization rawOrganization) {

        Organization organization = new Organization();
        BeanUtils.copyProperties(rawOrganization, organization);
        Organization result = organizationRepo.save(organization);
        Office mainOffice = new Office(result, rawOrganization.getFullName(), rawOrganization.getAddress(), rawOrganization.getPhone(), rawOrganization.getIsActive(), true);
        officeRepo.save(mainOffice);
        return new ResultResponse("success");
    }

    @Transactional
    public ResultResponse update(RawOrganization rawOrganization, Organization orgDest) {
        BeanUtils.copyProperties(rawOrganization, orgDest, "id");
        RawOffice updatedOffice = new RawOffice();
        BeanUtils.copyProperties(rawOrganization, updatedOffice);
        officeService.update(updatedOffice);
        organizationRepo.save(orgDest);
        return new ResultResponse("success");
    }

    public RawOrganization getOne(Integer id) {
        Organization organization = organizationRepo.findOrgByOrgId(id);
        if (organization == null) {
            return new RawOrganization();
        } ///Возвращаем пустую организацию в случае если нет такой
        Office mainOffice = officeRepo.findByOrganizationAndIsMain(organization, true);
        RawOrganization result = new RawOrganization();
        BeanUtils.copyProperties(organization, result);
        result.setAddress(mainOffice.getAddress());
        result.setPhone(mainOffice.getPhone());
        result.setIsActive(mainOffice.getIsActive());
        return result;
    }
}