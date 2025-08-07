package frc.robot.command;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TankSubsystem;

public class DriveCommand extends Command{
    
    TankSubsystem tankSubsystem = new TankSubsystem();
    DoubleSupplier first;
    DoubleSupplier second;

    public DriveCommand(TankSubsystem tankSubsystem, DoubleSupplier first, DoubleSupplier second){
        this.first = first;
        this.second = second;
        this.tankSubsystem = tankSubsystem;
        addRequirements(tankSubsystem);
        System.out.println("Comando criado");
    }

    @Override
    public void initialize(){
        System.out.println("Comando iniciando");
    }

    @Override
    public void execute(){
        System.out.println("ta executando o cmd");
        tankSubsystem.driveTank(first.getAsDouble(), second.getAsDouble());
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return  false;
    }
}
