/**
 * This class processes the image based on the RGB values of its pixels. Allows 
 * access to pictures using name of image.
 *
 * @author Surya Bhamidipati
 * @version 1.0
 */
public class ImageProcessor{
	private Pic picture;
	private Pic picture1;
	
	/**
	 * Constructor for ImageProcessor. Sets up main picture and watermark.
	 *
	 * @param imageName The name of the image you want to load, as a String. Includes file type.
	 * @param imageName1 The name of the image you want to load as watermark, as a String. Includes file type.
	 */
	public ImageProcessor(String imageName, String imageName1){
		picture = new Pic(imageName);
		picture1 = new Pic(imageName1);
	}
	
	/**
	 * Changes greyscale of image by taking average of RGB of each pixel and setting
	 * all pixels to that value.
	 *
	 * @return The manipulated copy of image.
	 */
	public Pic greyScale(){
		Pic copy = picture.deepCopy();
		for(int i = 0; i < copy.getWidth(); i++){
			for(int j = 0; j < copy.getHeight(); j++){
				int x = copy.getPixel(i,j).getRed();
				int y = copy.getPixel(i,j).getGreen();
				int z = copy.getPixel(i,j).getBlue();
				int avg = (x+y+z)/3;
				copy.getPixel(i,j).setRed(avg);
				copy.getPixel(i,j).setGreen(avg);
				copy.getPixel(i,j).setBlue(avg);
			}
		}
		return copy;
	}
	
	/**
	 * Inverts the color of image.
	 *
	 * @return The manipulated copy of image.
	 */
	public Pic invert(){
		Pic copy = picture.deepCopy();
		for(int i = 0; i < copy.getWidth(); i++){
			for(int j = 0; j < copy.getHeight(); j++){
				int x = 255 - copy.getPixel(i,j).getRed();
				int y = 255 - copy.getPixel(i,j).getGreen();
				int z = 255 - copy.getPixel(i,j).getBlue();
				copy.getPixel(i,j).setRed(x);
				copy.getPixel(i,j).setGreen(y);
				copy.getPixel(i,j).setBlue(z);
			}
		}
		return copy;	
	}
	
	/**
	 * Sets green and blue values of each pixel to zero.
	 *
	 * @return The manipulated copy of image.
	 */
	public Pic onlyRed(){
		Pic copy = picture.deepCopy();
		for(int i = 0; i < copy.getWidth(); i++){
			for(int j = 0; j < copy.getHeight(); j++){
				copy.getPixel(i,j).setGreen(0);
				copy.getPixel(i,j).setBlue(0);
			}
		}
		return copy;
	}
	
	/**
	 * Sets red and blue values of each pixel to zero.
	 *
	 * @return The manipulated copy of image.
	 */
	public Pic onlyGreen(){
		Pic copy = picture.deepCopy();
		for(int i = 0; i < copy.getWidth(); i++){
			for(int j = 0; j < copy.getHeight(); j++){
				copy.getPixel(i,j).setRed(0);
				copy.getPixel(i,j).setBlue(0);
			}
		}
		return copy;
	}
	
	/**
	 * Sets red and green values of each pixel to zero.
	 *
	 * @return The manipulated copy of image.
	 */
	public Pic onlyBlue(){
		Pic copy = picture.deepCopy();
		for(int i = 0; i < copy.getWidth(); i++){
			for(int j = 0; j < copy.getHeight(); j++){
				copy.getPixel(i,j).setRed(0);
				copy.getPixel(i,j).setGreen(0);
			}
		}
		return copy;
	}
	
	/**
	 * Sets non maximum values of each pixel to zero.
	 *
	 * @return The manipulated copy of image.
	 */
	public Pic maximize(){
		Pic copy = picture.deepCopy();
		for(int i = 0; i < copy.getWidth(); i++){
			for(int j = 0; j < copy.getHeight(); j++){
				int x = copy.getPixel(i,j).getRed();
				int y = copy.getPixel(i,j).getGreen();
				int z = copy.getPixel(i,j).getBlue();
				int big = x;
				if(y > big)
					big = y;
				if(z > big)
					big = z;
				if(x == big){
					copy.getPixel(i,j).setGreen(0);
					copy.getPixel(i,j).setBlue(0);
				}
				else if(y == big){
					copy.getPixel(i,j).setRed(0);
					copy.getPixel(i,j).setBlue(0);
				}
				else if(z == big){
					copy.getPixel(i,j).setRed(0);
					copy.getPixel(i,j).setGreen(0);
				}
			}
		}
		return copy;
	}
	
	/**
	 * Watermarks smaller picture on top of larger picture.
	 *
	 * @return The manipulated copy of image.
	 */
	public Pic watermark(){
		Pic copy, copy1;
		if(picture.getWidth() > picture1.getWidth()){
			copy = picture.deepCopy();
			copy1 = picture1.deepCopy();
		}
		
		else{
			copy = picture1.deepCopy();
			copy1 = picture.deepCopy();
		}
		
		for(int i = 0; i < copy1.getWidth(); i++){
			for(int j = 0; j < copy1.getHeight(); j++){
				int x = copy.getPixel(i,j).getRed();
				int y = copy.getPixel(i,j).getGreen();
				int z = copy.getPixel(i,j).getBlue();
				int a = copy1.getPixel(i,j).getRed();
				int b = copy1.getPixel(i,j).getGreen();
				int c = copy1.getPixel(i,j).getBlue();
				int avgRed = (x+a)/2;
				int avgGreen = (y+b)/2;
				int avgBlue = (z+c)/2;
				copy.getPixel(i,j).setRed(avgRed);
				copy.getPixel(i,j).setGreen(avgGreen);
				copy.getPixel(i,j).setBlue(avgBlue);
			}
		}
		return copy;
	}
	
	public static void main(String[]args){
		String imageName = args[0];
		String watermark = args[1];
		Pic picture = new Pic(imageName);
		Pic picture1 = new Pic(watermark);
		ImageProcessor imgPr = new ImageProcessor(imageName, watermark);
		imgPr.greyScale().show();
		imgPr.invert().show();
		imgPr.watermark().show();
		imgPr.onlyRed().show();
		imgPr.onlyGreen().show();
		imgPr.onlyBlue().show();
		imgPr.maximize().show();
	}
}
		

		
		

	