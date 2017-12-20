package examinador;

import examinador.constructor;
import examinador.setImg;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Main {

    private static String nombreArchivo;
    private int Aciertos = 0;
    ArrayList<constructor> datExa = new ArrayList<>();
    int colTabla;

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
        System.out.println(this.nombreArchivo);
    }

    public void AbrirArchivo() throws IOException {
        try {
            FileInputStream fis = new FileInputStream("dat/" + nombreArchivo);
            ObjectInputStream entrada = new ObjectInputStream(fis);
            datExa = (ArrayList<constructor>) entrada.readObject();
            entrada.close();
        } catch (FileNotFoundException e) {
        } catch (IOException | ClassNotFoundException e) {
        }
    }

    public void califica(ArrayList<setImg> imagenL) throws IOException, SQLException {

        //Abrimos OpenCV
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Main m = new Main();
        for (int i = 0; i < imagenL.size(); i++) {
            //Leemos la imagen original
            Mat image = Imgcodecs.imread(imagenL.get(i).getUrl());
            //creamos la imagen enderezada
            Mat straightImage = m.straightenImage(image);
            //Imgcodecs.imwrite("recortados/" + imagenL.get(i).getName()+ "straightImage.jpg", straightImage);
            Mat rec = m.recor(straightImage);
            Mat tam = m.tamanio(rec);
            Imgcodecs.imwrite("recortados/" + imagenL.get(i).getName(), tam);
            m.califi("recortados/" + imagenL.get(i).getName());
        }

    }

    //This is the pre-processing part where we create a binary image from our original
    //And after the morphology we can detect the test parts more easily
    private Mat preProcessForAngleDetection(Mat image) {
        Mat binary = new Mat();
        //Create binary image
        Imgproc.threshold(image, binary, 127, 255, Imgproc.THRESH_BINARY_INV);
        //"Connect" the letters and words
        //Convert the image to gray from RGB
        Imgproc.cvtColor(binary, binary, Imgproc.COLOR_BGR2GRAY);
        
        Imgproc.GaussianBlur(binary, binary, new Size(13, 7), 8);

        //Imgcodecs.imwrite("enderezado/processedImage.jpg", binary);
        return binary;
    }

    //Detectamos el angulo de rotación
    //Despues de esta funcion regresamos nuestro angulo necesario
    private double detectRotationAngle(Mat binaryImage) {
        //Store line detections here
        Mat lines = new Mat();
        //Detect lines
        Imgproc.HoughLinesP(binaryImage, lines, 1, 2 * Math.PI / 180, 20);
        //Imgproc.HoughLines(lines, lines, 0, 0, 0);
        //Imgcodecs.imwrite("recortados/j.jpg", lines);
        double angle = 0;

        //This is only for debugging and to visualise the process of the straightening
        Mat debugImage = binaryImage.clone();
        Imgproc.cvtColor(debugImage, debugImage, Imgproc.COLOR_GRAY2BGR);

        //Calculate the start and end point and the angle
        for (int x = 0; x < lines.cols(); x++) {
            double[] vec = lines.get(0, x);
            //System.out.println(lines.get(0, x) + "");
            double x1 = vec[0];
            //System.out.println("x1:" + x1);
            double y1 = vec[1];
            //System.out.println("y1:" + y1);
            double x2 = vec[2];
            //System.out.println("x2:" + x2);
            double y2 = vec[3];
            //System.out.println("y2:" + y2);

            Point start = new Point(x1, y1);
            //System.out.println("start:" + start);
            Point end = new Point(x2, y2);
            //System.out.println("end:" + end);

            //Draw line on the "debug" image for visualization
            Imgproc.line(debugImage, start, end, new Scalar(255, 255, 0), 5);
            //Imgcodecs.imwrite("recortados/" + x + "D.jpg", debugImage);
            //Calculate the angle we need
            angle = calculateAngleFromPoints(start, end);
            //System.out.println("angle:" + angle);
        }

        //Imgcodecs.imwrite("recortados/" +"detectedLines.jpg", debugImage);
        return angle;
    }

    //From an end point and from a start point we can calculate the angle
    private double calculateAngleFromPoints(Point start, Point end) {
        double deltaX = end.x - start.x;
        //System.out.println("deltaX:" + deltaX);
        double deltaY = end.y - start.y;
        //System.out.println("deltaY:" + deltaY);
        double g = Math.atan2(deltaY, deltaX) * (180 / Math.PI);
        if (g < 0) {
            g += 360;
        }
        if (g == 0.0 || g == 180.0 || g == 90.0 || g == 270.0) {
            g = 0;
        } else {
            if (g > 45 && g < 90) {
                g += 270;
            } else {
                if (g > 225 && g < 270) {
                    g += 90;
                }
            }

        }
        return g;
    }

    //Rotation is done here
    private Mat rotateImage(Mat image, double angle) {
        //Calculate image center
        Point imgCenter = new Point(image.cols() / 2, image.rows() / 2);
        int c = image.cols();
        int r = image.rows();
        //System.out.println(c + "");
        //System.out.println(r + "");
        //Get the rotation matrix
        Mat rotMtx = Imgproc.getRotationMatrix2D(imgCenter, angle, 1.0);
        //Calculate the bounding box for the new image after the rotation (without this it would be cropped)
        Rect bbox = new RotatedRect(imgCenter, image.size(), angle).boundingRect();

        //Rotate the image
        Mat rotatedImage = image.clone();
        Imgproc.warpAffine(image, rotatedImage, rotMtx, bbox.size());

        return rotatedImage;
    }

    //Sums the whole process and returns with the straight image
    private Mat straightenImage(Mat image) {
        Mat rotatedImage = image.clone();
        Mat processed = preProcessForAngleDetection(image);
        double rotationAngle = detectRotationAngle(processed);

        return rotateImage(rotatedImage, rotationAngle);
    }

    private Mat preProcess(Mat image) {
        Mat binary = new Mat();

        //Create binary image
        Imgproc.threshold(image, binary, 127, 255, Imgproc.THRESH_BINARY_INV);
        //int erosion_size = 5;
        //Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(2*erosion_size + 1, 2*erosion_size+1));
        //Imgproc.erode(image, binary, element);
        Imgproc.cvtColor(binary, binary, Imgproc.COLOR_BGR2GRAY);
        //"Connect" the letters and words
        Imgproc.GaussianBlur(binary, binary, new Size(13, 7), 8);
        //Imgcodecs.imwrite("recortados/hola.jpg", binary);

        return binary;
    }

    private Mat findLargestRectangle(Mat original_image, Mat cin) {
        Mat imgSource = original_image;

        //find the contours
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(imgSource, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        double maxArea = -1;
        int maxAreaIdx = -1;
        Rect boundRect;
        MatOfPoint temp_contour = contours.get(0); //the largest is at the index 0 for starting point
        MatOfPoint2f approxCurve = new MatOfPoint2f();
        Mat largest_contour = contours.get(0);
        List<MatOfPoint> largest_contours = new ArrayList<MatOfPoint>();
        for (int idx = 0; idx < contours.size(); idx++) {
            temp_contour = contours.get(idx);
            double contourarea = Imgproc.contourArea(temp_contour);
            //compare this contour to the previous largest contour found
            if (contourarea > maxArea) {
                //check if this contour is a square
                MatOfPoint2f new_mat = new MatOfPoint2f(temp_contour.toArray());
                int contourSize = (int) temp_contour.total();
                Imgproc.approxPolyDP(new_mat, approxCurve, contourSize * 0.05, true);
                if (approxCurve.total() == 4) {
                    maxArea = contourarea;
                    maxAreaIdx = idx;
                    largest_contours.add(temp_contour);
                    largest_contour = temp_contour;
                }
            }
        }
        MatOfPoint temp_largest = null;
        if (largest_contours.size() > 1) {
            temp_largest = largest_contours.get(largest_contours.size() - 1);
        } else {
            temp_largest = temp_contour;
        }
        largest_contours = new ArrayList<MatOfPoint>();
        largest_contours.add(temp_largest);

        boundRect = Imgproc.boundingRect(temp_largest);

        Mat image_roi = new Mat(cin, boundRect);

        // convert grayscale to color image
        //Imgproc.cvtColor(image_roi, image_roi, Imgproc.COLOR_GRAY2BGR);
        //Inverse the matrix
        //Imgproc.threshold(image_roi, image_roi, 100, 255, Imgproc.THRESH_BINARY_INV);
        //output the cropped image
        //Imgcodecs.imwrite("enderezado/croppedImage.jpg",image_roi);
        Imgproc.cvtColor(imgSource, imgSource, Imgproc.COLOR_BayerBG2RGB);
        Imgproc.drawContours(imgSource, largest_contours, -1, new Scalar(0, 255, 0), 3);
        //Imgcodecs.imwrite("recortados/cropped.jpg", imgSource);
        return image_roi;
    }

    private Mat recor(Mat image) {
        Mat processed = preProcess(image);
        Mat recorte = findLargestRectangle(processed, image);

        return recorte;
    }

    private Mat tamanio(Mat image) {
        Mat tam = image;
        double ancho = tam.cols(), alto = tam.rows();
        double proporcion = alto / ancho;
        Imgproc.resize(tam, tam, new Size(1080, 1080 * proporcion));
        return tam;
    }

    public void califi(String pad) throws IOException, SQLException {
        AbrirArchivo();
        int nPre = datExa.size() + 3;
        if (datExa.size() < 14) {
            colTabla = 1;
        } else if (datExa.size() < 34) {
            colTabla = 2;
        } else if (datExa.size() < 54) {
            colTabla = 3;
        }
        File imageFile = new File(pad);
        ITesseract instance = new Tesseract1();
        //Boleta
        Rectangle rctngl2 = new Rectangle(15, 78, 346, 377);

        //Preguntas 1-12
        Rectangle rec = new Rectangle(13, 464, 345, 639);

        //Preguntas 13-32
        Rectangle rec1 = new Rectangle(362, 19, 350, 1089);
        //Preguntas 33-52
        Rectangle rec2 = new Rectangle(708, 19, 350, 1087);

        //instance.setDatapath("C:\\Users\\joshu\\Documents\\NetBeansProjects\\11\\");
        try {
            String reslut = instance.doOCR(imageFile, rctngl2);
            String boleta = "";
            char num[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
            String bol[] = new String[11];
            char enc = ' ';
            int que = 0;
            for (int i = 0; i < reslut.length(); i++) {
                enc = reslut.charAt(i);
                bol[que] += reslut.charAt(i);
                if (enc == '\n') {
                    int marcados = 0;
                    for (int j = 0; j < 10; j++) {
                        if (bol[que].indexOf(num[j]) == -1) {
                            marcados++;
                            if (marcados == 1) {
                                char cual = num[j];
                                boleta += cual;
                            } else {
                            }
                        }
                    }
                    if (que < 10) {
                        que++;
                    }
                }
            }
            if (boleta.length() == 11) {
                String bo = "";
                for (int i = 0; i < boleta.length() - 1; i++) {
                    if (i == 1) {
                        bo += "0";
                    } else {
                        bo += boleta.charAt(i);
                    }
                }
                boleta=bo;
                System.out.println("Boleta: " + bo);//
            } else {
                System.out.println("Examen cancelado ");
            }
            String reslut2 = "";
            if (colTabla == 3) {
                reslut2 += instance.doOCR(imageFile, rec);
                reslut2 += instance.doOCR(imageFile, rec1);
                reslut2 += instance.doOCR(imageFile, rec2);
            } else if (colTabla == 2) {
                reslut2 += instance.doOCR(imageFile, rec);
                reslut2 += instance.doOCR(imageFile, rec1);
            } else if (colTabla == 1) {
                reslut2 += instance.doOCR(imageFile, rec);
            }
            char letras[] = {'A', 'B', 'C', 'D'};
            String bol2[] = new String[nPre];
            char enc2 = ' ', enc3 = ' ';
            int que2 = 0;

            for (int i = 0; i < reslut2.length(); i++) {
                enc2 = reslut2.charAt(i);
                bol2[que2] += reslut2.charAt(i);
                if (i != reslut2.length() - 1) {
                    enc3 = reslut2.charAt(i + 1);
                }
                if (enc2 == '\n' && enc3 == '\n') {

                    bol2[que2].replace("\n" + "\n", "\n");
                } else if (enc2 == '\n') {

                    int marcados = 0;
                    for (int j = 0; j < 4; j++) {
                        if (bol2[que2].indexOf(letras[j]) == -1) {
                            marcados++;
                            if (marcados == 1) {
                                char cual = letras[j];
                                if(datExa.get(que2+1).getRespuesta().equalsIgnoreCase(cual+"")){
                                    Aciertos++;
                                }
                            } else {
                            }
                        }
                    }
                    if (que2 < nPre) {
                        que2++;
                    }
                }

            }
            String idEx, cali, Aci;
            float calf;
            idEx = datExa.get(0).getIdExa();
            System.out.println(datExa.size());
            calf = (Aciertos * 10)/(datExa.size()-1);
            System.out.println(calf + "");
            System.out.println(idEx + "");
            System.out.println("Aciertos: " + Aciertos);
            cali = calf + "";
            Aci = Aciertos + "";
            examen_alumno exAl = new examen_alumno();
            System.out.println(exAl.guardaCalif(idEx, boleta, Aci, cali));
        } catch (TesseractException e) {
            System.out.println(e);
        }
    }
}
