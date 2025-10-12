package frc.robot.commands.Conveyor;

import frc.FRC9485.joysticks.SubsystemController;
import frc.robot.Constants.Conveyor;
import frc.robot.subsystems.mechanisms.ConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

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
        !controller.getCatchBallButton().getAsBoolean()|| 
        (stop == true && conveyorSubsystem.getHasBall());
    }

    @Override
    public void end(boolean interrupted) {
        conveyorSubsystem.stopMotor();
    }
}
