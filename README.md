# SRI (Simon Stefan Insuranci)

## How to run the project

Build in the root directory of the project:

`mvn clean install`

Run the project in 0-insurance-main:

`mvn exec:java -Dexec.mainClass="de.sri.Main"`

Combined command to run the project from 0-insurance-main for rapid development:

`cd .. && v && mvn clean install && cd 0-insurance-main && mvn exec:java -Dexec.mainClass="de.sri.Main"
`

## Rules to Implement
### General
- [ ] Customers under 18 cannot submit a Policy
- [ ] Customers under 21 have to pay young drivers fee
- [ ] Customers over 80 have to pay senior drivers fee
- [ ] If the car value is over 40.000€, you have to pay 1% of your car value per month on top
- [ ] Cars with value over 100k will not be insuranced


### Tickets
- [ ] Each ticket will increase the premium by 10€
- [ ] When Customer reach 5 Tickets, then the policy will be declined
- [ ] If the speed excess is over 20 km/h, then extra fee of 2% of car value 
- [ ] If speed excess is over 50 km/h, then decline policy

### Accidents
- [ ] Each accident will increase the premium by 20€
- [ ] When Customer reach 3 Accidents, then the policy will be declined
- [ ] If damage value of Customer car is over car value, then decline policy
- [ ] If damage value of other involements is higher 60.000€, then policy will be declined 