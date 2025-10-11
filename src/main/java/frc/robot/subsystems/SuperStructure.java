package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.superStructure.RackSubsystem;

public class SuperStructure extends SubsystemBase {
    private static SuperStructure m_instance;

    private RackSubsystem rackSubsystem;

    private SuperStructure() {
        this.rackSubsystem = RackSubsystem.getInstance();
    }

    public static SuperStructure getInstance() {
        if (m_instance == null) {
            m_instance = new SuperStructure(); 
        }

        return m_instance;
    }

    
    
    public Command setRackAngulation(DoubleSupplier speed) {
        return rackSubsystem.setAngulation(speed);
    }

} 
