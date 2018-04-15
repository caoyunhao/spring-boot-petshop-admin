package com.caoyunhao.petshop_admin.module.auth;

import com.caoyunhao.petshop_admin.PetshopInternApplication;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** 
* AuthController Tester. 
* 
* @author Li.Hou
* @date 10/16/2017
*/ 
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PetshopInternApplication.class)
@Transactional
public class AuthControllerTest {
    @Rule
    public ExpectedException throwException = ExpectedException.none();
    MockMvc mvc;
    @Autowired
    private AuthController authController;

    private static final String URL = "/auths";
    @Before
    public void before() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(authController).build();
    } 

    @After
    public void after() throws Exception { 
    } 

    /** 
    * 
    * Method: findAllAuths(PageParams pageParams) 
    * 
    */ 
    @Test
    public void testFindAllAuths() throws Exception {

        mvc.perform(get(URL)
                .param("pageNum","1")
                .param("pageSize","2")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0))
                .andExpect(jsonPath("$.data").isNotEmpty());

    } 

    /** 
    * 
    * Method: addAuth(@RequestBody BaseRequest<Auth> request) 
    * 
    */ 
    @Test
    public void testAddAuth() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",0);
        jsonObject.put("authName","testAuth");
        jsonObject.put("authDescription","testAuthDes");

        JSONObject requestJSON = new JSONObject();
        requestJSON.put("data",jsonObject);

        mvc.perform(post(URL)
                .content(requestJSON.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0));

    } 

    /** 
    * 
    * Method: updateAuth(@RequestBody BaseRequest<Auth> request, @PathVariable Long id) 
    * 
    */ 
    @Test
    public void testUpdateAuth() throws Exception { 
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id","13");
        jsonObject.put("authName","No13");
        jsonObject.put("authDescription","des13");

        JSONObject requestJSON = new JSONObject().put("data",jsonObject);

        String url = URL+"/13";
        mvc.perform(put(url)
                .content(requestJSON.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0));

    } 

    /** 
    * 
    * Method: deleteAuth(@PathVariable Long id) 
    * 
    */ 
    @Test
    public void testDeleteAuth() throws Exception { 
        String url = URL+"/13";
        mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0));
    } 

    /** 
    * 
    * Method: findAuthById(@PathVariable Long id) 
    * 
    */ 
    @Test
    public void testFindAuthById() throws Exception { 
        String url = URL+"/13";
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0))
                .andExpect(jsonPath("$.data").isNotEmpty());
    } 


} 
