/**
 * 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.angel.email.account.AccountInformation;
import com.angel.email.account.impl.MockAccountInformation;
import com.angel.email.configuration.EmailConfiguration;
import com.angel.email.configuration.builders.EmailConfigurationBuilder;
import com.angel.email.configuration.builders.impl.yahoo.YahooIncomingEmailConfigurationBuilder;
import com.angel.email.connection.IncomingEmailConnection;
import com.angel.email.dtos.EmailMessageDTO;
import com.angel.email.dtos.RecipientDTO;
import com.angel.email.response.EmailMessagesResponse;

/**
 * 
 * @author Guillermo Daniel Salazar.
 * @since 16/Semptiembre/2009.
 */
public class EmailReaderTest {

	@org.junit.Test
	public void testEmailReader() {
		AccountInformation accountInfo = new MockAccountInformation("guillesalazar1983@yahoo.com.ar", "42416467");
		EmailConfigurationBuilder ecb = new EmailConfigurationBuilder(Boolean.FALSE);
		ecb.setIncomingConfigurationBuilder(new YahooIncomingEmailConfigurationBuilder(accountInfo));
		EmailConfiguration emailConfiguration = ecb.buildEmailConfiguration();
		IncomingEmailConnection emailConnection = emailConfiguration.connectToIncomingEmail();
		EmailMessagesResponse messages = emailConnection.getMessagesForFolder("INBOX", 50);

		List<String> emails = new ArrayList<String>();

		String emailRegEx = "(\\w+)@(\\w+\\.)(\\w+)(\\.\\w+)*";
		Pattern emailPattern = Pattern.compile(emailRegEx);
		for (EmailMessageDTO email : messages.getMessages()) {
			List<RecipientDTO> recipients =  email.getRecipients();
			for(RecipientDTO r: recipients){
				if (!emails.contains(r.getAddress())) {
					System.out.println("Found a match: " + r.getAddress());
					emails.add(r.getAddress());
				}
			}
			if (email.getContent() != null) {
				Matcher matcher = emailPattern.matcher(email.getContent());
				// Find all the matches.
				while (matcher.find()) {
					String emailAddress = matcher.group();
					if (!emails.contains(emailAddress)) {
						System.out.println("Found a match: " + emailAddress);
						emails.add(emailAddress);
					}
				}
			}
		}
	}
}
