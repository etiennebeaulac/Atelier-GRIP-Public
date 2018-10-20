/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionThread;
import frc.robot.GripPipeline;
import frc.robot.K;

/**
 * Sous-système Caméra
 */
public class Camera extends Subsystem implements VisionRunner.Listener<GripPipeline> {

  private UsbCamera camera;
  private CvSource gripOutput;

  private double centreX;
  private double largeur;

  public Camera() {
    
    // Démarre l'envoi automatique d'images d'une caméra USB
    camera = CameraServer.getInstance().startAutomaticCapture();

    // Création d'un flux pour envoyer des images provenant de GRIP
    gripOutput = CameraServer.getInstance().putVideo("GRIP", 320, 240);

    // Paramètres de la caméra
    camera.setBrightness(0);
    camera.setExposureManual(0);
    camera.setFPS(20);

    VisionThread thread = new VisionThread(camera, new GripPipeline(), this);
    thread.start();

  }

  @Override
  public void initDefaultCommand() {
  }

  public synchronized double getCentreX() {
    return centreX;
  }

  public synchronized double getLargeur() {
    return largeur;
  }

  @Override
  public synchronized void copyPipelineOutputs(GripPipeline pipeline) {

    final double LARGEUR_IMAGE = pipeline.resizeImageOutput().cols();
    ArrayList<MatOfPoint> contours = pipeline.filterContoursOutput();
    Mat image = pipeline.hsvThresholdOutput();
    
    // Si on détecte rien, on ne bouge pas.
    if (contours.isEmpty()) {

      centreX = 0.0;
      largeur = 0.0;

    }
    // Sinon, on choisit celui le plus proche du centre.
    else {

      centreX = Double.MAX_VALUE;
      Rect bestRect = null;

      // On boucle sur chaque contour
      for (MatOfPoint contour : contours) {

        // On crée un rectangle autour du contour détecté
        Rect r = Imgproc.boundingRect(contour);
        
        // r.x correspond au coin supérieur gauche
        // Cette valeur est comprise dans l'intervalle [0, LARGEUR_IMAGE]
        double x = r.x + r.width / 2.0;

        // On normalise cette valeur pour qu'elle soit comprise
        // dans l'intervalle [-1, 1]
        x = 2 * x / LARGEUR_IMAGE - 1;

        // La caméra est rarement parfaitement centrée sur le robot
        x += K.Camera.OFFSET;

        // S'il est plus près du centre que la valeur actuelle, on la sélectionne
        if (Math.abs(x) < Math.abs(centreX)) {

          centreX = x;

          // Normalise la largeur sur l'intervalle [0, 1]
          largeur = r.width / LARGEUR_IMAGE;

          bestRect = r;

        }

      }
      
      if(bestRect != null ) {

        // Dessine un rectangle bleu autour du contour choisi
        Imgproc.cvtColor(image, image, Imgproc.COLOR_GRAY2BGR);
        Imgproc.rectangle(image, bestRect.tl(), bestRect.br(), new Scalar(255, 0, 0));

      }

    }

    // Envoie l'image résultante pour qu'elle soit afficher
    gripOutput.putFrame(image);
    
    // Envoie les valeurs calculées sur le Dashboard
    SmartDashboard.putNumber("CentreX", centreX);
    SmartDashboard.putNumber("Largeur", largeur);

  }

}
