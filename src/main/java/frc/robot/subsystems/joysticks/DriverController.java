package frc.robot.subsystems.joysticks;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.Controllers;
import frc.robot.subsystems.joysticks.IO.DriverControllerIO;

public class DriverController implements DriverControllerIO{
    private static DriverController m_instance;
    private CommandXboxController controller;
    
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
}
