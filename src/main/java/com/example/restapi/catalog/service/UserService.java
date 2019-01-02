package com.example.restapi.catalog.service;

import com.example.restapi.catalog.model.*;
import com.example.restapi.catalog.rawModel.RawUser;
import com.example.restapi.catalog.rawModel.resultResponce;
import com.example.restapi.catalog.repos.*;
import com.example.restapi.catalog.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final DocRepo docRepo;
    private final DocDataRepo docdDataRepo;
    private final CountryRepo countryRepo;
    private final OfficeRepo officeRepo;
    private final UserRepo userRepo;
    private final Utils utils;

    @Autowired
    public UserService(DocRepo docRepo, DocDataRepo docDataRepo, CountryRepo countryRepo, OfficeRepo officeRepo, UserRepo userRepo, Utils utils) {
        this.docRepo = docRepo;
        this.docdDataRepo = docDataRepo;
        this.countryRepo = countryRepo;
        this.officeRepo = officeRepo;
        this.userRepo = userRepo;
        this.utils =utils;

    }

public List<RawUser> getUserList (RawUser rawUser){
        List<User> all = userRepo.findAllByOfficeEmp(officeRepo.findById(rawUser.getOfficeId()).orElse(null));
        List<RawUser> result=new ArrayList<>();
//TODO тут будет фильтр

for(User iterableUser:all){
    RawUser currentRawUser = new RawUser();
    BeanUtils.copyProperties (iterableUser,currentRawUser,"phone","document","citizenship","isIdentified","officeEmp");
    result.add(currentRawUser);
}
return  result;
    }

public RawUser getOne(Integer id) {
    User requestedUser = userRepo.findById(id).orElse(null);
    if (requestedUser == null) { // Если невернвый ид - получи пустой результат
        return new RawUser(); }

        RawUser result = new RawUser();
        BeanUtils.copyProperties(requestedUser, result, "document", "citizenship", "officeEmp");

        DocData bindedDocData = requestedUser.getDocument(); /// Содержит docId dateDoc docNumber docType
        Doc bindedDoc = bindedDocData.getDocType();  ///Содержит docId docName docCode
        Country citizinship = requestedUser.getCitizenship(); /// Содержит citizinshipId citizenshipCode citizenshipName

        BeanUtils.copyProperties(bindedDocData, result, "docId","docType");
        BeanUtils.copyProperties(bindedDoc, result, "docId");
        BeanUtils.copyProperties(citizinship, result,"citizinshipId");
        result.setOfficeId(requestedUser.getOfficeEmp().getId());

    return result;
}
 @Transactional
   public resultResponce add (RawUser rawUser){
//TODO здесь будет фильтр
     if (rawUser.getFirstName()==null || rawUser.getPosition()==null) {}  ///TODO это обязательные параметры
    User result = new User();
       BeanUtils.copyProperties(rawUser,result);
    ///Вроде справочники не должны быть доступны для записи но тем не менее
       Doc newDoc=docRepo.findByDocNameAndDocCode(rawUser.getDocName(),rawUser.getDocCode());  ///Ищем в справочнике документ с именем и кодом
       if (newDoc==null) {  /// если не нашли то создаем новый
           newDoc = new Doc(rawUser.getDocName(),rawUser.getDocCode());
       docRepo.save(newDoc);
       }

       Country newCountry=countryRepo.findByCitizenshipCode(rawUser.getCitizenshipCode());
///TODO если country пустая - валимся с ошибкой
       DocData newDocData = new DocData(rawUser.getDocDate(),rawUser.getDocNumber(),newDoc); /// Создаем юзеру тугамент
       docdDataRepo.save(newDocData); ///сохраняем тугамент
       Office newOffice = officeRepo.findById(rawUser.getOfficeId()).orElse(null);  ///Либо офис либо ноль, ноль если ничего не указали или фигню указали, может быть здесь нужен фильр
       result.setCitizenship(newCountry);
       result.setDocument(newDocData);
       result.setOfficeEmp(newOffice);
       userRepo.save(result);
    return new resultResponce("success");}

@Transactional
    public resultResponce update(RawUser rawUser){
    User updatingUser = userRepo.findById(rawUser.getId()).orElse(null);
        if (updatingUser==null) { return new resultResponce(null,"пользователь не найден");}
    utils.copyNonNullProperties(rawUser, updatingUser);  ///перекидываем ненулевые свойства

    Doc updatingDoc = new Doc();  ///Болванка типа документа
    if (rawUser.getDocName()!=null) {  ///Если имя документа не пустое - меняем тип документа пользователя
        updatingDoc = docRepo.findByDocName(rawUser.getDocName());  ///Ищем в справочнике документ с именем и кодом
        if (updatingDoc == null) {  ///TODO если не нашли то ошибка неверное имя документа
        }
        updatingUser.getDocument().setDocType(updatingDoc); /// меняем тип документа
        docdDataRepo.save(updatingUser.getDocument()); //засейвим
    }
    if (rawUser.getCitizenshipCode()!=null ) {
        Country updatingCountry = countryRepo.findByCitizenshipCode(rawUser.getCitizenshipCode());
        if (updatingCountry == null) {
        } //TODO не нашли измененную страну ошибка
        updatingUser.setCitizenship(updatingCountry);
    }
    if ((rawUser.getDocDate()!=null) && rawUser.getDocNumber()!=null) {
        DocData updatingDocData = new DocData(rawUser.getDocDate(), rawUser.getDocNumber(), ((updatingDoc.getDocId() == null) ? updatingUser.getDocument().getDocType() : updatingDoc)); /// Если ИД документа нулл значит это блованка, он не менялся, берем то что в базе было
        docdDataRepo.save(updatingDocData); ///сохраняем тугамент
    }
    if (rawUser.getOfficeId()!=null) {
        Office updatingOffice = officeRepo.findById(rawUser.getOfficeId()).orElse(null);  ///Либо офис либо ноль, ноль если ничего не указали или фигню указали, может быть здесь нужен фильр
        if (updatingOffice == null) {
        } ///TODO Неправильно указан офис
        updatingUser.setOfficeEmp(updatingOffice);
    }
   userRepo.save(updatingUser);
    return new resultResponce("success");}
    }


