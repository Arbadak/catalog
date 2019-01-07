package com.example.restapi.catalog;

import com.example.restapi.catalog.controller.OfficeController;
import com.example.restapi.catalog.rawmodel.RawOffice;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"classpath:filldatabase.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class OfficeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OfficeController controller;

    /**
     * Добавление офиса
     *
     * @throws Exception
     */
    @Test
    public void addOfficeNormal() throws Exception {

        final String url = "/office/save/";

        RawOffice request = new RawOffice();
        request.setIsActive(true);
        request.setAddress("Полки 10");
        request.setOrgId(5);
        request.setName("Особо зеленый оффис");
        request.setPhone("300300");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String requestJson = ow.writeValueAsString(request);
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json("{'data':{'result':'success'}}"))
                .andReturn();
    }

    @Test
    public void addOfficeEmptyRequiredField() throws Exception {

        final String url = "/office/save/";

        RawOffice request = new RawOffice();
        request.setIsActive(true);
        request.setAddress("Колково 120");
        request.setOrgId(null);
        request.setName("Оффисищще");
        request.setPhone("300303");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String requestJson = ow.writeValueAsString(request);
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json(" {data:{'error':'Не указан идентификатор организации'}}"));
    }


    @Test
    public void getOfficeByOrgIdNormal() throws Exception {
        final String url = "/office/list";
        // Поиск организации

        RawOffice request = new RawOffice();
        request.setOrgId(5);
        request.setIsActive(true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String requestJson = ow.writeValueAsString(request);
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json("{'data':[{'name':'Особо зеленый оффис'},{}]}"));
    }


    @Test
    public void getSeveralByOrgIdNormal() throws Exception {
        final String url = "/office/list";
        // Поиск организации

        RawOffice request = new RawOffice();
        request.setOrgId(2);
        //request.setIsActive(true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String requestJson = ow.writeValueAsString(request);
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json("{'data':[{'name':'ГО РОГА и КО','isActive':true,'id':1},{'name':'Филиалс Северный РОГ и ко','isActive':true,'id':2}]}"));
    }

    @Test
    public void updateOfficeNormal() throws Exception {


        final String url = "/office/update/";

        RawOffice request = new RawOffice();
        request.setId(10);
        request.setIsActive(true);
        request.setAddress("Полки 22");
        request.setName("НеОсобо зеленый оффис");
        request.setPhone("300301");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String requestJson = ow.writeValueAsString(request);
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(content().json("{'data':{'result':'success'}}"));
    }

    @Test
    public void getOfficebyOfficeId() throws Exception {

        this.mockMvc.perform(get("/office/11"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{'data':{'id':11}}"));
    }
}
