package frc.robot.commands.Intake;

import frc.FRC9485.joysticks.SubsystemController;
import frc.robot.Constants.Intake;
import frc.robot.subsystems.mechanisms.ConveyorSubsystem;
import frc.robot.subsystems.mechanisms.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.Command;


public class EnableIntakeMotor extends Command{
    private SubsystemController controller;
    private IntakeSubsystem intakeSubsystem;
    private ConveyorSubsystem conveyorSubsystem;

    public EnableIntakeMotor() {
        this.controller = SubsystemController.getInstance();
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
        return 
        !controller.getIntakeButton().getAsBoolean() && 
        !controller.getCatchBallButton().getAsBoolean() ||
        conveyorSubsystem.getHasBall();
    }

    @Override
    public void end(boolean interrupted) {
        this.intakeSubsystem.stopMotor();
    }
}
