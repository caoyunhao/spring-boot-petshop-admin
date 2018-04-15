package com.caoyunhao.petshop_admin.module.custom;

import com.caoyunhao.petshop_admin.PetshopInternApplication;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Yunhao.Cao
 * @date 2017/10/17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PetshopInternApplication.class)
@Transactional
public class CustomControllerTest {
    @Rule
    public ExpectedException throwException = ExpectedException.none();
    MockMvc mvc;
    @Autowired
    private CustomController customController;

    private final String ROOT_URL = "/customs";

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(customController).build();
    }

    @Test
    public void getCommodities() throws Exception {
        String uri = ROOT_URL;

        //执行mvc请求
        mvc.perform(get(uri)
                .param("pageNum", "1")
                .param("pageSize", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    public void findCustom() throws Exception {
        JSONObject requestJson = new JSONObject();
        String uri = ROOT_URL + "/1";

        //执行mvc请求
        mvc.perform(get(uri)
                .content(requestJson.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    public void addCustom() throws Exception {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("customName", "test_add");
        jsonObject.put("customNameEncrypted", "test_add_encrypted");
        jsonObject.put("password", "test_add_password");
        jsonObject.put("customNickname", "test_add_nickname");
        jsonObject.put("customPhoneNumber", "17602102330");
        jsonObject.put("customEmail", "test_add@admin.com");
        jsonObject.put("roleList", null);

        JSONObject requestJson = new JSONObject();
        requestJson.put("data", jsonObject);

        String uri = ROOT_URL;

        //执行mvc请求
        mvc.perform(post(uri)
                .content(requestJson.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0));

    }

    @Test
    public void updateCustom() throws Exception {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("customName", "test_update");
        jsonObject.put("customNameEncrypted", "test_add_encrypted");
        jsonObject.put("customNickname", "test_update_nickname");
        jsonObject.put("customPhoneNumber", "18221073090");
        jsonObject.put("customEmail", "test_update@admin.com");
        jsonObject.put("roleList", null);

        JSONObject requestJson = new JSONObject();
        requestJson.put("data", jsonObject);

        String uri = ROOT_URL + "/2";

        //执行mvc请求
        mvc.perform(put(uri)
                .content(requestJson.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0));

    }

    @Test
    public void deleteCustom() throws Exception {
        String uri = ROOT_URL + "/2";

        //执行mvc请求
        mvc.perform(delete(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0));
    }

    @Test
    public void searchCustom() throws Exception {
        String uri = ROOT_URL + "/search";

        //执行mvc请求
        mvc.perform(get(uri)
                .param("q", "test")
                .param("pageNum", "1")
                .param("pageSize", "5")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

}
