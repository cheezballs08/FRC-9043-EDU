package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.auto.TimedShootCommand;
import frc.robot.commands.auto.TimedMoveForwardCommand;
import frc.robot.commands.teleop.TeleopDriveCommand;
import frc.robot.commands.teleop.TeleopShootCommand;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class RobotContainer {
  
  // Kontrolcümüz
  // Normal XboxController kullansaydık triggerlara erişimimiz olmayacaktı bu sebeple CommandXboxController kullanıyoruz.
  // Kontrolcü örneği olarak Logitech F710 kullanıyorum  isterseniz internetten bakabilirisiniz.
  CommandXboxController controller;

  // Komutların çalışmasını sağlayacak triggerlar.
  // Bu triggerları kontrolcümüz bize vericek. İsteseydik kendi triggerlarımızı da yazabiliridik ama hazıra konmak her zaman
  // daha iyidir.
  Trigger xTrigger;
  Trigger aTrigger;
  Trigger bTrigger;
  Trigger yTrigger;

  // Gerekli subsystemler
  DrivetrainSubsystem drivetrainSubsystem;
  ShooterSubsystem shooterSubsystem;

  // Gerekli komutlar
  TeleopDriveCommand teleopDriveCommand;
  TeleopShootCommand teleopShootCommand;

  TimedShootCommand autoShootCommand;
  TimedMoveForwardCommand autoMoveForwardCommand;

  // Bir de kolaylık açısından 2 saniye, 5 saniye ve 8 saniye atış yapan 3 komut tanımlayalım ve bunları
  // triggerlara atayalım.
  TimedShootCommand shootCommand1 = new TimedShootCommand(shooterSubsystem, 2);
  TimedShootCommand shootCommand2 = new TimedShootCommand(shooterSubsystem, 5);
  TimedShootCommand shootCommand3 = new TimedShootCommand(shooterSubsystem, 8);

  
  public RobotContainer() {
    //Şimdi objeleri oluşturuyoruz. Bunu yukarıda da yapabilirdik ancak burada oluşturmak her zaman daha güvenlidir.

    this.controller = new CommandXboxController(OperatorConstants.controllerID);

    this.xTrigger = controller.x();
    this.aTrigger = controller.a();
    this.bTrigger = controller.b();
    this.yTrigger = controller.y();

    this.drivetrainSubsystem = new DrivetrainSubsystem();
    this.shooterSubsystem = new ShooterSubsystem();

    this.teleopDriveCommand = new TeleopDriveCommand(drivetrainSubsystem, controller::getLeftY, controller::getRightX);
    this.teleopShootCommand = new TeleopShootCommand(shooterSubsystem);

    this.autoShootCommand = new TimedShootCommand(shooterSubsystem, ShooterConstants.autoShootDuration);
    this.autoMoveForwardCommand = new TimedMoveForwardCommand(drivetrainSubsystem, DrivetrainConstants.autoMoveDuration);
    
    this.configureBindings();
  }

  // Kol atamalarını burada yapıyoruz.
  private void configureBindings() {

    // xTrigger aktif olduğu sürece teleopShootCommand çalışacak.
    // Aktiflik bitince teleopShootCommand da bitecek.
    this.xTrigger.whileTrue(teleopShootCommand);

    // Bu komutlar triggerlar aktif olunca çağırılacaklar.
    // Biri aktifken bir diğeri aktif hale getirilirse aktif olan kapanır ve yenisi çalışmaya başlar.
    this.aTrigger.onTrue(shootCommand1);
    this.bTrigger.onTrue(shootCommand2);
    this.yTrigger.onTrue(shootCommand3);

    // Burada drivetrainSubsystemi gerektiren bir komut çalışmayınca çalışacak komutu belirliyoruz
    // Aynısınım shooterSubsystem için aynısının yapılması gerekmiyor. Bazı subsystemlerin defaultCommandleri olmayabilir.
    this.drivetrainSubsystem.setDefaultCommand(teleopDriveCommand);
  }

  // Otonomda çalışacak komutları buradan returnliyoruz.
  public Command getAutonomousCommand() {
    // Commands sınıfı bizim için kolaylık sağlayan bir sınıf.
    // sequence() metodunun içine yazdığımız her komut sıra ile çağırılıyor.
    // Bir komut bittiğinde diğer komut çalışıyor.
    // Bu girdileri yan yana da yazabilirdik ancak böyle daha kolay okunuyor ve komutların sırasını değiştirmek daha kolay.
    return Commands.sequence(
      autoShootCommand,
      autoMoveForwardCommand
    );
  }
}
