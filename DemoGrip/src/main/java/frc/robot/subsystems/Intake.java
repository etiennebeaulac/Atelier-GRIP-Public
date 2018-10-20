/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.K;

/**
 * Add your docs here.
 */
public class Intake extends Subsystem {
  
  private VictorSP moteurGauche;
  private VictorSP moteurDroit;

  public Intake() {
    moteurGauche = new VictorSP(K.Ports.INTAKE_MOTEUR_GAUCHE);
    moteurDroit = new VictorSP(K.Ports.INTAKE_MOTEUR_DROIT);
  }

  @Override
  public void initDefaultCommand() { }

  public void prendre() {
    moteurDroit.set(0.45);
    moteurGauche.set(-0.3);
  }

  public void deposer() {
    moteurDroit.set(-0.4);
    moteurGauche.set(0.4);
  }

  public void stop() {
    moteurDroit.set(0.0);
    moteurGauche.set(0.0);
  }

}
