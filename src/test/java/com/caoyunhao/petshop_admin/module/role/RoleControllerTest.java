package com.caoyunhao.petshop_admin.module.role;

import com.caoyunhao.petshop_admin.PetshopInternApplication;
import com.caoyunhao.petshop_admin.entity.Role;
import com.caoyunhao.petshop_admin.repository.RoleRepository;
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
 * @author tao.jiang
 * @date 2017/10/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PetshopInternApplication.class)
@Transactional
public class RoleControllerTest {

    @Rule
    public ExpectedException throwException = ExpectedException.none();
    MockMvc mvc;
    @Autowired
    private RoleController roleController;
    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Test
    public void getRoles() throws Exception {
        String uri = "/roles";

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
    public void findRole() throws Exception {
        JSONObject requestJson = new JSONObject();
        String uri = "/roles/7";

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
    public void addRole() throws Exception {
        JSONArray roleList = new JSONArray();
        JSONObject roleAuth1 = new JSONObject();
        roleAuth1.put("id", 1);
        roleList.put(roleAuth1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("roleName", "tyui1234");

        JSONObject requestJson = new JSONObject();
        requestJson.put("data", jsonObject);
        String uri = "/roles";

        //执行mvc请求
        mvc.perform(post(uri)
                .content(requestJson.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0));

    }

    @Test
    public void updateRole() throws Exception {
        JSONArray authList = new JSONArray();
        JSONObject roleAuth1 = new JSONObject();
        roleAuth1.put("id", 7);
        authList.put(roleAuth1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("roleName", "tyui1234");

        JSONObject requestJson = new JSONObject();
        requestJson.put("data", jsonObject);
        String uri = "/roles/7";

        //执行mvc请求
        mvc.perform(put(uri)
                .content(requestJson.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0));

    }

    @Test
    public void deleteRole() throws Exception {
        String uri = "/roles/7";

        //执行mvc请求
        mvc.perform(delete(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0));
    }

    @Test
    public void findByRoleName()throws Exception{
        JSONObject requestJson = new JSONObject();
        String uri = "/roles/name/s";

        //执行mvc请求
        mvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0));
    }

}
