package com.gwn.xcbl.bl.user;

import java.util.ArrayList;
import java.util.List;

import com.gwn.xcbl.data.shared.UserConstants;

import edu.vt.middleware.password.AlphabeticalSequenceRule;
import edu.vt.middleware.password.CharacterCharacteristicsRule;
import edu.vt.middleware.password.DigitCharacterRule;
import edu.vt.middleware.password.LengthRule;
import edu.vt.middleware.password.LowercaseCharacterRule;
import edu.vt.middleware.password.NonAlphanumericCharacterRule;
import edu.vt.middleware.password.NumericalSequenceRule;
import edu.vt.middleware.password.Password;
import edu.vt.middleware.password.PasswordData;
import edu.vt.middleware.password.PasswordValidator;
import edu.vt.middleware.password.QwertySequenceRule;
import edu.vt.middleware.password.RepeatCharacterRegexRule;
import edu.vt.middleware.password.Rule;
import edu.vt.middleware.password.RuleResult;
import edu.vt.middleware.password.UppercaseCharacterRule;
import edu.vt.middleware.password.WhitespaceRule;

/**
 * https://code.google.com/archive/p/vt-middleware/wikis/vtpassword.wiki
 */
public class PwdValidator {

	public List<String> validate(String pwd) {
		PasswordValidator validator = new PasswordValidator(getRules());
		PasswordData passwordData = new PasswordData(new Password(pwd));
		RuleResult result = validator.validate(passwordData);
		
		List<String> errs = new ArrayList<String>();
		if (!result.isValid()) {
			for (String err : validator.getMessages(result)) {
				errs.add(err);
			}
		}
		
		return errs;
	}
	
	protected List<Rule> getRules() {
		LengthRule lengthRule = new LengthRule(UserConstants.PWD_MIN_LENGTH, UserConstants.PWD_MAX_LENGTH);
		WhitespaceRule whitespaceRule = new WhitespaceRule();
		
//		CharacterCharacteristicsRule charRule = new CharacterCharacteristicsRule();
//		charRule.getRules().add(new DigitCharacterRule(1)); // require at least 1 digit in passwords
//		charRule.getRules().add(new NonAlphanumericCharacterRule(1)); // require at least 1 non-alphanumeric char
//		charRule.getRules().add(new UppercaseCharacterRule(1)); // require at least 1 upper case char
//		charRule.getRules().add(new LowercaseCharacterRule(1)); // require at least 1 lower case char
//		charRule.setNumberOfCharacteristics(3); // require at least 3 of the previous rules be met
//		
//		AlphabeticalSequenceRule alphaSeqRule = new AlphabeticalSequenceRule(); // don't allow alphabetical sequences
//		NumericalSequenceRule numSeqRule = new NumericalSequenceRule(3, false); // don't allow numerical sequences of length 3
//		QwertySequenceRule qwertySeqRule = new QwertySequenceRule(); // don't allow qwerty sequences
//		RepeatCharacterRegexRule repeatRule = new RepeatCharacterRegexRule(4); // don't allow 4 repeat characters
		
		List<Rule> rules = new ArrayList<Rule>();
		rules.add(lengthRule);
		rules.add(whitespaceRule);
//		rules.add(charRule);
//		rules.add(alphaSeqRule);
//		rules.add(numSeqRule);
//		rules.add(qwertySeqRule);
//		rules.add(repeatRule);
		
		return rules;
	}
}
