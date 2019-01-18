package com.example.restapi.catalog.service.implementation;

import com.example.restapi.catalog.exceptions.NotFoundException;
import com.example.restapi.catalog.helpers.CustomPropertyCopy;
import com.example.restapi.catalog.model.Country;
import com.example.restapi.catalog.model.Doc;
import com.example.restapi.catalog.model.DocData;
import com.example.restapi.catalog.model.Office;
import com.example.restapi.catalog.model.User;
import com.example.restapi.catalog.rawmodel.RawUser;
import com.example.restapi.catalog.rawmodel.ResultResponse;
import com.example.restapi.catalog.repos.CountryRepo;
import com.example.restapi.catalog.repos.DocDataRepo;
import com.example.restapi.catalog.repos.DocRepo;
import com.example.restapi.catalog.repos.OfficeRepo;
import com.example.restapi.catalog.repos.UserRepo;
import com.example.restapi.catalog.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImp implements UserService {

    private final DocRepo docRepo;
    private final DocDataRepo docdDataRepo;
    private final CountryRepo countryRepo;
    private final OfficeRepo officeRepo;
    private final UserRepo userRepo;
    private final CustomPropertyCopy customPropertyCopy;

    @Autowired
    public UserServiceImp(DocRepo docRepo, DocDataRepo docDataRepo, CountryRepo countryRepo, OfficeRepo officeRepo, UserRepo userRepo, CustomPropertyCopy customPropertyCopy) {
        this.docRepo = docRepo;
        this.docdDataRepo = docDataRepo;
        this.countryRepo = countryRepo;
        this.officeRepo = officeRepo;
        this.userRepo = userRepo;
        this.customPropertyCopy = customPropertyCopy;

    }

    /**
     * Метод получения списка пользоватлей в указанном офисе
     * @param rawUser
     * @return
     */
    public List<RawUser> getUserList(RawUser rawUser) {
        if (rawUser == null) {
            throw new NotFoundException("Ошибка в аргументе запроса");
        }
        List<User> all = userRepo.findAllByOffice(officeRepo.findById(rawUser.getOfficeId()).orElse(null));
        List<RawUser> result = new ArrayList<>();
        for (User iterableUser : all) {
            RawUser currentRawUser = new RawUser();
            BeanUtils.copyProperties(iterableUser, currentRawUser, "phone", "document", "citizenship", "isIdentified", "officeEmp");
            result.add(currentRawUser);
        }
        return result;
    }

    /**
     * Метод получения пользователя по идентификтору
     * @param id
     * @return
     */
    public RawUser getOne(Integer id) {
        if (id == null) {
            throw new NotFoundException("Ошибка в аргументе запроса");
        }
        User requestedUser = userRepo.findById(id).orElse(null);
        if (requestedUser == null) {
            return new RawUser();
        }
        RawUser result = new RawUser();
        BeanUtils.copyProperties(requestedUser, result, "document", "citizenship", "officeEmp");
        DocData bindedDocData = requestedUser.getDocument();
        Doc bindedDoc = bindedDocData.getDocType();
        Country citizinship = requestedUser.getCitizenship();
        BeanUtils.copyProperties(bindedDocData, result, "docId", "docType");
        BeanUtils.copyProperties(bindedDoc, result, "docId");
        BeanUtils.copyProperties(citizinship, result, "citizinshipId");
        result.setOfficeId(requestedUser.getOffice().getId());

        return result;
    }

    /**
     * Метод добавленя пользователя, при добавлении пользователя ему создается документ, если тип данного документ отсутствует то он создается в справочнике документов
     * укзанное в поле гражданство вроверятеся на сущевстввование
     * @param rawUser
     * @return
     */
    @Transactional
    public ResultResponse add(RawUser rawUser) {
        if (rawUser == null) {
            throw new NotFoundException("Ошибка в аргументе запроса");
        }
        User result = new User();
        BeanUtils.copyProperties(rawUser, result);
        Doc newDoc = docRepo.findByDocNameAndDocCode(rawUser.getDocName(), rawUser.getDocCode());
        if (newDoc == null) {
            newDoc = new Doc(rawUser.getDocName(), rawUser.getDocCode());
            docRepo.save(newDoc);
        }
        Country newCountry = countryRepo.findByCitizenshipCode(rawUser.getCitizenshipCode());
        if (newCountry == null) {
            throw new NotFoundException("Указанная страна не числится в справочнике стран id");
        }
        DocData newDocData = new DocData(rawUser.getDocDate(), rawUser.getDocNumber(), newDoc);
        docdDataRepo.save(newDocData);
        Office newOffice = officeRepo.findById(rawUser.getOfficeId()).orElse(null);
        result.setCitizenship(newCountry);
        result.setDocument(newDocData);
        result.setOffice(newOffice);
        userRepo.save(result);
        return new ResultResponse("success");
    }

    /**
     * Метод изменения информации имеющегося пользователя
     * @param rawUser
     * @return
     */
    @Transactional
    public ResultResponse update(RawUser rawUser) {
        if (rawUser == null) {
            throw new NotFoundException("Ошибка в аргументе запроса");
        }
        User updatingUser = userRepo.findById(rawUser.getId()).orElse(null);
        if (updatingUser == null) {
            throw new NotFoundException("пользователь не найден");
        }
        customPropertyCopy.copyNonNullProperties(rawUser, updatingUser);
        Doc updatingDoc = new Doc();
        if (rawUser.getDocName() != null) {
            updatingDoc = docRepo.findByDocName(rawUser.getDocName());
            if (updatingDoc == null) {
                throw new NotFoundException("Указанная документ не числится в справочнике документов");
            }
            updatingUser.getDocument().setDocType(updatingDoc);
            docdDataRepo.save(updatingUser.getDocument());
        }
        if (rawUser.getCitizenshipCode() != null) {
            Country updatingCountry = countryRepo.findByCitizenshipCode(rawUser.getCitizenshipCode());
            if (updatingCountry.getCitizinshipId() == null) {
                throw new NotFoundException("Указанная страна не числится в справочнике стран");
            }
            updatingUser.setCitizenship(updatingCountry);
        }
        if ((rawUser.getDocDate() != null) && rawUser.getDocNumber() != null) {
            DocData updatingDocData = new DocData(rawUser.getDocDate(), rawUser.getDocNumber(), ((updatingDoc.getDocId() == null) ? updatingUser.getDocument().getDocType() : updatingDoc));
            docdDataRepo.save(updatingDocData);
        }
        if (rawUser.getOfficeId() != null) {
            Office updatingOffice = officeRepo.findById(rawUser.getOfficeId()).orElse(null);
            if (updatingOffice == null) {
                throw new NotFoundException("Указанный офис не найден");
            }
            updatingUser.setOffice(updatingOffice);
        }
        userRepo.save(updatingUser);
        return new ResultResponse("success");
    }
}


