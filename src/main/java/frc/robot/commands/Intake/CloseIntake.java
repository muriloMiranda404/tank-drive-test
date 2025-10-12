package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.FRC9485.joysticks.SubsystemController;
import frc.robot.Constants.Pneumatics;
import frc.robot.subsystems.mechanisms.PneumaticSubsystem;

public class CloseIntake extends Command {
    PneumaticSubsystem pneumaticSubsystem;
    SubsystemController subsystemController;
    
    public CloseIntake() {
        this.subsystemController = SubsystemController.getInstance();

        this.pneumaticSubsystem = PneumaticSubsystem.getInstance();
    }

    @Override
    public void initialize() {}
    
    @Override
    public void execute() {
        if (pneumaticSubsystem.isFwdChannelDisabled()) {
            pneumaticSubsystem.toggleSolenoid();
        }
    }

    @Override
    public boolean isFinished() {
        return 
        !pneumaticSubsystem.isFwdChannelDisabled() || 
        !subsystemController.getCatchBallButton().getAsBoolean();
    }
}
