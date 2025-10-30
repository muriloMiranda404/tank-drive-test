package frc.robot.commands.Intake;

import frc.robot.Constants.Intake;
import frc.robot.subsystems.mechanisms.ConveyorSubsystem;
import frc.robot.subsystems.mechanisms.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.Command;


public class EnableIntakeMotor extends Command{
    private IntakeSubsystem intakeSubsystem;
    private ConveyorSubsystem conveyorSubsystem;

    public EnableIntakeMotor() {
        this.intakeSubsystem = IntakeSubsystem.getInstance();
        this.conveyorSubsystem = ConveyorSubsystem.getInstance();
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        this.intakeSubsystem.setSpeed(Intake.MAX_SPEED);
    }

    @Override
    public boolean isFinished() {
        return intakeSubsystem.getSpeed() == 0 ||
        conveyorSubsystem.getHasBall();
    }

    @Override
    public void end(boolean interrupted) {
        this.intakeSubsystem.stopMotor();
    }
}
