package frc.FRC9485.utils.logger;

import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CustomDoubleLog extends DoubleLogEntry{
    
    private String name;
    private static boolean isFms;
    private double loggedValue;

    public CustomDoubleLog(String name){
        super(DataLogManager.getLog(), name);
        this.name = name;
        this.loggedValue = 0;
        this.append(0);
        CustomDoubleLog.isFms = DriverStation.getMatchNumber() > 0;
    }

    @Override
    public void append(double value) {
        if(DriverStation.isEnabled() && value != loggedValue){
            this.loggedValue = value;
            super.append(value);
        }
        if(!isFms){
            SmartDashboard.putNumber(name, value);
        }
    }
}
