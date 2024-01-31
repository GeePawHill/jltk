package za.co.wethinkcode.flow;

import org.junit.jupiter.api.extension.*;

import java.lang.reflect.*;
import java.util.*;

public class WtcJunitExtension implements TestWatcher, BeforeAllCallback, AfterAllCallback {

    public WtcJunitExtension() {

    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        if (context == null) return;
        passes.add(niceTestName(context));
    }

    private String niceTestName(ExtensionContext context) {
        Method test = context.getRequiredTestMethod();
        return test.getDeclaringClass().getSimpleName() + "." + test.getName();
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        if (context == null) return;
        disables.add(niceTestName(context));
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        if (context == null) return;
        aborts.add(niceTestName(context));
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        if (context == null) return;
        fails.add(niceTestName(context));
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
    }

    static Recorder recorder = new Recorder();
    static List<String> passes = new ArrayList<>();
    static List<String> fails = new ArrayList<>();
    static List<String> disables = new ArrayList<>();
    static List<String> aborts = new ArrayList<>();

    static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            System.out.println("Shutdown");
            recorder.logTest(passes, fails, disables, aborts);
        }
    };
    static Thread hook = new Thread(runnable);
    static Runtime runtime = Runtime.getRuntime();

    static {
        runtime.addShutdownHook(hook);
    }
}
