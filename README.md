# DesignPattern_Study_Adapter

# Notion Link 
https://five-cosmos-fb9.notion.site/Adapter-30242c2569c94edcb44922d56a6782b7

# 적응자 (Adapter)

### 의도

클래스의 인터페이스를 사용자가 기대하는 인터페이스 형태로 적응(변환) 시킨다.

서로 일치하지 않는 인ㅌ터페이스를 갖는 클래스들을 함께 동작시킨다.

<aside>
🎈 다른 이름 : 래퍼 (Wrapper)

</aside>

![image](https://user-images.githubusercontent.com/18654358/156903929-1ee1e8d3-349b-4b26-9b93-e478be062cfc.png)

![image](https://user-images.githubusercontent.com/18654358/156903940-aa833fc1-3177-4745-bc10-af96e12e2bf8.png)

![image](https://user-images.githubusercontent.com/18654358/156903943-a7274c97-e0ca-463f-9dc2-e60c594b9e2a.png)

[Animal.java](http://Animal.java) (interface)

```java
public interface Animal {
    void walk();
}
```

Cat.java, [Dog.java](http://Dog.java) (implements Animal)

```java
public class Cat implements Animal{
    @Override
    public void walk() {
        System.out.println("Cat Walk!");
    }
}

public class Dog implements Animal{
    @Override
    public void walk() {
        System.out.println("Dog Walk!!");
    }
}
```

Cat, Dog를 walk 시키자

```java
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Cat());
        animals.add(new Dog());
        animals.forEach(Animal::walk);
    }
}
```

```java
C:\Users\ssh1224\.jdks\azul-15.0.5\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:65173,suspend=y,server=n -javaagent:C:\Users\ssh1224\AppData\Local\JetBrains\IntelliJIdea2021.3\captureAgent\debugger-agent.jar -Dfile.encoding=UTF-8 -classpath "D:\dev\study\DS_Adapter\out\production\DS_Adapter;C:\Program Files\JetBrains\IntelliJ IDEA 2021.3\lib\idea_rt.jar" Main
Connected to the target VM, address: '127.0.0.1:65173', transport: 'socket'
Cat Walk!
Dog Walk!!
Disconnected from the target VM, address: '127.0.0.1:65173', transport: 'socket'
```

다른곳에서 만들어진 독립적인 Fish.java

```java
public class Fish {
    public void swim(){
        System.out.println("Fish Swim~~~~");
    }
}
```

시간이 지나고보니 Animals 라는 범주 안에서 Fish도 같이 walk 시키고 싶다.. 

- 근데? Fish 에는 walk가 없고 swim이 있어!
- swim을 walk 와 같이 동작시켜야 겠어!

[FishAdapter.java](http://FishAdapter.java) (implements Animal)

```java
public class FishAdapter implements Animal{

    private final Fish fish;

    public FishAdapter(Fish fish) {this.fish = fish;}

    @Override
    public void walk() {
        System.out.println("\t\t adapted Fish!!");
        fish.swim();
    }
}
```

Animals 안에 Fish를 포함시켜서 같이 walk 시키자!

```java
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Cat());
        animals.add(new Dog());
        animals.add(new FishAdapter(new Fish()));
        animals.forEach(Animal::walk);
    }
}
```

```java
C:\Users\ssh1224\.jdks\azul-15.0.5\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:65383,suspend=y,server=n -javaagent:C:\Users\ssh1224\AppData\Local\JetBrains\IntelliJIdea2021.3\captureAgent\debugger-agent.jar -Dfile.encoding=UTF-8 -classpath "D:\dev\study\DS_Adapter\out\production\DS_Adapter;C:\Program Files\JetBrains\IntelliJ IDEA 2021.3\lib\idea_rt.jar" Main
Connected to the target VM, address: '127.0.0.1:65383', transport: 'socket'
Cat Walk!
Dog Walk!!
		 adapted Fish!!
Fish Swim~~~~
Disconnected from the target VM, address: '127.0.0.1:65383', transport: 'socket'

Process finished with exit code 0
```

### 우리 제품에 적용시킬 수 있는 케이스는 무엇이 있을까?

인사평가 시스템을 예로 들어볼 수 있을 것 같다.

- 성과평가, 역량평가라는 개념이 있을 때
- 성과평가의 평가 방식과 역량평가의 평가 방식은 달라질 수 있다
- 이런 상황에 “평가” 라는 큰 개념하에  evaluation() 시 각각의 평가를 수행할 수 있을 것이고
- 새로운 개념인 “설문”, “면담” 이라는 개념이 있을 때 같이 evaluation() 으로 써 수행시키고자 할 수 있을 것 같다.
    - 설문과 면담은 점수가 없을 것으로 예상되나
    - 피드백이나 레이팅과 같은 별도의 판단기준을 바탕으로 산출해낼 수 있을 것 이다.
- 최종 묶여진 evaluation()을 통해 평가 외 다른 개념을 함께 평가로써 수행시킬 수 있지 않을까 🙂

### 활용성

- 기존 클래스를 사용하고 싶은데 인터페이스가 맞지 않을 때
- 아직 예측하지 못한 클래스나 실제 관련되지 않는 클래스들이 기존 클래스를 재사용하고자 하지만
- 이미 정의된 재사용 가능한 클래스가 지금 요청하는 인터페이스를 꼭 정의하고 있지 않을 떄
- ***다시 말해, 이미 만든 것을 재사용하고자 하나 이 재사용 가능한 라이브러리를 수정할 수 없을 때***
- 이미 존재하는 여러 개의 서브 클래스를 사용해야 하는데, 이 서브 클래스들의 상속을 통해서 이들의 인터페이스를 다 개조한다는 것이 현실성이 없을 때, 객체 적응자를 써서 부모 클래스의 인터페이스를 변형하는 것이 더 바람직 함.

### 구조

**클래스 적응자**는 다중 상속을 활용해서 한 인터페이스를 다른 인터페이스로 적응시킨다.

![image](https://user-images.githubusercontent.com/18654358/156903950-553d4212-6d22-4c41-a45f-7d41400a3035.png)

**객체 적응자**는 객체 합성을 써서 이루어져 있다.

![image](https://user-images.githubusercontent.com/18654358/156903957-6ffd9d8a-82cd-40fb-9800-e2df47a8caf2.png)

### 참여자

**Target**

- 사용자가 사용할 응용 분야에 종속적인 인터페이스를 정의하는 클래스

**Client**

- Target 인터페이스를 만족하는 객체와 동작할 대상

**Adaptee**

- 인터페이스의 적응이 필요한 기존 인터페이스를 정의하는 클래스

**Adapter**

- Target 인터페이스에 Adaptee 인터페이스를 적응시키는 클래스

### 협력 방법

- 사용자는 **적응자**에 해당하는 클래스의 인스턴스에게 연산을 호출하고
- **적응자**는 해당 요청을 수행하기 위해 **적응 대상자**의 연산을 호출하게 된다.
