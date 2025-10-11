package frc.robot.commands.Shooter;

import frc.FRC9485.joysticks.SubsystemController;
import frc.robot.Constants.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.superStructure.ShooterSubsystem;

public class EnableShooter extends Command {
    private ShooterSubsystem shooterSubsystem;
    private SubsystemController controller = SubsystemController.getInstance();

    public EnableShooter() {
        this.shooterSubsystem = ShooterSubsystem.getInstance();
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        this.shooterSubsystem.setSpeed(-Shooter.MAX_SPEED, Shooter.MAX_SPEED);
    }

    @Override
    public boolean isFinished() {
        return !controller.getShooterButton().getAsBoolean();
    }

    @Override
    public void end(boolean interrupted) {
        this.shooterSubsystem.stopMotors();
    }
}
