# 100 Percent Coverage

[![Java CI](https://github.com/benjamineckstein/100-percent-coverage/actions/workflows/java_ci.yml/badge.svg)](https://github.com/benjamineckstein/100-percent-coverage/actions/workflows/java_ci.yml)

A Spring Boot showcase demonstrating that **100% test coverage is achievable — and enforceable**. Not as a vanity metric, but backed by mutation testing to prove the tests actually mean something.

Built with Spring Boot 4, Java 21, and a small set of powerful testing libraries.

---

## The Challenge: 100% Coverage That Actually Means Something

Reaching 100% line coverage is one thing. Having tests that *catch real bugs* is another.

This project enforces both:

1. **Jacoco** verifies 100% instruction coverage on every build — the build *fails* if coverage drops below 100%
2. **PIT Mutation Testing** validates that the tests are meaningful — it injects bugs into the source code and checks whether your tests catch them

```bash
# Verify 100% coverage (runs automatically on every build)
./gradlew check

# Run mutation tests and see how well your tests really perform
./gradlew pitest
```

---

## Architecture Tests

Coding rules that your whole team can rely on — enforced automatically, not just documented.

- Controllers only return `ResponseEntity`
- Boundary methods are always annotated with `@Transactional`
- Controllers can only access boundaries, not repositories directly (layer checks)
- And more — see the `archunit/` test package

Implemented with [ArchUnit](https://www.archunit.org/)

---

## Automated POJO Testing

Stop writing the same boilerplate tests for getters, setters, equals, and hashCode. Auto-generate them instead.

- Verifies that setters and getters exist and have no side effects
- Verifies that `equals` and `hashCode` implementations are correct — even when you add new fields

Implemented with [OpenPojo](https://github.com/OpenPojo/openpojo) and [EqualsVerifier](https://jqno.nl/equalsverifier/)

---

More examples and articles about software engineering practices at [codewithagents.de](https://www.codewithagents.de)
