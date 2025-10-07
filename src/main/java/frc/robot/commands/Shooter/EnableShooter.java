package frc.robot.commands.Shooter;

import frc.robot.RobotContainer;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.utils.SubsystemController;

public class EnableShooter extends Command {
    private ShooterSubsystem subsystem;
    private SubsystemController controller = new SubsystemController();

    public EnableShooter(RobotContainer container) {
        this.subsystem = container.getShooterSubsystemInstance();
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        subsystem.setSpeed(-0.8, 0.8);
    }

    @Override
    public boolean isFinished() {
        if (!controller.getLeftBumperButton()) {
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stopMotors();
    }
}
