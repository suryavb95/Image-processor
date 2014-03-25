import java.awt.image.*;
import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.*;
import javax.imageio.ImageIO;

/**
 * This class wraps around a picture using our custom Pixel class. Allows access to a pixel
 * using Cartesian (x,y) coordinates.
 *
 * @author Aaron Friesen
 * @version 1.0
 */
public class Pic {
    private Pixel[][] pixels;
    private BufferedImage buff;
    private String imageName;
    
    /**
     * Constructor for Pic. Sets up the backing Pixel array.
     *
     * @param imageName The name of the image you want to load, as a String. Includes file type.
     */
    public Pic (String imageName) {
        try {
            buff = ImageIO.read(new File(imageName));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Silly goose! That's not a valid file name.");
            System.exit(0);
        }
        this.imageName = imageName;
        pixels = new Pixel[buff.getWidth()][buff.getHeight()];
        ColorModel mod = buff.getColorModel();
        
        for (int x = 0; x < pixels.length; x++) {
            for (int y = 0; y < pixels[x].length; y++) {
                int p = buff.getRGB(x, y);
                int a = p >> 24 & 0xFF;
                int r = p >> 16 & 0xFF;
                int g = p >> 8 & 0xFF;
                int b = p & 0xFF;
                pixels[x][y] = new Pixel(r, g, b, a);
            }
        }
    }
    
    /**
     * Gets the width of the picture you set up in the constructor.
     *
     * @return The width of the picture.
     */
    public int getWidth() {
        return pixels.length;
    }
    
    /**
     * Gets the height of the picture you set up in the constructor.
     *
     * @return The height of the picture.
     */
    public int getHeight() {
        return pixels[0].length;
    }
    
    /**
     * Gets a Pixel object at an (x,y) point (starting from the top-left at 0,0). 
     * Note that any changes you make to the Pixel object are reflected in the Pixel array!
     *
     * @param x The x coordinate of the pixel in question.
     * @param y The y coordinate of the pixel in question.
     * @return The pixel at x,y.
     */
    public Pixel getPixel(int x, int y) {
        return pixels[x][y];   
    }
    
    /**
     * Returns a deep copy of the image. Useful when you want to maintain a "clean" copy.
     * 
     * @return The copy of the picture as a new Pic object.
     */
    public Pic deepCopy() {
        return new Pic(this.imageName);
    }
    
    /**
     * Shows the picture that corresponds to the Pixel array. Don't worry too much about the 
     * logic here - you'll learn about this soon enough. 
     *
     */
    public void show() {
        JFrame frame = new JFrame("Your Picture!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        for (int x = 0; x < pixels.length; x++) {
            for (int y = 0; y < pixels[x].length; y++) {
                int convert = convert(pixels[x][y]);
                buff.setRGB(x, y, convert);
            }
        }
        
        frame.add(new JLabel(new ImageIcon(buff)));
        frame.pack();
        frame.setVisible(true);       
    }
    
    /**
     * This is deep bitshifting magic. Also don't worry about this.
     *
     * @param p The pixel to convert back into int mode
     * @return The converted ARGB int.
     */
    private int convert(Pixel p) {
        return p.getAlpha() << 24 | p.getRed() << 16 | p.getGreen() << 8 | p.getBlue();
    }

    


}