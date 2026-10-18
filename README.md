# Programare Web cu Java – Laboratorul 3

## Spring Core – Bean Scopes, Lazy Instantiation & Aspects

Acest repository conține exercițiile practice pentru laboratorul 3 de Programare Web cu Java.

**Obiectivele laboratorului:**
- Înțelegerea diferenței dintre bean-urile **singleton** și **prototype**.
- Evitarea capcanei „prototype injectat într-un singleton”.
- Înțelegerea diferenței dintre instanțierea **eager** și **lazy** (`@Lazy`).
- Înțelegerea conceptelor de bază din AOP: **target object, aspect, advice, pointcut, weaving**.
- Scrierea primului aspect cu `@Aspect`, `@EnableAspectJAutoProxy` și `@Around`.
- Folosirea celor 5 tipuri de advice: `@Before`, `@AfterReturning`, `@AfterThrowing`, `@After`, `@Around`.

## Cerințe

- Java JDK 21
- IntelliJ IDEA
- Maven (integrat în IntelliJ IDEA)
- Spring Context și AspectJ Weaver (gestionate prin Maven)

## Pornirea proiectului

1. Clonați repository-ul.
2. Deschideți proiectul în IntelliJ IDEA.
3. Selectați JDK 21 dacă IntelliJ solicită configurarea SDK-ului.
4. Așteptați încărcarea dependențelor Maven din `pom.xml`.
5. Deschideți clasa `Main.java` din exercițiul dorit și rulați metoda `main()`.

Fiecare exercițiu are propriul pachet (`exercise1` … `exercise6`) și propriul punct de pornire.

---

## Partea I – Bean Scopes

| Scope | Câte instanțe? | Când se folosește |
|---|---|---|
| `singleton` (implicit) | **una** pentru fiecare definiție de bean, în tot contextul Spring | bean-uri **imutabile**, partajate de toată aplicația |
| `prototype` | **una nouă la fiecare cerere** a bean-ului | bean-uri care pot avea **stare (mutabile)** |

Există și scope-uri pentru aplicații web (`request`, `session`, `application`). Le vom folosi mai târziu, când vom lucra cu Spring Web.

## Exercițiul 1 – Singleton scope

**Scop:** observarea faptului că Spring creează o singură instanță pentru fiecare bean singleton.

**Cerințe:**
1. În `Main`, recuperați `ProductRepository` din context de două ori, în două variabile diferite.
2. Afișați `hashCode()` pentru ambele obiecte și comparați-le cu `==`.
3. În `AppConfig`, definiți al doilea bean `PriceFormatter`, numit `eurFormatter`, pentru moneda `"EUR"`.
4. Recuperați după nume `ronFormatter` (de două ori) și `eurFormatter`. Comparați `ron1 == ron2` și `ron1 == eur`.

**Întrebări:**
- Câte obiecte `ProductRepository` există în aplicație?
- Avem două bean-uri de tipul `PriceFormatter`. Spring a creat unul sau două obiecte? Ce înseamnă, de fapt, „singleton” în Spring?
- De ce e în regulă ca `ProductRepository` să fie partajat de toată aplicația?

## Exercițiul 2 – Prototype scope

**Scop:** observarea problemei unui bean mutabil partajat și rezolvarea ei cu scope-ul prototype.

**Cerințe:**
1. Rulați `Main` fără să modificați nimic. Ana și Ion sunt doi clienți diferiți. Ce are fiecare în coș?
2. Adăugați pe `ShoppingCart` adnotarea `@Scope(BeanDefinition.SCOPE_PROTOTYPE)`.
3. Rulați din nou și comparați rezultatul.

**Întrebări:**
- De ce era greșit ca `ShoppingCart` să fie singleton?
- `@Scope("prototype")` și `@Scope(BeanDefinition.SCOPE_PROTOTYPE)` fac același lucru. De ce e preferabilă a doua variantă?

## Exercițiul 3 – Capcana: prototype injectat într-un singleton

**Scop:** înțelegerea faptului că un bean prototype injectat într-un singleton este creat **o singură dată**.

**Cerințe:**
1. `ShoppingCart` este deja prototype. Rulați `Main`. Primește fiecare client propriul coș? Uitați-vă la numărul coșului.
2. În `CheckoutService`, înlocuiți câmpul `ShoppingCart` cu `ObjectProvider<ShoppingCart>` și apelați `getObject()` **în interiorul** metodei `placeOrder()`.
3. Rulați din nou. Fiecare comandă trebuie să aibă propriul coș.

**Întrebări:**
- De ce `@Scope(BeanDefinition.SCOPE_PROTOTYPE)` nu a fost suficient?
- Când are loc injectarea prin constructor în `CheckoutService`? De câte ori?

---

## Partea a II-a – Eager versus Lazy Instantiation

| | Eager (implicit) | Lazy (`@Lazy`) |
|---|---|---|
| Când se creează bean-ul | la crearea contextului | prima dată când este cerut |
| Erori de creare | apar imediat, la pornire (**fail-fast**) | apar abia când bean-ul este folosit |
| Avantaj | performanță mai bună la rulare, erorile se văd devreme | nu se creează bean-uri inutile pentru anumite părți ale aplicației |

## Exercițiul 4 – Eager vs Lazy

**Scop:** observarea momentului în care Spring creează bean-urile.

**Cerințe:**
1. Rulați `Main`. `PdfReportGenerator` este creat înainte sau după mesajul `2. Context created`? Cât durează crearea contextului?
2. Adăugați `@Lazy` pe `PdfReportGenerator`. Rulați din nou și comparați ordinea mesajelor și timpul.
3. Înregistrați `PaymentGatewayClient` drept componentă Spring (`@Component`). Rulați. Ce se întâmplă?
4. Adăugați `@Lazy` și pe `PaymentGatewayClient`. Rulați. Mai apare eroarea?
5. În `Main`, cereți bean-ul `PaymentGatewayClient` și apelați `charge(100)`, prinzând `BeansException`. Când apare acum eroarea?

**Întrebări:**
- La pasul 4, de ce a „dispărut” eroarea? A dispărut cu adevărat?
- Pentru `PaymentGatewayClient`, care variantă e mai sigură: eager sau lazy? De ce?

---

## Partea a III-a – Aspects (AOP)

AOP (Aspect Oriented Programming) este un mod prin care framework-ul **interceptează apelurile de metode** și poate modifica execuția lor. Îl folosim pentru logica care se repetă în multe locuri (**cross-cutting concerns**): logging, tranzacții, securitate, caching.

| Concept | Ce înseamnă |
|---|---|
| **Target object** | obiectul ale cărui metode vrem să le îmbogățim cu funcționalitate |
| **Aspect** | logica pe care vrem ca Spring să o execute când sunt apelate anumite metode |
| **Advice** | *când* se execută logica (înainte, după apel etc.) |
| **Pointcut** | *ce* metode interceptează Spring |
| **Weaving** | Spring creează un **proxy** în jurul target object-ului, care preia apelurile și aplică aspectul |

**Pașii pentru un aspect:**
1. `@EnableAspectJAutoProxy` pe clasa de configurare.
2. `@Aspect` pe o clasă nouă, înregistrată ca bean în contextul Spring.
3. O metodă cu o adnotare de advice, care spune *când* și *ce* metode să fie interceptate.
4. Logica aspectului.

**Anatomia unui pointcut:**

```
execution(*  ro.example.services  .*  .*  (..))
          │          │             │   │    └── orice parametri
          │          │             │   └─────── orice nume de metodă
          │          │             └─────────── orice clasă
          │          └───────────────────────── din pachetul services
          └──────────────────────────────────── orice tip returnat
```

## Exercițiul 5 – Primul aspect

**Scop:** adăugarea de logging pentru toate metodele din pachetul `service`, fără să modificăm clasa `OrderService`.

**Înainte de orice modificare:** rulați `Main` și notați numele clasei bean-ului (`Bean class: ...`).

**Cerințe:**
1. Adnotați clasa `LoggingAspect` cu `@Aspect` și `@Component`.
2. Scrieți în `LoggingAspect` o metodă `@Around("execution(* ro.example.lab3.exercise5.service.*.*(..))")`. Metoda afișează numele metodei interceptate și argumentele, apelează `joinPoint.proceed()`, apoi afișează valoarea returnată și durata.
3. Activați mecanismul de aspecte cu `@EnableAspectJAutoProxy` în `AppConfig`.
4. Rulați din nou. Comparați numele clasei bean-ului cu cel notat la început.
5. Modificați pointcut-ul astfel încât să fie interceptată **doar** metoda `placeOrder`.

**Întrebări:**
- Ce s-a schimbat la numele clasei bean-ului? Ce este, de fapt, obiectul primit din context?
- Este interceptată metoda `InvoiceNumberGenerator.next()`? De ce?
- Ce se întâmplă dacă uitați să apelați `joinPoint.proceed()`?

## Exercițiul 6 – Tipurile de advice

**Scop:** observarea momentului în care se execută fiecare tip de advice.

| Advice | Când se execută |
|---|---|
| `@Before` | înainte de metoda interceptată |
| `@AfterReturning` | după ce metoda se termină **cu succes** (nu se execută dacă metoda aruncă excepție) |
| `@AfterThrowing` | doar dacă metoda **aruncă o excepție** |
| `@After` | **întotdeauna** după metodă, cu succes sau cu excepție |
| `@Around` | înainte, după sau **în locul** metodei interceptate |

**Cerințe:**
1. **Înainte de a scrie cod**, citiți `Main` și notați în ce ordine credeți că vor apărea mesajele pentru fiecare plată.
2. În `PaymentAspect`, implementați advice-urile `@Before`, `@AfterReturning`, `@AfterThrowing` și `@After` (TODO 1–4).
3. Rulați și comparați cu ce ați notat.
4. **Bonus (TODO 5):** implementați un advice `@Around` care refuză plățile de peste 10 000 RON **fără** să apeleze `PaymentService`.

**Întrebări:**
- Pentru plata invalidă, care advice-uri s-au executat și care nu?
- La bonus, s-a mai executat `@Before`? De ce?

---

## Observații

- Exercițiile conțin comentarii `TODO` care trebuie completate în timpul laboratorului.
- Nu este necesară o bază de date sau un server web.
- Pentru fiecare exercițiu se rulează clasa `Main` corespunzătoare.

## Bibliografie

- [Aspect Oriented Programming with Spring](https://docs.spring.io/spring-framework/reference/core/aop.html)
- Laurențiu Spilcă – *Spring Start Here* și playlist-ul de Spring de pe YouTube
- Craig Walls – *Spring in Action*
- Ramnivas Laddad – *AspectJ in Action*
