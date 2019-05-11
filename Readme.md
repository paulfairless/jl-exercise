# Engineering Exercise #
I decided to use the Kotlin and Micronaut framework for this exercise. I have not used either before so gave myself an unnecessary learning curve to contend with and no doubt room for improvement with my solution.
 
Micronaut is specifically designed to write micro services and seemed well suited to the problem of building [12 Factor Apps](https://12factor.net/) 

## Instructions ##
#### Prerequisites ####
jdk8

#### Running ####
Provide API credentials in `application.yml`

`./gradlew run`

endpoint available at `http://localhost:8080/price-reduction`

#### Testing  ####
`./gradlew test`

## Assumptions & Issues ##
*Price Label Formatting* - keep decimals on prices over £10 when present

*RBG Colors* - Not all colours had a sensible RGB mapping (e.g. Multi). Assumed return empty string.

*Price format not consistent in API* - Price was provided both as a value `xx.xx` and as a nested object `{from:x, to:y}` which added unnecessary complexity. I would look to refactor the API perhaps `{minPrice:x, maxPrice:y}`, the same value could be used when no range and client can decide how to use.

*Presentation logic in API* - Adding the `labelType` formatting parameter also compromised the design as I could no longer use simple object marshalling to render the API. I'm not a fan overall of presentation logic in API's, and would prefer to keep the API clean and implement rendering logic in the client.

   

## Further Enhancements ##
Time permitting the following enhancements could be made

**Reactive streams** - Streaming api's could be implemented to provide non-blocking implementation

**API versioning** - Add versioning to endpoint to allow a route to future breaking changes https://docs.micronaut.io/snapshot/guide/index.html#apiVersioning

**Category Parameter** - Allow parameter for category such that the service can be used more widely

**Testing** - I'm not entirely happy with the test readability with Spek2. I normally use Spock (groovy) but wanted to stick with Kotlin for this exercise.

**Controller Logic** - If the service grew it would be better to move the controller logic to filter & sort into a service. This would also allow for better testing.

**Generate Rest docs** - Used Rest Docs as part of the test suite, it would be trivial to add a code gneration step to the build to generate API docs for the service.