package com.qiandu.dev.util;

import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.raster.JimiRasterImage;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;


/**
 * Created by LTN on 2016/7/27.
 */
public class ImageUtil {

    public static void main(String[] args) throws Exception {
        String path = "H:\\TestData\\img\\qiufen.jpg";
        byte[] b = toByteArray(new File(path));

        byte[] dst = ImageUtil.resizeImage(b, 80, 80);

        ByteArrayInputStream in = new ByteArrayInputStream(dst);
        BufferedImage image = ImageIO.read(in);
        File newFile = new File("H:\\TestData\\img\\qiufen_refine1.jpg");
        ImageIO.write(image, "jpg", newFile);
    }

    public static byte[] toByteArray(File imageFile) throws Exception {
        BufferedImage img = ImageIO.read(imageFile);
        ByteArrayOutputStream buf = new ByteArrayOutputStream((int) imageFile.length());
        try {
            ImageIO.write(img, "jpg", buf);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return buf.toByteArray();
    }

    public static byte[] resizeImage(byte[] src, int dstH, int dstW) {
        try {
            Image inImage = Toolkit.getDefaultToolkit().createImage(src);
            ImageIcon inImageIcon = new ImageIcon(src);

            int srcH = inImageIcon.getIconHeight();
            int srcW = inImageIcon.getIconWidth();
            double tempH = (double) dstH / (double) srcH;
            double tempW = (double) dstW / (double) srcW;
            double scale = (tempW < tempH) ? tempW : tempH;

            int scaledW = (int) (scale * srcW);
            int scaledH = (int) (scale * srcH);

            Image img = inImage.getScaledInstance(scaledW, scaledH, Image.SCALE_FAST);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            JimiRasterImage raster = Jimi.createRasterImage(img.getSource());
            // --java.io.ByteArrayOutputStream
            Jimi.putImage("image/jpeg", raster, outStream);
            outStream.flush();
            outStream.close();
            return outStream.toByteArray();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String encodeToString(byte[] imageBytes) {
        String imageString = null;
        imageString = Base64.encodeBase64String(imageBytes);
        return imageString;
    }

}
