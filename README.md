# TradingApplication
Application that handles events

JDK Version: JDK 11 

Maven 3.8.8 as Build tool

Junit 5 is used as unit testing framework

Created SpringBoot Application that can be run using mvn spring-boot:run

once the application starts, use the following URL to process a signal:
POST API: http://localhost:8081/trading-app/signal/{signalId}

ex: http://localhost:8081/trading-app/signal/2

The problem statement document( Java Coding Assessment) is added to the root folder.

Class Diagram(TradingApplication.png) file is added to the project root folder for reference. And a document(ClassDiagramNotes.docx) is also added to the project root folder decribing the details about the classes/interfaces that has been implemented. And also towards the end, the major benifit of the design is quoted along with the assumptions that has been made for the given problem statement. Along with Flow of request diagram at the end. This document can be used as a source to gain better understanding of the solution that has been implemented.

Questions.docx is also added to the project root folder which contains list of questions(some of those got converted to assumptions) after intial analysis of the problem statement.

## Assumptions
* Commands of a signal are assumed to be executed in the sequential order( the order that is given in the problem statement)
* Every signal is assumed to be a series to commands that need to be executed against behaviors of Algo class.
* Structural/Behavioral changes to Algo class are assumed to be minimal.
* Algo class is created as part of a separate maven module trading-algo. All the active code of this solution is in the module trading-app which contains dependency to trading-algo
* Security aspect of HTTP API is assumed to be not within the scope of this assessment as no relevant requirements are provided as part of problem statement.

## Description of major commits detailing the incremental development of solution
### First Major Commit (825c9aa) - Added skeleton Spring Boot application
This first major commit contains implementation of skeleton Spring Boot application to begin with. Which can be further used to develop the solution. Spring Boot framework has been chosen, since one of the requirement is to receive signals via HTTP API. Spring Boot has been used to develop the required Rest API quickly.
### Second Major Commit (dec8de1) - Implemented POST HTTP API accepting signal 
This commit deals with the implementation of Rest API that receives signal in the form of signalId (Integer) as path parameter. This API receives signalId and return success after succeful processing of the message. In the event of Exception, an 500 http status code is sent along with the details of the exception. In this commit implementation of ISignalHandler which inturn called by the rest api uses the given switch case implementation to deal with the processing of the signal. However the code under this commit accepts signal as part of HTTP API and process the signal based on signalID and returns the response back.
### Third Major Commit (4367900) - Processing Signal as a list of commands
Under the assumption that spec of signal implementation is executing a series of commands to Trading algo API, Updated the implementation to handle the steps involved in processing of each signal as a command object and then passing it onto AlgoExecutor to execute these commands against the behaviors of Algo class available under Trading algo library. By this implementation, extracted and centralized common functionality behind the execution of steps and delegated the resposibility to the AlgoAdaptor class. AlgoAdapter class is responsible for accepting commands and executing them against respective methods of Algo class library. The design is extended to consider that there can be multiple classes like Algo(from Trading library) that deals with execution of varios commands of a signal. Assumption is that with the growing rate of signals, there can be many different methods that could be added to Algo class making it overloaded which might lead to splitting of one Algo class in to multiple algo classes. Hence a type attribute is added to command object which in turn refers to the right Algo class implentation of the respective command. Also added relavent exception classes. (More details about each class can be found in ClassDiagramNotes.docx)
### Fourth Major Commit (52c0cdf) - Restructed/Refactord design implementing Single Responsibility principle
In the previous commit AlgoAdapter class is responsible for executing commands against Algo library. Also AlgoAdapter class accepts list of commands all those can be executed against Algo class of Trading System Library. And by default it executes the "doAlgo" method in the end. This commit refactors the design considering that the commands should be executed in the given order(where they cannot be grouped by type of ALgo class). Hence it splits command Executor duties from AlgoAdapter class. A new CommandExecutor class which deals with the default implementation is built to deal with the list of commands for a given signal and executing them against respective adaptor classes. BeanFactory class is responsible to return the instance of respective adapter class.AlgoAdapter now deals with executing the command that it received from CommandExecutor against Algo class of Trading Algo library. Also the responsibility of executing "doAlgo"  command at the end is now moved to SignalProcessor class which deals with creating a list of commands for a given signal.
### Fifth Major commit (18bf4c1) - updated the deisgn to provide the signal spec(alias commands) in config file
This last major commit deals with updating design now to create commands from a config json file that should available under signal-config folder under resources folder of the respective maven project. SignalProcessor class is now responsible to call SignalConfig class which inturn loads all the signal spec from the given json file and match them against the received signalID and returns the matching spec to the signalProcessor. Now SignalProcessor handles these list of commands to CommandExecutor to execute them against corresponding Also class of Trading Algo library. However a flexibility is provided in the design by suffixing signal-config/signalConfig*.json files to have a suffix indicating the range of signals that the file contain. For example file signalConfig-1-100.json contains spec for signals 1 to 100. In future when the signal population significantly grows, for a given signal, based on the suffix pick and load the appropriate json file to extract the signal spec. However the implementation related to matching the suffix based on signalId is not implemnted. Instead it is defaulted to return signaConfig-1-100.json as the problem statement defines only 3 signal specs. But a TODO has been added to the method spec to update the logic in the future. A defaulyt signalConfig json file is added to pick the default config when no matching signal is found. (More details about each class can be found in ClassDiagramNotes.docx)

### Major Benefit(s) of the design
1. With this design, I have implemented a new signal 10 by just adding new config spec to signalConfig-1-100.json file without making any changes to java code.
POST: http://localhost:8081/trading-app/signal/10
2. Extracted and centralizing common functionality and delegated dynamically changing data to configuration files.
3. Applied Single Responsibility Principle so that each class deals with its own purpose of creation.
4. Applied Open Close Principle by using IAdaptor Interface, ICommandExecutor Interface, ISignalHandler interface.
5. Used interface based implementation, so that objects talk to each other using behaviours exposed through interfaces rather than depending on the class implementation
