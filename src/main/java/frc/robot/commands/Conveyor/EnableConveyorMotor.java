package frc.robot.commands.Conveyor;

import frc.robot.Constants.Conveyor;
import frc.robot.subsystems.mechanisms.ConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class EnableConveyorMotor extends Command {
    private boolean stop;
    private ConveyorSubsystem conveyorSubsystem;

    public EnableConveyorMotor(boolean stop) {
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
        conveyorSubsystem.getSpeed() == 0 ||
        (stop == true && conveyorSubsystem.getHasBall());
    }

    @Override
    public void end(boolean interrupted) {
        conveyorSubsystem.stopMotor();
    }
}
