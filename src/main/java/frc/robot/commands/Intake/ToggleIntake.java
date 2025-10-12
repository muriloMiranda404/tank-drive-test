package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.FRC9485.joysticks.SubsystemController;
import frc.robot.Constants.Pneumatics;
import frc.robot.subsystems.mechanisms.PneumaticSubsystem;

public class ToggleIntake extends Command {
    PneumaticSubsystem pneumaticSubsystem;
    SubsystemController subsystemController;
    
    public ToggleIntake() {
        this.subsystemController = SubsystemController.getInstance();

        this.pneumaticSubsystem = PneumaticSubsystem.getInstance();
    }

    @Override
    public void initialize() {}
    
    @Override
    public void execute() {
        pneumaticSubsystem.toggleSolenoid();
    }

    @Override
    public boolean isFinished() {
        return 
        pneumaticSubsystem.isFwdChannelDisabled() || 
        pneumaticSubsystem.isRevChannelDisabled() ||
        subsystemController.getCatchBallButton().getAsBoolean();
    }
}
