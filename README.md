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
├── lab4/
│   ├── pom.xml
│   ├── requests.http
│   └── src/main/java/ro/example/lab4/
│       ├── controller/
│       ├── model/
│       └── service/
├── lab5/
│   ├── pom.xml
│   ├── requests.http
│   └── src/main/java/ro/example/lab5/
│       ├── controller/
│       ├── model/
│       └── service/
├── README.md
└── .gitignore
```

**Laboratoare:**
- **Laboratorul 2:** Spring Core – Bean Scopes, Lazy Initialization & AOP
- **Laboratorul 3:** Spring Boot & Spring MVC
- **Laboratorul 4:** REST Web Services
- **Laboratorul 5:** REST – Request Params, Request Body, Response & Headers

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
3. Selectați **File → Open** și alegeți folderul laboratorului dorit, de exemplu `lab4` sau `lab5`.
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

---

# Laboratorul 4 – REST Web Services

**Durată:** 70 de minute.

Construim **API-ul REST** al aplicației **Cinema FMI**: în loc de pagini HTML, serverul trimite și primește **JSON**. Lucrăm pe o singură resursă, filmele, la URI-ul `/api/movies`. Exercițiile se leagă între ele: la exercițiul 1 citim filmele, la exercițiul 2 le creăm, modificăm și ștergem, iar la exercițiul 3 corectăm codurile de status descoperite la primele două.

Tot codul de completat este în `controller/MovieRestController.java`. Clasa `MovieService` este deja scrisă.

## Pornirea aplicației

1. Deschideți folderul `lab4` în IntelliJ IDEA (**File → Open → lab4**) și așteptați importarea proiectului Maven.
2. Rulați configurația `Lab4 - Cinema FMI API` (sau clasa `Lab4Application`).
3. În consolă trebuie să apară `Tomcat started on port 8080`.
4. Cererile **GET** se pot deschide direct din browser, pornind de la [http://localhost:8080](http://localhost:8080).
5. Pentru **POST**, **PUT** și **DELETE**, deschideți fișierul `requests.http` și apăsați săgeata verde din stânga fiecărei cereri. Răspunsul (status, headere, body) apare în fereastra de jos.

**Important:** După fiecare modificare în cod, opriți aplicația și porniți-o din nou. Datele sunt ținute în memorie, deci la repornire revin cele 4 filme inițiale.

## Obiective

La finalul laboratorului, studenții ar trebui să poată:

- Explica noțiunile de **resursă**, **URI** și **reprezentare** (JSON).
- Scrie un `@RestController` și explica diferența față de `@Controller`.
- Proiecta URI-uri după bunele practici REST: `/api/...`, substantive la plural, identificatorul în cale.
- Folosi `@PathVariable`, `@RequestParam` și `@RequestBody`.
- Asocia operațiile **CRUD** cu metodele HTTP: `GET`, `POST`, `PUT`, `DELETE`.
- Explica ce înseamnă o metodă **safe** și o metodă **idempotentă**.
- Întoarce codurile de status potrivite cu `ResponseEntity`: `200`, `201`, `204`, `400`, `404`.

## Exercițiul 1 – Programul de filme (20 min)

**Scop:** Expunerea filmelor ca resursă REST și citirea lor cu `GET`.

**Cerințe:**

1. Porniți aplicația și deschideți [http://localhost:8080/api/movies](http://localhost:8080/api/movies). Ce status primiți?
2. Adnotați clasa `MovieRestController` cu `@RestController` și `@RequestMapping("/api/movies")` (TODO 1.1).
3. Adnotați `getMovies` cu `@GetMapping` și returnați toate filmele (TODO 1.2). Reporniți și reîncărcați pagina.
4. Adnotați `getMovie` cu `@GetMapping("/{id}")` și parametrul cu `@PathVariable` (TODO 1.3). Testați `/api/movies/1`.
5. Adăugați filtrarea după gen cu `@RequestParam(required = false)` (TODO 1.4). Testați `/api/movies?genre=Sci-Fi`.
6. Deschideți `/api/movies/99`. Ce status primiți? Este corect?

**Concepte:** Resursă, URI, Reprezentare, JSON, `@RestController`, `@RequestMapping`, `@GetMapping`, `@PathVariable`, `@RequestParam`.

## Exercițiul 2 – Administrarea filmelor (25 min)

**Scop:** Implementarea operațiilor CRUD cu metodele HTTP potrivite.

**Cerințe:**

1. Adnotați `createMovie` cu `@PostMapping` și parametrul cu `@RequestBody` (TODO 2.1).
2. Adnotați `updateMovie` cu `@PutMapping("/{id}")`, `@PathVariable` și `@RequestBody` (TODO 2.2).
3. Adnotați `deleteMovie` cu `@DeleteMapping("/{id}")` și `@PathVariable` (TODO 2.3).
4. Reporniți aplicația și rulați din `requests.http` cererile de la exercițiul 2. După fiecare cerere, verificați lista cu `GET /api/movies`.
5. Rulați cererea **POST** de două ori. Apoi rulați cererea **PUT** de două ori. Care dintre ele schimbă rezultatul la a doua rulare?
6. Uitați-vă la statusul primit după POST și după DELETE. Spune ceva despre ce s-a întâmplat?

**Concepte:** CRUD, `POST`, `PUT`, `DELETE`, `@RequestBody`, Safe, Idempotent.

## Exercițiul 3 – Coduri de status (20 min)

**Scop:** Întoarcerea codului de status potrivit pentru fiecare situație, cu `ResponseEntity`.

**Cerințe:**

1. `getMovie`: întoarceți `404 Not Found` dacă filmul nu există și `200 OK` altfel (TODO 3.1).
2. `createMovie`: întoarceți `400 Bad Request` pentru un film fără titlu sau cu prețul `<= 0`, altfel `201 Created` cu headerul `Location` (TODO 3.2).
3. `updateMovie`: întoarceți `404 Not Found` dacă filmul nu există (TODO 3.3).
4. `deleteMovie`: întoarceți `204 No Content` după ștergere și `404 Not Found` dacă filmul nu există (TODO 3.4).
5. Reporniți aplicația și rulați cererile de la exercițiul 3 din `requests.http`. Verificați statusul fiecărui răspuns.
6. **Bonus:** Rulați cererile `OPTIONS` și `HEAD`. Ce conține headerul `Allow`? Are răspunsul la `HEAD` un body?

**Concepte:** `ResponseEntity`, `200 OK`, `201 Created`, `204 No Content`, `400 Bad Request`, `404 Not Found`, headerul `Location`.

## Observații

- Codul de completat este marcat cu `TODO x.y` (exercițiul `x`, pasul `y`). Exercițiul 3 modifică metodele scrise la exercițiile 1 și 2.
- Fișierul `requests.http` conține toate cererile laboratorului, în ordinea exercițiilor.
- Dacă portul 8080 este ocupat, decomentați `server.port=8081` în `application.properties` și schimbați `@baseUrl` în `requests.http`.
- Nu este necesară o bază de date: filmele sunt ținute în memorie, în `MovieService`.

---

# Laboratorul 5 – REST: Request Params, Request Body, Response & Headers

**Durată:** 70 de minute.

Continuăm **API-ul REST Cinema FMI** din Laboratorul 4. Proiectul `lab5` pornește de unde s-a terminat `lab4`: GET, POST, PUT și DELETE pe `/api/movies` funcționează deja, cu codurile de status corecte. Azi adăugăm:

- **proiecțiile** fiecărui film, ca subresursă: `/api/movies/{movieId}/screenings`;
- **paginarea** listei de filme;
- modificarea parțială cu **PATCH** și o **căutare** complexă, cu body;
- **headere** în cerere și în răspuns: `X-Total-Count`, `X-Api-Key`, `ETag`, `If-Match`.

Codul de completat este în `controller/MovieRestController.java`, `controller/ScreeningRestController.java` și într-o singură metodă din `service/MovieService.java`.

## Pornirea aplicației

1. Deschideți folderul `lab5` în IntelliJ IDEA (**File → Open → lab5**) și așteptați importarea proiectului Maven.
2. Rulați configurația `Lab5 - Cinema FMI API` (sau clasa `Lab5Application`).
3. În consolă trebuie să apară `Tomcat started on port 8080`. Dacă portul e ocupat, opriți aplicația din laboratorul trecut.
4. Cererile **GET** se pot deschide din browser, pornind de la [http://localhost:8080](http://localhost:8080). Headerele răspunsului se văd cu **F12 → Network**.
5. Pentru celelalte cereri, deschideți `requests.http` și apăsați săgeata verde din stânga fiecărei cereri.

**Important:** După fiecare modificare în cod, reporniți aplicația. La repornire, datele revin la cele inițiale.

## Obiective

La finalul laboratorului, studenții ar trebui să poată:

- Proiecta URI-uri pentru **subresurse**, cu mai multe `@PathVariable`.
- Alege între **path param** (obligatoriu, identifică resursa) și **query param** (opțional: filtrare, paginare).
- Folosi `@RequestParam` cu `defaultValue` pentru paginare.
- Explica diferența dintre **PUT** (înlocuire completă) și **PATCH** (modificare parțială).
- Folosi `@RequestBody` pentru o căutare complexă (`POST /search`).
- Citi headere din cerere cu `@RequestHeader` și trimite headere în răspuns cu `ResponseEntity`.
- Explica headerele standard `Content-Type`, `Accept`, `Location`, `ETag`, `If-Match` și ce sunt headerele custom.

## Exercițiul 1 – Proiecțiile și paginarea (20 min)

**Scop:** Lucrul cu parametri în cale (subresurse) și parametri în query (paginare).

**Cerințe:**

1. Porniți aplicația și rulați din `requests.http` prima cerere a exercițiului 1. Ce status primiți?
2. Adnotați `ScreeningRestController` cu `@RestController` și `@RequestMapping("/api/movies/{movieId}/screenings")` (TODO 1.1).
3. Implementați `getScreenings`: 404 dacă filmul nu există, altfel lista proiecțiilor (TODO 1.2).
4. Implementați `getScreening`, cu două `@PathVariable` (TODO 1.3).
5. Rulați cererile pentru `/api/movies/1/screenings/3` și `/api/movies/99/screenings`. Ce status primiți și de ce?
6. În `MovieRestController.getMovies`, adăugați paginarea cu `page` și `size` (TODO 1.4). Testați `?page=0&size=2` și `?page=1&size=2`.

**Concepte:** Path param, `@PathVariable`, Subresursă, Query param, `@RequestParam(defaultValue = ...)`, Paginare.

## Exercițiul 2 – Modificări parțiale și căutare (20 min)

**Scop:** Trimiterea datelor în body: PUT vs. PATCH și o căutare complexă.

**Cerințe:**

1. Rulați cererea **PUT** cu un singur câmp (`price`) pe filmul 1, apoi `GET /api/movies/1`. Ce s-a întâmplat cu titlul și genul?
2. Rulați cererea **PATCH**. Ce status primiți?
3. Adnotați `patchMovie` cu `@PatchMapping("/{id}")`, `@PathVariable` și `@RequestBody` (TODO 2.1).
4. În `MovieService.patch`, modificați doar câmpurile care nu sunt `null` (TODO 2.2). De ce în `MoviePatch` prețul este `Double`, nu `double`?
5. Adnotați `searchMovies` cu `@PostMapping("/search")` și `@RequestBody` (TODO 2.3). Rulați cererea de căutare.

**Concepte:** `@RequestBody`, `PUT` vs. `PATCH`, `@PatchMapping`, Căutare complexă cu `POST /search`.

## Exercițiul 3 – Headere (25 min)

**Scop:** Citirea headerelor din cerere și trimiterea headerelor în răspuns.

**Cerințe:**

1. `getMovies`: întoarceți `ResponseEntity<List<Movie>>` cu headerul `X-Total-Count` (TODO 3.1). Verificați headerul în `requests.http` sau în browser, cu **F12 → Network**.
2. `deleteMovie`: citiți headerul `X-Api-Key` cu `@RequestHeader` și întoarceți `403 Forbidden` dacă nu este `cinema-admin` (TODO 3.2). Rulați cele trei cereri DELETE.
3. `getMovie`: trimiteți versiunea filmului în headerul `ETag` (TODO 3.3).
4. `patchMovie`: citiți headerul `If-Match` și întoarceți `412 Precondition Failed` dacă versiunea nu mai este cea curentă (TODO 3.4).
5. Rulați, în ordine, cererile pentru filmul 4: GET, primul PATCH, al doilea PATCH. Ce primește al doilea administrator și de ce?
6. **Bonus:** Rulați cererile bonus. Ce statusuri primiți pentru `Accept: application/xml`, pentru `Content-Type: text/plain` și pentru `If-None-Match`?

**Concepte:** `@RequestHeader`, `ResponseEntity` cu headere, Headere custom (`X-...`), `ETag`, `If-Match`, `Accept`, `Content-Type`, `403`, `412`.

## Observații

- Codul de completat este marcat cu `TODO x.y` (exercițiul `x`, pasul `y`). Exercițiul 3 modifică metode scrise la exercițiile 1 și 2.
- Metodele din secțiunea „Din Laboratorul 4” sunt complete și nu trebuie modificate.
- Fișierul `requests.http` conține toate cererile laboratorului, în ordinea exercițiilor.
- Dacă portul 8080 este ocupat, decomentați `server.port=8081` în `application.properties` și schimbați `@baseUrl` în `requests.http`.
