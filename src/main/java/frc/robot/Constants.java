package frc.robot;

import com.revrobotics.CANSparkLowLevel.MotorType;

// Buraya robot ile ilgili bütün sabitlerimizi tanımlıyoruz, bunu yaparken ki amacımız
// eğer robotta değişecek bir durum olursa bütün dosyalarda o sabiti arayıp bulmak zorunda kalmamak
// tek bir yerden değiştirebilmek.

public class Constants {

    public class DrivetrainConstants {
        // Public: Her yerden görülmesi için
        // Static: DrivetrainConstant sınıfı oluşturmadan sabite erişebilmek için
        // Static metodlar, sabitler ve değişkenler Sınıfİsmi.metod şeklinde çağırılırlar. 
        // Final:  Değişmeyeceği için
        public static final MotorType motorType = MotorType.kBrushed; 

        public static final int leftMotorID = 0;
        public static final int rightMotorID = 0;

        // Motorlardan birini ters çevirmemizin sebebi motorlar takıldığı zaman birbiri tersi yönünde çalışmaktadır.
        // Bu sebeple motorlardan birini ters çevirmemiz gerekir. Bunu yapmak yerine girdileri -1 ile de çarpabiliriz ama REV
        // bizim için setInverted() diye bir metod oluşturmuş niye kullanmayalım.
        // yazılım yaparken tekerleği yeniden icat etmek yerine hazıra konmak her zaman daha iyidir bu da bir tavsiye.
        public static final boolean invertLeftMotor = false;

        // Soldaki yerine sağdaki motoru çevirmemizin sebebi eğer soldakini çevirseydik -1 değeri ilerisi olacaktı.
        // Ancak şimdi +1 değeri ileriyi temsil eder.
        public static final boolean invertRightMotor = true;

        // Maximum hızlar tanımlıyoruz çünkü 1 hızı çok hızlı ve kontrol etmesi zor. Bir kontrolcüden aldığımız değeri
        // bu sayılar ile çarparsak hızımızın maksimum bu değeri alabileceğini garantilemiş oluruz.
        public static final double maxForwardSpeed = 0;
        public static final double maxRotationalSpeed = 0;

        // Normalde DifferentialDrive objesi girilen hızların karesini alır ve öyle motorlara gerekli hızları verir.
        // Bu robotun düşük hızlarda daha rahat kontrol edilmesini sağlar. Eğer olur da değiştirmek istersek burada bu değeri tanımladık.
        public static final boolean isSquaredInput = true;

        // Otonomda alıcağımız hız değeri. MoveForwardCommand'de kullanılacak.
        public static final double autoSpeed = 0;

        // Otonomda ne ne kadar saniye ilerliyeceğimizi ayarlayan sabit.
        public static final double autoMoveDuration = 0;
    }
    
    // Drivetrain ile aynı mevzu
    public class ShooterConstants {
        public static final MotorType motorType = MotorType.kBrushed; 

        public static final int motorID = 0;

        public static final double shotSpeed = 0;
    
        public static final double autoShootDuration = 0;
    }

    // Diğerleri ile aynı olay.
    public class OperatorConstants {
        public static final int controllerID = 0;
    }

}
