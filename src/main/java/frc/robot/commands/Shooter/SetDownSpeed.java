package frc.robot.commands.Shooter;

import frc.robot.RobotContainer;

import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.utils.SubsystemController;

public class SetDownSpeed extends Command {
    private ShooterSubsystem shooterSubsystem;
    private SubsystemController controller = new SubsystemController();

    public SetDownSpeed(RobotContainer container) {
        shooterSubsystem = container.getShooterSubsystemInstance();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double down = controller.getShooterDownSpeed();

        shooterSubsystem.setDownSpeed(down);
    }

    @Override
    public boolean isFinished() {
        double down = controller.getShooterDownSpeed();
        if (down == 0) {
            return true;
        }

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.stopDownMotor();
    }
}
