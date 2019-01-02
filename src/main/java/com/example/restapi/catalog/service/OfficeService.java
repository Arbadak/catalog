package com.example.restapi.catalog.service;

import com.example.restapi.catalog.exceptions.NotFoundException;
import com.example.restapi.catalog.model.Office;
import com.example.restapi.catalog.rawModel.RawOffice;
import com.example.restapi.catalog.rawModel.resultResponce;
import com.example.restapi.catalog.repos.OfficeRepo;
import com.example.restapi.catalog.repos.OrganizationRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
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

    public List<RawOffice> getOfficeList(RawOffice rawOffice) {
        // TODO Фильтрация некорректного orgid
        List<Office> all = officeRepo.findAllByOrgId(organizationRepo.findOrgByOrgId(rawOffice.getOrgId())); /// создаем список всех оффисов в организации
        List<RawOffice> result = new ArrayList<>();

        if(rawOffice.getName()!=null) {all.removeIf(x->!rawOffice.getName().equals(x.getName()));}
        if(rawOffice.getPhone()!=null){all.removeIf(x->!rawOffice.getName().equals(x.getPhone()));}
        if(rawOffice.getIsActive()!=null){all.removeIf(x->!rawOffice.getIsActive().equals(x.getIsActive()));}

        for (Office iterableOffice:all){        /// перекидываем отфильтованный список в спосок с моделями исключая ненужные поля
            RawOffice currentRawOffice= new RawOffice();
            BeanUtils.copyProperties(iterableOffice,currentRawOffice,"orgId","address","phone","isMain");
            result.add(currentRawOffice);
     }
        return result;
    }

    public RawOffice getOne(Integer officeId) {
        Office requestedOffice= officeRepo.findById(officeId).orElse(null); ///дурацкий  optional
        if (requestedOffice==null) { // Если невернвый ид - получи пустой результат
            return new RawOffice();
        }
        RawOffice result= new RawOffice();
        BeanUtils.copyProperties(requestedOffice,result,"orgId");
        result.setOrgId(requestedOffice.getOrganizationId().getOrgId());
        return result;
    }

    @Transactional
    public resultResponce add(RawOffice office) {

        //if (office.getOrgId()==null){return new resultResponce(null,"не указана организация");}  /// не будем создавать "висячие" офисы

        Office newoffice = new Office();
        BeanUtils.copyProperties(office, newoffice, "orgId","Id");
        newoffice.setOrganizationId(organizationRepo.findOrgByOrgId(office.getOrgId())); ///Выдергиваем организацию из номера в сырой модели, и суем уже обьект
        officeRepo.save(newoffice);
        return new resultResponce("success");
    }

    @Transactional
    public resultResponce update(RawOffice office) {

        /// TODO решить вариант если указали больше параметров чем нужно orgID например

        RawOffice checkoffice=getOne(office.getId());
        if (checkoffice.getId()==null) {
            throw new NotFoundException("Офис с указаным id не найден, выйдите в окно");
        }
        //if (getOne(office.getId())==null) { throw new NotFoundException("Не сущевствует оффиса с укащанным id");}
            //return new resultResponce(null,"не сущевствует оффиса с укащанным id");} /// проверим если ли то что собираемся апдейтить
        //if (office.getId()==null || office.getName()==null || office.getAddress()==null || office.getIsActive()==null) {return new resultResponce(null,"не указан обязательный параметр");}

        Office newoffice = officeRepo.findById(office.getId()).orElse(null); //вытскиваем из базы хранимый офис для редактирования
        BeanUtils.copyProperties(office, newoffice, "orgId","phone");  //orgid не указывается а phone необязательнй потому их не копируем
        //newoffice.setOrganizationId(organizationRepo.findOrgByOrgId(office.getOrgId())); ///Выдергиваем организацию из номера в сырой модели, и суем уже обьект
        newoffice.setPhone((office.getPhone() == (null)) ? getOne(office.getId()).getPhone() : office.getPhone()); /// если не null то заменяем хранящийся телефон
        officeRepo.save(newoffice);
        return new resultResponce("success");
    }

}
