package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.ShooterSubsystem;

// Bu komut bir trigger aktif olduğu sürece çalışacak.
// Bunu triggerın whileTrue() metodu ile yapıcağız.
// Geri kalan açıklamalar TeleopDriveCommand ile aynı.
public class TeleopShootCommand extends Command {
  
  private ShooterSubsystem shooterSubsystem;

  public TeleopShootCommand(ShooterSubsystem shooterSubsystem) {
    this.shooterSubsystem = shooterSubsystem;

    this.addRequirements(shooterSubsystem);
  }

  @Override
  public void initialize() {
    this.shooterSubsystem.setSpeed(0);
  }

  @Override
  public void execute() {
    this.shooterSubsystem.setSpeed(ShooterConstants.shotSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    this.shooterSubsystem.setSpeed(0);
  }

  // Bu metodun bitmesine gerek yok zaten trigger aktifleşmeyi bırakınca trigger metodu bitirecektir.
  public boolean isFinished() {
    return false;
  }
}
