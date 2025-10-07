package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Cremalheira;
import frc.robot.subsystems.utils.SubsystemController;

public class CremalheiraSubsystem extends SubsystemBase {
    private SparkMax cremalheira;

    public CremalheiraSubsystem() {
        this.cremalheira = new SparkMax(Cremalheira.CREMALHEIRA_MOTOR_ID, MotorType.kBrushless);
    }

    public RelativeEncoder getEncoder() {
        return this.cremalheira.getEncoder();
    }
    
    public double getSpeed() {
        return this.getEncoder().getVelocity();
    }

    public double getPosition() {
        return this.getEncoder().getPosition();
    }

    public void setSpeed(double speed) {
        this.cremalheira.set(speed);
    }

    public void resetEncoder() {
        this.getEncoder().setPosition(0);
    }

    public Command setAngulation(SubsystemController controller) {
        return run(() -> {
            double position = this.getPosition();
            double speed = controller.getCremalheiraSpeed();

            if (position >= Cremalheira.MAXIMO_CIMA && speed > 0.0) {
                speed = 0;
            } else if (position <= Cremalheira.MAXIMO_BAIXO && speed < 0.0) {
                speed = 0;
            }
            this.cremalheira.set(speed);
        });
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Posicao do encoder", this.getPosition());
    }
}
