package com.example.restapi.catalog.service.implementation;


import com.example.restapi.catalog.exceptions.NotFoundException;
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
        this.officeService = officeService;
    }

    /**
     * Метод получения списка организации по запросу
     * @param rawOrganization
     * @return
     */
    public List<RawOrganization> getOrgList(RawOrganization rawOrganization) {
        if (rawOrganization == null) {
            throw new NotFoundException("Ошибка в аргументе запроса");
        }
        List<Organization> all = organizationRepo.findOrgByName(rawOrganization.getName());
        List<RawOrganization> result = new ArrayList<>();
        if (rawOrganization.getInn() != null) {
            all.removeIf(x -> !rawOrganization.getInn().equals(x.getInn()));
        }

        for (Iterator<Organization> iterableOrg = all.iterator(); iterableOrg.hasNext(); ) {
            Organization currentOrg = iterableOrg.next();
            Office bindedOffice = officeRepo.findByOrganizationAndIsMain(currentOrg, true);
            if (rawOrganization.getIsActive() != null) {
                if (!bindedOffice.getIsActive().equals(rawOrganization.getIsActive())) {
                    iterableOrg.remove();
                    continue;
                }
            }
            result.add(new RawOrganization(currentOrg.getOrgId(), currentOrg.getName(), bindedOffice.getIsActive()));
        }
        return result;
    }

    /**
     * Метод добавления новой организации, при создании организации поля не приналежащие к организации такие как адрес и телефон сохранаются в создаваемом внтури этой организации офисе
     * который является головным офисом и имеет признак isMain=true, офису присваетвася сокращенное имя организации
     * @param rawOrganization
     * @return
     */
    @Transactional
    public ResultResponse add(RawOrganization rawOrganization) {
        if (rawOrganization == null) {
            throw new NotFoundException("Ошибка в аргументе запроса");
        }
        Organization organization = new Organization();
        BeanUtils.copyProperties(rawOrganization, organization);
        Organization result = organizationRepo.save(organization);
        Office mainOffice = new Office(result, rawOrganization.getFullName(), rawOrganization.getAddress(), rawOrganization.getPhone(), rawOrganization.getIsActive(), true);
        officeRepo.save(mainOffice);
        return new ResultResponse("success");
    }

    /**
     * Метод изменения информации сущевствующей организации
     * @param rawOrganization
     * @param orgDest
     * @return
     */
    @Transactional
    public ResultResponse update(RawOrganization rawOrganization, Organization orgDest) {
        if (rawOrganization == null || orgDest == null) {
            throw new NotFoundException("Ошибка в аргументе запроса");
        }
        BeanUtils.copyProperties(rawOrganization, orgDest, "id");
        RawOffice updatedOffice = new RawOffice();
        BeanUtils.copyProperties(rawOrganization, updatedOffice);
        officeService.update(updatedOffice);
        organizationRepo.save(orgDest);
        return new ResultResponse("success");
    }

    public RawOrganization getOne(Integer id) {
        if (id == null) {
            throw new NotFoundException("Ошибка в аргументе запроса");
        }
        Organization organization = organizationRepo.findOrgByOrgId(id);
        if (organization == null) {
            return new RawOrganization();
        }
        Office mainOffice = officeRepo.findByOrganizationAndIsMain(organization, true);
        RawOrganization result = new RawOrganization();
        BeanUtils.copyProperties(organization, result);
        result.setAddress(mainOffice.getAddress());
        result.setPhone(mainOffice.getPhone());
        result.setIsActive(mainOffice.getIsActive());
        return result;
    }
}