package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

// Detaylı bilgi için DrivetrainSubsytem'i okuyarak başlayın. Burası ile orası aynı mantık.

public class ShooterSubsystem extends SubsystemBase {

  private CANSparkMax motor;
  
  public ShooterSubsystem() {
    motor = new CANSparkMax(ShooterConstants.motorID, ShooterConstants.motorType);    
  }

  @Override
  public void periodic() {}

  public void setSpeed(double speed) {
    
    if (speed > 1) {
      speed = 1;
    }
    else if (speed < -1) {
      speed = -1;
    }

    motor.set(speed);
  }

}
