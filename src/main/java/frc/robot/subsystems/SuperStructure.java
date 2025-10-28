package frc.robot.subsystems;

import frc.robot.subsystems.IO.SuperStructureIO;
import frc.robot.commands.Conveyor.EnableConveyorMotor;
import frc.robot.commands.Intake.EnableIntakeMotor;
import frc.robot.commands.Intake.OpenIntake;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SuperStructure extends SubsystemBase implements SuperStructureIO {
    private static SuperStructure m_instance;

    private SuperStructure() {}

    public static SuperStructure getInstance() {
        if (m_instance == null) {
            m_instance = new SuperStructure(); 
        }

        return m_instance;
    }

    @Override
    public SequentialCommandGroup catchBall() {
        return new SequentialCommandGroup(
            new OpenIntake(),
            new ParallelCommandGroup(
                new EnableIntakeMotor(),
                new EnableConveyorMotor(true)
            )
        );
    }

    // @Override
    // public SequentialCommandGroup stopCatchingBall() {
    //     System.out.println("nao pegando");
    //     return new SequentialCommandGroup(
    //         new CloseIntake(),
    //         new ParallelCommandGroup(
    //             new StopIntakeMotor(),
    //             new StopConveyorMotor()
    //         )
    //     );
    // }

} 
