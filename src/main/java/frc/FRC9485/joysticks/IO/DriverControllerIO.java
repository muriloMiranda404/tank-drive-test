package frc.FRC9485.joysticks.IO;

import edu.wpi.first.wpilibj2.command.button.Trigger;

public interface DriverControllerIO {
    double getRotationAxis();
    double getTranslationAxis();
    
    boolean getSlowGear();
    boolean getFastGear();

    Trigger getTeste();
}
