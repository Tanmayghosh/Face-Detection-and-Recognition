import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

public class Detection {

	public static void main(String[] args) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.out.println("\nRunning FaceDetector");

		CascadeClassifier faceDetector = new CascadeClassifier(
				"src/Classifiers/haarcascade_frontalface_alt.xml");

		// CascadeClassifier faceDetector = new
		// CascadeClassifier(FaceDetector.class.getResource("haarcascade_frontalface_alt.xml").getPath().substring(1));
		Mat image = Highgui
				.imread("src/Pictures/leonardo-dicaprio-golden-globes-2010-02.jpg");

		MatOfRect faceDetections = new MatOfRect();
		// faceDetector.detectMultiScale(image, faceDetections);
		// faceDetector.detectMultiScale(image, objects, scaleFactor,
		// minNeighbors, flags, minSize, maxSize);

		faceDetector.detectMultiScale(image, faceDetections, 3, 2,
				Objdetect.CASCADE_DO_CANNY_PRUNING, new Size(0, 0), new Size(
						600, 600));

		System.out.println(String.format("Detected %s faces",
				faceDetections.toArray().length));

		for (Rect rect : faceDetections.toArray()) {
			Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x
					+ rect.width, rect.y + rect.height), new Scalar(0, 0, 255),
					10);
		}

		String filename = "ouput.png";
		System.out.println(String.format("Writing %s", filename));
		Highgui.imwrite(filename, image);
	}
}