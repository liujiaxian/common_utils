package com.jihuiweb.common.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.Hashtable;

/**
 * Created by Doaim on 2017/5/1.
 * 二维码工具类
 * 修改自http://blog.csdn.net/rongku/article/details/51872156
 */
public class QRCodeUtil {
    /**
     * 生成二维码
     * @param qrCodeParams
     * @return 二维码的 Base64 字符串
     */
    public static String generateQRImageToBase64(QRCodeParams qrCodeParams) throws QRParamsException {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        generateQRImage(qrCodeParams,arrayOutputStream);
        return new String(Base64.getEncoder().encode(arrayOutputStream.toByteArray()));

    }
    public static File generateQRImage(QRCodeParams qrCodeParams) throws QRParamsException {
        if(qrCodeParams == null || qrCodeParams.getFileName() == null || qrCodeParams.getFilePath() == null){
            throw new QRParamsException("参数错误");
        }
        String imgPath = qrCodeParams.getFilePath();
        String imgName = qrCodeParams.getFileName();
        String outputFilePath;
        File filePath = new File(imgPath);
        if(!filePath.exists()){
            filePath.mkdirs();
        }
        if(imgPath.endsWith("/")){
            outputFilePath = imgPath + imgName;
        }else{
            outputFilePath = imgPath + "/"+imgName;
        }
        try {
            OutputStream outputStream = new FileOutputStream(outputFilePath);
            generateQRImage(qrCodeParams,outputStream);
            return new File(outputFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static void generateQRImage(QRCodeParams qrCodeParams,OutputStream outputStream)throws QRParamsException{
        if(qrCodeParams.getContent() == null){
            throw new QRParamsException("内容不能为空！");
        }
        try{
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, qrCodeParams.getLevel());
            hints.put(EncodeHintType.MARGIN, qrCodeParams.getMargin());  //设置白边
            if(qrCodeParams.getLogoPath() != null && !"".equals(qrCodeParams.getLogoPath().trim())){
                BitMatrix bitMatrix = new MultiFormatWriter().encode(qrCodeParams.getContent(), BarcodeFormat.QR_CODE, qrCodeParams.getWidth(), qrCodeParams.getHeight(), hints);
                writeToFile(bitMatrix, qrCodeParams.getImageType(), outputStream, qrCodeParams.getLogoPath(),qrCodeParams.getForegroundColor(), qrCodeParams.getBackgroundColor());
            }else{
                MatrixToImageConfig config = new MatrixToImageConfig(qrCodeParams.getForegroundColor(), qrCodeParams.getBackgroundColor());
                BitMatrix bitMatrix = new MultiFormatWriter().encode(qrCodeParams.getContent(), BarcodeFormat.QR_CODE, qrCodeParams.getWidth(), qrCodeParams.getHeight(), hints);
                MatrixToImageWriter.writeToStream(bitMatrix, qrCodeParams.getImageType(), outputStream, config);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     *
     * @param matrix 二维码矩阵相关
     * @param format 二维码图片格式
     * @param logoPath logo路径
     * @throws IOException
     */
    private static void writeToFile(BitMatrix matrix,String format,OutputStream outputStream,String logoPath,Integer foregroundColor,Integer backgroundColor) throws IOException {
        BufferedImage image = toBufferedImage(matrix,foregroundColor,backgroundColor);
        Graphics2D gs = image.createGraphics();

        int ratioWidth = image.getWidth()*2/10;
        int ratioHeight = image.getHeight()*2/10;
        //载入logo
        Image img = ImageIO.read(new File(logoPath));
        int logoWidth = img.getWidth(null)>ratioWidth?ratioWidth:img.getWidth(null);
        int logoHeight = img.getHeight(null)>ratioHeight?ratioHeight:img.getHeight(null);

        int x = (image.getWidth() - logoWidth) / 2;
        int y = (image.getHeight() - logoHeight) / 2;

        gs.drawImage(img, x, y, logoWidth, logoHeight, null);
        gs.setColor(Color.black);
        gs.setBackground(Color.WHITE);
        gs.dispose();
        img.flush();
        if(!ImageIO.write(image, format, outputStream)){
            throw new IOException("Could not write an image of format " + format);
        }
    }

    private static BufferedImage toBufferedImage(BitMatrix matrix,Integer foregroundColor,Integer backgroundColor){
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                image.setRGB(x, y, matrix.get(x, y) ?  foregroundColor: backgroundColor);
            }
        }
        return image;
    }

    public static BitMatrix deleteWhite(BitMatrix matrix){
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }
}
