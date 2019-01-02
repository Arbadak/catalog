package com.example.restapi.catalog;

import com.example.restapi.catalog.controller.OrganizationController;
import com.example.restapi.catalog.rawModel.RawOrganization;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"classpath:filldatabase.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class OrganizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrganizationController controller;

    /**
     * Testing list method of Organization controller
     * @throws Exception
     */
   /* final String fullName="ООО Вектор";
    final String name="Вектор";
    final Integer inn=123;
    final Integer kpp=123456789012;
    final String address="Ново-Свистуново";
    final Integer phone=557222;
    final Boolean isActive=true;
    final Integer orgId=1;*/

    /**
     * Тестируем поиск ID
     */
    @Test
    public void testOneByID() throws Exception {

        // Поиск организации
        this.mockMvc.perform(get("/organization/1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{'data':{'inn':'123456789012','kpp':'321321321','address':'Ново-Свистуново','phone':'557222','isActive':true,'name':'Вектор','fullName':'ООО Вектор'}}"));
        //несущевствующая организация
        this.mockMvc.perform(get("/organization/999"))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().json("{data:{}}"));  // ожидаем пустую организацию
    }

    /**
     * Тестируем поиск имени
     *
     * @throws Exception
     */
    @Test
    public void listByName() throws Exception {
        String requeststring;
        /// Сущевствующее имя
        requeststring = "{\"name\":\"Луч\"}";
        this.mockMvc.perform(post("/organization/list/").contentType(MediaType.APPLICATION_JSON).content(requeststring)).andDo(print())
                .andExpect(content().json("{'data':[{isActive: true, name: 'Луч', id: 8},{isActive: false, name: 'Луч', id: 5}]}"));

        //несущевствующее имя
        requeststring = "{\"name\":\"DDSBGFKEfs\"}";
        this.mockMvc.perform(post("/organization/list/").contentType(MediaType.APPLICATION_JSON).content(requeststring)).andDo(print())
                //.andExpect(content().json("{'data':[{isActive: true, name: 'Луч', id: 20},{isActive: false, name: 'Луч', id: 13}]}"));
                .andExpect(content().json("{'data':[]}")); ///ожидаем пустой массив
    }

    /**
     * Тестируем по имени и признаку поля isActive
     *
     * @throws Exception
     */
    @Test
    public void ListByNameAnd() throws Exception {
        String url = "/organization/list/";
        RawOrganization requestBody = new RawOrganization();

        requestBody.setName("Луч");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        /// Поле true isActive true
        requestBody.setIsActive(false);
        String requestJson = ow.writeValueAsString(requestBody);
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json("{'data':[{'isActive':false,'name':'Луч','id':5}]}"));

        /// Поле true isActive true
        requestBody.setIsActive(true);
        requestJson = ow.writeValueAsString(requestBody);
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json("{'data':[{'isActive':true,'id':8,'name':'Луч'}]}"));

        /// указан инн и поле isActive true
        requestBody.setInn("567890123456");
        requestJson = ow.writeValueAsString(requestBody);
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json("{'data':[{'isActive':true,'id':8,'name':'Луч'}]}"));

        /// указан инн и поле isActive true
        requestBody.setInn("567890123456");
        requestBody.setIsActive(false);
        requestJson = ow.writeValueAsString(requestBody);
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json("{'data':[]}"));

        /// инн указан некорректно
        //Присутсвуют не цифры
        requestBody.setInn("1234567890АБ");
        requestBody.setIsActive(null);
        requestJson = ow.writeValueAsString(requestBody);
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json("{'data':{'error': 'ИНН организации введен неверно'}}"));

        //Цифр меньше
        requestBody.setInn("1234567");
        requestBody.setIsActive(null);
        requestJson = ow.writeValueAsString(requestBody);
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json("{'data':{'error': 'ИНН организации введен неверно'}}"));

        //Цифр больше
        requestBody.setInn("12345678901234567890");
        requestBody.setIsActive(null);
        requestJson = ow.writeValueAsString(requestBody);
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json("{'data':{'error': 'ИНН организации введен неверно'}}"));
    }

    @Test
    @Sql(value = {"classpath:delete_organization.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void AddOrganization() throws Exception {
        String url = "/organization/save/";


        RawOrganization requestBody = new RawOrganization();

        requestBody.setName("Йорик");
        requestBody.setFullName("ЗАО Бедный Йорик");
        requestBody.setInn("098765432100");
        requestBody.setKpp("123456789");
        requestBody.setAddress("Площадь неживых бригад 10");
        requestBody.setIsActive(true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String requestJson = ow.writeValueAsString(requestBody);
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json("{'data':{'result':'success'}}"));

        /// не указан обязательный параметр
        requestBody.setName(null);
        requestJson = ow.writeValueAsString(requestBody);
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json("{'data':{'error':'Не указано имя организации'}}"));

        /// обязательный параметр указан не верно

        requestBody.setName("Эта строка будет длиннее поля в 30 симолов");
        requestJson = ow.writeValueAsString(requestBody);
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json("{'data':{'error':'Имя организации не может быть длинее 30 символов'}}"));

        /// кпп указано неверно
        /// не соответствует длинне
        requestBody.setName("Йорик");
        requestBody.setKpp("1234");
        requestJson = ow.writeValueAsString(requestBody);
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json("{'data':{'error':'КПП организации введен неверно'}}"));

        /// кпп указано неверно
        /// некорректный
        requestBody.setKpp("1234567ЖЩ");
        requestJson = ow.writeValueAsString(requestBody);
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json("{'data':{'error':'КПП организации введен неверно'}}"));

        /// инн указано неверно
        /// не соответствует длинне
        requestBody.setKpp("123456789");
        requestBody.setInn("123456789012345");
        requestJson = ow.writeValueAsString(requestBody);
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json("{'data':{'error':'ИНН организации введен неверно'}}"));

        /// инн указано неверно
        /// некорректный
        requestBody.setKpp("1234567890АБ");
        requestJson = ow.writeValueAsString(requestBody);
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json("{'data':{'error':'ИНН организации введен неверно'}}"));


    }


    public void printThat(MvcResult result) throws UnsupportedEncodingException {

        System.out.println("*************************************************************************************************");
        System.out.println(result.getResponse().getContentAsString());
        System.out.println("*************************************************************************************************");
    }

}
