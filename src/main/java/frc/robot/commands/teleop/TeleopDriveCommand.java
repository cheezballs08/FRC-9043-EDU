package frc.robot.commands.teleop;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.subsystems.DrivetrainSubsystem;

public class TeleopDriveCommand extends Command {
  
  // Supplier kullanmamızın sebebi her döngüde yeni bir komut oluşturmak yerine aynı komutu kullanabilmek.
  // Çünkü her döngüde kontrolcünün verdiği hızlara göre olan yeni bir komut oluşturmak zorunda kalırdık.
  // Burada Supplier ile kontrolcünün gerekli metodlarını alıp her döngüde buradan hızları alıyoruz.
  // Böylece her seferinde yeni bir komut oluşturmak zorunda kalmıyoruz.
  // Bu sayede robotumuzun kodu daha hızlı çalışır.
  private Supplier<Double> xFunction, rFunction; 

  private DrivetrainSubsystem drivetrainSubsystem;

  private double xSpeed, rSpeed;

  // Okunabilirlik açısından alt alta yazdım, yan yana yazmak ile herhangi bir fark yok.
  public TeleopDriveCommand(
    DrivetrainSubsystem drivetrainSubsystem,
    Supplier<Double> xFunction,
    Supplier<Double> rFunction
    ) {
    
    // This: Sayesinde şuan oluşturulan objeden bahsedebilmekteyiz. Bu sayede istersek fonksiyonları farklı iki veya daha fazla
    // drive command yazabiliriz.
    this.xFunction = xFunction;
    this.rFunction = rFunction;
    
    this.drivetrainSubsystem = drivetrainSubsystem;

    // Bu sınıfın bir metodu olan addRequirements() metodunu kullanarak bu komutun drivetrainSubsystem'i kullandığını CommandScheduler'e
    // belirtiyoruz. Bu sınıfın objesinin bir metodu olmasına rağmen bu dosyada olmamasının sebebi biz Command sınıfını extendliyoruz.
    // Biz bir sınıfı extendlerken her metodunu sıfırdan yazmak zorunda değiliz. Command sınıfı bizim için addRequirements() metodunu yazmış bile
    // Biz extendlerken bu metodu yanında aldık.

    // Burada ve altlarda this kullanılmasına gerek yok, java bizim için otomatik koyuyor zaten
    // This normalde java derleyicisinin kafasının karışabileceği yerlerde özellikle belirtmek amacıyla koyulur.
    // Örneğin yukarıda parametreler ile sınıfın değerleri aynı isimde bu sebeple this kullanmamız gerekir.
    // ancak ben siz okurken this in ne olduğunu daha iyi anlamanız için önemli önemsiz her yere koydum.
    // Hatırlatma: This bu metodun üzerinde çağırldığı objeden bahseder.
    // This kullanan her metodun bir obje üzerinden obje.birMetod() gibi çağırılması gerekir ve Static olamaz!
    // Sadece oluşturucular bu kuraldan muaftır. 
    this.addRequirements(drivetrainSubsystem);
  }

  // Fark ederseniz aşağıdaki initialize() gibi metodların üzerinde @Override diye bir yazı var
  // Bu bizim Command sınıfını extendlerken zaten bulunan bir metodu silip üzerine yeniden yazdığımız anlamına gelir.
  
  // Bu metod komut başladığına çalışır ne olur ne olmaz hızları önce 0 a ayarlayalım.
  @Override
  public void initialize() {
    this.drivetrainSubsystem.setSpeeds(0);
  }

  // Bu metod komut aktifken her döngüde bir defa çalışır.
  @Override
  public void execute() {

    // Fonksiyonların değerini alıp maksimum hızlarla çarpıp xSpeed ve rSpeed değerlerimizi güncelliyoruz. 
    // Bu maksimum hızların sebebini Constants.java'da bulabilirsiniz. CTRL tuşuna basılı tutup tanımlanan yere gitmeyi hatırlayınız.
    this.xSpeed = this.xFunction.get() * DrivetrainConstants.maxForwardSpeed;
    this.rSpeed = this.rFunction.get() * DrivetrainConstants.maxRotationalSpeed;

    this.drivetrainSubsystem.drive(this.xSpeed, this.rSpeed);
  }

  // Bu metod komut bittiğinde veya CommandScheduler tarafından yarıda kesildiğinde çalışır.
  // Komut bittiği zaman da hızları 0 yapalım.
  @Override
  public void end(boolean interrupted) {
    this.drivetrainSubsystem.setSpeeds(0);
  }

  // Her döngüde çağırılır. Komutun bitip bitmeyeceğini belirler.
  // Biz bu komutun bitmesini istemiyoruz. Eğer bitecek ise bu subsystemi kullanan başka bir komut aktif olunca bitsin.
  // Bunu zaten çakışmaları önlemek amacılya Command Scheduler bizim için yapıyor.
  // Bu sebeple bu metodun true returnlemesini istemiyoruz. Bu sebeple metod içinden false returnliyoruz.
  @Override
  public boolean isFinished() {
    return false;
  }
}
