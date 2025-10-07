package frc.robot.subsystems.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RaspberrySubsystem extends SubsystemBase {
    NetworkTable visionTable;
    NetworkTable configTable;
    NetworkTable raspberryTable;
    NetworkTable detectionsTable;

    public RaspberrySubsystem(String raspberryTablePath) {
        this.raspberryTable = NetworkTableInstance.getDefault().getTable(raspberryTablePath);
        
        this.visionTable = raspberryTable.getSubTable("vision");
        this.configTable = raspberryTable.getSubTable("config");
        this.detectionsTable = raspberryTable.getSubTable("detections");
    }

    // VISION
    public String getClassName() {
        return visionTable.getEntry("cls_name").getString("");
    }

    public boolean getTargetValid() {
        return visionTable.getEntry("tv").getBoolean(false);
    }

    public double getTargetX() {
        return visionTable.getEntry("tx").getDouble(0);
    }

    public double getTargetY() {
        return visionTable.getEntry("ty").getDouble(0);
    }

    // CONFIG
    public double getMinConf() {
        return configTable.getEntry("min_conf").getDouble(0);
    }

    public void setMinConf(double value) {
        configTable.getEntry("min_conf").setDouble(value);
    }

    @Override
    public void periodic() {
        
    }
}
