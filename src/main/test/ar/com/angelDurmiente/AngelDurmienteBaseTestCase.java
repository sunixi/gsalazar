/**
 * 
 */
package ar.com.angelDurmiente;

import org.junit.Test;

import com.angel.test.GenericSpringTestCase;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class AngelDurmienteBaseTestCase extends GenericSpringTestCase{

	private static final String[] testContexts =
		new String[]
	    {
			"classpath:applicationContext-adflex.xml",
			"classpath:applicationContext-addaos.xml",
			"classpath:applicationContext-adservices.xml",
			"classpath:applicationContext-admodel.xml"
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
