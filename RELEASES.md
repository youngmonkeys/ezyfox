**version 1.1.3**

- Add serialize and deserialize features, example: 

```java
public class BetRequestSerializer implements ObjectSerializer {

    @Override
    public Parameters serialize(Object object) {
        BetRequestListener bet = (BetRequestListener)object;
        Parameters answer = new ParameterWrapper();
        answer.set("1", bet.getBettingTypeId());
        return answer;
    }

}
```

```java
public class BetRequestDeserializer implements ObjectDeserializer {

    @SuppressWarnings("unchecked")
    @Override
    public BetRequestListener deserialize(Object object, Parameters params) {
        BetRequestListener bet = (BetRequestListener)object;
        bet.setBettingTypeId(params.get("1", int.class));
        return bet;
    }

}
```

```java
context.command(AddObjectSerializer.class)
	.add(BetRequestListener.class, new BetRequestSerializer());
context.command(AddObjectDeserializer.class)
	.add(BetRequestDeserializer.class, new BetRequestDeserializer());
```

**version 1.1.4**

- add AdditionalClientRequestListeners annotation
- add AdditionalServerEventHandlers annotation
- add ParamsMapper annotation

Example:

```java
@ParamsMapper(serializer = BetRequestSerializer.class,
              deserializer = BetRequestDeserializer.class)
public class BetRequestListener {
	// source code
}
```

```java
@AdditionalClientRequestListeners(classes = {ExClientRequestListener.class})
@AdditionalServerEventHandlers(classes = {ExServereEventHandler.class})
```

**version 1.1.5**

- update AdditionalClientRequestListeners and AdditionalServerEventHandlers annotation

Example:

```java
@AdditionalClientRequestListeners({
        "com.tvd12.ezyfox.videopoker.interceptor.BetRequestInterceptor"
})
@AdditionalServerEventHandlers({
        "com.tvd12.ezyfox.videopoker.event.ServerReadyEventHandler"
})
```

**version 1.2.6**

- add PropagateRequest command

Example:

```java
context.command(PropagateRequest.class)
    .command("deal")
    .param("money", 1000)
    .param("auto", true)
    .user(current())
    .execute();
```

