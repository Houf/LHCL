package ca.houf.test.helper;

import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Ignore
@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class TestHelper
{
    @Rule public final JUnitRuleMockery mocker = new JUnitRuleMockery()
    {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    @After
    public void done()
    {
        mocker.assertIsSatisfied();
    }
}
