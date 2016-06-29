/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagepixelation;
import simplegui.SimpleGUI;
import simplegui.GUIListener;
import simplegui.DrwImage;
import simplegui.RGB;
import java.awt.Color;
/**
 *
 * @author CAP
 */
public class Imagepixelation implements GUIListener {

    private SimpleGUI sg;
    private DrwImage im;
    
    public Imagepixelation(){
        im = new DrwImage("images/fallleaves.jpg");
        sg = new SimpleGUI(im.getWidth(), im.getHeight(), true);
        
        sg.drawImage(im, 0,0,  im.getWidth(), im.getHeight(), null);
        sg.print("Move Slider to adjust Pixelation. Click \"Reset\" to go back"
                + "to the original pixelation");
        sg.labelButton1("Reset");
        sg.registerToGUI(this);

    }
    
    
    @Override
    public void reactToButton1(){
        sg.eraseAllDrawables("box");
    }
    @Override
    public void reactToButton2(){}
    @Override
    public void reactToSwitch (boolean bln){}
    @Override
    public void reactToSlider(int i){
        
        double g = (0.48*i) + 2; // adjusting the slider range to 2 - 50;
        i = (int)g; // i already wrote the code using integer i so I just changed it here to avoid changing it throughout the whole code
        
        for (int j = 0; j <= im.getWidth() - i; j+= i)
            for (int k = 0; k <= im.getHeight() - i; k+=i){
                //the if - else statements take care of the issue of boxes
                //that could potentially end up off the screen
                if(k + i > im.getHeight() && j + i > im.getWidth()) // corner box
                    sg.drawFilledBox(j, k, im.getWidth() - j, im.getHeight() - k, getColor(j,k,im.getWidth() - j, im.getHeight() - k,im), 1, "box");
                else if (k+i > im.getHeight()) //boxes on bottom edge
                    sg.drawFilledBox(j, k, i-1, im.getHeight() - k, getColor(j,k,i,im.getHeight() - k,im), 1, "box");
                else if (j+i > im.getWidth()) //boxes on right edge
                    sg.drawFilledBox(j, k, im.getWidth() - j, i, getColor(j,k,im.getWidth() - j,k,im), 1, "box");
                else //all other boxes
                    sg.drawFilledBox(j,k,i ,i , getColor(j,k,i,i, im), 1, "box");

            }
        

    }
    
    public static Color getColor(int x, int y, int gridsize1, int gridsize2, DrwImage image){
        int red, green, blue, count;
        red = green = blue = count = 0;
        int width = x + gridsize1;
        int height = y + gridsize2;
        for(; x<width; x++){
            for(; y < height; y ++){
                RGB rgb = image.getPixelRGB(x,y);
                red += rgb.r; 
                blue += rgb.b;
                green += rgb.g;
                count ++;
            }
            y = height - gridsize2;
        }
        double avgRed = (double) red/count;
        double avgGreen = (double) green/count;
        double avgBlue = (double) blue/count;
        return new Color ((int) avgRed, (int) avgGreen, (int) avgBlue);
           
    }
    
    
    public static void main(String[] args) {  
        new Imagepixelation();
        
    }
}

