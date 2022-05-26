package com.example.bulksms.Utils;

import java.util.Arrays;
import java.util.List;

public class ForbiddenWords {
	static String[] forbiddenWordsForId = new String[] { "BANK", "Alert", "GTBank", "First Bank", "Ecobank Nigeria",
			"Access Bank", "United Bank", "Diamond Bank.", "UBA bank", "Account", "Facebook", "Whatsapp", "WHATSAPP",
			"NECO", "WAEC", "NECO BOARD", "Weac", "EFCC", "PROMO", "BANK", "Facebook", "BITCOIN", "Scam", "NCC", "Bank",
			"BANK", "Zenith", "Airtel", "Glo", "FBK", "Whtsap", "Whatsap", "Diamond", "UBA", "Sex", "Phone", "ECO",
			"City Bank", "United", "Nigeria", "GHANA", "USA", "USSD", "Money", "Fake", "UNION", "Union Bank", "Code",
			"Google", "Twitter", "Instagram", "BITCOIN", "Invest", "Yahoo", "Gmail", "Online", "Payment", "jamb board",
			"Jamb", "GCE", "Nabteb", "Ijamb" };

	public static List<String> getListOfForbiddenWordId() {
		return Arrays.asList(forbiddenWordsForId);

	}

}