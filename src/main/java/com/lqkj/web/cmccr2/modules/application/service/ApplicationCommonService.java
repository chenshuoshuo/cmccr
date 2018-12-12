package com.lqkj.web.cmccr2.modules.application.service;

import com.google.common.collect.Lists;
import com.lqkj.web.cmccr2.modules.application.domain.CcrVersionApplication;
import com.lqkj.web.cmccr2.modules.application.dao.CcrVersionApplicationRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Service
public class ApplicationCommonService {

    public static final String UPLOAD_FILE_PATH = "./upload/application/";

    @Autowired
    public CcrVersionApplicationRepository managerApplicationDao;

    /**
     * 保存上传的文件
     *
     * @return 保存的路径
     */
    public String saveUploadFile(MultipartFile file, String... supportFormats) throws Exception {
        String format = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];

        if (supportFormats.length != 0 && !Lists.newArrayList(supportFormats).contains(format)) {
            throw new Exception("格式不支持:" + format);
        }

        File outPutFile = new File(new StringBuilder().append(UPLOAD_FILE_PATH)
                .append(DigestUtils.md2Hex(String.valueOf(System.currentTimeMillis())))
                .append(".")
                .append(format)
                .toString());

        InputStream is = null;

        try {
            is = file.getInputStream();

            FileUtils.copyInputStreamToFile(is, outPutFile);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return outPutFile.getPath();
    }

    /**
     * 获取应用名称
     *
     * @param id 应用id
     * @return 应用名称
     */
    public String getApplicationName(Long id) {
        return managerApplicationDao.getOne(id).getName();
    }

    /**
     * 设置应用图片访问地址
     */
    public CcrVersionApplication setIconURL(CcrVersionApplication application) {
       String url = application.getIconPath()
               .replace("./upload/","/static/");

       application.setIconURL(url);

       return application;
    }

    /**
     * 根据应用名称搜索
     */
    public Page<CcrVersionApplication> search(String keyword, String type, Integer page, Integer pageSize) {
        CcrVersionApplication versionApplication = new CcrVersionApplication();
        versionApplication.setType(type);
        versionApplication.setName(keyword);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("id");

        return this.managerApplicationDao.findAll(Example.of(versionApplication, matcher),
                PageRequest.of(page, pageSize));
    }
}
