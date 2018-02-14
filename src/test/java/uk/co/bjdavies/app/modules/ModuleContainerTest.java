package uk.co.bjdavies.app.modules;

import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

public class ModuleContainerTest
{

    /**
     * This is the instance of container.
     */
    private ModuleContainer container;
    /**
     * This is a test module to be used to add to the container.
     */
    private Module module;

    private TestLogger testLogger = TestLoggerFactory.getTestLogger(ModuleContainer.class);


    @org.junit.Before
    public void setUp() throws Exception
    {
        container = new ModuleContainer(null);
        module = new Module()
        {
            @Override
            public String getName()
            {
                return "Test";
            }

            @Override
            public String getVersion()
            {
                return "1";
            }

            @Override
            public String getAuthor()
            {
                return "Ben Davies";
            }
        };
    }

    @org.junit.After
    public void tearDown() throws Exception
    {
        testLogger.clearAll();
    }

    @org.junit.Test
    public void testAddModule() throws Exception
    {
        //Test adding the same name
        container.addModule("test", module);
        container.addModule("test", module);
//        assertThat(testLogger.getLoggingEvents()).extracting("level", "message").contains(
//                tuple(Level.ERROR, "The key or module is already in the container")
//        );
//        assertThat(testLogger.getLoggingEvents()).extracting("level", "message").contains(
//                tuple(Level.ERROR, "The key or module is already in the container")
//        );
    }
}