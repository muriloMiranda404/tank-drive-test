package frc.FRC9485.joysticks;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.Controllers;
import frc.FRC9485.joysticks.IO.DriverControllerIO;

public class DriverController implements DriverControllerIO{
    private static DriverController m_instance;
    private CommandXboxController controller;

    private short Y = 4;
    
    private DriverController() {
        this.controller = new CommandXboxController(Controllers.DRIVER_CONTROLLER);
    }

    public static DriverController getInstance() {
        if (m_instance == null) {
            m_instance = new DriverController();
        }
        return m_instance;
    }

    @Override
    public double getTranslationAxis() {
        return this.controller.getLeftY();
    }

    @Override
    public double getRotationAxis() {
        return this.controller.getRightX();
    }

    @Override
    public boolean getSlowGear() {
        return this.controller.leftBumper().getAsBoolean();
    }

    @Override
    public boolean getFastGear() {
        return this.controller.rightBumper().getAsBoolean();
    }

    @Override
    public Trigger getCatchBallAndAdjustTranslationButton() {
        return controller.button(Y);
    }
}
