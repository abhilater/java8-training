# Reflection API and Method Handles

https://drive.google.com/open?id=1eZNy6KbGuosKfGJN88mZMwdAGUdWQwZE

## Creating object metamodel using Annotations and Reflection

### Appying the concepts in an ORM
* How to introspect/query a class to access meta-data for fields etc
* `PlayWithMetamodel`

### Dependency Injection
* How to invoke methods by reflection
* Apply it to inject Database connection in prev project
    * In prev we created a tight dependency between the EntityManager and the
    Database connection
    * Then we solved it by inheritance creating a sub-type for H2EntityManager
    and making main manager as abstract
    * Composition is better than inheritance, we don't want to create many subtypes 
    like MySQLEManager, PostgesEManager etc
* `PlayWithMetamodel`

### Performance of Reflection API and Method Handles   
    