/*
SPDX-License-Identifier: Apache-2.0
*/

package org.example;

import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Properties;
import java.util.Set;

import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallet.Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

public class RegisterUser {

	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "false");
	}

	public static void main(String[] args) throws Exception {

		// Create a CA client for interacting with the CA.
		Properties props = new Properties();
		props.put("pemFile",
			"ca.org1.example.com-cert.pem");
		props.put("allowAllHostNames", "true");
		HFCAClient caClient = HFCAClient.createNewInstance("https://34.64.151.5:7054", props);
		CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
		caClient.setCryptoSuite(cryptoSuite);

		// Create a wallet for managing identities
		Wallet wallet = Wallet.createFileSystemWallet(Paths.get("wallet"));

		// Check to see if we've already enrolled the user.
		boolean userExists = wallet.exists("appUsergcp");
		if (userExists) {
			System.out.println("An identity for the user \"appUser\" already exists in the wallet");
			return;
		}

		userExists = wallet.exists("admingcp");
		if (!userExists) {
			System.out.println("\"admin\" needs to be enrolled and added to the wallet first");
			return;
		}

		Identity adminIdentity = wallet.get("admingcp");
		User admin = new User() {

			@Override
			public String getName() {
				return "admingcp";
			}

			@Override
			public Set<String> getRoles() {
				return null;
			}

			@Override
			public String getAccount() {
				return null;
			}

			@Override
			public String getAffiliation() {
				return "org1.department1";
			}

			@Override
			public Enrollment getEnrollment() {
				return new Enrollment() {

					@Override
					public PrivateKey getKey() {
						return adminIdentity.getPrivateKey();
					}

					@Override
					public String getCert() {
						return adminIdentity.getCertificate();
					}
				};
			}

			@Override
			public String getMspId() {
				return "Org1MSP";
			}

		};

		// Register the user, enroll the user, and import the new identity into the wallet.
		RegistrationRequest registrationRequest = new RegistrationRequest("appUsergcp");
		registrationRequest.setAffiliation("org1.department1");
		registrationRequest.setEnrollmentID("appUsergcp");
		String enrollmentSecret = caClient.register(registrationRequest, admin);
		Enrollment enrollment = caClient.enroll("appUsergcp", enrollmentSecret);
		Identity user = Identity.createIdentity("Org1MSP", enrollment.getCert(), enrollment.getKey());
		wallet.put("appUsergcp", user);
		System.out.println("Successfully enrolled user \"appUser\" and imported it into the wallet");
	}

}
