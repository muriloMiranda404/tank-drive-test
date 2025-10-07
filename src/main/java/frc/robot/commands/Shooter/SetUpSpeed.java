package frc.robot.commands.Shooter;

import frc.robot.RobotContainer;

import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.utils.SubsystemController;

public class SetUpSpeed extends Command {
    private ShooterSubsystem shooterSubsystem;
    private SubsystemController controller = new SubsystemController();

    public SetUpSpeed(RobotContainer container) {
        this.shooterSubsystem = container.getShooterSubsystemInstance();
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        double up = controller.getShooterUpSpeed();
       

        //shooterSubsystem.setUpSpeed(up);
        //shooterSubsystem.setDownSpeed(up);
        shooterSubsystem.setSpeed(-up,up);
    }

    @Override
    public boolean isFinished() {
        double up = controller.getShooterUpSpeed();
        if (up == 0) {
            return true;
        }

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        //shooterSubsystem.stopUpMotor();
        shooterSubsystem.stopMotors();
    }
}
