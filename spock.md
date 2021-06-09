# 一、介绍
java/groovy应用测试框架，它的优雅和富有表现力让它非常出众。

被很多ide，构建工具支持。

灵感来自junit、jmock、rspec、groovy、scala等

[官网文档](https://spockframework.org/spock/docs/1.3/all_in_one.html)

# 二、开始
[Spock Example Project](https://github.com/spockframework/spock-example)

# 三、核心Spock Primer

## 3.1 术语Terminology
- 规格 specifications
- 特性 features
- 特性的初始化 feature’s fixture

## 3.2 导包
```import spock.lang.*```

## 3.3 Specification
```
class MyFirstSpecification extends Specification {
// fields
// fixture methods
// feature methods
// helper methods
}
```
## 3.4 Fields
```
def obj = new ClassUnderSpecification()
def coll = new Collaborator()
@Shared res = new VeryExpensiveResource()
static final PI = 3.141592654
```
## 3.5 Fixture Methods
```
def setupSpec() {}    // runs once -  before the first feature method
def setup() {}        // runs before every feature method
def cleanup() {}      // runs after every feature method
def cleanupSpec() {}  // runs once -  after the last feature method
```
### 3.5.1 Invocation Order
1. super.setupSpec
2. sub.setupSpec
3. super.setup
4. sub.setup
5. feature method
6. sub.cleanup
7. super.cleanup
8. sub.cleanupSpec
9. super.cleanupSpec

## 3.6 Feature Methods
四个阶段
1. Set up the feature’s fixture
2. Provide a stimulus to the system under specification
3. Describe the response expected from the system
4. Clean up the feature’s fixture
### 3.6.1 Blocks
given, when, then, expect, cleanup, and where blocks.
![alt 块和阶段](Blocks2Phases.png)

#### 3.6.1.1 Expect Blocks
```
when:
def x = Math.max(1, 2)

then:
x == 2
```
```
expect:
Math.max(1, 2) == 2
```
#### 3.6.1.2 Where Blocks
```
def "computing the maximum of two numbers"() {
  expect:
  Math.max(a, b) == c

  where:
  a << [5, 3]
  b << [1, 9]
  c << [5, 9]
}
```

## 3.7 Helper Methods

## 3.8 Using with for expectations
```
def "offered PC matches preferred configuration"() {
  when:
  def pc = shop.buyPc()

  then:
  with(pc) {
    vendor == "Sunny"
    clockRate >= 2333
    ram >= 406
    os == "Linux"
  }
}
```
## 3.9 Using verifyAll to assert multiple expectations together
```
def "offered PC matches preferred configuration"() {
  when:
  def pc = shop.buyPc()

  then:
  verifyAll(pc) {
    vendor == "Sunny"
    clockRate >= 2333
    ram >= 406
    os == "Linux"
  }
}

```

```
expect:
  verifyAll {
    2 == 2
    4 == 4
  }

```
## 3.10 Specifications as Documentation
```
given: "open a database connection"
// code goes here
and: "seed the customer table"
// code goes here
and: "seed the product table"
// code goes here
```

## 3.11 Extensions
- @Timeout
- @Ignore
- @IgnoreRest
- @FailsWith

## 3.12 Comparison to JUnit
|Spock|	JUnit|
|:---:|:---:|
|Specification|Test class|
|setup()|@Before|
|cleanup()|@After|
|setupSpec()|@BeforeClass
|cleanupSpec()|@AfterClass
|Feature |Test
|Feature method|Test method
|Data-driven feature|Theory
|Condition|Assertion
|Exception condition |@Test(expected=…​)
|Interaction |Mock expectation (e.g. in Mockito)

# 四、Data Driven Testing
## 4.1 Introduction
要解决的问题
- Code and data are mixed and cannot easily be changed independently
- Data cannot easily be auto-generated or fetched from external sources
- In order to exercise the same code multiple times, it either has to be duplicated or extracted into a separate method
- In case of a failure, it may not be immediately clear which inputs caused the failure
- Exercising the same code multiple times does not benefit from the same isolation as executing separate methods does


## 4.2 Data Tables
```
class MathSpec extends Specification {
  def "maximum of two numbers"(int a, int b, int c) {
    expect:
    Math.max(a, b) == c

    where:
    a | b | c
    1 | 3 | 3
    7 | 4 | 7
    0 | 0 | 0
    __
    x|y
    1|2
    __
    z = 3
  }
}
```
## 4.3 Isolated Execution of Iterations
每一个迭代都是独立的，都会调用setup和cleanup
## 4.4 Sharing of Objects between Iterations
可以使用@Shared或者静态变量在迭代间共享变量
## 4.5 Syntactic Variations(句法变化)
## Data Pipes
```
...
where:
a << [1, 7, 0]
b << [3, 4, 0]
c << [3, 7, 0]
```

## Multi-Variable Data Pipes
```
@Shared sql = Sql.newInstance("jdbc:h2:mem:", "org.h2.Driver")

def "maximum of two numbers"() {
  expect:
  Math.max(a, b) == c

  where:
  [a, b, c] << sql.rows("select a, b, c from maxdata")
}
```
...
https://stackoverflow.com/questions/19185596/spock-testing-exceptions-with-data-tables

# 五、Interaction Based Testing
## Mocking
### Creating Mock Objects
```
def subscriber = Mock(Subscriber)
def subscriber2 = Mock(Subscriber)
```
```
Subscriber subscriber = Mock()
Subscriber subscriber2 = Mock()
```
https://stackoverflow.com/questions/24413184/difference-between-mock-stub-spy-in-spock-test-framework


# 六、Extensions

# 七、Modules
## 7.1 Guice Module
## 7.2 Spring Module
- https://github.com/rieckpil/blog-tutorials/tree/master/testing-spring-boot-applications-with-mockmvc
- https://stackoverflow.com/questions/18336277/how-to-check-string-in-response-body-with-mockmvc
- https://rieckpil.de/guide-to-testing-spring-boot-applications-with-mockmvc/
- https://stackoverflow.com/questions/42725199/how-to-use-mockmvcresultmatchers-jsonpath
## 7.3 Tapestry Module
## 7.4 Unitils Module
## 7.5 Grails Module

