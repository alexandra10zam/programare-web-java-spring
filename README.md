# FMI Shop – Spring Core: Bean Scopes, Lazy Instantiation & Aspects

În acest laborator lucrăm la **FMI Shop**, un mic magazin online, fără bază de date și fără server web. Fiecare exercițiu pornește de la codul lăsat de exercițiul anterior și modifică **aceleași clase**: problema descoperită într-un exercițiu e rezolvată în următorul.

**Durată:** ~70 de minute.

| | Exercițiu | Concepte | Timp |
|---|---|---|---|
| | Setup | | 5 min |
| 1 | Catalogul și coșul | singleton, prototype | 10 min |
| 2 | Comenzile | prototype injectat într-un singleton | 10 min |
| 3 | Facturile | eager vs lazy, fail-fast | 15 min |
| 4 | Logging pentru comenzi | aspect, pointcut, proxy, `@Around` | 15 min |
| 5 | Verificarea plăților | `@Before`, `@AfterReturning`, `@AfterThrowing`, `@After` | 15 min |

## Cerințe

- Java JDK 21
- IntelliJ IDEA
- Maven (integrat în IntelliJ IDEA)

## Pornirea proiectului

1. Clonați repository-ul și deschideți proiectul în IntelliJ IDEA.
2. Selectați JDK 21 dacă IntelliJ solicită configurarea SDK-ului.
3. Așteptați încărcarea dependențelor Maven din `pom.xml`.
4. Fiecare exercițiu are propria clasă de pornire în pachetul `exercises`: `Exercise1` … `Exercise5`.

## Harta proiectului

```
ro.example.fmishop
├── config       AppConfig                  configurarea Spring
├── model        Product, ShoppingCart      produsul și coșul de cumpărături
├── repository   ProductCatalog             catalogul de produse
├── service      OrderService               plasează comenzile
│                PaymentService             încasează plățile
│                InvoicePdfGenerator        generează facturi (bean „greu”)
├── aspect       LoggingAspect, PaymentAspect
└── exercises    Exercise1 … Exercise5      câte un main() pentru fiecare exercițiu
```

```
OrderService ──► ProductCatalog
     ├─────────► ShoppingCart
     ├─────────► PaymentService
     └─────────► InvoicePdfGenerator
```

Comentariile `TODO x.y` din cod corespund pașilor de mai jos (exercițiul `x`, pasul `y`). Mesajele `[Spring] ... created` din constructori arată **când** creează Spring fiecare obiect: urmăriți-le la fiecare rulare.

---

# Partea I – Bean Scopes

| Scope | Câte instanțe? | Când îl folosim |
|---|---|---|
| `singleton` (implicit) | **una** în tot contextul Spring | bean-uri **imutabile**, partajate de toată aplicația |
| `prototype` | **una nouă la fiecare cerere** a bean-ului | bean-uri care au **stare care se schimbă** |

Pentru aplicațiile web mai există scope-urile `request`, `session` și `application`.

## Exercițiul 1 – Catalogul și coșul: singleton vs prototype

Magazinul are un singur catalog, care nu se schimbă. Fiecare client are însă **propriul coș**.

- **1.1** Rulați `Exercise1`. Există un catalog sau două? Ce are fiecare client în coș?
- **1.2** Adăugați pe `ShoppingCart` adnotarea `@Scope(BeanDefinition.SCOPE_PROTOTYPE)`.
- **1.3** Rulați din nou. Numărați mesajele `[Spring] ShoppingCart created`.

**Întrebări:**
- De ce e în regulă ca `ProductCatalog` să fie singleton, dar `ShoppingCart` nu?
- Ana și Ion au cerut două coșuri, dar mesajul `ShoppingCart created` apare de **trei** ori. Cine a cerut primul coș, încă de la pornire?

## Exercițiul 2 – Comenzile: prototype injectat într-un singleton

Răspunsul la ultima întrebare: primul coș l-a cerut `OrderService`, care e **singleton**.

- **2.1** Rulați `Exercise2`. Primește fiecare client propriul coș? Uitați-vă la numerele coșurilor și la sumele plătite.
- **2.2** În `OrderService`, înlocuiți câmpul `ShoppingCart` cu `ObjectProvider<ShoppingCart>` și cereți un coș nou **în interiorul** lui `checkout()`, cu `cartProvider.getObject()`.
- **2.3** Rulați din nou. Fiecare comandă trebuie să aibă propriul coș.

**Întrebare:** de ce `@Scope(BeanDefinition.SCOPE_PROTOTYPE)` nu a fost suficient? De câte ori are loc injectarea prin constructor într-un singleton?

---

# Partea a II-a – Eager versus Lazy Instantiation

| | Eager (implicit) | Lazy (`@Lazy`) |
|---|---|---|
| Când se creează bean-ul | la crearea contextului | prima dată când este cerut |
| Erori la creare | apar imediat, la pornire (**fail-fast**) | apar abia când bean-ul este folosit |
| Avantaj | erorile se văd devreme | nu se creează bean-uri inutile pentru părți rar folosite |

## Exercițiul 3 – Facturile: eager vs lazy

Puțini clienți cer factură, dar `InvoicePdfGenerator` durează mult să se creeze.

- **3.1** Rulați `Exercise3`. `InvoicePdfGenerator` e creat înainte sau după `2. Context created`? Cât durează pornirea?
- **3.2** Adăugați `@Lazy` pe `InvoicePdfGenerator`. Rulați din nou. Mai e creat la pornire? De ce?
- **3.3** În `OrderService`, folosiți `ObjectProvider<InvoicePdfGenerator>`, **la fel ca pentru coș la exercițiul 2**. Rulați și comparați ordinea mesajelor și timpul.
- **3.4** Decomentați linia `throw` din constructorul `InvoicePdfGenerator`. Rulați cu `@Lazy`, apoi fără. Când apare eroarea în fiecare caz? La final, comentați linia la loc.

**Întrebare:** pentru o eroare de configurare, ce e mai sigur: s-o descoperim la pornire sau la primul client care cere factură?

---

# Partea a III-a – Aspects (AOP)

Vrem să **logăm** fiecare comandă și să **verificăm** fiecare plată, **fără să modificăm** `OrderService` și `PaymentService`. Logging-ul, tranzacțiile sau securitatea apar în sute de locuri dintr-o aplicație: sunt **cross-cutting concerns**.

**AOP (Aspect Oriented Programming)** este un mod prin care framework-ul **interceptează apelurile de metode** și le poate modifica execuția.

| Concept | Ce înseamnă | În FMI Shop |
|---|---|---|
| **Target object** | obiectul ale cărui metode vrem să le îmbogățim | `OrderService`, `PaymentService` |
| **Aspect** | logica executată când se apelează anumite metode | `LoggingAspect`, `PaymentAspect` |
| **Advice** | *când* se execută logica | `@Around`, `@Before` ... |
| **Pointcut** | *ce* metode sunt interceptate | `execution(...)` |
| **Weaving** | Spring creează un **proxy** în jurul target object-ului, care preia apelurile și aplică aspectul | `OrderService$$SpringCGLIB$$0` |

**Pașii pentru un aspect:** `@EnableAspectJAutoProxy` pe configurare → `@Aspect` pe o clasă înregistrată ca bean → o metodă cu adnotare de advice → logica aspectului.

```
execution(*  ro.example.fmishop.service.OrderService  .*  (..))
          │                    │                       │    └── orice parametri
          │                    │                       └─────── orice nume de metodă
          │                    └─────────────────────────────── din clasa OrderService
          └──────────────────────────────────────────────────── orice tip returnat
```

## Exercițiul 4 – Logging pentru comenzi: primul aspect

**Înainte de orice modificare:** rulați `Exercise4` și notați numele clasei bean-ului (`Bean class: ...`).

- **4.1** Adnotați clasa `LoggingAspect` cu `@Aspect` și `@Component`.
- **4.2** Adnotați metoda `log` cu `@Around` și pointcut-ul din comentariu.
- **4.3** Înlocuiți `Object result = null;` cu apelul metodei reale: `Object result = joinPoint.proceed();`.
- **4.4** Activați aspectele cu `@EnableAspectJAutoProxy` în `AppConfig`, apoi rulați.

**Întrebări:**
- Ce s-a schimbat la numele clasei bean-ului? Ce primim, de fapt, din context?
- Ce se întâmplă dacă nu apelăm `joinPoint.proceed()`?
- Cum ar arăta pointcut-ul ca să intercepteze **toate** clasele din pachetul `service`?

## Exercițiul 5 – Verificarea plăților: tipurile de advice

| Advice | Când se execută |
|---|---|
| `@Before` | înainte de metoda interceptată |
| `@AfterReturning` | după ce metoda se termină **cu succes** |
| `@AfterThrowing` | doar dacă metoda **aruncă o excepție** |
| `@After` | **întotdeauna** după metodă, cu succes sau cu excepție |
| `@Around` | înainte, după sau **în locul** metodei interceptate |

**Înainte de a scrie cod:** citiți `Exercise5` și notați în ce ordine credeți că vor apărea mesajele. Liniile `[LOG]` vin de la aspectul din exercițiul 4.

- **5.1** Adnotați `PaymentAspect` cu `@Aspect` și `@Component`.
- **5.2 – 5.5** Puneți pe cele 4 metode adnotările din comentarii: `@Before`, `@AfterReturning`, `@AfterThrowing`, `@After`. Rulați și comparați cu ce ați notat.
- **5.6 (bonus)** Adnotați `limitPayment` cu `@Around` și refuzați plățile de peste 10 000 RON **fără** să apelați `proceed()`.

**Întrebări:**
- Pentru comanda cu coșul gol, care advice-uri s-au executat și care nu?
- De ce `[LOG]` apare înainte de `[BEFORE]` și după `[AFTER]`?

---

## Bibliografie

- [Aspect Oriented Programming with Spring](https://docs.spring.io/spring-framework/reference/core/aop.html)
- Laurențiu Spilcă – playlist-ul de Spring de pe YouTube și *Spring Start Here*
- Craig Walls – *Spring in Action*
- Ramnivas Laddad – *AspectJ in Action*
