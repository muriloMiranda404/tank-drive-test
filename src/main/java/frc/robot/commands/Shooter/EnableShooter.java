package frc.robot.commands.Shooter;

import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.joysticks.SubsystemController;
import edu.wpi.first.wpilibj2.command.Command;

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
        this.shooterSubsystem.setSpeed(-0.8, 0.8);
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
