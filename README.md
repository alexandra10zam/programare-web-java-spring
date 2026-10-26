# Programare Web cu Java

Acest repository conține exercițiile practice pentru laboratoarele de **Programare Web cu Java**, folosind Java, Maven și Spring Framework.

Fiecare laborator este organizat într-un folder separat și reprezintă un proiect Maven independent, cu propriile dependențe și exerciții.

## Structura repository-ului

```text
programare-web-java-spring/
├── lab2/
│   ├── pom.xml
│   └── src/
│       └── main/java/ro/example/lab2/
│           ├── exercise1/
│           ├── exercise2/
│           ├── exercise3/
│           ├── exercise4/
│           ├── exercise5/
│           └── demo/
├── lab3/
│   ├── pom.xml
│   └── src/main/
│       ├── java/ro/example/lab3/
│       │   ├── controller/
│       │   ├── model/
│       │   ├── scope/
│       │   └── service/
│       └── resources/templates/
├── README.md
└── .gitignore
```

**Laboratoare:**
- **Laboratorul 2:** Spring Core – Bean Scopes, Lazy Initialization & AOP
- **Laboratorul 3:** Spring Boot & Spring MVC

## Cerințe generale

- Java JDK 21
- IntelliJ IDEA
- Maven (integrat în IntelliJ IDEA)
- Git (opțional, pentru clonarea repository-ului)

Dependențele Spring sunt gestionate automat prin Maven, conform fișierului `pom.xml` al fiecărui laborator.

## Cum pornim proiectul

1. Clonați repository-ul:

   ```bash
   git clone https://github.com/alexandra10zam/programare-web-java-spring.git
   ```

   Alternativ, puteți descărca proiectul folosind **Code → Download ZIP** din GitHub.

2. Deschideți IntelliJ IDEA.
3. Selectați **File → Open** și alegeți folderul laboratorului dorit, de exemplu `lab2` sau `lab3`.
4. Verificați că proiectul utilizează **JDK 21**.
5. Așteptați importarea proiectului Maven și descărcarea dependențelor din `pom.xml`-ul laboratorului.
6. Alegeți exercițiul din lista de configurații de lângă butonul **Run** (sus, în dreapta): `Ex1 - Singleton vs Prototype` … `Ex5 - Tipurile de advice`, apoi apăsați **Run**.
   Alternativ, deschideți clasa `Main.java` din exercițiul dorit și apăsați triunghiul verde de lângă metoda `main()`.

**Important:** Fiecare exercițiu are propria clasă `Main.java` și poate fi rulat separat.

---

# Laboratorul 2 – Spring Core: Bean Scopes, Lazy Initialization & AOP

**Durată:** 70 de minute.

În acest laborator lucrăm la **Cinema FMI**, o aplicație de rezervare a biletelor la film. Exercițiile se leagă între ele: fiecare pornește din starea în care s-a terminat exercițiul anterior și rezolvă problema descoperită acolo. Fiecare pachet `exerciseN` conține deja codul de la finalul exercițiului precedent, deci poate fi rulat separat.

Mesajele `[Spring] ... created` din constructori arată **când** creează Spring fiecare obiect: urmăriți-le la fiecare rulare.

## Obiective

La finalul laboratorului, studenții ar trebui să poată:

- Explica diferența dintre bean-urile **singleton** și **prototype**.
- Obține bean-uri prototype dintr-un singleton folosind `ObjectProvider`.
- Explica diferența dintre inițializarea **eager** și **lazy** (`@Lazy`) și ce înseamnă **fail-fast**.
- Explica noțiunile de bază din AOP: **target object, aspect, advice, pointcut, weaving (proxy)**.
- Scrie un aspect cu `@Aspect`, `@EnableAspectJAutoProxy` și `@Around`.
- Folosi cele 5 tipuri de advice: `@Before`, `@AfterReturning`, `@AfterThrowing`, `@After`, `@Around`.

## Exercițiul 1 – Programul și rezervările (9 min)

**Scop:** Observarea diferenței dintre un bean singleton și unul prototype.

**Cerințe:**

1. Rulați `Main`. Există un `MovieCatalog` sau două? Ce bilete are fiecare client?
2. Adnotați clasa `Reservation` cu `@Scope(BeanDefinition.SCOPE_PROTOTYPE)`.
3. Rulați din nou și numărați mesajele `[Spring] Reservation created`.

**Concepte:** Singleton, Prototype, `@Scope`.

## Exercițiul 2 – Rezervările clienților (9 min)

**Scop:** Înțelegerea faptului că un bean prototype injectat într-un singleton este creat o singură dată.

**Cerințe:**

1. Rulați `Main`. Primește fiecare client propria rezervare? Uitați-vă la numerele rezervărilor și la sumele plătite.
2. În `BookingService`, înlocuiți câmpul `Reservation` cu `ObjectProvider<Reservation>` și cereți o rezervare nouă în metoda `book()`, cu `getObject()`.
3. Rulați din nou și verificați că fiecare client are propria rezervare.

**Concepte:** Prototype injectat în Singleton, `ObjectProvider`.

## Exercițiul 3 – Imprimanta de bilete (11 min)

**Scop:** Observarea momentului în care Spring creează bean-urile.

**Cerințe:**

1. Rulați `Main`. `TicketPrinter` este creat înainte sau după `2. Context created`? Cât durează pornirea?
2. Adnotați `TicketPrinter` cu `@Lazy` și rulați din nou. Mai este creat la pornire? De ce?
3. În `BookingService`, folosiți `ObjectProvider<TicketPrinter>`, la fel ca la exercițiul 2. Rulați și comparați.
4. Decomentați linia `throw` din constructorul `TicketPrinter`. Rulați cu `@Lazy`, apoi fără. Când apare eroarea? La final, comentați linia la loc.

**Concepte:** Eager vs. Lazy Initialization, `@Lazy`, Fail-fast.

## Exercițiul 4 – Auditul rezervărilor (12 min)

**Scop:** Adăugarea unui audit pentru toate metodele din `BookingService`, fără să modificăm clasa.

**Cerințe:**

1. Rulați `Main` înainte de orice modificare și notați numele clasei bean-ului (`Bean class: ...`).
2. Adnotați clasa `AuditAspect` cu `@Aspect` și `@Component`.
3. Adnotați metoda `audit` cu `@Around` și pointcut-ul din comentariu.
4. Înlocuiți `Object result = null;` cu `Object result = joinPoint.proceed();`.
5. Activați aspectele cu `@EnableAspectJAutoProxy` în `AppConfig`, rulați și comparați numele clasei bean-ului.

**Concepte:** Aspect, Advice, Pointcut, Proxy, `@Around`, `proceed()`.

## Exercițiul 5 – Verificarea plăților (11 min)

**Scop:** Observarea momentului în care se execută fiecare tip de advice.

**Cerințe:**

1. Citiți `Main` și notați în ce ordine credeți că vor apărea mesajele, înainte să scrieți cod.
2. Adnotați clasa `PaymentAspect` cu `@Aspect` și `@Component`.
3. Adăugați `@Before(PAY)` pe metoda `beforePayment`.
4. Adăugați `@AfterReturning` pe metoda `afterSuccessfulPayment`.
5. Adăugați `@AfterThrowing` pe metoda `afterFailedPayment`.
6. Adăugați `@After` pe metoda `afterPayment` și rulați.
7. **Bonus:** Adnotați `limitPayment` cu `@Around` și refuzați rezervările de peste 200 RON fără să apelați `proceed()`.

**Concepte:** `@Before`, `@AfterReturning`, `@AfterThrowing`, `@After`, `@Around`.

## Observații

- Exercițiile conțin comentarii `TODO` care trebuie completate în timpul laboratorului.
- Nu este necesară o bază de date sau un server web.
- Fiecare exercițiu are o clasă `Main.java` separată.
- Pachetul `demo` conține exemplele scurte prezentate la începutul fiecărei părți (`DemoScopes`, `DemoLazy`, `DemoAop`).

---

# Laboratorul 3 – Spring Boot & Spring MVC

**Durată:** 70 de minute.

Continuăm aplicația **Cinema FMI**, de data aceasta ca aplicație web: o pagină cu programul de filme, un formular de rezervare și o listă personală de filme. Exercițiile se leagă între ele și se rezolvă în aceeași aplicație Spring Boot.

## Pornirea aplicației

1. Deschideți folderul `lab3` în IntelliJ IDEA (**File → Open → lab3**) și așteptați importarea proiectului Maven.
2. Rulați configurația `Lab3 - Cinema FMI` (sau clasa `Lab3Application`).
3. În consolă trebuie să apară `Tomcat started on port 8080`.
4. Deschideți în browser [http://localhost:8080](http://localhost:8080).

**Important:** După fiecare modificare în cod, opriți aplicația și porniți-o din nou.

## Obiective

La finalul laboratorului, studenții ar trebui să poată:

- Explica ce aduce Spring Boot: **starters**, **autoconfigurare** și **convention over configuration**.
- Explica rolul adnotării `@SpringBootApplication`.
- Descrie drumul unei cereri HTTP prin Spring MVC: **DispatcherServlet → Controller → Model → View**.
- Scrie un `@Controller` cu `@GetMapping` și `@PostMapping`.
- Trimite date către un template Thymeleaf prin `Model` și primi date dintr-un formular cu `@ModelAttribute`.
- Explica scope-urile web: **request**, **session** și **application**.

## Exercițiul 1 – Programul de filme (20 min)

**Scop:** Afișarea filmelor într-o pagină web, folosind un Controller, Model-ul și un template Thymeleaf.

**Cerințe:**

1. Porniți aplicația și deschideți [http://localhost:8080/movies](http://localhost:8080/movies). Ce se afișează?
2. În `MovieController`, obțineți lista filmelor din `MovieService` (TODO 1.1).
3. Adăugați lista în `Model` cu numele `movies` (TODO 1.2).
4. În `templates/movies.html`, folosiți `th:each` pentru a parcurge lista (TODO 1.3).
5. Folosiți `th:text` pentru a afișa id, title, genre și price (TODO 1.4). Reporniți aplicația și verificați pagina.

**Concepte:** `@SpringBootApplication`, Starters, Autoconfigurare, `@Controller`, `@GetMapping`, `Model`, Thymeleaf.

## Exercițiul 2 – Rezervarea biletelor (20 min)

**Scop:** Primirea datelor dintr-un formular HTML.

**Cerințe:**

1. Deschideți [http://localhost:8080/bookings/new](http://localhost:8080/bookings/new), completați formularul și apăsați **Book**. Ce eroare apare?
2. În `BookingController`, adăugați `@PostMapping("/bookings")` pe metoda `createBooking` (TODO 2.1).
3. Adnotați parametrul `booking` cu `@ModelAttribute("booking")` (TODO 2.2).
4. Salvați rezervarea cu `bookingService.book(booking)` (TODO 2.3).
5. Adăugați rezervarea salvată în `Model` cu numele `booking` (TODO 2.4). Reporniți aplicația și faceți o rezervare.

**Concepte:** `@PostMapping`, `@ModelAttribute`, `th:object`, `th:field`.

## Exercițiul 3 – Lista mea de filme (20 min)

**Scop:** Observarea scope-urilor web: request, session și application.

**Cerințe:**

1. Deschideți [http://localhost:8080/watchlist](http://localhost:8080/watchlist) și adăugați două filme în listă. Reîncărcați pagina de câteva ori: se schimbă Request ID?
2. Deschideți aceeași pagină într-o fereastră **Incognito** (un alt utilizator). Ce listă vedeți?
3. Adnotați `RequestInfo` cu `@RequestScope` (TODO 3.1).
4. Adnotați `WatchList` cu `@SessionScope` (TODO 3.2).
5. Adnotați `VisitCounter` cu `@ApplicationScope` (TODO 3.3).
6. Reporniți aplicația și repetați pașii 1 și 2. Ce s-a schimbat?

**Concepte:** `@RequestScope`, `@SessionScope`, `@ApplicationScope`, scoped proxy.

## Observații

- Codul de completat este marcat cu `TODO x.y` (exercițiul `x`, pasul `y`), atât în clasele Java, cât și în template-urile HTML.
- Template-urile Thymeleaf se află în `src/main/resources/templates`.
- Nu este necesară o bază de date: datele sunt ținute în memorie, în clasele din pachetul `service`.
