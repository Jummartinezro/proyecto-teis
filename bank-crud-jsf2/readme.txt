@author volkodavav

                                 bank-crud-jsf2

 What is it?
 ===========

 Specifically, this project is setup to allow you to create a JSF 2.0 application that
 can run on Servlet Containers like Tomcat 7. You may be satisfied
 with this combination in the long run, or you may eventually migrate to a true
 Java EE 6 environment to leverage EJB 3.1, JPA 2.0 and other platform
 technologies.

 System requirements
 ===================

 All you need to run this project is Java 6.0 (Java SDK 1.6) or greator and
 Maven 2.0.10 or greater. This application is setup to be run on a Servlet
 Container. The embedded Tomcat containers get downloaded
 automatically by the Maven commands.

 If you want to deploy the application to a standalone Servlet Container, then
 you will need to set one up.

 Deploying the application
 =========================

 You can deploy the application without moving any files around using the
 embedded Tomcat container.

 To run the application using embedded Tomcat, execute this command:

  mvn clean install t7:run

 The application will be running at the following URL:
 
  http://localhost:8080/bank-crud-jsf2/

 To run the application on a standalone container, first execute this command:

  mvn package

 Then copy the archive target/bank-crud-jsf2.war to the hot deploy directory of
 the container (e.g., the webapps directory of Tomcat). Of course, you also
 need to start the container.

 Importing the project into an IDE
 =================================

 If you created the project using the Maven 2 archetype wizard in your IDE
 (Eclipse, NetBeans or IntelliJ IDEA), then there is nothing to do. You should
 already have an IDE project.

 If you created the project from the commandline using archetype:generate, then
 you need to bring the project into your IDE. If you are using NetBeans 6.8 or
 IntelliJ IDEA 9, then all you have to do is open the project as an existing
 project. Both of these IDEs recognize Maven 2 projects natively.

 To import into Eclipse, you first need to install the m2eclipse plugin. To get
 started, add the m2eclipse update site (http://m2eclipse.sonatype.org/update/)
 to Eclipse and install the m2eclipse plugin and required dependencies. Once
 that is installed, you'll be ready to import the project into Eclipse.

 Select File > Import... and select "Import... > Maven Projects" and select
 your project directory. m2eclipse should take it from there.

 Once in the IDE, you can execute the Maven commands through the IDE controls
 to run the application on an embedded Servlet Container.
