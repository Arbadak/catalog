package com.example.restapi.catalog.service.implementation;

import com.example.restapi.catalog.exceptions.NotFoundException;
import com.example.restapi.catalog.model.Office;
import com.example.restapi.catalog.rawmodel.RawOffice;
import com.example.restapi.catalog.rawmodel.ResultResponse;
import com.example.restapi.catalog.repos.OfficeRepo;
import com.example.restapi.catalog.repos.OrganizationRepo;
import com.example.restapi.catalog.service.OfficeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfficeServiceImp implements OfficeService {

    private final OrganizationRepo organizationRepo;
    private final OfficeRepo officeRepo;

    @Autowired
    public OfficeServiceImp(OrganizationRepo organizationRepo, OfficeRepo officeRepo) {
        this.organizationRepo = organizationRepo;
        this.officeRepo = officeRepo;
    }

    /**
     * Метод получения списка офисов по идентификтору организации
     * @param rawOffice
     * @return
     */
    public List<RawOffice> getOfficeList(RawOffice rawOffice) {

        if (rawOffice == null) {
            throw new NotFoundException("Ошибка в аргументе запроса");
        }
        List<Office> all = officeRepo.findAllByOrganization(organizationRepo.findOrgByOrgId(rawOffice.getOrgId()));
        List<RawOffice> result = new ArrayList<>();

        if (rawOffice.getName() != null) {
            all.removeIf(x -> !rawOffice.getName().equals(x.getName()));
        }
        if (rawOffice.getPhone() != null) {
            all.removeIf(x -> !rawOffice.getName().equals(x.getPhone()));
        }
        if (rawOffice.getIsActive() != null) {
            all.removeIf(x -> !rawOffice.getIsActive().equals(x.getIsActive()));
        }

        for (Office iterableOffice : all) {
            RawOffice currentRawOffice = new RawOffice();
            BeanUtils.copyProperties(iterableOffice, currentRawOffice, "orgId", "address", "phone", "isMain");
            result.add(currentRawOffice);
        }
        return result;
    }

    /**
     * Метод получения офиса по идентификатору
     * @param officeId
     * @return
     */
    public RawOffice getOne(Integer officeId) {
        if (officeId == null) {
            throw new NotFoundException("Ошибка в аргументе запроса");
        }
        Office requestedOffice = officeRepo.findById(officeId).orElse(null);
        if (requestedOffice == null) {
            return new RawOffice();
        }
        RawOffice result = new RawOffice();
        BeanUtils.copyProperties(requestedOffice, result, "orgId");
        result.setOrgId(requestedOffice.getOrganization().getOrgId());
        return result;
    }

    /**
     * Метод добавления нового офиса
     * @param office
     * @return
     */
    @Transactional
    public ResultResponse add(RawOffice office) {
        if (office == null) {
            throw new NotFoundException("Ошибка в аргументе запроса");
        }
        Office newoffice = new Office();
        BeanUtils.copyProperties(office, newoffice, "orgId", "Id");
        newoffice.setOrganization(organizationRepo.findOrgByOrgId(office.getOrgId()));
        officeRepo.save(newoffice);
        return new ResultResponse("success");
    }

    /**
     * Метод обновления информации о сущевствующем офисе
     * @param office
     * @return
     */
    @Transactional
    public ResultResponse update(RawOffice office) {
        if (office == null) {
            throw new NotFoundException("Ошибка в аргументе запроса");
        }
        RawOffice checkoffice = getOne(office.getId());
        if (checkoffice.getId() == null) {
            throw new NotFoundException("Офис с указаным id не найден, выйдите в окно");
        }
        if (getOne(office.getId()) == null) {
            throw new NotFoundException("Не сущевствует оффиса с указанным id");
        }
        Office newoffice = officeRepo.findById(office.getId()).orElse(null);
        BeanUtils.copyProperties(office, newoffice, "orgId", "phone");
        newoffice.setPhone((office.getPhone() == (null)) ? getOne(office.getId()).getPhone() : office.getPhone());
        officeRepo.save(newoffice);
        return new ResultResponse("success");
    }
}