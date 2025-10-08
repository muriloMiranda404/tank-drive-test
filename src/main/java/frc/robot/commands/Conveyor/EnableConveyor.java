package frc.robot.commands.Conveyor;

import frc.robot.Constants.Conveyor;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.joysticks.SubsystemController;

public class EnableConveyor extends Command {
    private boolean stop;
    private SubsystemController controller;
    private ConveyorSubsystem conveyorSubsystem;

    public EnableConveyor(boolean stop) {
        this.controller = SubsystemController.getInstance();
        this.stop = stop;
        this.conveyorSubsystem = ConveyorSubsystem.getInstance();
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        this.conveyorSubsystem.setSpeed(Conveyor.MAX_SPEED);
    }

    @Override
    public boolean isFinished() {
        return 
        !controller.getConveyorButton().getAsBoolean() &&
        !controller.getIntakeAndConveyorButton().getAsBoolean()|| 
        (stop == true && conveyorSubsystem.ballInConveyor());
    }

    @Override
    public void end(boolean interrupted) {
        conveyorSubsystem.stopMotor();
    }
}
