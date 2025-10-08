package frc.robot.subsystems.joysticks;

import frc.robot.Constants.Controles;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class SubsystemController implements SubsystemControllerIO{
    private static SubsystemController m_instance;

    private short A = 1;
    private short X = 3;
    private short leftBumper = 5;
    private short rightBumper = 6;

    private CommandXboxController controller;

    private SubsystemController() {
        controller = new CommandXboxController(Controles.SUBSYSTEMS_CONTROLLER);
    }

    public static SubsystemController getInstance() {
        if (m_instance == null) {
            m_instance = new SubsystemController();
        }
        return m_instance;
    }

    @Override
    public Trigger getIntakeButton() {
        return controller.button(A);
    }

    @Override
    public Trigger getConveyorButton() {
        return controller.button(X);
    }

    @Override
    public Trigger getShooterButton() {
        return controller.button(leftBumper);
    }
 
    @Override
    public Trigger getIntakeAndConveyorButton() {
        return controller.button(rightBumper);
    }
    
    @Override
    public double getCremalheiraSpeed() {
        return controller.getRightY() * -0.1;
    }

    @Override
    public double getShooterDownSpeed() {
        double speed = controller.getLeftTriggerAxis();
        if (speed >= 0.4) {
            speed = 0.4;
        }
        return speed;
    }

    @Override
    public double getShooterUpSpeed() {
        double speed = controller.getRightTriggerAxis();
        if (speed >= 0.4) {
            speed = 0.4;
        }
        return -speed;
    }
}
