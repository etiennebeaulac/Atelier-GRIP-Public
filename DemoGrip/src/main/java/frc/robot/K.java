package frc.robot;

public class K {

    public static class Ports {

        public final static int BASE_PILOTABLE_MOTEUR_GAUCHE    = 0;
        public final static int BASE_PILOTABLE_MOTEUR_DROIT     = 1;
        //                                                      = 2;
        //                                                      = 3;
        public static final int INTAKE_MOTEUR_GAUCHE            = 4;
        public static final int INTAKE_MOTEUR_DROIT             = 5;

    }

    public static class Camera {

        public static double OFFSET = 0.0;

    }

    public static class Viser {

        public static double X_THRESHOLD = 0.05;

        public static double LARGEUR_TARGET = 0.155;
        public static double LARGEUR_THRESHOLD = 0.016;

        public static double TURN_SPEED = 0.55;
        public static double FORWARD_SPEED = 0.6;

    }
    
}