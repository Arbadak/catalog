package com.example.restapi.catalog.service.implementation;

import com.example.restapi.catalog.exceptions.NotFoundException;
import com.example.restapi.catalog.model.Office;
import com.example.restapi.catalog.rawmodel.RawOffice;
import com.example.restapi.catalog.rawmodel.ResultResponce;
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

    public List<RawOffice> getOfficeList(RawOffice rawOffice) {
        // TODO Фильтрация некорректного orgid
        List<Office> all = officeRepo.findAllByOrganization(organizationRepo.findOrgByOrgId(rawOffice.getOrgId())); /// создаем список всех оффисов в организации
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

        for (Office iterableOffice : all) {        /// перекидываем отфильтованный список в спосок с моделями исключая ненужные поля
            RawOffice currentRawOffice = new RawOffice();
            BeanUtils.copyProperties(iterableOffice, currentRawOffice, "orgId", "address", "phone", "isMain");
            result.add(currentRawOffice);
        }
        return result;
    }

    public RawOffice getOne(Integer officeId) {
        Office requestedOffice = officeRepo.findById(officeId).orElse(null); ///дурацкий  optional
        if (requestedOffice == null) { // Если невернвый ид - получи пустой результат
            return new RawOffice();
        }
        RawOffice result = new RawOffice();
        BeanUtils.copyProperties(requestedOffice, result, "orgId");
        result.setOrgId(requestedOffice.getOrganization().getOrgId());
        return result;
    }

    @Transactional
    public ResultResponce add(RawOffice office) {

        //if (office.getOrgId()==null){return new ResultResponce(null,"не указана организация");}  /// не будем создавать "висячие" офисы

        Office newoffice = new Office();
        BeanUtils.copyProperties(office, newoffice, "orgId", "Id");
        newoffice.setOrganization(organizationRepo.findOrgByOrgId(office.getOrgId())); ///Выдергиваем организацию из номера в сырой модели, и суем уже обьект
        officeRepo.save(newoffice);
        return new ResultResponce("success");
    }

    @Transactional
    public ResultResponce update(RawOffice office) {

        /// TODO решить вариант если указали больше параметров чем нужно orgID например

        RawOffice checkoffice = getOne(office.getId());
        if (checkoffice.getId() == null) {
            throw new NotFoundException("Офис с указаным id не найден, выйдите в окно");
        }
        if (getOne(office.getId())==null) { throw new NotFoundException("Не сущевствует оффиса с указанным id");}

        Office newoffice = officeRepo.findById(office.getId()).orElse(null); //вытскиваем из базы хранимый офис для редактирования
        BeanUtils.copyProperties(office, newoffice, "orgId", "phone");  //orgid не указывается а phone необязательнй потому их не копируем
        //newoffice.setOrganizationId(organizationRepo.findOrgByOrgId(office.getOrgId())); ///Выдергиваем организацию из номера в сырой модели, и суем уже обьект
        newoffice.setPhone((office.getPhone() == (null)) ? getOne(office.getId()).getPhone() : office.getPhone()); /// если не null то заменяем хранящийся телефон
        officeRepo.save(newoffice);
        return new ResultResponce("success");
    }

}
