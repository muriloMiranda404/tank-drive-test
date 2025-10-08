package frc.robot.subsystems.joysticks.IO;

public interface DriverControllerIO {
    double getRotationAxis();
    double getTranslationAxis();
    
    boolean getSlowGear();
    boolean getFastGear();
}
