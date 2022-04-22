package com.tvd12.ezyfox.testing.asm;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.asm.EzyInstruction;
import com.tvd12.test.base.BaseTest;

public class EzyInstructionTest extends BaseTest {

    @Test
    public void test() {
        EzyInstruction instruction = new EzyInstruction();
        assertEquals(instruction.finish().toString(), ";");

        instruction = new EzyInstruction();
        assertEquals(instruction.constructor(A.class).toString(),
                A.class.getName() + "();");

        instruction = new EzyInstruction();
        assertEquals(instruction.append((Object)"hello").toString(), "hello;");

        instruction = new EzyInstruction();
        assertEquals(instruction.string("hello").toString(), "\"hello\";");

        instruction = new EzyInstruction();
        assertEquals(instruction.clazz(A.class, true).toString(),
                A.class.getName() + ".class;");

        instruction = new EzyInstruction();
        assertEquals(instruction.dot().toString(), ".;");

        instruction = new EzyInstruction();
        assertEquals(instruction.comma().toString(), ", ;");

        instruction = new EzyInstruction();
        assertEquals(instruction.bracketopen().bracketclose().toString(), "();");

        instruction = new EzyInstruction();
        assertEquals(instruction.brackets(A.class).toString(),
                "(" + A.class.getTypeName() + ");");

        instruction = new EzyInstruction();
        assertEquals(instruction.brackets("hello").toString(), "(hello);");

        instruction = new EzyInstruction();
        assertEquals(instruction.variable(A.class).toString(),
                A.class.getTypeName() + " a;");

        instruction = new EzyInstruction();
        assertEquals(instruction.cast(A.class, "va").toString(),
                "((" + A.class.getTypeName() + ")(va));");

        instruction = new EzyInstruction();
        assertEquals(instruction.valueOf(int.class, "value").toString(),
                Integer.class.getName() + ".valueOf(value);");

        instruction = new EzyInstruction();
        assertEquals(instruction.valueOf(Integer.class, "value").toString(), "value;");

        instruction = new EzyInstruction("", "", true);
        assertEquals(instruction.append((Object)"hello").toString(), "hello;");

        instruction = new EzyInstruction("", "", true);
        assertEquals(instruction.append((Object)"hello;").toString(), "hello;");

        instruction = new EzyInstruction("", "", false);
        assertEquals(instruction.append((Object)"hello").toString(true), "hello");

        instruction = new EzyInstruction("", "", true);
        assertEquals(instruction.append((Object)"hello;").toString(true), "hello;");

        instruction = new EzyInstruction("", "", true);
        assertEquals(instruction.append((Object)"hello;").toString(false), "hello;");

        instruction = new EzyInstruction("", "", true);
        assertEquals(instruction.append((Object)"hello").toString(false), "hello");

        instruction = new EzyInstruction("", "", true);
        instruction.invoke("object", "hello");
        assertEquals(instruction.toString(false), "object.hello()");

        instruction = new EzyInstruction("", "", true);
        instruction.invoke("object", "hello", "arg0", "arg1");
        assertEquals(instruction.toString(false), "object.hello(arg0, arg1)");

        instruction = new EzyInstruction("", "", true);
        instruction.cast(boolean.class, "arg0");
        assertEquals(instruction.toString(false), "((java.lang.Boolean)(arg0)).booleanValue()");

        instruction = new EzyInstruction("", "", true);
        instruction.cast(byte.class, "arg0");
        assertEquals(instruction.toString(false), "((java.lang.Byte)(arg0)).byteValue()");

        instruction = new EzyInstruction("", "", true);
        instruction.cast(char.class, "arg0");
        assertEquals(instruction.toString(false), "((java.lang.Character)(arg0)).charValue()");

        instruction = new EzyInstruction("", "", true);
        instruction.cast(double.class, "arg0");
        assertEquals(instruction.toString(false), "((java.lang.Double)(arg0)).doubleValue()");

        instruction = new EzyInstruction("", "", true);
        instruction.cast(float.class, "arg0");
        assertEquals(instruction.toString(false), "((java.lang.Float)(arg0)).floatValue()");

        instruction = new EzyInstruction("", "", true);
        instruction.cast(int.class, "arg0");
        assertEquals(instruction.toString(false), "((java.lang.Integer)(arg0)).intValue()");

        instruction = new EzyInstruction("", "", true);
        instruction.cast(long.class, "arg0");
        assertEquals(instruction.toString(false), "((java.lang.Long)(arg0)).longValue()");

        instruction = new EzyInstruction("", "", true);
        instruction.cast(short.class, "arg0");
        assertEquals(instruction.toString(false), "((java.lang.Short)(arg0)).shortValue()");

        instruction.append(instruction);

        instruction = new EzyInstruction("", "", true);
        instruction.valueOf(char.class, "'a'", true);
        System.out.println(instruction);
        instruction = new EzyInstruction("", "", true);
        instruction.valueOf(Character.class, "answer", true);
        System.out.println(instruction);
    }

    @Test
    public void defaultValueTest() {
        EzyInstruction instruction = new EzyInstruction("", "", false)
                .defaultValue(boolean.class);
        assert instruction.toString().equals("false");

        instruction = new EzyInstruction("", "", false)
                .defaultValue(byte.class);
        assert instruction.toString().equals("(byte)0");

        instruction = new EzyInstruction("", "", false)
                .defaultValue(char.class);
        assert instruction.toString().equals("(char)0");

        instruction = new EzyInstruction("", "", false)
                .defaultValue(double.class);
        assert instruction.toString().equals("0D");

        instruction = new EzyInstruction("", "", false)
                .defaultValue(float.class);
        assert instruction.toString().equals("0F");

        instruction = new EzyInstruction("", "", false)
                .defaultValue(int.class);
        assert instruction.toString().equals("0");

        instruction = new EzyInstruction("", "", false)
                .defaultValue(long.class);
        assert instruction.toString().equals("0L");

        instruction = new EzyInstruction("", "", false)
                .defaultValue(short.class);
        assert instruction.toString().equals("(short)0");

        instruction = new EzyInstruction("", "", false)
                .defaultValue(String.class);
        assert instruction.toString().equals("null");
    }

    public static class A {

    }

}
