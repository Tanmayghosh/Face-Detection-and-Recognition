 import java.awt.*;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;  
import java.awt.image.DataBufferByte;  
import java.io.File;

import javax.swing.*;  

import org.opencv.core.Core;  
import org.opencv.core.Mat;  
import org.opencv.core.MatOfRect;  
import org.opencv.core.Point;  
import org.opencv.core.Rect;  
import org.opencv.core.Scalar;  
import org.opencv.core.Size;  
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;  
import org.opencv.imgproc.Imgproc;  
import org.opencv.objdetect.CascadeClassifier;  
 class My_Panel extends JPanel{  
      private static final long serialVersionUID = 1L;  
      public static int globalInt = 0;
      private BufferedImage image;  
      // Create a constructor method  
      public My_Panel(){  
           super();   
      }  
   
      public boolean MatToBufferedImage(Mat matBGR){  
           long startTime = System.nanoTime();  
           int width = matBGR.width(), height = matBGR.height(), channels = matBGR.channels() ;  
           byte[] sourcePixels = new byte[width * height * channels];  
           matBGR.get(0, 0, sourcePixels);  
           // create new image and get reference to backing data  
           image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);  
           final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();  
           System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);  
           long endTime = System.nanoTime();  
           System.out.println(String.format("Elapsed time: %.2f ms", (float)(endTime - startTime)/1000000));  
           return true;  
      }  
      public void paintComponent(Graphics g){  
           super.paintComponent(g);   
           if (this.image==null) return;  
            g.drawImage(this.image,10,10,this.image.getWidth(),this.image.getHeight(), null);  
     
      }  
 }  
 class processor {  
      private CascadeClassifier face_cascade;  
      // Create a constructor method  
      public processor(){  
           face_cascade= new CascadeClassifier("src/Classifiers/haarcascade_frontalface_alt.xml");  
           if(face_cascade.empty())  
           {  
                System.out.println("--(!)Error loading A\n");  
                 return;  
           }  
           else  
           {  
                      System.out.println("Face classifier loaded up");  
           }  
      }  
      public Mat detect(Mat inputframe){  
           Mat mRgba=new Mat();  
           Mat mGrey=new Mat();  
           MatOfRect faces = new MatOfRect();  
           inputframe.copyTo(mRgba);  
           inputframe.copyTo(mGrey);  
           Imgproc.cvtColor( mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);  
           Imgproc.equalizeHist( mGrey, mGrey );  
           face_cascade.detectMultiScale(mGrey, faces);  
           System.out.println(String.format("Detected %s faces", faces.toArray().length));  
           for(Rect rect:faces.toArray())  
           {  
                Point center= new Point(rect.x + rect.width*0.5, rect.y + rect.height*0.5 );  
                Core.ellipse( mRgba, center, new Size( rect.width*0.5, rect.height*0.5), 0, 0, 360, new Scalar( 255, 0, 255 ), 4, 8, 0 );  
           }  
           return mRgba;  
      }  
 }  
 public class live {  
      public static void main(String arg[]){  
         
       System.loadLibrary("opencv_java249");       
       String window_name = "Capture - Face detection";  
       JFrame frame = new JFrame(window_name);  
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    frame.setSize(400,400);  
    processor my_processor=new processor();  
    final My_Panel my_panel = new My_Panel();  
    frame.setContentPane(my_panel);       
    frame.setVisible(true);        
         
     
    JButton click = new JButton("Click");
    my_panel.add(click);
       
        
        
        VideoCapture camera = new VideoCapture(0);
    	
    	if(!camera.isOpened()){
    		System.out.println("Error");
    	}
    	else {
    		final Mat frame1 = new Mat();
    
	        click.addActionListener(  
	        		
	                new ActionListener() {
	                	
          
						public void actionPerformed(ActionEvent arg0) {
							
							 String path = "src/Pics"; 
							 final String filename  ;
							  
							  File folder = new File(path);
							  int i = folder.listFiles().length; 
							 i++;
							  
							  if(i== 0){
								   filename = "Pics/ouput0.png";
							  }
							  else{
								  filename = "Pics/ouput"+i+".png";
							  }
							  
							  Highgui.imwrite(filename, frame1);
								Highgui.imwrite("src/"+filename, frame1);
								
							
							System.out.print(filename);
							
							
						} 
						
	                
	                });   	    
    		
    		
    		while(true){
    	    	if (camera.read(frame1)){
    	    		   frame.setSize(frame1.width()+40,frame1.height()+60);  
     
                       Mat frame2 = my_processor.detect(frame1);  
     
                       my_panel.MatToBufferedImage(frame2);
                         
                       my_panel.repaint(); 
                       
                       
                       
                       
    	    	}
    	    }	
    	}
    	camera.release();
       }

 }  