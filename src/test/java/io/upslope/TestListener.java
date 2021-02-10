package io.upslope;

import org.junit.jupiter.api.extension.*;

import java.io.PrintWriter;
import java.io.StringWriter;

public class TestListener implements
BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback, BeforeTestExecutionCallback, AfterTestExecutionCallback {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
//        System.out.println("BEFORE ALL");
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
//        System.out.println("BEFORE EACH");
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
//        System.out.println("AFTER ALL");
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
//        System.out.println("AFTER EACH");
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
//        System.out.printf("BEFORE TEST EXECUTION %s%n", context.getDisplayName());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
//        System.out.printf("AFTER TEST EXECUTION %s%n", context.getDisplayName());
        context.getExecutionException().ifPresent(throwable -> {
            StringWriter errors = new StringWriter();
            throwable.printStackTrace(new PrintWriter(errors));
//            System.out.println(errors.toString());
        });
    }
}
