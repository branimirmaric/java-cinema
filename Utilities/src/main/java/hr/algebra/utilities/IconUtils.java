/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utilities;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author dnlbe
 */
public class IconUtils {

    private IconUtils() {
    }

    public static ImageIcon createIcon(Optional<File> file, int width, int height) throws IOException {
        File fl = file.get();
        BufferedImage bufferedImage = ImageIO.read(fl);
        Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}
