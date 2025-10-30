package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;

import frc.FRC9485.joysticks.SubsystemController;
import frc.robot.subsystems.mechanisms.PneumaticSubsystem;

public class OpenIntake extends Command {
    PneumaticSubsystem pneumaticSubsystem;
    SubsystemController subsystemController;
    
    public OpenIntake() {
        this.subsystemController = SubsystemController.getInstance();
        this.pneumaticSubsystem = PneumaticSubsystem.getInstance();
    }

    @Override
    public void initialize() {}
    
    @Override
    public void execute() {
        pneumaticSubsystem.openSolenoid();
    }

    @Override
    public boolean isFinished() {
        return pneumaticSubsystem.isRevChannelEnabled();
    }
}
