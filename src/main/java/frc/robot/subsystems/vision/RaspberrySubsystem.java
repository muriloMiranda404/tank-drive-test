package frc.robot.subsystems.vision;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.FRC9485.utils.logger.CustomBooleanLog;
import frc.FRC9485.utils.logger.CustomDoubleLog;
import frc.FRC9485.utils.logger.CustomStringLog;
import frc.robot.subsystems.vision.IO.RaspberrySubsystemIO;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class RaspberrySubsystem extends SubsystemBase implements RaspberrySubsystemIO{
    private static RaspberrySubsystem m_instance;
    
    // NetworkTables config
    private NetworkTable locationsTable;
    private NetworkTable configTable;
    private NetworkTable raspberryTable;
    private NetworkTable detectionsTable;

    // Logging
    private CustomDoubleLog txLogger;
    private CustomDoubleLog tyLogger;
    private CustomBooleanLog tvLogger;
    private CustomStringLog clsNameLogger;
    private CustomDoubleLog minConfLogger;

    private RaspberrySubsystem(String raspberryTablePath) {
        txLogger = new CustomDoubleLog("Raspberry1/TX");
        tyLogger = new CustomDoubleLog("Raspberry1/TY");
        tvLogger = new CustomBooleanLog("Raspberry1/TV");
        clsNameLogger = new CustomStringLog("Raspberry1/Cls Name");
        minConfLogger = new CustomDoubleLog("Raspberry1/Min Conf");

        this.raspberryTable = NetworkTableInstance.getDefault().getTable(raspberryTablePath);
        this.detectionsTable = raspberryTable.getSubTable("detections");
        this.locationsTable = detectionsTable.getSubTable("locations");
        this.configTable = detectionsTable.getSubTable("config");
    }

    public static RaspberrySubsystem getInstance(String raspberryTablePath) {
        if (m_instance == null) {
            m_instance = new RaspberrySubsystem(raspberryTablePath);
        }
        return m_instance;
    }

    public static RaspberrySubsystem getInstance() {
        return m_instance;
    }

    @Override
    public String getClsName() {
        return this.locationsTable.getEntry("cls_name").getString("");
    }

    @Override
    public boolean getTV() {
        return this.locationsTable.getEntry("tv").getBoolean(false);
    }

    @Override
    public double getTX() {
        return this.locationsTable.getEntry("tx").getDouble(0) - 8;
    }

    @Override
    public double getTY() {
        return this.locationsTable.getEntry("ty").getDouble(0);
    }

    @Override
    public double getMinConf() {
        return configTable.getEntry("min_conf").getDouble(0);
    }

    @Override
    public void setMinConf(double val) {
        configTable.getEntry("min_conf").setDouble(val);
    }

    @Override
    public void periodic() {
        txLogger.append(getTX());
        tyLogger.append(getTY());
        tvLogger.append(getTV());

        clsNameLogger.append(getClsName());
        minConfLogger.append(getMinConf());
    }
}
