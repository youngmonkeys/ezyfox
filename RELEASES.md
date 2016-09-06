**version 1.1.3**

- Add serialize and deserialize features, example: 

public class BetRequestSerializer implements ObjectSerializer {

    @Override
    public Parameters serialize(Object object) {
        BetRequestListener bet = (BetRequestListener)object;
        Parameters answer = new ParameterWrapper();
        answer.set("1", bet.getBettingTypeId());
        return answer;
    }

}

public class BetRequestDeserializer implements ObjectDeserializer {

    @SuppressWarnings("unchecked")
    @Override
    public BetRequestListener deserialize(Object object, Parameters params) {
        BetRequestListener bet = (BetRequestListener)object;
        bet.setBettingTypeId(params.get("1", int.class));
        return bet;
    }

}

context.command(AddObjectSerializer.class)
	.add(BetRequestListener.class, new BetRequestSerializer());
context.command(AddObjectDeserializer.class)
	.add(BetRequestDeserializer.class, new BetRequestDeserializer());