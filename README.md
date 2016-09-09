# reactive-jee
Code and Slides for "Improving Your JEE Monolith with Reactive Techniques" talk

## Link to slides
[https://jlprat.github.io/reactive-jee](https://jlprat.github.io/reactive-jee)


## To Start
* asadmin start-database
* mvn package
* asadmin deploy --name library --contextroot library target/reactive-jee-1.0-SNAPSHOT.jar

## Glassifhs issues
* Jackson Jars are buggy - copy the latest ones under $GLASSFISH_HOME/glassfish/modules
* EclipseLink Moxy is buggy - copy the latest one under $GLASSFISH_HOME/glassfish/modules
