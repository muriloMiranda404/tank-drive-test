package frc.robot.commands.Conveyor;

import frc.FRC9485.joysticks.SubsystemController;
import frc.robot.Constants.Conveyor;
import frc.robot.subsystems.mechanisms.ConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class EnableConveyorMotor extends Command {
    private boolean stop;
    private SubsystemController controller;
    private ConveyorSubsystem conveyorSubsystem;

    public EnableConveyorMotor(boolean stop) {
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
        !controller.getConveyorWithPauseButton().getAsBoolean() &&
        !controller.getConveyorWithNoPauseButton().getAsBoolean() &&
        !controller.getCatchBallButton().getAsBoolean()|| 
        (stop == true && conveyorSubsystem.getHasBall());
    }

    @Override
    public void end(boolean interrupted) {
        conveyorSubsystem.stopMotor();
    }
}
