package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;

public class DrivetrainSubsystem extends SubsystemBase {

  // Objeler burada tanımlanır ancak burada oluşturulmazlar, oluşturma işini oluşturucu metoda bırakmalıyız.
  // Private: Çünkü komutlara uğraşırken yanlışlıkla bunların tanımlamasını değiştirebiliriz, örneğin yanlışlıkla
  // motor tipini değiştirirsek motorları yakma ihtimalimiz var. Ancak bu durumda komutlar kullanması gereken metodları kullanamaz.
  // Bu sebeple aşağıda biz gerekli metodları bu sınıfın bir metoduna koyup görünür hale getireceğiz. Böylece diğer komutlar sadece
  // bizim istediğimiz değişiklikleri yapabilecek. Bu metodlar çoğunlukla get, set ile başlar.
  private CANSparkMax leftMotor;  
  private CANSparkMax rightMotor;
  
  private DifferentialDrive differentialDrive;

  public DrivetrainSubsystem() {   
    // Objeleri oluşturuyoruz ve gerekli işlemleri yapıyoruz.
    this.leftMotor = new CANSparkMax(DrivetrainConstants.leftMotorID, DrivetrainConstants.motorType);  
    this.rightMotor = new CANSparkMax(DrivetrainConstants.rightMotorID, DrivetrainConstants.motorType);

    this.rightMotor.setInverted(true);

    this.differentialDrive = new DifferentialDrive(leftMotor, rightMotor);
  }

  // Bu alt birimin periodic olarak yapması gereken bir şey yok, eğer isteseydik motorların hızı gibi değerleri
  // buradan güncelleyip yazdırabilirdik.
  public void periodic() {}

  // Şimdi komutlar için gerekli olan get, set türevi metodları tanımlayalım.
  public void drive(double xSpeed, double rSpeed) {
    differentialDrive.arcadeDrive(xSpeed, rSpeed, DrivetrainConstants.isSquaredInput);
  }

  // get, set türevi metodların bir diğer avantajı eğer riskli durumlar bulunuyorsa bu durumların giderilmesinin sağlanmasıdır.
  // Örneğin burada eğer motor hızı 1 den büyük veya -1 den küçük olursa motorumuzun bozulma ihtimali var. Bazı motor kontrolcüleri
  // bunu ayarlayabilirken bazıları ayarlayamıyor. Bu sebeple ne olur ne olmaz biz ayarlama yapalım.
  public void setLeftSpeed(double speed) {
    if (speed > 1) {
      speed = 1;
    }
    else if (speed < -1) {
      speed = -1;
    }

    leftMotor.set(speed);
  }
  
  public void setRightSpeed(double speed) {
    if (speed > 1) {
      speed = 1;
    }
    else if (speed < -1) {
      speed = -1;
    }

    rightMotor.set(speed);
  }

  public void setSpeeds(double speed) {
    setLeftSpeed(speed);
    setRightSpeed(speed);
  }

}
  