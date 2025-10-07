package frc.robot.subsystems.utils;

import frc.robot.Constants.Controles;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class SubsystemController {
    private short A = 1;
    private short B = 2;
    private short X = 3;
    private short Y = 4;
    private short leftBumper = 5;
    private short rightBumper = 6;

    private CommandXboxController controller;

    public SubsystemController() {
        controller = new CommandXboxController(Controles.SUBSYSTEMS_CONTROLLER);
    }

    public GenericHID getHID() {
        return controller.getHID();
    }

    public int getAButtonID() {
        return A;
    }

    public int getBButtonID() {
        return B;
    }

    public int getXButtonID() {
        return X;
    }

    public int getYButtonID() {
        return Y;
    }

    public int getRightBumperID() {
        return rightBumper;
    }

    public int getLeftBumperID() {
        return leftBumper;
    }

    public boolean getRightBumperButton() {
        return controller.rightBumper().getAsBoolean();
    }

    public boolean getLeftBumperButton() {
        return controller.leftBumper().getAsBoolean();
    }

    public boolean getAButton() {
        return controller.a().getAsBoolean();
    }

    public boolean getBButton() {
        return controller.b().getAsBoolean();
    }

    public boolean getXButton() {
        return controller.x().getAsBoolean();
    }

    public boolean getYButton() {
        return controller.y().getAsBoolean();
    }

    public boolean getRightTriggerAxis() {
        return controller.rightTrigger().getAsBoolean();
    }   

    public double getShooterDownSpeed() {
        double speed = controller.getLeftTriggerAxis();
        if (speed >= 0.4) {
            speed = 0.4;
        }
        return speed;
    }

    public double getShooterUpSpeed() {
        double speed = controller.getRightTriggerAxis();
        if (speed >= 0.4) {
            speed = 0.4;
        }
        return -speed;
    }

    public double getCremalheiraSpeed() {
        return controller.getRightY() * -0.1;
    }
}
