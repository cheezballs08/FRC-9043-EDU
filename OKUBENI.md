# *Komut Tabanlı Sistem Özet*

**Önemli!** kodu okumadan önce burayı tamamen okuyunuz.

Kodu okurken `Ctrl` tuşuna basılı tutup bir *değişken*, *sabit*, *sınıf* veya *metodun* üzerine tıklarsanız o *değişken*, *sabit*, *sınıf* veya *metodun* tanımlandığı yere gidersiniz. Bunu kullanmanızı şiddetle tavsiye ederim.

## Command Scheduler (Komut Zamanlayıcsı)
  Bizim için `Subsystem`'lerin ve `Command`'lerin gerekli periodik metodlarını çağırır ve aynı `Subsystem`'i kullanan `Command`'lerin birbiri ile çakışmamasını sağlar.

  Bir projede aktif olarak kullanılmaz zaten proje ilk oluşturulduğunda gerekli işlemleri yapılmıştır.

## Subsystem (Alt Birim)
  Bir `Robot`'taki temel mekanizmaların koddaki halidir.
  Örnek olarak bir `Robot`'ta varsa Drivetrain, Kol, Görüntü İşleme gibi mekanizmaların hepsi koda döküldüğü zaman kendine ait  bir `Subsystem` tarafından temsil edilmelidirler.

  Her bir `Subsystem`'in aktif olarak çalışan sadece 1 `Command`'i olabilir. Yeni aktifleşen bir `Command` olursa eski `Command` kapatılır ve yerine yeni `Command` aktif hale gelir.

  Eğer `Subsystem`'in aktif bir komutu yoksa istenilirse bu durumda çalışacak bir `Command` belirtilebilir. Bu `Subsystem`'in `void setDefaultCommand(<Command>)` metodu ile yapılır.

  Her döngüde bir defa olacak şekilde `Subsystem`'in `void periodic()` metodu `CommandScheduler` tarafından çağırılır.

  Normalde her `Subsystem`'in varlığının `CommandScheduler`'e belirtilmesi gerekmektedir. Ancak bu bizim için `SubsystemBase` sınıfında otomatik olarak yapılmaktadır. Biz bu sınıfı extendleyince bu özellik te otomatik olarak `Subsystem`'imize ekleniyor. Detaylarına bakmak isterseniz `SubsystemBase` sınıfının tanımlanmasına bakabilirsiniz.

## Trigger (Tetikleyici)
  Bir `Command`'in kolay bir şekilde aktif hale gelmesini sağlar.

  `Trigger`'lar bize `CommandXboxController` gibi objeler tarafından sağlanabilir. Ayrıca kendimiz de bir durumun doğruluk veya yanlışlık değerine göre aktifleşen `Trigger`'lar yazabiliriz.

  `Trigger`'ların `void onTrue(<Command>)`, `void onFalse(<Command>)` gibi metodları komutları aktif hale getirmeye yarar.

  `Trigger`'ların kullanılan metodları aşağıda verilmiştir.

  1. `void onTrue(<Command>)` ve `void onFalse(<Command>)`: `Trigger` aktifleşince `Command` aktifleşir. `Trigger`'in deaktif olması `Command`'in çalışmasını durdurmaz. `Command` başka bir şekilde kapatılana kadar çalışır.
   
  2. `void whileTrue(<Command>)` ve `void whileFalse(<Command>)`: `Trigger` aktif olduğu sürece `Command`' aktiftir. Aktifleşme bittiği zaman `Command` kapanır.
   
  3. `void toggleOnTrue(<Command>)` ve `void toggleOnFalse(<Command>)`: `Trigger` ilk aktif olduğu zaman `Command` aktifleşir, `Trigger` ikinci defa aktif olduğu zaman `Command` kapanır.
   
  4. `Trigger negate()`: Üzerinde çağırılan `Trigger`'ın tersi şekilde aktifleşen yeni bir `Trigger` returnler.
   
  5. `Boolean getAsBoolean()`: `Trigger`'ın aktiflik değerini `Boolean` cinsinden returnler.

## Command (Komut)

 `Command`'ler çoğunlukla `Subsystem`'leri kullanır. Her `Command`'in bir `Subsystem`'i olmak zorunda değildir. 

  Eğer bir `Command` bir `Subsystem`'i kullanıyorsa kullandığı `Subsystem`'i kendi oluşturucu metodundan almak zorundadır.

  Ayrıca aynı `Subsystem`'i kullanan diğer `Command`'ler ile çakışmayı önlemek amacıyla `Command`, oluşturucusunda kullandığı `Subsystem`'i `void addRequirements(<Subsystem>)` metodu ile `CommandScheduler`'a belirtmek zorundadır.

  Birden çok `Command` türü vardır. Büyük çoğunluğu şu an önemli değil o sebeple buraya türlerini yazmayacağım.

  Bir `Command`'in hayat döngüsü aşağıdaki gibidir:

1. Bir döngü başında `Command` bir `Trigger` tarafından aktif hale getirilir ve `void initialize()` metodu `CommandScheduler` tarafından çağırılır.
2. Bundan sonra her döngüde bir defa olacak şekilde aktif olan her `Command`'in `void execute()` ve `boolean isFinished()` metodları çağrılır. Eğer komutun `boolean isFinished()` metodu o döngüde `true` değeri returnlerse `Command` kapatılır ve son aşamaya gelir. `Command`'ler ayrıca aynı `Subsystem`'i kullanan başka bir `Command`'in aktif hale gelmesi sonucu çakışmayı engellemek amacıyla `CommandScheduler` tarafından da kapatılabilir. `CommandScheduler` çalışmakta olan `Command`'i kapatır ve yeni çalışmaya başlayacak `Command`'i aktif hale getirir.
  
3. `Command` bittiği zaman `Command`'in `void end(<Boolean>)` metodu `CommanScheduler` tarafından çağrılır ve `Command` kapatılır. İstenilirse aynı `Command` daha sonra bir daha aktif hale gelebilir.

## Robot Container (Robot Konteyneri)
  `Subsystem`, `Command`, `Controller` ve `Trigger` gibi robotun parçalarının tanımlamasının yapıldığı yer. Kolaylık sağlar.

  Yukarıda belirtilen parçaların tanımlaması `RobotContainer`'in oluşturucusunda olur. Ayrıca `Trigger`'ların gerkeli komutlar ile ilişkisinin kurulması `void configureBindings()` metodu içerisinide yapılır. Ayrıca burada `Subsystem`'lerin `DefaultCommand`'leri de tanımlanır.

  Otonom süreçte çağırılacak `Command`, `Command getAutonomousCommand()` metodu içerisinden returnlenir.


# Robot Döngüsü
1. İlk önce `Subsystem`'lerin `void periodic()` metodu çağırılır
2. `Trigger`'lara ve `DefaultCommand`'lere baklılır ve aktifleşmesi gereken `Command`'ler aktifleşir.
3. Aktifleşen `Command`'lerin `void initilaize()` metodları çağırılır.
4. Zaten aktif olan `Command`'lerin `void execute()` ve `boolean isFinished()` metodları çağırılır.
5. `boolean isFinished()` metodu `true` returnleyen komutların veya `CommandScheduler` tarafından yarıda kesilen `Command`'lerin `void end(<Boolean>)` metodları çağırılır.

ve 20ms süreç sonunda döngü biter. Bir sonraki döngü başlar `Robot` kapatılana kadar döngü bu şekilde devam eder.

# Robotun Tanımlaması

Bu projedeki `Robot`'un sadece atıcı alt birimi yani `ShooterSubsytem`'i ve sürüş alt birimi yani `DrivetrainSubsystem`'i var. `CommandXboxController`'in joystickleri ile `Robot`'u hareket ettireceğiz ve x butonu ile de `ShootCommand`'i çalıştıracağız.

`Robot`'un sağda ve solda olmak üzere iki motoru var ve arcade drive sistemi ile çalışacak. Motorları birer `MotorController` kontrol edecek.

Shooter bir motor ile çalışacak ve o motoru da bir `MotorController` kontrol edecek.

Otonom kısmında ise robot 5 saniye boyu atış yapıp sonra 2 saniye ileri gidecek.