package org.bouncycastle.pqc.crypto.lms;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.bouncycastle.test.PrintTestResult;

public class AllTests
    extends TestCase
{
    public static void main(String[] args)
    {
       PrintTestResult.printResult( junit.textui.TestRunner.run(suite()));
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite("Lightweight LMS PQ Crypto Tests");

        suite.addTestSuite(HSSTests.class);
        suite.addTestSuite(LMSKeyGenTests.class);
        suite.addTestSuite(LMSTests.class);
        suite.addTestSuite(TypeTests.class);

        return new BCTestSetup(suite);
    }
    
    static class BCTestSetup
        extends TestSetup
    {
        public BCTestSetup(Test test)
        {
            super(test);
        }

        protected void setUp()
        {

        }

        protected void tearDown()
        {

        }
    }
}
