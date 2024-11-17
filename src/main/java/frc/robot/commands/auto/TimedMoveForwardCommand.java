package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.subsystems.DrivetrainSubsystem;

// Diğer komutlarla aynı mantık, sadece isFinished() metodu farklı, çünkü bu sefer metod 5 saniyeyi geçtiği zaman komut
// bitmeli.
public class TimedMoveForwardCommand extends Command {
  
  // Gerekli subsystem.
  DrivetrainSubsystem drivetrainSubsystem;
  
  // Normalde zaman, uzaklık gibi birimleri tanımlarken FRC'nin Units kütüphanesini kullanmalıyız.
  // Ancak şu anlık double tipini kullanmak yeter.

  // Çalışacağı süre
  double executionTime;

  // Komutun başladığı süre.
  double startTime;

  // Komut çalışırken o an ki süresi. execute() metodu ile her döngüde güncelleyeceğiz.
  // (currentTime - startTime) hesaplaması ile komutun ne kadar çalıştığını bulabiliriz.
  // Bunu ayrı bir değişkende tanımlayacağız.
  double currentTime;

  // Bu da komut başladığından beri geçen süreyi tanımlıyor
  // elapsedTime ile executionTime'ı karşılaştırarak süre bitti mi bitmedi mi bilebiliriz.
  double elapsedTime;

  public TimedMoveForwardCommand(DrivetrainSubsystem drivetrainSubsystem, double moveDuration) {
    this.drivetrainSubsystem = drivetrainSubsystem;
    this.executionTime = moveDuration;

    this.addRequirements(drivetrainSubsystem);
  }

  @Override
  public void initialize() {
    this.drivetrainSubsystem.setSpeeds(0);
    
    // Timer sınıfının getFPGATimestamp() metodu robot başladığından beri olan süreyi bize verir. Bu sayede komutun
    // başlama süresini alırız.

    // Bunu komutun oluşturusundan yapmamamızın sebebi komut ilk oluştuğu zaman bir kere bu sayı güncellenecek.
    // Ancak komutlar bittikten sonra yok olmuyor bir daha çağırılabiliyor. Bu durum hatalara sebep olabilir.
    // Bu sebeple bizim başlangıç zamanını komut başladığı zaman tanımlamamız gerekir. Komut oluşturulduğu zaman değil.
    this.startTime = Timer.getFPGATimestamp();
  }

  @Override
  public void execute() {
    this.drivetrainSubsystem.setSpeeds(DrivetrainConstants.autoSpeed);

    //Komutun o an ki süresini ayarlıyoruz.
    this.currentTime = Timer.getFPGATimestamp();

    // Toplam geçen süreyi hesaplıyoruz.
    this.elapsedTime = this.currentTime - this.startTime;
  }

  @Override
  public void end(boolean interrupted) {
    this.drivetrainSubsystem.setSpeeds(0);
  }

  // Artık bu metodu değiştirmemiz gerekiyor.
  // Geçen süre komutun aktif olma süresinden fazla olduğu zaman komutun kapanması için true returleyeceğiz.
  // Bunun dışında false returleyeceğiz.
  @Override
  public boolean isFinished() {
    // Eğer geçen süre çalışma süresinden fazla ise komutu kapatıyoruz.
    if (this.elapsedTime > this.executionTime ){
      return true;
    }
    // Değil ise false returleyip kapatmıyoruz.
    else {
      return false;
    }
  }
}
