/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.Intake;

public class Robot extends TimedRobot {

  public final static BasePilotable basePilotable = new BasePilotable();
  public final static Intake intake = new Intake();
  public final static Camera camera = new Camera();

  public static OI oi;
  public static GripPipeline grip;

  @Override
  public void robotInit() {
    oi = new OI();
    grip = new GripPipeline();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    Scheduler.getInstance().run();
  }
}
