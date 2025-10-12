package frc.robot.subsystems.IO;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public interface SuperStructureIO {
    public Command scoreRobot();
    public SequentialCommandGroup catchBall();
}
