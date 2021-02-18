# java8-training

## Design patterns using Lambda

**Composition:** 1st fundamental operation of functional programming

### Lamdas
* The JVM does not create a new object every time we create a Lambda
* New object is created (expensive) everytime we create anonymous interface objects
* Stream is an object on which one can define operations
* Stream object does not hold data, instead it holds pipeline of stages of 
operations like map, filter, reduce etc
    * Pipeline of operations are performed on collection in one pass
    * It doesn't change the data, helps in parallel processing thread safety
* Stream is a very light-weight object, can be recreated
    * Only once terminal operation i.e cannot be reused by another terminal

* https://drive.google.com/drive/folders/1buPCHYHd2ksRLbaBWSQgk3jo60hFm4QP

### Chaining Lamdas
* Adding a `Default method` on functional interfaces, provides a way to chain Lambdas
* Do not forget to fail fast -> `Objects.requireNonNull`
* `Factory methods` in functional interfaces mostly to create helper lambdas
* `PlayWithConsumers`

### Combining and negating predicates
* `PlayWithPredicates`

### Chaining and composing functions
* Difference between chaining and composing functions
    * Composition is only possible for functions
    * Not for predicates or consumers
*    `PlayWithFunctions`

### Identity functions
* Uses factory (static) methods in the FI
* `PlayWithIdentity`

### Comparators
* They can be chained, reversed (using factory methods)
* Comparator only depends on the type of item it compares on 
    * Needs a function to extract the key item
    * Using it can be easily build using a factor `comparing`
* `PlayWithComparators`

### Creational Design patterns
* Design Factory, Registry and Builder
* A `Factory` is an object to create other objects
    * It can be modeled by a Supplier FI by adding `default` and `factory` methods
    * Abstract generic factories
* We can make factory to return Singletons too instead of creating new instance
* `Registry` is similar to a factory but takes in a key to identify which objects 
to create (switch case)
    * This needs to know all object's at compile time and is hence static
    * We can create a dynamic registry using Hash maps and `Builders`
    1. Add elements to registry
    2. Build the registry and seal it (Not allowed to add more elements)
* `PlayWithSwitchRegistry`

### Visitor Pattern using Functions and Composition
* A class hierarchy or object structure in which elements can be visited
* Without any code change needed in class structure 
* All elements need to be ready with accept(visitor) method
* With lambdas you can define operations on classes WITHOUT ADDING THEM TO THE CLASS
* Lambdas can be either passed as params to methods or stored in `Registry`
* So if we have `Registry` of classes and lambdas for classes you can define
operations on classes WITHOUT ADDING...

### Validator pattern
* Validate a set of fields, report single mother exception wrapping and 
supressing all the other exceptions with messages
* `PlayWithValidator`

### Conclusion
* How to model a problem with a function
* Top down approach
    * Write the client pattern in the way you want to 
    * Try to understand the underlying function
* They can be created using functional interface
* Factory methods to provide helper implementations to create functions
* default methods to chain and compose the functions
* Supplier is the right lambda if you want to handle exceptions