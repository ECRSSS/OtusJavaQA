package task1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import task1.suites.SuiteOne;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SuiteOne.class
})

public class SuiteRunner {
    // the class remains empty,
    // used only as a holder for the above annotations
}