package com.caoyunhao.petshop_admin.module.image;

import com.caoyunhao.petshop_admin.common.exception.ErrorCode;
import com.caoyunhao.petshop_admin.common.exception.WebBackendException;
import com.caoyunhao.petshop_admin.common.util.DataUtil;
import com.caoyunhao.petshop_admin.common.util.HashCodeUtil;
import com.caoyunhao.petshop_admin.entity.*;
import com.caoyunhao.petshop_admin.repository.CommodityImageRepository;
import com.caoyunhao.petshop_admin.repository.CustomImageRepository;
import com.caoyunhao.petshop_admin.repository.ImageStoreRepository;
import com.caoyunhao.petshop_admin.repository.UserImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.Optional;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@Service
@Transactional
public class ImageService {

    @Autowired
    ImageStoreRepository imageStoreRepository;

    @Autowired
    CommodityImageRepository commodityImageRepository;

    @Autowired
    UserImageRepository userImageRepository;

    @Autowired
    CustomImageRepository customImageRepository;

    private final int COMMODITY_DEFAULT_IMAGE_NUMBER = 1;

    /**
     * 查找商品图片
     * @param commodityId
     * @param httpServletResponse
     * @throws Exception
     */
    public void findCommodityImage(Long commodityId, HttpServletResponse httpServletResponse) throws Exception {
        CommodityImage commodityImage = commodityImageRepository.findByCommodityId(commodityId);
        if (commodityImage == null) {
            return;
            // throw new WebBackendException(ErrorCode.QUERY_DATA_EMPTY);
        }
        Long imageId = commodityImage.getImageId();
        this.findImage(imageId, httpServletResponse);
    }

    /**
     * 添加商品图片
     * @param multipartFile
     * @param commodityId
     * @throws Exception
     */
    public void addCommodityImage(MultipartFile multipartFile, Long commodityId) throws Exception {
        if (null == multipartFile) {
            throw new WebBackendException(ErrorCode.FILE_PARAM_IS_REQUIRED);
        }

        Long imageId = this.addImage(multipartFile);

        commodityImageRepository.deleteAllByCommodityId(commodityId);

        CommodityImage commodityImage = new CommodityImage();

        commodityImage.setCommodityId(commodityId);
        commodityImage.setImageId(imageId);
        commodityImage.setImageNumber(COMMODITY_DEFAULT_IMAGE_NUMBER);

        commodityImageRepository.save(commodityImage);
    }

    /**
     * 查找用户图片
     */
    public void findUserImage(Long userId, HttpServletResponse httpServletResponse) throws Exception {
        UserImage userImage = userImageRepository.findByUserId(userId);
        if (userImage == null) {
            return;
            // throw new WebBackendException(ErrorCode.QUERY_DATA_EMPTY);
        }
        Long imageId = userImage.getImageId();
        this.findImage(imageId, httpServletResponse);
    }

    /**
     * 添加用户头像
     */
    public void addUserImage(MultipartFile multipartFile, Long userId) throws Exception {
        if (null == multipartFile) {
            throw new WebBackendException(ErrorCode.FILE_PARAM_IS_REQUIRED);
        }
        Long imageId = this.addImage(multipartFile);
        userImageRepository.deleteAllByUserId(userId);
        UserImage userImage = new UserImage();
        userImage.setUserId(userId);
        userImage.setImageId(imageId);
        userImage.setImageNumber(COMMODITY_DEFAULT_IMAGE_NUMBER);

        userImageRepository.save(userImage);
    }

    /**
     * 查找用户图片
     */
    public void findCustomImage(Long customId, HttpServletResponse httpServletResponse) throws Exception {
        CustomImage customImage = customImageRepository.findByCustomId(customId);
        if (customImage == null) {
            return;
            // throw new WebBackendException(ErrorCode.QUERY_DATA_EMPTY);
        }
        Long imageId = customImage.getImageId();
        this.findImage(imageId, httpServletResponse);
    }

    /**
     * 添加用户头像
     */
    public void addCustomImage(MultipartFile multipartFile, Long customId) throws Exception {
        if (null == multipartFile) {
            throw new WebBackendException(ErrorCode.FILE_PARAM_IS_REQUIRED);
        }
        Long imageId = this.addImage(multipartFile);
        customImageRepository.deleteAllByCustomId(customId);
        CustomImage userImage = new CustomImage();
        userImage.setCustomId(customId);
        userImage.setImageId(imageId);
        userImage.setImageNumber(COMMODITY_DEFAULT_IMAGE_NUMBER);

        customImageRepository.save(userImage);
    }

    /**
     * 查找图片
     */
    private void findImage(Long id, HttpServletResponse httpServletResponse) throws Exception {
        if (null == id) {
            throw new WebBackendException(ErrorCode.IMAGE_ID_NOT_VALID);
        }

        ImageStore imageStore = getImageStore(id);

        if (null == imageStore) {
            throw new WebBackendException(ErrorCode.QUERY_DATA_EMPTY);
        }

        ServletOutputStream out = httpServletResponse.getOutputStream();

        out.write(imageStore.getImage());

        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    /**
     * 添加图片
     * @return image id
     */
    private Long addImage(MultipartFile multipartFile) throws Exception {
        if (null == multipartFile) {
            throw new WebBackendException(ErrorCode.NOT_VALID_PARAM);
        }

        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
        // 是否为图片
        if (bufferedImage == null) {
            throw new WebBackendException(ErrorCode.FILE_NOT_VALID);
        }
        System.out.println(bufferedImage.getType());

        byte[] imageBytes = multipartFile.getBytes();

        String imageHash = HashCodeUtil.getHash(imageBytes);
        if (null == imageHash) {
            throw new WebBackendException(ErrorCode.FILE_NOT_VALID);
        }

        ImageStore existedImageStore = getImageStoreByHash(imageHash);
        if (null != existedImageStore) {
            return existedImageStore.getId();
        } else {
            String fileName = multipartFile.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));

            if (suffixName.length() <= 1) {
                throw new WebBackendException(ErrorCode.FILE_NOT_VALID);
            }

            ImageStore imageStore = new ImageStore();
            imageStore.setImage(multipartFile.getBytes());
            imageStore.setFormat(suffixName.replace(".", "").toLowerCase());
            imageStore.setHash(imageHash);

            return imageStoreRepository.save(imageStore).getId();
        }
    }

//    public CustomImage getCustomImageByCustomId(Long customId)
//            throws WebBackendException {
//        return DataUtil.getOrElse(id_ -> customImageRepository.findByCustomId(id_), customId, false);
//    }
//
//    public CommodityImage getCommodityImageByCommodityId(Long commodityId)
//            throws WebBackendException {
//        return DataUtil.getOrElse(id_ -> commodityImageRepository.findByCommodityId(id_), commodityId, false);
//    }

    public ImageStore getImageStore(Long id)
            throws WebBackendException {
        return DataUtil.getOrElse(id_ -> imageStoreRepository.findById(id_), id);
    }

    public ImageStore getImageStoreByHash(String hash)
            throws WebBackendException {
        Optional<ImageStore> imageStoreOptional = imageStoreRepository.findByHash(hash);
        return imageStoreOptional.orElse(null);
    }
}
