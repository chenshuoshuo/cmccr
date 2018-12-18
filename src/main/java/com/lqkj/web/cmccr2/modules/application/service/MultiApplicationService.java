package com.lqkj.web.cmccr2.modules.application.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.modules.application.domain.CcrMultiApplication;
import com.lqkj.web.cmccr2.modules.application.dao.CcrMultiApplicationRepository;
import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * 组合应用服务
 */
@Service
@Transactional
public class MultiApplicationService {
    //二维码参数
    private final int QR_CODE_WIDTH = 200;
    private final int QR_CODE_HEIGHT = 200;
    private final int QR_CODE_SCALE = 7;

    @Autowired
    CcrMultiApplicationRepository multiApplicationDao;

    @Autowired
    CcrSystemLogService systemLogService;

    /**
     * 创建一个组合应用
     */
    public Long createApplication(CcrMultiApplication application, String iconPath)
            throws Exception {
        systemLogService.addLog("组合应用服务","createApplication"
                ,"创建一个组合应用");

        if (application.getAndroidURL() == null && application.getIosURL() == null &&
                application.getWebURL() == null) {
            throw new Exception("请至少输入应用URL参数");
        }

        application.setIconPath(iconPath);

        return multiApplicationDao.save(application).getId();
    }

    /**
     * 删除组合应用
     *
     * @param id 组合应用id
     */
    public void deleteApplication(Long[] id) {
        systemLogService.addLog("组合应用服务","deleteApplication"
                ,"批量删除组合应用");

        for (Long i : id) {
            multiApplicationDao.deleteById(i);
        }
    }

    /**
     * 更新组合应用
     */
    public Long updateApplication(@NotNull Long applicationId, CcrMultiApplication application) throws Exception {
        if (application.getAndroidURL() == null && application.getIosURL() == null &&
                application.getWebURL() == null) {
            throw new Exception("请至少输入应用URL参数");
        }
        if (!applicationId.equals(application.getId())) {
            throw new Exception("应用id不相等");
        }
        systemLogService.addLog("组合应用服务","updateApplication"
                ,"更新组合应用");

        return multiApplicationDao.save(application).getId();
    }

    /**
     * 根据id查询组合应用信息
     *
     * @param id 组合应用id
     * @return 信息
     */
    public CcrMultiApplication getApplication(Long id) {
        systemLogService.addLog("组合应用服务","getApplication"
                ,"查询组合应用");

        return multiApplicationDao.findById(id).get();
    }

    /**
     * 分页查询
     */
    public Page<CcrMultiApplication> getPage(Integer page, Integer pageSize) {
        systemLogService.addLog("组合应用服务","getPage"
                ,"分页查询组合应用");

        return multiApplicationDao.findAll(PageRequest.of(page, pageSize));
    }

    /**
     * 创建二维码
     *
     * @param applicationId 组合应用id
     */
    public String createAppQRCode(Long applicationId, String requestURL) throws Exception {
        systemLogService.addLog("组合应用服务","createAppQRCode"
                ,"创建二维码");

        CcrMultiApplication application = multiApplicationDao.getOne(applicationId);

        String iconPath = application.getIconPath();

        String url = requestURL + "/center/application/multi/" + APIVersion.V2 + "/jump/"
                + applicationId;

        return createQRCode(iconPath, url);
    }

    public String createQRCode(String iconPath, String url) throws WriterException, IOException {
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");    //指定字符编码为“utf-8”
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  //指定二维码的纠错等级
        hints.put(EncodeHintType.MARGIN, 0);    //设置图片的边距

        QRCodeWriter writer = new QRCodeWriter();

        BitMatrix m = writer.encode(url, BarcodeFormat.QR_CODE, QR_CODE_WIDTH, QR_CODE_HEIGHT);

        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(m,
                new MatrixToImageConfig(0xFF000001, 0xFFFFFFF1));

        if (iconPath != null) {
            Graphics2D graphics = bufferedImage.createGraphics();

            BufferedImage logoImage = ImageIO.read(new File(iconPath));

            graphics.drawImage(
                    logoImage.getScaledInstance(QR_CODE_WIDTH / QR_CODE_SCALE,
                            QR_CODE_HEIGHT / QR_CODE_SCALE, Image.SCALE_SMOOTH),
                    QR_CODE_WIDTH / 2 - QR_CODE_WIDTH / QR_CODE_SCALE / 2,
                    QR_CODE_HEIGHT / 2 - QR_CODE_HEIGHT / QR_CODE_SCALE / 2, null);

            graphics.dispose();

            bufferedImage.flush();
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        ImageIO.write(bufferedImage, "png", os);

        return Base64.encodeBase64String(os.toByteArray());
    }

    /**
     * 创造一个测试用的二维码
     */
    public void createSimpleQRCode(OutputStream outputStream) throws WriterException, IOException {
        systemLogService.addLog("组合应用服务","createSimpleQRCode"
                ,"创建测试二维码");

        String content = "http://www.baidu.com";

        QRCodeWriter writer = new QRCodeWriter();

        BitMatrix m = writer.encode(content, BarcodeFormat.QR_CODE, 200, 200);

        MatrixToImageWriter.writeToStream(m, "png", outputStream);
    }
}
