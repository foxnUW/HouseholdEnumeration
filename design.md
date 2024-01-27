# Household Enumeration Design Document

## Project Requirements

Given input data in the provided format (see [Appendix 1](#appendix-1-sample-input-data)), produce output to either the console or to a text file in the 
following format:
```
Each household and number of occupants, followed by:
Each First Name, Last Name, Address and Age sorted by Last Name then First Name where the occupant(s) are older than 18
```
## Project Structure

### Overview

Each household is represented as an element of a `SortedMap<Address, List<Resident>>`, where all members of the 
`List<Resident>` have the same `Address`. This map is made and iterated through in `Solution`, the launch point 
for the program.

Both `Resident` and `Address` implement `Comparable`.
- `Resident` is sorted first by last name, lexicographically, then by firstName, lexicographically, then by address,
then by age.
- `Address` is sorted lexicographically by state, then city, then street, then apartment (if present). If the apartment 
isn't present, it is "less" than an address with an apartment. Apartments are sorted lexicographically to account for 
cases of `Apt. 12B`, `Apt. B15`, etc.

#### Considerations

- `SortedMap` is used for the households representation because it allows for easier testing, guaranteeing that each 
run of the program will produce output in the same order given the same input. Since the prompt did not mention 
extremely large input files to be expected, the easier readability for testing outweighs the slightly slower runtime 
for large input files.
- `Solution` uses `main` to set up the program and `start` to run the program because it allows for easier testing. 
The tests can focus on how the output of the program will look without redirecting input and output for the test case. 
It is noted this is not necessary when certain test harnesses are used, but that the cost of setting up a test harness 
for such a simple program seemed unnecessary.

### Class Diagram
```plantuml
skinparam classAttributeIconSize 0
class Solution {
    + {static} main(args : String[])
    + {static} start(scanner : Scanner) : String
    ~ {static} getDataFromFile(scanner : Scanner) : List<String>
    ~ {static} constructResident(line : String) : Resident
    ~ {static} getHouseholds(residents : List<Resident>) : SortedMap<Address, List<Resident>>
    ~ {static} getHouseholdsDisplay(households : SortedMap<Address, List<Resident>>) : String
}
class Address implements Comparable {
    - street : String
    - apartment : String
    - city : String
    - state : String
    
    + Address(street : String, city : String, state : String)
    + Address(address : Address)
    + equals(other : Object) : boolean
    + hashCode() : integer
    + toString() : String
    + compareTo(other : Address) : integer
}

class Resident implements Comparable {
    - firstName : String
    - lastName : String
    - address : Address
    - age : integer
    
    + Resident(firstName : String, lastName : String,
                address : Address, age : integer)
    + getAddress() : Address
    + getAge() : integer
    + compareTo(other : Resident) : integer
    + equals(other : Object) : boolean
    + hashCode() : integer
    + toString() : String
}

Address -right-* Resident
Address <.. Solution
Resident <.. Solution
```

### Unit Testing

- Both `Address` and `Resident` classes will have complete unit testing files (`AddressTest` and `ResidentTest` 
respectively).
- `Solution` will have accompanying unit tests, which will test how the `Solution.start` method works, given various 
input files from `tst/data` as arguments.

## Appendix 1: Sample Input Data
```
"Dave","Smith","123 main st.","seattle","wa","43"
"Alice","Smith","123 Main St.","Seattle","WA","45"
"Bob","Williams","234 2nd Ave.","Tacoma","WA","26"
"Carol","Johnson","234 2nd Ave","Seattle","WA","67"
"Eve","Smith","234 2nd Ave.","Tacoma","WA","25"
"Frank","Jones","234 2nd Ave.","Tacoma","FL","23"
"George","Brown","345 3rd Blvd., Apt. 200","Seattle","WA","18"
"Helen","Brown","345 3rd Blvd. Apt. 200","Seattle","WA","18"
"Ian","Smith","123 main st ","Seattle","Wa","18"
"Jane","Smith","123 Main St.","Seattle","WA","13"
```