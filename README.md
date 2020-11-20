# Showcase

[![Build Status](https://travis-ci.org/benjamineckstein/showcase.svg?branch=main)](https://travis-ci.org/benjamineckstein/showcase)
[![codecov](https://codecov.io/gh/benjamineckstein/showcase/branch/main/graph/badge.svg)](https://codecov.io/gh/benjamineckstein/showcase)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e556f5bf22834f8792109a59587fe7e0)](https://app.codacy.com/gh/benjamineckstein/showcase?utm_source=github.com&utm_medium=referral&utm_content=benjamineckstein/showcase&utm_campaign=Badge_Grade)
[![Code Inspector](https://www.code-inspector.com/project/14288/score/svg)](https://frontend.code-inspector.com/public/project/14288/showcase/dashboard)
![Lines of code](https://img.shields.io/tokei/lines/github/benjamineckstein/showcase?label=Lines%20of%20Code&style=flat-square)

Showcase with a minimal spring boot application showing what you can do with a little bit of extra testing

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

### Credits
This showcase was sponsored by my current employer Novatec Consulting GmbH https://www.novatec-gmbh.de/en/
