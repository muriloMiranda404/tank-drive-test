package frc.robot.commands.Conveyor;

import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.utils.SubsystemController;

public class EnableConveyor extends Command {
    private ConveyorSubsystem subsystem;
    private SubsystemController controller = new SubsystemController();
    private boolean stop;

    public EnableConveyor(RobotContainer container, boolean stop) {
        this.stop = stop;
        this.subsystem = container.getConveyorSubsystemInstance();
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        subsystem.setSpeed(-1.0);
    }

    @Override
    public boolean isFinished() {
        return false || (stop == true && subsystem.ballInConveyor());
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stopMotor();
    }
}
