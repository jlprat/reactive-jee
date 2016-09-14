# reactive-jee
Code and Slides for "Improving Your JEE Monolith with Reactive Techniques" talk

## Link to slides
[https://jlprat.github.io/reactive-jee](https://jlprat.github.io/reactive-jee)


## To Start
* asadmin create-jms-resource --restype javax.jms.Queue --property imqDestinationName=jms_BookLendingQueue jms/BookLendingQueue
* asadmin start-database
* mvn package
* asadmin deploy --name library --contextroot library target/reactive-jee-1.0-SNAPSHOT.jar

## Glassfish issues
* EclipseLink Moxy is buggy - copy the latest one under $GLASSFISH_HOME/glassfish/modules
