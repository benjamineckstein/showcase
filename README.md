# Showcase

[![Java CI](https://github.com/benjamineckstein/showcase/actions/workflows/java_ci.yml/badge.svg)](https://github.com/benjamineckstein/showcase/actions/workflows/java_ci.yml)

Showcase with a minimal Spring Boot application demonstrating what you can achieve with a little extra testing effort — including **100% test coverage** enforced by mutation testing.

## Architecture Tests

Define coding rules that will support your team to maintain a certain standard of quality architecture. What can you do? 

 * Verify that controller only return Responseentities
 * Verify that boundary methods are always annotated with @Transactional
 * Verify that controllers can only access boundaries, but not repositories (Layerchecks)
 * And a lot more (see implemented architecture tests) 

Architecture tests are implemented with <https://www.archunit.org/>

## Mutation Tests

Is your software bug-free if you can reach 100% test coverage? For sure not. Mutation testing is simple. Take your tests as they are, add bugs to the source code, and see if your tests will still be green. This is an excellent way to discover missing test cases even if your code is covered by tests.

 * Automatically mutate your source code and let's check if your tests would fail and catch that mutation
 * Simulate a lot of different mutations on all possible code lines
 * Simply run `gradle pitest` and see a nice html report generated within minutes

Mutation testing is implemented with <https://pitest.org/>

## Automated Pojo Testing

Do not waste time writing boring and repeating tests. Go one step further and autogenerate simple tests for all of your plain old java objects (pojo).
 
 * Verify that setters and getters exists
 * Verify that setters and getters do not have any side effects.
 * Verify that equal and hashcode implementations work as expected 
 * even if you add new fields to your pojo.  

Automated Pojo testing is implemented with <https://github.com/OpenPojo/openpojo> and <https://jqno.nl/equalsverifier/>

---

More examples and articles about software engineering practices at [codewithagents.de](https://www.codewithagents.de)
