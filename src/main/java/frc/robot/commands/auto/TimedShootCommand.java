package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.ShooterSubsystem;

// Detaylı bilgi için MoveForwardCommand'i okuyun.

public class TimedShootCommand extends Command {

  ShooterSubsystem shooterSubsystem;
  
  double executionTime, startTime, currentTime, elapsedTime;

  public TimedShootCommand(ShooterSubsystem shooterSubsystem, double shotDuration) {
    this.shooterSubsystem = shooterSubsystem;
    this.executionTime = shotDuration;

    this.addRequirements(shooterSubsystem);
  }

  @Override
  public void initialize() {
    this.startTime = Timer.getFPGATimestamp();

    this.shooterSubsystem.setSpeed(0);
  }

  @Override
  public void execute() {
    this.shooterSubsystem.setSpeed(ShooterConstants.shotSpeed);

    this.currentTime = Timer.getFPGATimestamp();

    this.elapsedTime = this.currentTime - this.startTime;
  }

  @Override
  public void end(boolean interrupted) {
    this.shooterSubsystem.setSpeed(0);
  }

  @Override
  public boolean isFinished() {
    if (this.elapsedTime > this.executionTime ){
      return true;
    }
    else {
      return false;
    }
  }
}
