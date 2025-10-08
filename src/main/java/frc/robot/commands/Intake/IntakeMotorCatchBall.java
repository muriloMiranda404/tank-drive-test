package frc.robot.commands.Intake;

import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.joysticks.SubsystemController;
import frc.robot.subsystems.ConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;


public class IntakeMotorCatchBall extends Command{
    private SubsystemController controller;
    private IntakeSubsystem intakeSubsystem;
    private ConveyorSubsystem conveyorSubsystem;

    public IntakeMotorCatchBall() {
        this.controller = SubsystemController.getInstance();
        this.intakeSubsystem = IntakeSubsystem.getInstance();
        this.conveyorSubsystem = ConveyorSubsystem.getInstance();
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        this.intakeSubsystem.setSpeed(-0.7);
    }

    @Override
    public boolean isFinished() {
        return 
        !controller.getIntakeButton().getAsBoolean() && 
        !controller.getIntakeAndConveyorButton().getAsBoolean() ||
        conveyorSubsystem.ballInConveyor();
    }

    @Override
    public void end(boolean interrupted) {
        this.intakeSubsystem.stopMotor();
    }
}
