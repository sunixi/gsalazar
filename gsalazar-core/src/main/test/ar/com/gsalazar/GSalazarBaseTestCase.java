/**
 * 
 */
package ar.com.gsalazar;

import org.junit.Test;

import com.angel.test.GenericSpringTestCase;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class GSalazarBaseTestCase extends GenericSpringTestCase{

	private static final String[] testContexts =
		new String[]
	    {
			"classpath:applicationContext-gsflex.xml",
			"classpath:applicationContext-gsdaos.xml",
			"classpath:applicationContext-gsservices.xml",
			"classpath:applicationContext-gsmodel.xml"
	    };
	
	@Override
	protected String[] getOtherContextsApplicationFiles() {
		return testContexts;
	}
	
	@Test
	public void testNothing(){
		//Do nothing
	}

}
