# The ResTZ Framework
ResTZ is an extensible REST client framework written in Java.

ResTZ was written for three main reasons:

 - There's a huge amount of shared code as far as REST clients concern (e.g., connection pool management, serialization, oauth token expiration, etc...).
In Jive Software, we integrate with almost every known enterprise service out there, and this kind of framework saves us a lot of time and effort.
 - We want to be able to share the REST APIs themselves without depending on a specific HttpClient version.
 - Apache tends to break the API of HttpClient across versions (even minor versions), so unless you provision it as a [boundary](http://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882/ref=tmm_pap_title_0?ie=UTF8&qid=1409902638&sr=1-1) (which is actually as difficult as writing ResTZ) - the larger your code base is, the more impossible it gets to merge that change in.

## Getting Started

Add the any one of the ResTZ HttpClient connectors as a maven dependency to your project
```xml
<dependency>
	<groupId>com.jivesoftware.boundaries.restz</groupId>
	<artifactId>restz-hc435</artifactId>
	<version>1.1-SNAPSHOT</version>
</dependency>
```

> **Note:**
> 
> You may now remove the HttpClient dependency from your project.
> The right version of it is already included within the `restz-hc###` connector.

Create any kind of HttpClient connection pool, configure it in any way you want
```java
HttpClientConnectionManager connectionPool = new PoolingHttpClientConnectionManager();
```

Create an `ExecutorFactory` with that connection pool
```java
ExecutorFactory executorFactory = new HC435ExecutorFactory(connectionPool);
```

That it.
We've got everything we need to start making rest calls.

Now let's say we have a POST endpoint at *http://mydomain:8080/restz/test* that receives an entity named TestRequest and returns an entity named TestResponse, we'll need to:
```java
Executor executor = executorFactory.get();

RequestBuilder requestBuilder = new RequestBuilder("http://mydomain:8080/restz/test", HttpVerb.POST)
	.setEntity(testRequest);

ResTZ restz = new ResTZ(executor);
TestResponse testResponse = restz.execute(requestBuilder, TestResponse.class);
```

ResTZ will handle serialization back and forth.

## ResponseReadingStrategy

ResTZ is built in a way that understands your needs.
It'll deserialize the response to whatever class you'd want, but it'll also understand that if you ask for `InputStream`, then it means just that - return the response stream as it is. If you want it to convert the response to `String` that's fine as well. If you need the response to be deserialized, but you also want the HTTP status or perhaps some of the headers, then simply ask for `ResponseEntity<T>`. If you're not entirely sure what the response is, then just ask for a raw `Response`.

The component that is responsible for this is called `ReponseReadingStrategy`.
ResTZ comes with a couple of such predefined strategies that enables you some real level of flexibility.

If you want, you can of course create your own `ReponseReadingStrategy` and pass it along as a parameter to the `ResTZ` executor.

## Layers

Layers are somewhat smart extension points to the ResTZ framework.

Currently, we have two different types of layers:

 - `ExecutionWrapperLayer` which has before and after execution methods, so you can add any special headers just before the request is sent, globally to the API.
 - `RecoverableFailureLayer` which is a smart mechanism for detecting failures, deal with them and then automatically retry the original request (e.g., as in OAuth token expiration)

ResTZ comes with a small set of predefined layers that deal with OAuth & Basic authentications.

### OAuth Layer

OAuth is a great example of a layer that is both `ExecutionWrapperLayer` and `RecoverableFailureLayer`, because:

1. You need to add the `Authorization: BEARER access_token` header before every execution.
2. You need to deal with **HTTP 401 Unauthorized** and refresh the token when it expires.
3. Retry the original request if it has failed because of token expiration.

When dealing with a certain REST API that supports OAuth you should:

1. Inherit from either `BasicOAuth2Layer` or `AbstractOAuth2Layer<C, T>`.
2. Add your implementation to a `LayerCollection`.
3. Pass that layer collection to the `ResTZ` executor whenever needed.

## Good to Know
We've gathered a list of important things you should be aware of, before you start using ResTZ in your project:

Aspect   		 | Know that..
---------------- | -----------
Threading		 | `Executor` cannot execute multiple requests simultaneously, because **`Executor` isn't thread-safe**. It can, however, execute multiple consequent requests. This is why we only call `ExecutorFactory.get()` once at the beginning of every sequence of requests.
Async   		 | Async operations aren't supported yet. This will be addressed in the near future, but for the time being, you must handle async requests manually (e.g., by running the `ResTZ` executor in a side-thread).
DateFormat		 | The default `ResponseReadingStrategy` and its default `Serializer` component are set to parse and format `Date` fields according to  ISO-8601 ([RFC-3339](https://www.ietf.org/rfc/rfc3339.txt)) standard. For example, the moment of **Nov 05 08:15:30 AM EST 1994** will match both **1994-11-05T08:15:30-05:00** and **1994-11-05T13:15:30Z**.
`ResTZ` Overrides	| The `ResTZ`'s constructor accepts optional `LayerCollection` and/or `ResponseReadingStrategy` that will affect every request it executes. Those can be overridden on per-execution basis.
Request Overrides	| It happens often that we want to configure a timeout for the entire connection pool and at the same time, to be able to override it for a specific request. In such a case, we'd need to inherit from the connector's HC###ExecutorFactory and provide ourselves another `get(long timeout)` method.

## Roadmap

Next releases of ResTZ will focus on the following features:

Feature | Description
------- | -----------
Async | Adding support for async operations using `Future<T>`.
Batch | Adding a mechanism for executing a sequence of requests, possibly depended one on another.