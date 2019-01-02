package com.example.restapi.catalog.service;


import com.example.restapi.catalog.model.Office;
import com.example.restapi.catalog.model.Organization;
import com.example.restapi.catalog.rawModel.RawOrganization;
import com.example.restapi.catalog.rawModel.resultResponce;
import com.example.restapi.catalog.repos.OfficeRepo;
import com.example.restapi.catalog.repos.OrganizationRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
public class OrganizationService {

    private final OrganizationRepo organizationRepo;
    private final OfficeRepo officeRepo;

    @Autowired
    public OrganizationService(OrganizationRepo organizationRepo, OfficeRepo officeRepo) {
        this.organizationRepo = organizationRepo;
        this.officeRepo = officeRepo;
    }

    public List<RawOrganization> getOrgList(RawOrganization rawOrganization) {
        List<Organization> all = organizationRepo.findOrgByName(rawOrganization.getName()); //находим все по обязательному полю
        List<RawOrganization> result = new ArrayList<>();  //лист который вернется пользователю
        if (rawOrganization.getInn() != null) {  ///Если указан ИНН
            all.removeIf(x -> !rawOrganization.getInn().equals(x.getInn())); ///Выкидываем всех у кого не такой ИНН как указан
        }

        for (Iterator<Organization> iterableOrg = all.iterator(); iterableOrg.hasNext(); ) { ///цикл для из списка с учетом фильторв имя и инн, в список который вернется пользователю с учетом поля isActive
            Organization currentOrg = iterableOrg.next();
            Office bindedOffice = officeRepo.findByOrgIdAndIsMain(currentOrg, true);
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
    public resultResponce add(RawOrganization rawOrganization) {

        Organization organization = new Organization();
        BeanUtils.copyProperties(rawOrganization, organization);
        Organization result = organizationRepo.save(organization);
        Office mainOffice = new Office(result, rawOrganization.getFullName(), rawOrganization.getAddress(), rawOrganization.getPhone(), rawOrganization.getIsActive(), true);
        officeRepo.save(mainOffice);
        return new resultResponce("success");
    }

    @Transactional
    public resultResponce update(RawOrganization rawOrganization, Organization orgDest) {
        BeanUtils.copyProperties(rawOrganization, orgDest, "id");
        Office storedOffice = officeRepo.findByOrgIdAndIsMain(orgDest, true);
        Office updatingOffice = new Office(storedOffice.getId(), orgDest,  /// Заменяем непередданые данные данными из базы в обновляемом офисе
                (rawOrganization.getFullName() == (null)) ? rawOrganization.getFullName() : storedOffice.getName(),
                (rawOrganization.getAddress() == (null)) ? storedOffice.getAddress() : rawOrganization.getAddress(),
                (rawOrganization.getPhone() == (null)) ? storedOffice.getPhone() : rawOrganization.getPhone(),
                (rawOrganization.getIsActive() == (null)) ? storedOffice.getIsActive() : rawOrganization.getIsActive(),
                true);
        officeRepo.save(updatingOffice);
        organizationRepo.save(orgDest);
        return new resultResponce("success");
    }

    public RawOrganization getOne(Integer id) {
        Organization organization = organizationRepo.findOrgByOrgId(id);
        if (organization == null) {
            return new RawOrganization();
        } ///Возвращаем пустую организацию в случае если нет такой
        Office mainOffice = officeRepo.findByOrgIdAndIsMain(organization, true);
        RawOrganization result = new RawOrganization();
        BeanUtils.copyProperties(organization, result);
        result.setAddress(mainOffice.getAddress());
        result.setPhone(mainOffice.getPhone());
        result.setIsActive(mainOffice.getIsActive());
        return result;
    }
}