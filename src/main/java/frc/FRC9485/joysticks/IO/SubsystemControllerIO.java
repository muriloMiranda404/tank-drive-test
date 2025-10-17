package frc.FRC9485.joysticks.IO;

import edu.wpi.first.wpilibj2.command.button.Trigger;

public interface SubsystemControllerIO {
    Trigger getIntakeButton();
    Trigger getShooterButton();
    Trigger getConveyorWithNoPauseButton();
    Trigger getConveyorWithPauseButton();
    Trigger getCatchBallButton();

    double getRackSpeed();
    double getShooterUpSpeed();
    double getShooterDownSpeed();
}
