package frc.robot.subsystems;

import frc.robot.commands.Intake.ToggleIntake;
import frc.robot.commands.Shooter.EnableShooter;
import frc.robot.commands.Conveyor.EnableConveyor;
import frc.robot.commands.Intake.EnableIntakeMotor;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SuperStructure extends SubsystemBase {
    private static SuperStructure m_instance;

    private SuperStructure() {}

    public static SuperStructure getInstance() {
        if (m_instance == null) {
            m_instance = new SuperStructure(); 
        }

        return m_instance;
    }

    public SequentialCommandGroup catchBall() {
        return new SequentialCommandGroup(
            new ToggleIntake(),
            new ParallelCommandGroup(
                new EnableIntakeMotor(),
                new EnableConveyor(true)
            )
        );
    }

    public Command scoreRobot() {
        return run(
            () -> {
                new EnableShooter();
            }
        );
    }

} 
