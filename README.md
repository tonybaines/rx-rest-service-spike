# Spike project: RxJava Observables and Async NIO Servlets

The idea was to examine the patterns and structure of code that uses the event-driven/non-blocking style provided
by RxJava (Observables) to do a standard HTTP request -> Database -> Response cycle.
 
## Libraries

* Servlet 3.1 - the Servlet 3.0 API introduced the option of using the Java NIO features; ```request.startAsync()``` for simple GET requests
* [RxJava](https://github.com/Netflix/RxJava/wiki) - the Java implementation of the Reactive Extensions paradigm (Rx)
* [Hystrix](https://github.com/Netflix/Hystrix/wiki) - implements the [Circuit Breaker](http://martinfowler.com/bliki/CircuitBreaker.html) pattern to protect back-end services from overload and cascading failure

## What does it buy you?
The [RxJava](https://github.com/Netflix/RxJava/wiki) site explains the rationale for the reactive approach to composing code

> ... to support sequences of data/events and adds operators that allow you to compose sequences together 
> declaratively while abstracting away concerns about things like low-level threading, synchronization, 
> thread-safety, concurrent data structures, and non-blocking I/O.

By separating threading logic from the processing logic programmer-intent becomes clearer, and the functional style
(no side-effects, no shared state) encourages inherently thread-safe code.