package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.K;
import frc.robot.commands.Piloter;

public class BasePilotable extends Subsystem {

    private VictorSP moteurGauche;
    private VictorSP moteurDroit;

    private DifferentialDrive drive;

    public BasePilotable() {

        moteurGauche = new VictorSP(K.Ports.BASE_PILOTABLE_MOTEUR_GAUCHE);
        moteurDroit = new VictorSP(K.Ports.BASE_PILOTABLE_MOTEUR_DROIT);

        drive = new DifferentialDrive(moteurGauche, moteurDroit);

    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new Piloter());
    }

    public void arcadeDrive(Joystick joystick) {
        drive.arcadeDrive(-1 * joystick.getY(), joystick.getX());
    }

    public void arcadeDrive(double forward, double rotation) {
        drive.arcadeDrive(forward, rotation);
    }

    public void stop() {
        moteurGauche.set(0.0);
        moteurDroit.set(0.0);
    }

}