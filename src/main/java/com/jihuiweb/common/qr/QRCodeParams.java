package com.jihuiweb.common.qr;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * Created by Doaim on 2017/5/1.
 */
public class QRCodeParams {
    private String content;                 //二维码内容
    private String qrCodeUrl;               //二维码网络路径
    private String filePath;                //二维码生成物理路径
    private String fileName;                //二维码生成图片名称（包含后缀名）
    private String logoPath;                //logo图片
    private Integer width = 300;            //二维码宽度
    private Integer height = 300;           //二维码高度
    private Integer foregroundColor = 0xFF000000;   //前景色
    private Integer backgroundColor = 0xFFFFFFFF;  //背景色
    private Integer margin = 1;             //白边大小，取值范围0~4
    private String imageType = "jpg";       //图片类型
    private ErrorCorrectionLevel level = ErrorCorrectionLevel.L;  //二维码容错率

    public String getContent() {
        return content;
    }
    public QRCodeParams setContent(String content) {
        this.content = content;
        return this;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public Integer getWidth() {
        return width;
    }
    public QRCodeParams setWidth(Integer width) {
        this.width = width;
        return this;
    }
    public Integer getHeight() {
        return height;
    }
    public QRCodeParams setHeight(Integer height) {
        this.height = height;
        return this;
    }
    public String getQrCodeUrl() {
        return qrCodeUrl;
    }
    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }
    public String getLogoPath() {
        return logoPath;
    }
    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }
    public Integer getForegroundColor() {
        return foregroundColor;
    }
    public void setForegroundColor(Integer foregroundColor) {
        this.foregroundColor = foregroundColor;
    }
    public Integer getBackgroundColor() {
        return backgroundColor;
    }
    public void setBackgroundColor(Integer backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    public ErrorCorrectionLevel getLevel() {
        return level;
    }
    public void setLevel(ErrorCorrectionLevel level) {
        this.level = level;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public Integer getMargin() {
        return margin;
    }
    public void setMargin(Integer margin) {
        this.margin = margin;
    }
}
