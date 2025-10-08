package frc.robot.subsystems.joysticks;

import edu.wpi.first.wpilibj2.command.button.Trigger;

public interface SubsystemControllerIO {
    Trigger getIntakeButton();
    Trigger getShooterButton();
    Trigger getConveyorButton();
    Trigger getIntakeAndConveyorButton();

    double getShooterUpSpeed();
    double getShooterDownSpeed();
    double getCremalheiraSpeed();
}
