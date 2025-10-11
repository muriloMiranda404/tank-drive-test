package frc.FRC9485.joysticks.IO;

public interface DriverControllerIO {
    double getRotationAxis();
    double getTranslationAxis();
    
    boolean getSlowGear();
    boolean getFastGear();
}
