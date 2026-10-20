# Cinema FMI – Spring Core: Bean Scopes, Lazy Instantiation & Aspects

În acest laborator lucrăm la **Cinema FMI**, o aplicație mică de rezervare a biletelor la film, fără bază de date și fără server web. Fiecare exercițiu pornește de la codul lăsat de exercițiul anterior și modifică **aceleași clase**: problema descoperită într-un exercițiu e rezolvată în următorul.

**Durată:** ~70 de minute.

| | Exercițiu | Concepte | Timp |
|---|---|---|---|
| | Pornire | | 5 min |
| 1 | Programul și rezervările | singleton, prototype | 12 min |
| 2 | Rezervările clienților | prototype injectat într-un singleton | 10 min |
| 3 | Imprimanta de bilete | eager vs lazy, fail-fast | 13 min |
| 4 | Auditul rezervărilor | aspect, pointcut, proxy, `@Around` | 15 min |
| 5 | Verificarea plăților | `@Before`, `@AfterReturning`, `@AfterThrowing`, `@After` | 12 min |

## Pornirea proiectului

1. Clonați repository-ul și deschideți **folderul `cinema-fmi`** în IntelliJ IDEA (*File → Open → cinema-fmi*).
2. Selectați JDK 21 dacă IntelliJ solicită configurarea SDK-ului.
3. Așteptați încărcarea dependențelor Maven din `pom.xml`.
4. Fiecare exercițiu are propria clasă de pornire în pachetul `ro.example.cinema.exercises`: `Exercise1` … `Exercise5`.

## Harta proiectului

```
ro.example.cinema
├── config       AppConfig                    configurarea Spring
├── model        Movie, Ticket, Reservation   filmul, biletul și rezervarea unui client
├── repository   MovieCatalog                 programul cinematografului
├── service      BookingService               face rezervările
│                PaymentService               încasează plățile
│                TicketPrinter                tipărește biletele (bean „greu”)
├── aspect       AuditAspect, PaymentAspect
└── exercises    Exercise1 … Exercise5        câte un main() pentru fiecare exercițiu

ro.example.demo                               exemplele arătate de laborant la începutul fiecărei părți
├── scopes       DemoScopes
├── lazy         DemoLazy
└── aop          DemoAop
```

```
BookingService ──► MovieCatalog
       ├─────────► Reservation
       ├─────────► PaymentService
       └─────────► TicketPrinter
```

Comentariile `TODO x.y` din cod corespund pașilor de mai jos (exercițiul `x`, pasul `y`). Mesajele `[Spring] ... created` din constructori arată **când** creează Spring fiecare obiect: urmăriți-le la fiecare rulare.

---

# Partea I – Bean Scopes

| Scope | Câte instanțe? | Când îl folosim |
|---|---|---|
| `singleton` (implicit) | **una** în tot contextul Spring | bean-uri **imutabile**, partajate de toată aplicația |
| `prototype` | **una nouă la fiecare cerere** a bean-ului | bean-uri care au **stare care se schimbă** |

Pentru aplicațiile web mai există scope-urile `request`, `session` și `application`.

## Exercițiul 1 – Programul și rezervările: singleton vs prototype

Cinematograful are un singur program de filme, care nu se schimbă. Fiecare client are însă **propria rezervare**.

- **1.1** Rulați `Exercise1`. Există un catalog sau două? Ce bilete are fiecare client?
- **1.2** Adăugați pe `Reservation` adnotarea `@Scope(BeanDefinition.SCOPE_PROTOTYPE)`.
- **1.3** Rulați din nou. Numărați mesajele `[Spring] Reservation created`.

**Întrebări:**
- De ce e în regulă ca `MovieCatalog` să fie singleton, dar `Reservation` nu?
- Ana și Ion au cerut două rezervări, dar mesajul `Reservation created` apare de **trei** ori. Cine a cerut prima rezervare, încă de la pornire?

## Exercițiul 2 – Rezervările clienților: prototype injectat într-un singleton

Răspunsul la ultima întrebare: prima rezervare a cerut-o `BookingService`, care e **singleton**.

- **2.1** Rulați `Exercise2`. Primește fiecare client propria rezervare? Uitați-vă la numerele rezervărilor și la sumele plătite.
- **2.2** În `BookingService`, înlocuiți câmpul `Reservation` cu `ObjectProvider<Reservation>` și cereți o rezervare nouă **în interiorul** lui `book()`, cu `reservationProvider.getObject()`.
- **2.3** Rulați din nou. Fiecare rezervare trebuie să fie separată.

**Întrebare:** de ce `@Scope(BeanDefinition.SCOPE_PROTOTYPE)` nu a fost suficient?

---

# Partea a II-a – Eager versus Lazy Instantiation

| | Eager (implicit) | Lazy (`@Lazy`) |
|---|---|---|
| Când se creează bean-ul | la crearea contextului | prima dată când este cerut |
| Erori la creare | apar imediat, la pornire (**fail-fast**) | apar abia când bean-ul este folosit |
| Avantaj | erorile se văd devreme | nu se creează bean-uri inutile pentru părți rar folosite |

## Exercițiul 3 – Imprimanta de bilete: eager vs lazy

Puțini clienți vor bilete pe hârtie, dar conectarea la imprimantă durează mult.

- **3.1** Rulați `Exercise3`. `TicketPrinter` e creat înainte sau după `2. Context created`? Cât durează pornirea?
- **3.2** Adăugați `@Lazy` pe `TicketPrinter`. Rulați din nou. Mai e creat la pornire? De ce?
- **3.3** În `BookingService`, folosiți `ObjectProvider<TicketPrinter>`, **la fel ca pentru rezervare la exercițiul 2**. Rulați și comparați ordinea mesajelor și timpul.
- **3.4** Decomentați linia `throw` din constructorul `TicketPrinter`. Rulați cu `@Lazy`, apoi fără. Când apare eroarea în fiecare caz? La final, comentați linia la loc.

---

# Partea a III-a – Aspects (AOP)

Vrem să **audităm** fiecare rezervare și să **verificăm** fiecare plată, **fără să modificăm** `BookingService` și `PaymentService`. Logging-ul, auditul, tranzacțiile sau securitatea apar peste tot într-o aplicație: sunt **cross-cutting concerns**.

| Concept | Ce înseamnă | În Cinema FMI |
|---|---|---|
| **Target object** | obiectul ale cărui metode vrem să le îmbogățim | `BookingService`, `PaymentService` |
| **Aspect** | logica executată când se apelează anumite metode | `AuditAspect`, `PaymentAspect` |
| **Advice** | *când* se execută logica | `@Around`, `@Before` ... |
| **Pointcut** | *ce* metode sunt interceptate | `execution(...)` |
| **Weaving** | Spring creează un **proxy** în jurul target object-ului | `BookingService$$SpringCGLIB$$0` |

```
execution(*  ro.example.cinema.service.BookingService  .*  (..))
          │                    │                        │    └── orice parametri
          │                    │                        └─────── orice nume de metodă
          │                    └──────────────────────────────── din clasa BookingService
          └───────────────────────────────────────────────────── orice tip returnat
```

## Exercițiul 4 – Auditul rezervărilor: primul aspect

**Înainte de orice modificare:** rulați `Exercise4` și notați numele clasei bean-ului (`Bean class: ...`).

- **4.1** Adnotați clasa `AuditAspect` cu `@Aspect` și `@Component`.
- **4.2** Adnotați metoda `audit` cu `@Around` și pointcut-ul din comentariu.
- **4.3** Înlocuiți `Object result = null;` cu apelul metodei reale: `Object result = joinPoint.proceed();`.
- **4.4** Activați aspectele cu `@EnableAspectJAutoProxy` în `AppConfig`, apoi rulați.

**Întrebări:**
- Ce s-a schimbat la numele clasei bean-ului? Ce primim, de fapt, din context?
- Ce se întâmplă dacă nu apelăm `joinPoint.proceed()`?

## Exercițiul 5 – Verificarea plăților: tipurile de advice

| Advice | Când se execută |
|---|---|
| `@Before` | înainte de metoda interceptată |
| `@AfterReturning` | după ce metoda se termină **cu succes** |
| `@AfterThrowing` | doar dacă metoda **aruncă o excepție** |
| `@After` | **întotdeauna** după metodă, cu succes sau cu excepție |
| `@Around` | înainte, după sau **în locul** metodei interceptate |

**Înainte de a scrie cod:** citiți `Exercise5` și notați în ce ordine credeți că vor apărea mesajele. Liniile `[AUDIT]` vin de la aspectul din exercițiul 4.

- **5.1** Adnotați `PaymentAspect` cu `@Aspect` și `@Component`.
- **5.2 – 5.5** Puneți pe cele 4 metode adnotările din comentarii. Rulați și comparați cu ce ați notat.
- **5.6 (bonus)** Adnotați `limitPayment` cu `@Around` și refuzați rezervările de grup de peste 200 RON **fără** să apelați `proceed()`.

**Întrebări:**
- Pentru rezervarea fără locuri, care advice-uri s-au executat și care nu?
- De ce `[AUDIT]` apare înainte de `[BEFORE]` și după `[AFTER]`?

---

## Bibliografie

- [Aspect Oriented Programming with Spring](https://docs.spring.io/spring-framework/reference/core/aop.html)
- Laurențiu Spilcă – playlist-ul de Spring de pe YouTube și *Spring Start Here*
- Craig Walls – *Spring in Action*
