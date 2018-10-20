package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.DeposerCube;
import frc.robot.commands.PrendreCube;
import frc.robot.commands.ViserDeposer;

public class OI {

    private Joystick stick;

    private JoystickButton button2;
    private JoystickButton button3;
    private JoystickButton button4;
    
    public OI() {

        stick = new Joystick(0);
        
        button2 = new JoystickButton(stick, 2);
        button2.toggleWhenPressed(new ViserDeposer());
        
        button3 = new JoystickButton(stick, 3);
        button3.toggleWhenPressed(new PrendreCube());

        button4 = new JoystickButton(stick, 4);
        button4.whenPressed(new DeposerCube());

    }

    
    public Joystick getJoystick() {
        return stick;
    }

}