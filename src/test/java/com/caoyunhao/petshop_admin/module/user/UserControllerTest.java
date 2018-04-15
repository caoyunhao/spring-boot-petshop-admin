package com.caoyunhao.petshop_admin.module.user;

import com.caoyunhao.petshop_admin.PetshopInternApplication;
import com.caoyunhao.petshop_admin.common.util.EncryptionUtil;
import com.caoyunhao.petshop_admin.repository.UserRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author jie.yao
 * @date 2017/9/18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PetshopInternApplication.class)
@Transactional
public class UserControllerTest {

    @Rule
    public ExpectedException throwException = ExpectedException.none();
    MockMvc mvc;
    @Autowired
    private UserController userController;
    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void findUsers() throws Exception {
        String uri = "/users";

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
    public void findUser() throws Exception {
        JSONObject requestJson = new JSONObject();
        String uri = "/users/35";

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
    public void addUser() throws Exception {
        JSONArray userList = new JSONArray();
        JSONObject userRole1 = new JSONObject();
        userRole1.put("id",1);
        userList.put(userRole1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", "tyui1234");
        jsonObject.put("password","12345678");
        jsonObject.put("userNameEncrypted", EncryptionUtil.encryptFrontString("tyui1234"));
        jsonObject.put("userAvatar","null");

        JSONObject requestJson = new JSONObject();
        requestJson.put("data", jsonObject);
        String uri = "/users";

        //执行mvc请求
        mvc.perform(post(uri)
                .content(requestJson.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0));

    }

    @Test
    public void updateUser() throws Exception {
        JSONArray roleList = new JSONArray();
        JSONObject userRole1 = new JSONObject();
        userRole1.put("id", 1);
        roleList.put(userRole1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", "tyui1234");
        jsonObject.put("roleList", roleList);
        JSONObject requestJson = new JSONObject();
        requestJson.put("data", jsonObject);
        String uri = "/users/35";

        //执行mvc请求
        mvc.perform(put(uri)
                .content(requestJson.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0));

    }

    @Test
    public void deleteUser() throws Exception {
        String uri = "/users/35";

        //执行mvc请求
        mvc.perform(delete(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0));
    }

    @Test
    public void findByUserName()throws Exception{
        JSONObject requestJson = new JSONObject();
        String uri = "/users/name/s";

        //执行mvc请求
        mvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0));
    }

//    @Test
//    public void addUserImage()throws Exception{
//        JSONObject requestJson=new JSONObject();
//        requestJson.put("file",null);
//        String uri = "/users/9";
//
//        //执行mvc请求
//        mvc.perform(get(uri)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.errorCode").value(0));
//    }

}
