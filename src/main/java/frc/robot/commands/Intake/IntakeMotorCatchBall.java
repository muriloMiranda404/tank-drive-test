package frc.robot.commands.Intake;

import frc.robot.RobotContainer;
import frc.robot.Constants.Conveyor;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.utils.SubsystemController;

public class IntakeMotorCatchBall extends Command{
    private IntakeSubsystem subsystem;
    private SubsystemController controller = new SubsystemController();
    private ConveyorSubsystem conveyorSubsystem;

    public IntakeMotorCatchBall(RobotContainer container) {
        this.subsystem = container.getIntakeSubsystemInstance();
        this.conveyorSubsystem = container.getConveyorSubsystemInstance();
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        subsystem.setSpeed(-0.7);
    }

    @Override
    public boolean isFinished() {
        return false || conveyorSubsystem.ballInConveyor();
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stopMotor();
    }
}
