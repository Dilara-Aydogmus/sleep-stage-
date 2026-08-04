# SleepBench

SleepBench, uçtan uca uyku evresi replikasyonu ve model karşılaştırması için
planlanan bir araştırma platformudur. Platform; 10'dan fazla bilimsel makaledeki
yaklaşımların replikasyonlarını kataloglayacak, tek kanallı EEG kayıtlarını ve
etiketli veri setlerini kabul edecek, seçilen modelleri çalıştıracak ve tahminleri,
metrikleri ve model karşılaştırmalarını REST API'leri üzerinden sunacaktır.

Proje özellikle N1 uyku evresinin düşük temsil edilmesi ve zor sınıflandırılması
problemine odaklanmaktadır. Backend geliştirme, makine öğrenmesi, veri tabanı
tasarımı, otomatik test, container orkestrasyonu ve CI/CD çalışmalarını tek bir
portföy projesinde birleştirir.

> Proje durumu: Planlama ve temel hazırlık aşaması. Mevcut Java sınıfı IntelliJ
> tarafından oluşturulmuş bir başlangıç dosyasıdır ve bağımsız Spring Boot
> mikroservisleriyle değiştirilecektir.

## Hedefler

- Identity, araştırma kataloğu ve deney yönetimi için bağımsız Java mikroservisleri geliştirmek.
- Her servise kendi veri sahipliğini ve bağımsız deployment yaşam döngüsünü vermek.
- 10'dan fazla makaledeki uyku evreleme yaklaşımını replike etmek ve kataloglamak.
- Analiz veya karşılaştırma için adlandırılmış bir ya da birden fazla model sürümü seçmek.
- Birden fazla eğitilmiş modeli birbirinden izole Python çalışma ortamlarında çalıştırmak.
- Modelleri izlenebilir ve tekrarlanabilir değerlendirme koşullarında karşılaştırmak.
- Uzun süren analizleri Redis Streams ile asenkron işlemek.
- Yapılandırılmış metadata ve tahminleri PostgreSQL'de saklamak.
- EDF dosyalarını ilişkisel veri tabanının dışında saklamak.
- Sistemi birim, entegrasyon, sözleşme ve HTTP iş akışı seviyelerinde test etmek.
- Servisleri Docker ile paketlemek ve Kubernetes üzerinde çalıştırmak.
- Derleme, test, image oluşturma ve deployment işlemlerini Jenkins ile otomatikleştirmek.
- Web arayüzünü yalnızca backend ve altyapı kararlı hâle geldikten sonra eklemek.

## Kapsam

İlk sürüm bir araştırma ve portföy uygulamasıdır; sertifikalı bir tıbbi cihaz
değildir. Ürettiği tahminler teşhis veya tedavi amacıyla kullanılmamalıdır.

### Temel yetenekler

- JWT tabanlı kayıt, kimlik doğrulama ve yetkilendirme
- EDF/EEG kaydı yükleme ve metadata yönetimi
- Asenkron analiz işi oluşturma ve durum takibi
- Beş sınıflı uyku evreleme: `W`, `N1`, `N2`, `N3` ve `REM`
- Epoch bazında güven skorları ve düşük güvenli tahmin işaretleri
- JSON biçiminde hipnogram verisi
- N1 odaklı model metrikleri ve inceleme iş akışı
- Tahmin edilen epoch'ların uzman tarafından düzeltilmesi ve anotasyonu
- Makale, model implementasyonu ve eğitilmiş artifact sürümleme
- Aynı kayıt veya benchmark için bir ya da birden fazla model seçimi
- Metriklerin, confusion matrix'lerin ve sınıf bazlı sonuçların yan yana karşılaştırılması
- Tekrarlanabilir deney yapılandırmaları ve sonuç dışa aktarma
- Önemli kullanıcı ve analiz işlemleri için audit kayıtları

### İlk sürümün kapsamı dışında kalanlar

- Büyük modellerin AWS üzerinde eğitilmesi
- Klinik teşhis veya hastane entegrasyonu
- Multi-region veya high-availability altyapısı
- Amazon EKS ile yönetilen Kubernetes
- API tamamlanmadan önce grafiksel kullanıcı arayüzü

## SleepStage AI ile ilişki

SleepStage AI, SleepBench'ten ayrı bir ürün veya dördüncü bir mikroservis değildir.
Bu ad projenin makine öğrenmesi ve uyku evreleme katmanını ifade eder. Bitirme
tezinde geliştirilen N1 odaklı model, SleepBench kataloğuna kaydedilecek ilk aday
model runner'lardan biri olacaktır. Replike edilen 10'dan fazla makale modeli ise
bu aday modelin karşılaştırılacağı baseline'ları oluşturacaktır.

SleepBench; modelleri, veri setlerini, deneyleri ve sonuçları yöneten platformdur.
SleepStage AI çalışması ise bu platform üzerinde ölçülen bilimsel katkıdır. Temel
başarı ölçütü yalnızca altyapının çalışması değil; önerilen yaklaşımın aynı
değerlendirme protokolünde N1 F1, Macro F1 ve veri setleri arası genellenebilirlik
açısından baseline modellerle karşılaştırılabilmesidir.

## Araştırma replikasyonu ve model karşılaştırması

Temel araştırma senaryosu; 10'dan fazla makaledeki yöntemleri replike etmek ve
birbirinden kopuk script, yapılandırma ve sonuç dosyalarını elle yönetmeden bu
yöntemlerin uyku evreleme performansını karşılaştırmaktır.

Platform üç ilişkili işlem türünü destekleyecektir:

| İşlem | Amaç |
| --- | --- |
| Tek model analizi | Yüklenen bir kayıt üzerinde seçilen tek bir eğitilmiş modeli çalıştırmak |
| Çoklu model analizi | Aynı kayıt üzerinde seçilen birden fazla modeli çalıştırıp epoch tahminlerini karşılaştırmak |
| Veri seti benchmark'ı | Etiketli bir veri setinde birden fazla model sürümünü değerlendirip toplu metrikleri karşılaştırmak |

### Karşılaştırma modları

Makaleye sadık bir replikasyon ile sıkı biçimde kontrol edilen bir benchmark
farklı araştırma sorularını yanıtladığı için iki karşılaştırma modu gereklidir.

#### Makaleye sadık replikasyon

Her implementasyon; ön işleme, mimari, kayıp fonksiyonu, örnekleme stratejisi,
eğitim yapılandırması ve değerlendirme ayrımı dâhil olmak üzere ilgili makaleyi
mümkün olduğunca yakından takip eder. Makalede eksik olduğu için replike
edilemeyen ayrıntılar platformda açıkça işaretlenir.

#### Kontrollü benchmark

Seçilen modeller ortak bir benchmark protokolüyle değerlendirilir. En azından
veri seti sürümü, denek grubu, etiket eşlemesi, train/validation/test veya LOSO
ayrımları, dışlanan epoch'lar, rastgelelik tohumları ve metrik tanımları sabitlenir.
Uyumlu modeller için ortak bir ön işleme hattı zorunlu tutulabilir. Bu mümkün
değilse her modelin ön işleme hattı açıkça kaydedilir ve karşılaştırma kaydına
eklenir.

Bu iki modun sonuçları açık bir etiket olmadan aynı sıralamada birleştirilmemelidir.

### Tekrarlanabilirlik kaydı

Her model çalıştırması aşağıdaki bilgileri korur:

- Makale başlığı, DOI veya URL ve yayın yılı
- Sistem içindeki model adı ve benzersiz sürümü
- Kaynak kodun Git commit'i
- Docker image digest'i ve bağımlılık sürümleri
- Model artifact checksum'ı
- Veri seti adı, sürümü ve checksum'ı
- Denek kimlikleri ve fold atamaları
- Ön işleme ve eğitim yapılandırmalarının anlık görüntüleri
- Rastgelelik tohumları
- Değerlendirme protokolü ve metrik tanımları
- Başlangıç zamanı, süre, donanım özeti ve son durum

Model görünen adları kullanıcı seçimini kolaylaştırır; ancak daha sonraki kod veya
ağırlık güncellemelerinin eski bir sonucu sessizce değiştirmemesi için deneyler
değiştirilemez model sürümü kimliklerine referans verir.

## Planlanan mimari

~~~mermaid
flowchart LR
    Client[Postman / HTTP İstemcisi] --> Ingress[Kubernetes Ingress]
    UI[Nuxt / Vue UI - son aşama] --> Ingress
    Ingress --> Identity[identity-service]
    Ingress --> Catalog[catalog-service]
    Ingress --> Experiment[experiment-service]
    Identity --> IdentityDB[(identity schema)]
    Catalog --> CatalogDB[(catalog schema)]
    Experiment --> ExperimentDB[(experiment schema)]
    Catalog --> Storage[(MinIO veya S3)]
    Experiment --> Catalog
    Experiment --> Queue[Redis Stream]
    Queue --> Dispatcher[Experiment Dispatcher]
    Dispatcher --> Jobs[Kubernetes Job Yöneticisi]
    Jobs --> Runner[Seçilen İzole Model Runner]
    Runner --> Storage
    Storage --> Artifacts[Sürümlenmiş Model Artifact'ları]
    Runner --> InternalAPI[experiment-service İç Sonuç API'si]
    InternalAPI --> Experiment
~~~

Mimari, bağımsız domain mikroservisleri ile izole model çalıştırma düzleminden oluşur:

| Bileşen | Sorumluluk |
| --- | --- |
| `identity-service` | Kullanıcı kaydı, giriş, JWT üretimi, roller ve kimlik güvenliği |
| `catalog-service` | Makaleler, kayıtlar, veri setleri, model implementasyonları, sürümler ve protokoller |
| `experiment-service` | Deneyler, model seçimi, sabitlenmiş snapshot'lar, Redis kuyruğu, Kubernetes Job yönetimi, sonuçlar ve karşılaştırmalar |
| Kubernetes Ingress | Yeni bir Spring Boot servisi eklemeden public path routing sağlar |
| Model runner | Değiştirilemez tek bir model sürümünü izole container içinde çalıştırır ve standart sonuç üretir |
| PostgreSQL, Redis ve object storage | Servis verileri, kuyruk durumu, veri setleri, model artifact'ları ve çıktıları saklar |

Deney dağıtıcısı `experiment-service` içinde çalışır; ayrıca bağımsız bir servis
olarak bölünmez. Model runner'lar mikroservis veya sürekli çalışan servisler değil,
kısa ömürlü batch container'lardır. Bir model yalnızca işi başladığında belleğe
yüklenir ve container sona erdiğinde belleği serbest bırakılır.

### Mikroservis sınırları ve veri sahipliği

Her mikroservis kendi verisinin tek sahibidir. Maliyeti azaltmak için aynı
PostgreSQL instance kullanılabilir; ancak her servis ayrı schema ve database user
kullanır. Bir servis başka bir servisin tablolarına doğrudan sorgu gönderemez.

| Servis | Sahip olduğu schema | Temel veriler |
| --- | --- | --- |
| `identity-service` | `identity` | Kullanıcılar, roller ve refresh token kayıtları |
| `catalog-service` | `catalog` | Makaleler, denekler, kayıtlar, veri setleri, model sürümleri ve protokoller |
| `experiment-service` | `experiment` | Deneyler, model çalışmaları, fold sonuçları, epoch tahminleri, anotasyonlar ve metrikler |

### Mikroservis kararının gerekçesi ve maliyeti

Bu domain için mikroservisler teknik bir zorunluluk değildir. Aynı identity,
catalog ve experiment sınırları; tek Spring Boot uygulaması içinde ayrı package,
modül ve schema'larla oluşturulan modüler monolit ile daha düşük operasyonel
maliyetle korunabilirdi.

Mikroservis mimarisi; bağımsız deployment, servisler arası REST sözleşmesi,
dağıtık hata yönetimi, JWT/JWKS, veri sahipliği, contract test ve Jenkins/Kubernetes
entegrasyonu gibi pattern'leri uygulamalı olarak göstermek için bilinçli bir CV ve
öğrenme hedefi olarak seçilmiştir. Bu seçim mühendislik gereksinimi gibi
sunulmayacaktır ve aşağıdaki ek maliyetler kabul edilmektedir:

- Üç ayrı Spring Boot uygulaması ve Docker image
- Üç migration seti, schema ve Kubernetes Deployment/Service kaynağı
- Servisler arası HTTP timeout, retry ve hata senaryoları
- OpenAPI contract testleri ve bağımsız Jenkins aşamaları
- Tek uygulamaya göre daha yüksek bellek ve operasyonel bakım ihtiyacı

Kapsamı kontrol etmek için servis sayısı `identity-service`, `catalog-service` ve
`experiment-service` ile sınırlandırılır. Ayrı gateway servisi, service mesh,
Eureka, merkezi config server veya her entity için ayrı mikroservis eklenmez.
Public routing Kubernetes Ingress ile çözülür. Araştırma hedefleri gecikmeye
başlarsa doğal sadeleştirme yolu aynı domain sınırlarını koruyan modüler monolittir.

Servis iletişim kuralları:

- Public istekler Kubernetes Ingress üzerinden ilgili servise yönlendirilir.
- JWT `identity-service` tarafından asimetrik anahtarla imzalanır; diğer servisler
  yalnızca public key/JWKS üzerinden doğrulama yapar ve signing secret paylaşmaz.
- `experiment-service`, model ve protokol bilgilerini `catalog-service` REST API'sinden alır.
- Deney başlatılırken gerekli katalog verileri değiştirilemez bir snapshot olarak
  `experiment-service` içinde saklanır; sonraki katalog değişiklikleri eski deneyi değiştirmez.
- Uzun süren model çalışmaları Redis Streams ile asenkron yürütülür.
- Deney kaydı ile kuyruk mesajı arasında iş kaybını önlemek için
  `experiment-service` transactional outbox kullanır.
- Model runner sonuç callback'i Ingress üzerinden yayınlanmaz; kısa ömürlü run
  credential'ı ile korunan internal endpoint'e gönderilir.
- Kubernetes içinde servis keşfi için Kubernetes DNS kullanılır; Eureka eklenmez.
- Servis çağrılarında timeout, kontrollü retry, correlation ID ve idempotency uygulanır.
- Servisler arasında dağıtık transaction veya doğrudan cross-schema join kullanılmaz.

### Mimari kararın gerekçesi

Tek node üzerinde aynı anda yalnızca bir model çalıştırmak için Kubernetes teknik
bir zorunluluk değildir. Redis kuyruğundan iş alıp sırayla Docker container
başlatan daha küçük bir çözüm de işlevsel gereksinimleri karşılayabilirdi ve daha
düşük operasyonel karmaşıklığa sahip olurdu.

Kubernetes Job yaklaşımı; yatay ölçek ihtiyacından dolayı değil, container yaşam
döngüsü, resource request/limit, Job retry politikası, rollout, Secret/ConfigMap
yönetimi ve Jenkins deployment deneyimini portföyde göstermek amacıyla bilinçli
olarak seçilmiştir. Bu tercih bir ölçek veya production zorunluluğu gibi
sunulmayacaktır.

İki ayrı model execution yolu geliştirilmeyecektir. Model çalıştırmaları yerel
ortamda Minikube, bulut demosunda ise tek node k3s üzerinde daima Kubernetes Job
olarak yürütülecektir:

| Ortam | Kubernetes dağıtımı | Model execution |
| --- | --- | --- |
| Yerel geliştirme ve test | Minikube | Kubernetes Job |
| AWS portföy demosu | Tek node k3s | Kubernetes Job |

Bu karar iki farklı executor implementasyonunu ve bunlara ait çift test yükünü
ortadan kaldırır. Kubernetes istemcisi test edilebilirlik amacıyla dağıtıcı içinde
ayrı bir adapter sınıfında tutulabilir; ancak bu bir runtime profili değildir ve
alternatif Docker execution yolu sağlamaz.

## İzole model runner tasarımı

Replike edilen makaleler birbiriyle uyumsuz Python, TensorFlow, PyTorch, CUDA veya
sinyal işleme sürümleri gerektirebilir. Bu nedenle bütün modeller aynı Python
ortamını paylaşmayacaktır. Her model implementasyonu kendi Dockerfile'ını,
kilitlenmiş bağımlılık dosyasını, adapter'ını ve testlerini sağlar.

~~~text
models/
|-- deepsleepnet/
|   |-- Dockerfile
|   |-- requirements.lock
|   |-- adapter.py
|   `-- tests/
|-- seqsleepnet/
|-- cnn_bilstm/
`-- se_resnet/
~~~

Her runner aynı batch sözleşmesini uygular:

1. Veri seti, fold, protokol ve model artifact referanslarını içeren değiştirilemez
   çalıştırma tanımını okur.
2. Girdilerin ve artifact'ların checksum'larını doğrular.
3. Makaleye özel veya kontrollü ön işleme hattını uygular.
4. Deney türüne göre eğitim, değerlendirme veya inference çalıştırır.
5. Tahminleri ve metrikleri ortak sonuç şemasında yazar.
6. Sonucu iç API'ye gönderir ve anlamlı bir durum koduyla sona erer.

### Model sürümü kaydı

Bir modeli çalıştırmak için gereken bilgiler ayrı bir manifest dosyasında değil,
model sürümü kaydedilirken PostgreSQL'deki `model_versions` ve ilişkili
`model_artifacts` kayıtlarında tutulur. Bu bilgiler şunları kapsar:

- Model adı, sürümü ve ilişkili makale
- Framework, framework sürümü ve Python sürümü
- Değiştirilemez runner image digest'i
- Model artifact konumu ve checksum'ı
- Örnekleme frekansı, epoch süresi, kanal sayısı ve etiket şeması
- Minimum CPU ve bellek gereksinimi ile GPU zorunluluğu

API, çalıştırmayı planlamadan önce model sürümü kaydını ve veri seti uyumluluğunu
doğrular. Kullanıcı seçimi için görünen adlar; çalıştırma ve tekrarlanabilirlik
içinse değiştirilemez kimlikler, image digest'leri ve artifact checksum'ları
kullanılır.

### Kapasite ve planlama

Kayıtlı model sayısı bellek kullanımını belirlemez. Yalnızca planlanmış runner'lar
CPU ve bellek tükettiğinden katalogda 10 veya daha fazla model saklanabilir. Yerel
ve düşük maliyetli bulut ortamlarında varsayılan eş zamanlılık bir model çalışması
olacaktır. Kubernetes kaynak istekleri ve limitleri, tek bir modelin bütün node'u
tüketmesini engeller.

Büyük bir benchmark model ve fold bazında ayrı işlere bölünebilir. Seçilen başka
bir model başarısız olsa bile başarılı model çalışmalarının sonuçları korunur.
Yalnızca GPU üzerinde çalışabilen eğitimler Google Colab gibi harici bir ortamda
yürütülür. Platform; yapılandırma, log, artifact, tahmin ve metrikleri içeren
imzalanmış bir çalışma paketinin içe aktarılmasını destekler.

## Teknoloji yığını

### Backend

- Java
- Spring Boot
- Spring Security ve JWT
- Spring Data JPA ve Hibernate
- Flyway database migrations
- Maven
- Bean Validation
- OpenAPI / Swagger UI
- Servisler arası REST sözleşmeleri ve üretilmiş API client'ları

### Makine öğrenmesi

- Python
- Makale gerektiriyorsa TensorFlow ve Keras
- Makale gerektiriyorsa PyTorch
- MNE
- NumPy
- Scikit-learn
- Framework'ten bağımsız model runner sözleşmesi
- CNN-LSTM, SE-ResNet ve replike edilecek diğer mimariler

### Veri ve depolama

- PostgreSQL
- Redis ve Redis Streams
- Yerel object storage için MinIO
- Olası bulut depolama adapter'ı olarak Amazon S3

### Test

- JUnit 5
- Mockito
- Spring MockMvc
- Testcontainers
- pytest
- HTTPX
- Postman ve Newman

### DevOps

- Git
- Docker ve Docker Compose
- Kubernetes
- Yerel geliştirme için Minikube
- Bulut demosu için Amazon EC2 üzerinde k3s
- Jenkins
- Amazon EC2 ve EBS

### Kullanıcı arayüzü - son aşama

- TypeScript
- Nuxt.js ve Vue
- Quasar Framework
- Pinia

## Deney ve analiz iş akışı

1. Kullanıcı `identity-service` üzerinden kimliğini doğrular ve JWT alır.
2. Makaleler, model implementasyonları, artifact'lar, kayıtlar, veri setleri ve
   değerlendirme protokolleri `catalog-service` üzerinden kaydedilir.
3. Kullanıcı `experiment-service` üzerinden bir kayıt veya veri seti,
   karşılaştırma modu ve bir ya da birden fazla model sürümü kimliği seçer.
4. `experiment-service`, seçilen kimlikleri `catalog-service` üzerinden doğrular.
5. Doğrulanan model, veri seti ve protokol bilgileri deney içinde değiştirilemez
   bir snapshot olarak saklanır.
6. `experiment-service`, seçilen her model sürümü için `QUEUED` durumunda bir
   model çalışması ve aynı transaction içinde outbox kaydı oluşturur. Outbox
   publisher mesajı Redis Stream'e gönderir.
7. Aynı servisteki dağıtıcı kuyruktaki işi alır, eş zamanlılık sınırını uygular ve
   seçilen model sürümü için bir Kubernetes Job oluşturur.
8. İzole model runner artifact'ı indirip doğrular, ardından snapshot'taki ön
   işleme ve değerlendirme yapılandırmasını uygular.
9. Epoch tahminleri, fold metrikleri, toplu metrikler, kaynak kullanımı ve hatalar
   `experiment-service` iç sonuç API'sine gönderilir.
10. `experiment-service` sonuçları saklar ve bütün model çalışmaları son duruma
    ulaştığında karşılaştırma görünümünü oluşturur.
11. Sonuçlar UI beklenmeden JSON veya CSV biçiminde alınabilir.

### Catalog service kullanılamadığında deney oluşturma

Yeni deney oluşturmak için model, veri seti ve protokol doğrulaması zorunludur;
bu nedenle `catalog-service` o anda kullanılamıyorsa sistem eksik bilgiyle deney
oluşturmaz. `experiment-service` çağrısı aşağıdaki kuralları uygular:

1. Catalog çağrısında kısa ve yapılandırılabilir connect/read timeout kullanılır.
2. Yalnızca geçici network veya `5xx` hatalarında sınırlı retry uygulanır; `4xx`
   doğrulama hataları yeniden denenmez.
3. Retry sınırı aşılırsa circuit breaker hatayı hızlı keser ve kullanıcıya
   `503 CATALOG_UNAVAILABLE` döndürülür.
4. Catalog doğrulaması tamamlanmadan `experiments`, `model_runs` veya
   `outbox_events` kaydı oluşturulmaz; kısmi deney bırakılmaz.
5. İstemci aynı `Idempotency-Key` ile isteği daha sonra güvenle tekrarlayabilir.
6. Daha önce snapshot'ı oluşturulmuş deneyler catalog kesintisinden etkilenmez;
   çalışmaya ve sonuç sunmaya devam eder.

### İş durumları

~~~text
QUEUED -> PROCESSING -> COMPLETED
                   \-> FAILED -> QUEUED (kontrollü yeniden deneme)
~~~

Bir deney, model çalışmalarının son durumlarına göre `COMPLETED`,
`PARTIALLY_COMPLETED` veya `FAILED` olabilir. Bir implementasyonun başarısız
olması, seçilen diğer modellerin başarılı sonuçlarını silmemelidir.

## İlk domain modeli

| Entity | Sahip servis | Amaç |
| --- | --- | --- |
| `users` | `identity-service` | Hesaplar ve kimlik bilgileri |
| `roles` | `identity-service` | `USER`, `RESEARCHER` ve `ADMIN` yetkileri |
| `refresh_tokens` | `identity-service` | Oturum yenileme ve token iptali |
| `subjects` | `catalog-service` | Takma kimlik verilmiş kayıt denekleri |
| `recordings` | `catalog-service` | EDF metadata'sı ve object-storage referansları |
| `papers` | `catalog-service` | Makalelerin bibliyografik bilgileri ve replikasyon notları |
| `model_implementations` | `catalog-service` | Bir makaleyle ilişkilendirilmiş mimari ve adapter |
| `model_versions` | `catalog-service` | Değiştirilemez kod, runner image digest'i, yapılandırma ve artifact kimliği |
| `model_artifacts` | `catalog-service` | Ağırlık dosyaları, checksum'lar, formatlar ve depolama referansları |
| `datasets` | `catalog-service` | Veri seti kimliği, lisansı ve genel metadata |
| `dataset_versions` | `catalog-service` | Değiştirilemez veri seti manifestleri ve checksum'ları |
| `evaluation_protocols` | `catalog-service` | Ayrımlar, etiket eşleme, dışlamalar, tohumlar ve metrik tanımları |
| `experiments` | `experiment-service` | Karşılaştırma modu ve sabitlenmiş katalog snapshot'ı |
| `model_runs` | `experiment-service` | Durum, zamanlama, donanım ve seçilen model sürümü snapshot'ı |
| `fold_results` | `experiment-service` | Fold veya denek bazlı benchmark sonuçları |
| `sleep_epochs` | `experiment-service` | Epoch indeksi, tahmin, gerçek etiket ve güven skoru |
| `expert_annotations` | `experiment-service` | Uzman düzeltmeleri ve inceleme notları |
| `experiment_metrics` | `experiment-service` | Accuracy, Macro F1, N1 F1 ve ilgili metrikler |
| `outbox_events` | `experiment-service` | Redis'e güvenilir biçimde gönderilecek model çalışma olayları |

Her servis kendi audit kaydını kendi schema'sında tutar.

Ham EDF içeriği veri tabanında binary olarak saklanmaz. PostgreSQL bunun yerine
object key, checksum, dosya boyutu ve content type bilgilerini saklar.

## Taslak REST API

Public route sahipliği:

| Route grubu | Servis |
| --- | --- |
| `/api/v1/auth/**` | `identity-service` |
| `/api/v1/recordings/**`, `/api/v1/papers/**`, `/api/v1/models/**`, `/api/v1/datasets/**`, `/api/v1/evaluation-protocols/**` | `catalog-service` |
| `/api/v1/experiments/**`, `/api/v1/model-runs/**`, `/api/v1/epochs/**` | `experiment-service` |

Kubernetes Ingress public path'leri değiştirmeden istekleri ilgili servise yönlendirir.

~~~http
POST   /api/v1/auth/register
POST   /api/v1/auth/login

POST   /api/v1/recordings
GET    /api/v1/recordings
GET    /api/v1/recordings/{recordingId}
DELETE /api/v1/recordings/{recordingId}

POST   /api/v1/papers
GET    /api/v1/papers

POST   /api/v1/models
GET    /api/v1/models
POST   /api/v1/models/{modelId}/versions
GET    /api/v1/models/{modelId}/versions

POST   /api/v1/datasets
GET    /api/v1/datasets
POST   /api/v1/evaluation-protocols
GET    /api/v1/evaluation-protocols

POST   /api/v1/experiments
GET    /api/v1/experiments/{experimentId}
GET    /api/v1/experiments/{experimentId}/runs
GET    /api/v1/experiments/{experimentId}/comparison
GET    /api/v1/experiments/{experimentId}/export?format=csv

GET    /api/v1/model-runs/{runId}/status
GET    /api/v1/model-runs/{runId}/epochs
GET    /api/v1/model-runs/{runId}/hypnogram
GET    /api/v1/model-runs/{runId}/metrics

PUT    /api/v1/epochs/{epochId}/annotation

POST   /internal/v1/model-runs/{runId}/results
~~~

Kontrollü benchmark isteği örneği:

~~~json
{
  "name": "Sleep-EDF-SC temel karşılaştırması",
  "mode": "CONTROLLED_BENCHMARK",
  "datasetVersionId": "2c28d4ca-5f7f-4e47-8be0-4ae904371326",
  "evaluationProtocolId": "aa4c4c44-5e2b-48c0-b734-393c6e61f31e",
  "modelVersionIds": [
    "147fb95b-c972-44ca-9610-f6f66e17942c",
    "ae66f161-5287-49f8-a95b-d288a9f0089b"
  ]
}
~~~

Karşılaştırma yanıtı; accuracy, balanced accuracy, Macro F1, Cohen's kappa,
sınıf bazlı precision/recall/F1, N1 F1, confusion matrix'ler, çalışma süresi ve
değerlendirilen denek ve epoch sayılarını içerir. Temel benchmark doğru biçimde
çalıştıktan sonra istatistiksel testler eklenebilir.

API sözleşmesi OpenAPI ile belgelenir. Hata yanıtları code, message, timestamp,
path ve gerektiğinde doğrulama ayrıntılarını içeren tutarlı bir yapı kullanır.

## Planlanan repository yapısı

~~~text
sleepbench/
|-- services/
|   |-- identity-service/     # Kullanıcı, JWT ve roller
|   |-- catalog-service/      # Makale, kayıt, veri seti ve model kataloğu
|   `-- experiment-service/   # Deney, Kubernetes Job ve sonuç karşılaştırma
|-- contracts/
|   |-- model-runner/         # Sürümlenmiş çalıştırma tanımı ve sonuç şemaları
|   `-- service-api/          # OpenAPI sözleşmeleri ve üretilmiş client'lar
|-- models/                   # Replike edilen her makale için izole runner paketi
|   |-- deepsleepnet/
|   |-- seqsleepnet/
|   `-- ...
|-- shared-python/            # İsteğe bağlı sürümlenmiş yardımcı kodlar
|-- frontend/                 # Yalnızca son aşamada eklenecek
|-- infrastructure/
|   |-- docker/
|   |-- kubernetes/
|   `-- jenkins/
|-- http/                     # Manuel ve otomatik HTTP istekleri
|-- postman/                  # Collection ve environment dosyaları
|-- docs/                     # Mimari kararlar ve diyagramlar
|-- docker-compose.yml
|-- pom.xml                   # Yalnızca build aggregator; domain paylaşmaz
`-- README.md
~~~

Ortak Python koduna yalnızca açık biçimde sürümlenerek izin verilir. Bir runner,
başka bir model güncellendiğinde eski bir deneyi değiştirebilecek sürümlenmemiş
yerel modüllere bağımlı olmamalıdır.

Java servisleri ortak entity, repository veya domain service kütüphanesi
paylaşmaz. Monorepo üst Maven projesi yalnızca ortak plugin sürümlerini ve toplu
build işlemini yönetebilir. Servisler ayrı Docker image, migration seti ve
deployment kaynağına sahip olur.

## Test stratejisi

| Seviye | Araçlar | Temel amaç |
| --- | --- | --- |
| Birim | JUnit, Mockito, pytest | Her servisin iş kuralları ve izole dönüşümleri |
| Servis component | MockMvc | Servis güvenliği, doğrulama ve controller davranışları |
| Entegrasyon | Testcontainers | Her servisin kendi schema'sı, Redis ve depolama entegrasyonu |
| Model runner | pytest | Adapter, ön işleme, model uyumluluğu ve deterministik çıktı davranışı |
| Servis sözleşmesi | OpenAPI, JSON Schema ve stub server | Servisler arası request/response uyumluluğu |
| Runner sözleşmesi | JSON Schema | `experiment-service` ile bütün runner'ların uyumluluğu |
| Uçtan uca HTTP | Postman, Newman | Ingress üzerinden giriş, model seçimi, deney ve karşılaştırma |
| Deployment smoke | Newman veya curl | Deployment sonrasında sağlık ve kritik endpoint kontrolleri |

Testler harici production veri tabanına veya geliştiricinin yerel makine durumuna
bağlı olmamalıdır. Entegrasyon bağımlılıkları geçici container'larda oluşturulur.
Bir servisin testi, başka bir servisin gerçek instance'ına bağımlı olmak yerine
sürümlenmiş sözleşme stub'larını kullanır.

## CI/CD pipeline

Planlanan Jenkins pipeline aşağıdaki gibidir:

~~~text
Checkout
  -> Değişen servisleri ve model runner'ları belirleme
  -> Değişen Java servislerini paralel derleme ve test etme
  -> Model sürümü ve runner sözleşmelerini doğrulama
  -> Değişen model runner'larını paralel test etme
  -> Servisler arası contract testlerini çalıştırma
  -> Entegrasyon testlerini çalıştırma
  -> Değişen servis ve model runner image'larını oluşturma
  -> Image güvenlik açığı kontrolleri
  -> Image'ları gönderme ve değiştirilemez digest'leri kaydetme
  -> Değişen mikroservisleri bağımsız deploy etme
  -> Her servis için rollout ve health kontrolü
  -> Sabit CPU-only smoke model Job'ını çalıştırma
  -> HTTP karşılaştırma smoke testleri
~~~

Deployment kimlik bilgileri ve JWT secret'ları Jenkins Credentials ve Kubernetes
Secrets üzerinden sağlanır. Bu bilgiler hiçbir zaman Git'e gönderilmez.

CI/CD smoke testi kullanıcı tarafından seçilen herhangi bir modeli çalıştırmaz.
Pipeline'a özel, sürümü sabitlenmiş küçük bir `smoke-cpu` runner kullanılır. Bu
runner için `gpuRequired=false` zorunludur; küçük sentetik bir EEG fixture'ı,
düşük CPU/bellek limiti ve kısa timeout kullanılır. Testin amacı bilimsel model
performansını ölçmek değil, aşağıdaki entegrasyon zincirini doğrulamaktır:

~~~text
Ingress -> experiment-service -> Redis -> Kubernetes Job -> result callback -> PostgreSQL -> HTTP API
~~~

GPU gerektiren bir model CI smoke aşamasında hiçbir zaman otomatik seçilmez. Bu
modellerin sözleşme ve adapter testleri CPU üzerinde mock veya küçük fixture ile
çalıştırılır; gerçek GPU eğitimi Colab çalışma paketi üzerinden sisteme alınır.

## Deployment ve maliyet stratejisi

Mikroservis geliştirme sırasında PostgreSQL, Redis ve MinIO gibi altyapı
bağımlılıkları Docker Compose ile hızlıca başlatılabilir. Aynı PostgreSQL instance
maliyeti azaltmak için paylaşılır; `identity`, `catalog` ve `experiment`
schema'ları ayrı database user'lar ile izole edilir. Bir servis başka servisin
schema'sına erişemez.

Bir modelin çalıştırıldığı bütün uçtan uca yerel senaryolarda Minikube zorunludur;
mikroservisler ayrı Deployment/Service kaynakları, model ise Kubernetes Job olarak
çalışır. Ağır model eğitimleri Google Colab'da gerçekleştirilecektir. AWS yalnızca
CPU inference ve portföy demosu için kullanılacaktır.

İlk bulut ortamında k3s kurulu tek bir EC2 instance kullanılacaktır. Bu seçim
uygulamanın ölçek gereksinimi değil, portföy hedefidir. EKS, RDS,
NAT Gateway, GPU instance ve ayrı load balancer kullanılmayacaktır. Jenkins yerel
olarak çalışıp EC2 üzerindeki k3s cluster'ına deployment yapabilir. Kullanılabilir
AWS kredilerini korumak için instance demo dışında kapatılır.

k3s ortamında her Spring Boot servisine JVM bellek limiti verilir ve varsayılan
olarak aynı anda yalnızca bir model Job'ına izin verilir. Kayıtlı modellerin
tamamı önceden yüklenmez. Model sürümü kaydında node'un sağladığından daha fazla
bellek veya GPU isteyen bir runner, node'u kararsız hâle getirmek yerine
planlamadan önce reddedilir.

Bu tek node'lu tasarım bir portföy ortamı için uygundur; production seviyesinde
tıbbi bir sistem için uygun değildir.

### Yerel kaynak bütçesi

Minikube ve EC2 kaynakları tahminle değil ölçümle belirlenecektir. Tam sistem
kaynak bütçesi aşağıdaki bileşenlerin ölçülen tepe kullanımlarını içerecektir:

~~~text
Minikube ve Kubernetes sistem pod'ları
  + identity-service JVM
  + catalog-service JVM
  + experiment-service JVM
  + PostgreSQL
  + Redis
  + MinIO
  + aynı anda çalışan en büyük CPU model Job'ı
  + güvenlik marjı
~~~

Her Spring Boot servisi için `-Xms`/`-Xmx` değerleri ve Kubernetes memory
request/limit değerleri birlikte belirlenecektir. PostgreSQL, Redis, MinIO ve model
runner için de request/limit tanımlanacaktır. Tek model concurrency sınırı
korunacak; tam sistem soak testi sırasında OOM, pod eviction, restart ve CPU
throttling gözlemlenecektir.

Bulut demosuna geçmeden önce aynı request/limit değerleri yerel Minikube'da
ölçülüp doğrulanır; k3s'e tahmini değerlerle deploy edilmez. EC2 instance tipi,
ölçülen toplam tepe kullanım ve güvenlik marjı hesaplandıktan sonra seçilir; belirli
bir instance tipi ölçüm yapılmadan varsayılmaz.

## Geliştirme yol haritası

### Aşama 0 - Temel hazırlık

- [ ] IntelliJ başlangıç projesini mikroservis monorepo yapısıyla değiştirmek
- [ ] `identity-service`, `catalog-service` ve `experiment-service` Maven modüllerini oluşturmak
- [ ] Contract dizinlerini ve model runner şablonunu oluşturmak
- [ ] Ortak Maven plugin sürümlerini ve kod kalitesi kurallarını eklemek
- [ ] PostgreSQL, Redis ve MinIO'yu Docker Compose'a eklemek
- [ ] Ortam yapılandırmasını ve secret yönetimini tanımlamak

### Aşama 1 - Mikroservis temelleri

- [ ] Her servis için bağımsız Spring Boot uygulaması ve Dockerfile oluşturmak
- [ ] Ayrı schema, database user ve Flyway migration setleri tanımlamak
- [ ] Ortak hata formatını sözleşme düzeyinde belirlemek
- [ ] Her servis için OpenAPI spesifikasyonu üretmek
- [ ] Health, readiness ve liveness endpoint'lerini eklemek
- [ ] Correlation ID, timeout ve servis loglama kurallarını eklemek

### Aşama 2 - Identity service

- [ ] Kullanıcı, rol ve refresh token domain'ini geliştirmek
- [ ] Kayıt ve giriş endpoint'lerini geliştirmek
- [ ] JWT access token üretmek ve doğrulamak
- [ ] Asimetrik signing key ve JWKS endpoint'ini yapılandırmak
- [ ] `USER`, `RESEARCHER` ve `ADMIN` rollerini eklemek
- [ ] Diğer servisler için JWT doğrulama yapılandırmasını sağlamak
- [ ] Yetkilendirme ve güvenlik testlerini eklemek

### Aşama 3 - Catalog service

- [ ] Güvenli EDF yükleme işlemini geliştirmek
- [ ] Dosya checksum'larını hesaplamak ve saklamak
- [ ] Makaleleri ve replikasyon notlarını kaydetmek
- [ ] Veri setlerini, değiştirilemez sürümlerini ve denek manifestlerini kaydetmek
- [ ] Sürümlenmiş değerlendirme protokollerini tanımlamak

### Aşama 4 - Model kataloğu ve izole runner'lar

- [ ] Çalıştırma tanımı ve sonuç sözleşmelerini tanımlayıp sürümlemek
- [ ] Model sürümü kayıt alanlarını ve doğrulama kurallarını tanımlamak
- [ ] Model implementasyonlarını ve değiştirilemez sürümlerini kaydetmek
- [ ] İzole bağımlılıklara sahip bir model runner şablonu oluşturmak
- [ ] Yeniden kullanılabilen ön işleme hatlarını adlandırmak ve sürümlemek
- [ ] İlk iki replike modeli ayrı Docker image'ları olarak paketlemek
- [ ] Image ve artifact'ları değiştirilemez model sürümü kimliğiyle çözümlemek
- [ ] Runner sözleşmesi ve uyumluluk testlerini eklemek

### Aşama 5 - Deney ve benchmark motoru

- [ ] `experiment-service` domain ve persistence katmanlarını geliştirmek
- [ ] `catalog-service` için sürümlenmiş REST client ve contract testleri eklemek
- [ ] Her deney için bir veya daha fazla model sürümü seçmek
- [ ] Model, girdi ve protokol uyumluluğunu doğrulamak
- [ ] Katalog verilerini değiştirilemez deney snapshot'ı olarak saklamak
- [ ] Model çalışması durum makinesini oluşturmak
- [ ] Transactional outbox ve Redis publisher geliştirmek
- [ ] Redis Streams producer ve consumer group'larını eklemek
- [ ] Kubernetes Job oluşturma ve takip bileşenini geliştirmek
- [ ] Her model çalışmasını kaynak limitli Kubernetes Job olarak dağıtmak
- [ ] Yapılandırılabilir CPU, GPU, bellek ve eş zamanlılık politikalarını uygulamak
- [ ] Acknowledgement ve yeniden deneme yönetimini geliştirmek
- [ ] Tekrarlanan mesajları güvenli biçimde işlemek
- [ ] Fold, toplu, sınıf bazlı ve N1 metriklerini hesaplamak
- [ ] Karşılaştırma ve CSV/JSON dışa aktarma endpoint'lerini sunmak
- [ ] Colab'da çalıştırılmış deney paketlerini içe aktarmak

### Aşama 6 - Servis entegrasyonu ve otomatik test

- [ ] Kubernetes Ingress ile public route'ları tanımlamak
- [ ] Ingress üzerinden JWT ve routing testlerini eklemek
- [ ] Her mikroservisin birim ve component testlerini tamamlamak
- [ ] Servis başına Testcontainers entegrasyon testlerini eklemek
- [ ] Servisler arası OpenAPI contract testlerini eklemek
- [ ] Correlation ID, timeout, retry ve idempotency senaryolarını test etmek
- [ ] Python testlerini tamamlamak
- [ ] Postman collection ve Newman iş akışını eklemek
- [ ] Uçtan uca çoklu model benchmark ve karşılaştırma testi eklemek

### Aşama 7 - DevOps ve bulut deployment

- [ ] Optimize edilmiş Docker image'ları oluşturmak
- [ ] Her mikroservis için Kubernetes Deployment, Service, ConfigMap ve Secret kaynaklarını eklemek
- [ ] Minikube üzerinde yerel deployment yapmak
- [ ] Değişen servisleri bağımsız işleyen Jenkins pipeline'ını oluşturmak
- [ ] Sabit, CPU-only `smoke-cpu` runner ve sentetik test fixture'ı eklemek
- [ ] Üç JVM, altyapı servisleri, Kubernetes overhead'i ve en büyük CPU runner için kaynak bütçesi çıkarmak
- [ ] JVM heap değerlerini ve bütün pod request/limit değerlerini ölçüme göre ayarlamak
- [ ] Tek model Job'ı çalışırken Minikube üzerinde tam sistem soak ve OOM dayanıklılık testi yapmak
- [ ] Ölçülen tepe kullanım ve güvenlik marjına göre EC2 instance tipini seçmek
- [ ] Amazon EC2 üzerindeki k3s ortamına deployment yapmak
- [ ] Deployment sonrası otomatik smoke testlerini çalıştırmak

### Aşama 8 - Kullanıcı arayüzü

- [ ] Nuxt, Vue ve TypeScript uygulamasını oluşturmak
- [ ] Kimlik doğrulama ve kayıt yükleme işlevlerini geliştirmek
- [ ] İş ilerlemesini ve hataları göstermek
- [ ] Makale ve model adına göre model sürümlerini seçmek
- [ ] Seçilen modelleri tablo ve grafiklerle karşılaştırmak
- [ ] Hipnogramları ve güven skorlarını görselleştirmek
- [ ] N1 odaklı inceleme ve uzman anotasyonu ekranlarını eklemek

## MVP tamamlanma kriterleri

Backend-first MVP; `identity-service`, `catalog-service` ve `experiment-service`
servislerinin bağımsız build ve deployment edilebilmesiyle başlar. Servisler kendi
schema'larına sahip olmalı, birbirlerinin tablolarına erişmemeli ve sürümlenmiş API
sözleşmeleriyle iletişim kurmalıdır.

Kimliği doğrulanmış bir araştırmacı en az iki replike makale modelini kaydedebilmeli,
değiştirilemez sürümlerini seçebilmeli, ikisini aynı etiketli veri seti ve
değerlendirme protokolü üzerinde çalıştırabilmeli ve yan yana metrik
karşılaştırmasını Ingress üzerinden alabilmelidir. İş akışı modelleri ayrı runner
container'larında çalıştırmalı, gerekli tekrarlanabilirlik bilgilerini korumalı,
servis ve contract testlerinden geçmeli ve grafiksel kullanıcı arayüzü olmadan
Jenkins pipeline üzerinden Minikube'da çalışmalıdır.

## Dil kuralı

Bu README Türkçe yazılır. Kaynak kod, package ve class adları, API endpoint'leri,
JSON alanları, veri tabanı tanımlayıcıları, commit mesajları, loglar, hata kodları,
test adları ve kullanıcı arayüzü metinleri İngilizce olacaktır.
