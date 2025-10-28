package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.Conveyor;
import frc.robot.Constants.Intake;
import frc.robot.subsystems.SuperStructure;
import frc.robot.subsystems.mechanisms.ConveyorSubsystem;
import frc.robot.subsystems.mechanisms.IntakeSubsystem;
import frc.robot.subsystems.mechanisms.PneumaticSubsystem;
import frc.robot.subsystems.vision.RaspberrySubsystem;

public class OpenIntakeWithVision extends Command {
    SuperStructure superStructure;
    IntakeSubsystem intakeSubsystem;
    ConveyorSubsystem conveyorSubsystem;
    PneumaticSubsystem pneumaticSubsystem;
    RaspberrySubsystem raspberrySubsystem;

    public OpenIntakeWithVision() {
        this.superStructure = SuperStructure.getInstance();
        this.intakeSubsystem = IntakeSubsystem.getInstance();
        this.conveyorSubsystem = ConveyorSubsystem.getInstance();
        this.pneumaticSubsystem = PneumaticSubsystem.getInstance();
        this.raspberrySubsystem = RaspberrySubsystem.getInstance("raspberry-frc9485");
    }

    @Override
    public void execute() {
        pneumaticSubsystem.openSolenoid();
        conveyorSubsystem.setSpeed(Conveyor.MAX_SPEED);
        intakeSubsystem.setSpeed(Intake.MAX_SPEED);
    }

    @Override
    public boolean isFinished() {
        return !raspberrySubsystem.getTV();
    }

    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.stopMotor();
        conveyorSubsystem.stopMotor();
        pneumaticSubsystem.closeSolenoid();
    }
}
