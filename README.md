# SRI (Simon Stefan Insuranci)

## How to run the project

Build in the root directory of the project:

`mvn clean install`

Run the project in 0-insurance-main:

`mvn exec:java -Dexec.mainClass="de.sri.Main"`

Combined command to run the project from 0-insurance-main for rapid development:

`cd .. && v && mvn clean install && cd 0-insurance-main && mvn exec:java -Dexec.mainClass="de.sri.Main"
`

