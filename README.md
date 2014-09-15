Face-Detection-and-Recognition
==============================

This is Face Detection and Recognition Package. The related seminar was conducted in GDGKolkata event.

For running this software you will need OpenCV 2.4.9 and JavaCV 0.9.

Download OpenCV 2.4.9 from http://opencv.org/downloads.html as per your system.
Download JavaCV 0.9 binary archive javacv-0.9-bin.zip (129 MB) from http://bytedeco.org/download/

Make sure you have properly configured your dependencies and classpath before running these codes.

File Descriptions:

1. src/Detections.java                : Very basic code to take a input file from src/Pictures/ and detect face in it and write output file.
2. src/live.java                      : Detect a face from a live input stream.
3. src/FaceDetector.java              : Takes input from all the files present in src/Pictures,detect faces in them,crop those faces and write in Faces folder.
4. src/testrecognizer.java            : Takes input a TESTIMAGE.png (if you uncomment camera write,it will take live input).
5. src/OpenCVFaceRecognizer.java      : Recoginizes and predicts the TESTIMAGE.png from FACES folder. returns -1 if couldn't find.



Play with thresholds and fine tune it.
