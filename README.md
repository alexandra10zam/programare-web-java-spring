# Java Web Programming with Spring

This repository contains the hands-on exercises for the **Java Web Programming** labs, built with Java, Maven and the Spring Framework.

Each lab lives in its own folder and is an independent Maven project with its own dependencies and exercises.

## Repository structure

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

**Labs:**

- **Lab 2:** Spring Core: Bean Scopes, Lazy Initialization and AOP
- **Lab 3:** Spring Boot and Spring MVC
- **Lab 4:** REST Web Services
- **Lab 5:** REST: Request Params, Request Body, Response and Headers

## Requirements

- Java JDK 21
- IntelliJ IDEA
- Maven (bundled with IntelliJ IDEA)
- Git (optional, to clone the repository)

Spring dependencies are managed automatically by Maven through each lab's `pom.xml`.

## Getting started

1. Clone the repository:

   ```bash
   git clone https://github.com/alexandra10zam/programare-web-java-spring.git
   ```

   Alternatively, download it from GitHub with **Code → Download ZIP**.

2. Open IntelliJ IDEA.
3. Select **File → Open** and choose the folder of the lab you want, for example `lab4` or `lab5`.
4. Make sure the project uses **JDK 21**.
5. Wait for the Maven import to finish and for the dependencies in the lab's `pom.xml` to download.
6. Pick an exercise from the run configurations next to the **Run** button (top right), from `Ex1 - Singleton vs Prototype` to `Ex5 - Tipurile de advice`, then press **Run**.
   Alternatively, open the exercise's `Main.java` class and click the green triangle next to the `main()` method.

**Important:** every exercise has its own `Main.java` class and can be run on its own.

---

# Lab 2: Spring Core (Bean Scopes, Lazy Initialization and AOP)

**Duration:** 70 minutes.

In this lab we work on **Cinema FMI**, a movie ticket booking application. The exercises build on each other: each one starts from where the previous exercise ended and fixes the problem discovered there. Every `exerciseN` package already contains the code from the end of the previous exercise, so it can be run independently.

The `[Spring] ... created` messages printed by the constructors show **when** Spring creates each object. Watch them on every run.

## Learning objectives

By the end of the lab, students should be able to:

- Explain the difference between **singleton** and **prototype** beans.
- Obtain prototype beans from a singleton using `ObjectProvider`.
- Explain the difference between **eager** and **lazy** (`@Lazy`) initialization, and what **fail-fast** means.
- Explain the core AOP concepts: **target object, aspect, advice, pointcut, weaving (proxy)**.
- Write an aspect with `@Aspect`, `@EnableAspectJAutoProxy` and `@Around`.
- Use the five advice types: `@Before`, `@AfterReturning`, `@AfterThrowing`, `@After`, `@Around`.

## Exercise 1: Schedule and bookings (9 min)

**Goal:** observe the difference between a singleton bean and a prototype bean.

**Tasks:**

1. Run `Main`. Is there one `MovieCatalog` or two? Which tickets does each customer have?
2. Annotate the `Reservation` class with `@Scope(BeanDefinition.SCOPE_PROTOTYPE)`.
3. Run it again and count the `[Spring] Reservation created` messages.

**Concepts:** Singleton, Prototype, `@Scope`.

## Exercise 2: Customer bookings (9 min)

**Goal:** understand that a prototype bean injected into a singleton is created only once.

**Tasks:**

1. Run `Main`. Does each customer get their own reservation? Look at the reservation numbers and the amounts paid.
2. In `BookingService`, replace the `Reservation` field with `ObjectProvider<Reservation>` and request a new reservation inside `book()` with `getObject()`.
3. Run it again and check that each customer now has their own reservation.

**Concepts:** prototype injected into a singleton, `ObjectProvider`.

## Exercise 3: The ticket printer (11 min)

**Goal:** observe when Spring creates beans.

**Tasks:**

1. Run `Main`. Is `TicketPrinter` created before or after `2. Context created`? How long does startup take?
2. Annotate `TicketPrinter` with `@Lazy` and run again. Is it still created at startup? Why?
3. In `BookingService`, use `ObjectProvider<TicketPrinter>`, as in exercise 2. Run and compare.
4. Uncomment the `throw` line in the `TicketPrinter` constructor. Run with `@Lazy`, then without it. When does the error appear? Comment the line back out when you are done.

**Concepts:** eager vs. lazy initialization, `@Lazy`, fail-fast.

## Exercise 4: Booking audit (12 min)

**Goal:** add auditing to every method of `BookingService` without modifying the class.

**Tasks:**

1. Run `Main` before making any change and note the bean's class name (`Bean class: ...`).
2. Annotate the `AuditAspect` class with `@Aspect` and `@Component`.
3. Annotate the `audit` method with `@Around` and the pointcut given in the comment.
4. Replace `Object result = null;` with `Object result = joinPoint.proceed();`.
5. Enable aspects with `@EnableAspectJAutoProxy` in `AppConfig`, run, and compare the bean's class name.

**Concepts:** Aspect, Advice, Pointcut, Proxy, `@Around`, `proceed()`.

## Exercise 5: Payment checks (11 min)

**Goal:** observe when each type of advice runs.

**Tasks:**

1. Read `Main` and write down the order in which you expect the messages to appear, before writing any code.
2. Annotate the `PaymentAspect` class with `@Aspect` and `@Component`.
3. Add `@Before(PAY)` to the `beforePayment` method.
4. Add `@AfterReturning` to the `afterSuccessfulPayment` method.
5. Add `@AfterThrowing` to the `afterFailedPayment` method.
6. Add `@After` to the `afterPayment` method and run.
7. **Bonus:** annotate `limitPayment` with `@Around` and reject bookings above 200 RON without calling `proceed()`.

**Concepts:** `@Before`, `@AfterReturning`, `@AfterThrowing`, `@After`, `@Around`.

## Notes

- The exercises contain `TODO` comments to complete during the lab.
- No database or web server is needed.
- Every exercise has its own `Main.java` class.
- The `demo` package contains the short examples shown at the start of each part (`DemoScopes`, `DemoLazy`, `DemoAop`).

---

# Lab 3: Spring Boot and Spring MVC

**Duration:** 70 minutes.

We continue the **Cinema FMI** application, this time as a web application: a page with the movie schedule, a booking form and a personal watch list. The exercises build on each other inside the same Spring Boot application.

## Running the application

1. Open the `lab3` folder in IntelliJ IDEA (**File → Open → lab3**) and wait for the Maven import.
2. Run the `Lab3 - Cinema FMI` configuration (or the `Lab3Application` class).
3. The console should show `Tomcat started on port 8080`.
4. Open [http://localhost:8080](http://localhost:8080) in the browser.

**Important:** after every code change, stop the application and start it again.

## Learning objectives

By the end of the lab, students should be able to:

- Explain what Spring Boot provides: **starters**, **auto-configuration** and **convention over configuration**.
- Explain the role of the `@SpringBootApplication` annotation.
- Describe the path of an HTTP request through Spring MVC: **DispatcherServlet → Controller → Model → View**.
- Write a `@Controller` with `@GetMapping` and `@PostMapping`.
- Pass data to a Thymeleaf template through the `Model` and receive form data with `@ModelAttribute`.
- Explain the web scopes: **request**, **session** and **application**.

## Exercise 1: The movie schedule (20 min)

**Goal:** display movies on a web page using a controller, the model and a Thymeleaf template.

**Tasks:**

1. Start the application and open [http://localhost:8080/movies](http://localhost:8080/movies). What is displayed?
2. In `MovieController`, get the list of movies from `MovieService` (TODO 1.1).
3. Add the list to the `Model` under the name `movies` (TODO 1.2).
4. In `templates/movies.html`, use `th:each` to iterate over the list (TODO 1.3).
5. Use `th:text` to display the id, title, genre and price (TODO 1.4). Restart the application and check the page.

**Concepts:** `@SpringBootApplication`, starters, auto-configuration, `@Controller`, `@GetMapping`, `Model`, Thymeleaf.

## Exercise 2: Booking tickets (20 min)

**Goal:** receive data from an HTML form.

**Tasks:**

1. Open [http://localhost:8080/bookings/new](http://localhost:8080/bookings/new), fill in the form and press **Book**. What error appears?
2. In `BookingController`, add `@PostMapping("/bookings")` to the `createBooking` method (TODO 2.1).
3. Annotate the `booking` parameter with `@ModelAttribute("booking")` (TODO 2.2).
4. Save the booking with `bookingService.book(booking)` (TODO 2.3).
5. Add the saved booking to the `Model` under the name `booking` (TODO 2.4). Restart the application and make a booking.

**Concepts:** `@PostMapping`, `@ModelAttribute`, `th:object`, `th:field`.

## Exercise 3: My watch list (20 min)

**Goal:** observe the web scopes: request, session and application.

**Tasks:**

1. Open [http://localhost:8080/watchlist](http://localhost:8080/watchlist) and add two movies to the list. Reload the page a few times: does the Request ID change?
2. Open the same page in an **Incognito** window (a different user). Which list do you see?
3. Annotate `RequestInfo` with `@RequestScope` (TODO 3.1).
4. Annotate `WatchList` with `@SessionScope` (TODO 3.2).
5. Annotate `VisitCounter` with `@ApplicationScope` (TODO 3.3).
6. Restart the application and repeat steps 1 and 2. What changed?

**Concepts:** `@RequestScope`, `@SessionScope`, `@ApplicationScope`, scoped proxy.

## Notes

- The code to complete is marked with `TODO x.y` (exercise `x`, step `y`), in both the Java classes and the HTML templates.
- The Thymeleaf templates are in `src/main/resources/templates`.
- No database is needed: data is kept in memory, in the classes of the `service` package.

---

# Lab 4: REST Web Services

**Duration:** 70 minutes.

We build the **REST API** of the **Cinema FMI** application: instead of HTML pages, the server sends and receives **JSON**. We work on a single resource, movies, at the URI `/api/movies`. The exercises build on each other: in exercise 1 we read movies, in exercise 2 we create, update and delete them, and in exercise 3 we fix the status codes discovered in the first two.

All the code to complete is in `controller/MovieRestController.java`. The `MovieService` class is already written.

## Running the application

1. Open the `lab4` folder in IntelliJ IDEA (**File → Open → lab4**) and wait for the Maven import.
2. Run the `Lab4 - Cinema FMI API` configuration (or the `Lab4Application` class).
3. The console should show `Tomcat started on port 8080`.
4. **GET** requests can be opened directly in the browser, starting from [http://localhost:8080](http://localhost:8080).
5. For **POST**, **PUT** and **DELETE**, open the `requests.http` file and click the green arrow to the left of each request. The response (status, headers, body) appears in the bottom panel.

**Important:** after every code change, stop the application and start it again. Data is kept in memory, so a restart brings back the 4 initial movies.

## Learning objectives

By the end of the lab, students should be able to:

- Explain the concepts of **resource**, **URI** and **representation** (JSON).
- Write a `@RestController` and explain how it differs from `@Controller`.
- Design URIs following REST best practices: `/api/...`, plural nouns, the identifier in the path.
- Use `@PathVariable`, `@RequestParam` and `@RequestBody`.
- Map **CRUD** operations to HTTP methods: `GET`, `POST`, `PUT`, `DELETE`.
- Explain what **safe** and **idempotent** methods are.
- Return the right status codes with `ResponseEntity`: `200`, `201`, `204`, `400`, `404`.

## Exercise 1: The movie schedule (20 min)

**Goal:** expose movies as a REST resource and read them with `GET`.

**Tasks:**

1. Start the application and open [http://localhost:8080/api/movies](http://localhost:8080/api/movies). What status do you get?
2. Annotate the `MovieRestController` class with `@RestController` and `@RequestMapping("/api/movies")` (TODO 1.1).
3. Annotate `getMovies` with `@GetMapping` and return all movies (TODO 1.2). Restart and reload the page.
4. Annotate `getMovie` with `@GetMapping("/{id}")` and its parameter with `@PathVariable` (TODO 1.3). Test `/api/movies/1`.
5. Add filtering by genre with `@RequestParam(required = false)` (TODO 1.4). Test `/api/movies?genre=Sci-Fi`.
6. Open `/api/movies/99`. What status do you get? Is it correct?

**Concepts:** resource, URI, representation, JSON, `@RestController`, `@RequestMapping`, `@GetMapping`, `@PathVariable`, `@RequestParam`.

## Exercise 2: Managing movies (25 min)

**Goal:** implement CRUD operations with the right HTTP methods.

**Tasks:**

1. Annotate `createMovie` with `@PostMapping` and its parameter with `@RequestBody` (TODO 2.1).
2. Annotate `updateMovie` with `@PutMapping("/{id}")`, `@PathVariable` and `@RequestBody` (TODO 2.2).
3. Annotate `deleteMovie` with `@DeleteMapping("/{id}")` and `@PathVariable` (TODO 2.3).
4. Restart the application and run the exercise 2 requests from `requests.http`. After each request, check the list with `GET /api/movies`.
5. Run the **POST** request twice. Then run the **PUT** request twice. Which one changes the result on the second run?
6. Look at the status returned after POST and after DELETE. Does it tell you anything about what happened?

**Concepts:** CRUD, `POST`, `PUT`, `DELETE`, `@RequestBody`, safe, idempotent.

## Exercise 3: Status codes (20 min)

**Goal:** return the right status code for each situation using `ResponseEntity`.

**Tasks:**

1. `getMovie`: return `404 Not Found` if the movie does not exist, and `200 OK` otherwise (TODO 3.1).
2. `createMovie`: return `400 Bad Request` for a movie without a title or with a price `<= 0`, otherwise `201 Created` with a `Location` header (TODO 3.2).
3. `updateMovie`: return `404 Not Found` if the movie does not exist (TODO 3.3).
4. `deleteMovie`: return `204 No Content` after deletion and `404 Not Found` if the movie does not exist (TODO 3.4).
5. Restart the application and run the exercise 3 requests from `requests.http`. Check the status of each response.
6. **Bonus:** run the `OPTIONS` and `HEAD` requests. What does the `Allow` header contain? Does the `HEAD` response have a body?

**Concepts:** `ResponseEntity`, `200 OK`, `201 Created`, `204 No Content`, `400 Bad Request`, `404 Not Found`, the `Location` header.

## Notes

- The code to complete is marked with `TODO x.y` (exercise `x`, step `y`). Exercise 3 changes the methods written in exercises 1 and 2.
- The `requests.http` file contains all of the lab's requests, in exercise order.
- If port 8080 is taken, uncomment `server.port=8081` in `application.properties` and change `@baseUrl` in `requests.http`.
- No database is needed: movies are kept in memory, in `MovieService`.

---

# Lab 5: REST (Request Params, Request Body, Response and Headers)

**Duration:** 70 minutes.

We continue the **Cinema FMI REST API** from Lab 4. The `lab5` project starts where `lab4` ended: GET, POST, PUT and DELETE on `/api/movies` already work, with the correct status codes. Today we add:

- each movie's **screenings**, as a subresource: `/api/movies/{movieId}/screenings`;
- **pagination** for the movie list;
- partial updates with **PATCH** and a complex **search** with a request body;
- request and response **headers**: `X-Total-Count`, `X-Api-Key`, `ETag`, `If-Match`.

The code to complete is in `controller/MovieRestController.java`, `controller/ScreeningRestController.java` and one method in `service/MovieService.java`.

## Running the application

1. Open the `lab5` folder in IntelliJ IDEA (**File → Open → lab5**) and wait for the Maven import.
2. Run the `Lab5 - Cinema FMI API` configuration (or the `Lab5Application` class).
3. The console should show `Tomcat started on port 8080`. If the port is taken, stop the application from the previous lab.
4. **GET** requests can be opened in the browser, starting from [http://localhost:8080](http://localhost:8080). Response headers are visible under **F12 → Network**.
5. For the other requests, open `requests.http` and click the green arrow to the left of each request.

**Important:** after every code change, restart the application. A restart resets the data to its initial state.

## Learning objectives

By the end of the lab, students should be able to:

- Design URIs for **subresources** with multiple `@PathVariable` parameters.
- Choose between a **path param** (required, identifies the resource) and a **query param** (optional: filtering, pagination).
- Use `@RequestParam` with `defaultValue` for pagination.
- Explain the difference between **PUT** (full replacement) and **PATCH** (partial update).
- Use `@RequestBody` for a complex search (`POST /search`).
- Read request headers with `@RequestHeader` and send response headers with `ResponseEntity`.
- Explain the standard headers `Content-Type`, `Accept`, `Location`, `ETag`, `If-Match`, and what custom headers are.

## Exercise 1: Screenings and pagination (20 min)

**Goal:** work with path parameters (subresources) and query parameters (pagination).

**Tasks:**

1. Start the application and run the first exercise 1 request from `requests.http`. What status do you get?
2. Annotate `ScreeningRestController` with `@RestController` and `@RequestMapping("/api/movies/{movieId}/screenings")` (TODO 1.1).
3. Implement `getScreenings`: 404 if the movie does not exist, otherwise the list of screenings (TODO 1.2).
4. Implement `getScreening`, with two `@PathVariable` parameters (TODO 1.3).
5. Run the requests for `/api/movies/1/screenings/3` and `/api/movies/99/screenings`. What status do you get, and why?
6. In `MovieRestController.getMovies`, add pagination with `page` and `size` (TODO 1.4). Test `?page=0&size=2` and `?page=1&size=2`.

**Concepts:** path param, `@PathVariable`, subresource, query param, `@RequestParam(defaultValue = ...)`, pagination.

## Exercise 2: Partial updates and search (20 min)

**Goal:** send data in the request body: PUT vs. PATCH, and a complex search.

**Tasks:**

1. Run the **PUT** request with a single field (`price`) on movie 1, then `GET /api/movies/1`. What happened to the title and genre?
2. Run the **PATCH** request. What status do you get?
3. Annotate `patchMovie` with `@PatchMapping("/{id}")`, `@PathVariable` and `@RequestBody` (TODO 2.1).
4. In `MovieService.patch`, update only the fields that are not `null` (TODO 2.2). Why is the price in `MoviePatch` a `Double` and not a `double`?
5. Annotate `searchMovies` with `@PostMapping("/search")` and `@RequestBody` (TODO 2.3). Run the search request.

**Concepts:** `@RequestBody`, `PUT` vs. `PATCH`, `@PatchMapping`, complex search with `POST /search`.

## Exercise 3: Headers (25 min)

**Goal:** read request headers and send response headers.

**Tasks:**

1. `getMovies`: return `ResponseEntity<List<Movie>>` with an `X-Total-Count` header (TODO 3.1). Check the header in `requests.http` or in the browser under **F12 → Network**.
2. `deleteMovie`: read the `X-Api-Key` header with `@RequestHeader` and return `403 Forbidden` if it is not `cinema-admin` (TODO 3.2). Run the three DELETE requests.
3. `getMovie`: send the movie's version in the `ETag` header (TODO 3.3).
4. `patchMovie`: read the `If-Match` header and return `412 Precondition Failed` if the version is no longer current (TODO 3.4).
5. Run, in order, the requests for movie 4: GET, the first PATCH, the second PATCH. What does the second administrator get, and why?
6. **Bonus:** run the bonus requests. What statuses do you get for `Accept: application/xml`, for `Content-Type: text/plain` and for `If-None-Match`?

**Concepts:** `@RequestHeader`, `ResponseEntity` with headers, custom headers (`X-...`), `ETag`, `If-Match`, `Accept`, `Content-Type`, `403`, `412`.

## Notes

- The code to complete is marked with `TODO x.y` (exercise `x`, step `y`). Exercise 3 changes methods written in exercises 1 and 2.
- The methods in the section marked "Din Laboratorul 4" (from Lab 4) are complete and do not need to be changed.
- The `requests.http` file contains all of the lab's requests, in exercise order.
- If port 8080 is taken, uncomment `server.port=8081` in `application.properties` and change `@baseUrl` in `requests.http`.
