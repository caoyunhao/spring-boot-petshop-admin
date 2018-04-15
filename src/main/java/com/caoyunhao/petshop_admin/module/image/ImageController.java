package com.caoyunhao.petshop_admin.module.image;

import com.caoyunhao.petshop_admin.common.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@RequestMapping(value = "/images")
@RestController
public class ImageController {

    @Autowired
    ImageService imageService;

    /**
     * 获取商品图片
     */
    @RequestMapping(value = "/commodities/{id}", method = RequestMethod.GET)
    public void findCommodityImage(@PathVariable Long id, HttpServletResponse httpServletResponse) throws Exception {
        imageService.findCommodityImage(id, httpServletResponse);
    }

    /**
     * 上传商品图片
     */
    @RequestMapping(value = "/commodities/{id}", method = RequestMethod.POST)
    public BaseResponse addCommodityImage(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws Exception {
        imageService.addCommodityImage(file, id);
        return new BaseResponse();
    }

    /**
     * 获取用户头像
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public void findUserImage(@PathVariable Long id, HttpServletResponse httpServletResponse) throws Exception {
        imageService.findUserImage(id ,httpServletResponse);
    }

    /**
     * 上传用户头像
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
    public BaseResponse addUserImage(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws Exception {
        imageService.addUserImage(file, id);
        return new BaseResponse();
    }

    /**
     * 获取顾客头像
     */
    @RequestMapping(value = "/customs/{id}", method = RequestMethod.GET)
    public void findCustomImage(@PathVariable Long id, HttpServletResponse httpServletResponse) throws Exception {
        imageService.findCustomImage(id ,httpServletResponse);
    }

    /**
     * 上传顾客头像
     */
    @RequestMapping(value = "/customs/{id}", method = RequestMethod.POST)
    public BaseResponse addCustomImage(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws Exception {
        imageService.addCustomImage(file, id);
        return new BaseResponse();
    }
}
