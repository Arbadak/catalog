package com.example.restapi.catalog;

import com.example.restapi.catalog.controller.UserController;
import com.example.restapi.catalog.rawmodel.RawOffice;
import com.example.restapi.catalog.rawmodel.RawOrganization;
import com.example.restapi.catalog.rawmodel.RawUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Тест контроллера USER, в сявзи с тем что junit по умолчанию запускает тесты в абы каком порядке, в классе форсирован порядок запуска тестов по алфавитному порядку
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController controller;

    private static Integer createdOrgId;
    private static Integer createdOfficeId;
    private static JSONObject resultJson;


    /**
     * Полная цепочка создания пользователя
     * <p>
     * создается организация, создается офис в организации, в офисе создается пользователь
     */
    @Test
    @Sql(value = {"classpath:fillsprav.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void a_addUserNormal() throws Exception {

        String url = "/organization/save/";
        RawOrganization addOrganization = new RawOrganization();
        addOrganization.setName("Светлое будущее");
        addOrganization.setFullName("ООО Светлое будущее");
        addOrganization.setInn("309030903090");
        addOrganization.setKpp("784784784");
        addOrganization.setAddress("Бульвар Освобождения 526");
        addOrganization.setIsActive(true);
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(makeMeJson(addOrganization))).andDo(print())
                .andExpect(content().json("{'data':{'result':'success'}}"));
        MvcResult getOrgId = mockMvc.perform(post("/organization/list/").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Светлое будущее\"}")).andReturn();
        JSONObject jsonOrgId = new JSONObject(getOrgId.getResponse().getContentAsString());
        createdOrgId = jsonOrgId.getJSONArray("data")
                .getJSONObject(0)
                .getInt("id");
        RawOffice addOffice = new RawOffice();
        addOffice.setIsActive(true);
        addOffice.setAddress("Площадь революции 19");
        addOffice.setOrgId(createdOrgId);
        addOffice.setName("Революционный");
        addOffice.setPhone("19172019");
        MvcResult resulRequest = mockMvc.perform(post("/office/save/").contentType(MediaType.APPLICATION_JSON).content(makeMeJson(addOffice))).andDo(print())
                .andExpect(content().json("{'data':{'result':'success'}}")).andReturn();
        MvcResult getOfficeId = mockMvc.perform(post("/office/list/").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Революционный\", \"orgId\":" + createdOrgId + "}")).andReturn();
        createdOfficeId = new JSONObject(getOfficeId.getResponse().getContentAsString()).getJSONArray("data").getJSONObject(0).getInt("id");
        RawUser addUserRequest = new RawUser();
        addUserRequest.setOfficeId(createdOfficeId);
        addUserRequest.setFirstName("Владимир");
        addUserRequest.setSecondName("Ильичь");
        addUserRequest.setLastName("Ульянов");
        addUserRequest.setPosition("Юрист");
        addUserRequest.setPhone("12341234123");
        addUserRequest.setDocCode("81");
        addUserRequest.setDocName("Паспорт гражданиана Российской Империи");
        addUserRequest.setDocNumber("1234567890");
        addUserRequest.setCitizenshipCode("901");
        addUserRequest.setIsIdentified(true);
        String requestJson = makeMeJson(addUserRequest);
        requestJson = requestJson.replaceAll("\"docDate\" : null", "\"docDate\" : \"1890-04-10\"");
        this.mockMvc.perform(post("/user/save/").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json("{'data':{'result':'success'}}"));
    }

    /**
     * Добавляем пользователя в сущевствующую организацию
     */
    @Test
    public void b_addAnotherUserInSameOffice() throws Exception {
        String url = "/user/save/";
        RawUser addUserRequest = new RawUser();
        addUserRequest.setOfficeId(createdOfficeId);
        addUserRequest.setFirstName("Лейба");
        addUserRequest.setSecondName("Давидович");
        addUserRequest.setLastName("Бронштейн");
        addUserRequest.setPosition("Председатель исполкома");
        addUserRequest.setPhone("47295627453");
        addUserRequest.setDocCode("81");
        addUserRequest.setDocName("Паспорт гражданиана Российской Империи");
        addUserRequest.setDocNumber("3848695460");
        addUserRequest.setCitizenshipCode("901");
        addUserRequest.setIsIdentified(true);
        String requestJson = makeMeJson(addUserRequest);
        requestJson = requestJson.replaceAll("\"docDate\" : null", "\"docDate\" : \"1899-08-26\""); ///Потому что модель у нас с Localdate а UserController ждет просто String
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json("{'data':{'result':'success'}}"));
    }

    /**
     * Выводим список пользователей в конкретной организации
     */
    @Test
    public void c_listByOfficeId() throws Exception {
        MvcResult responce = mockMvc.perform(post("/user/list/").contentType(MediaType.APPLICATION_JSON).content("{\"officeId\":" + createdOfficeId + "}")).andDo(print())
                .andExpect(content().json("{\"data\":[{\"firstName\":\"Владимир\",\"secondName\":\"Ильичь\",\"lastName\":\"Ульянов\",\"position\":\"Юрист\",\"phone\":null,\"docName\":null,\"docNumber\":null,\"docDate\":null,\"docCode\":null,\"citizenshipName\":null,\"citizenshipCode\":null,\"isIdentified\":null,\"officeId\":null},{\"firstName\":\"Лейба\",\"secondName\":\"Давидович\",\"lastName\":\"Бронштейн\",\"position\":\"Председатель исполкома\",\"phone\":null,\"docName\":null,\"docNumber\":null,\"docDate\":null,\"docCode\":null,\"citizenshipName\":null,\"citizenshipCode\":null,\"isIdentified\":null,\"officeId\":null}]}"))
                .andReturn();
        resultJson = new JSONObject(responce.getResponse().getContentAsString());
    }

    /**
     * Находим пользоватля по идентификатору
     *
     * @throws Exception
     */
    @Test
    public void d_listById() throws Exception {
        String requeststring = "/user/" + resultJson.getJSONArray("data").getJSONObject(0).getInt("id");

        this.mockMvc.perform(get(requeststring))
                .andDo(print()).andExpect(status().isOk()).andDo(print())
                .andExpect(content().json("{'data':{'firstName':'Владимир'}}"));
    }

    protected String makeMeJson(Object source) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(source);
        return requestJson;
    }
}
