/*
SPDX-License-Identifier: Apache-2.0
*/

package org.example;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;

public class ClientApp {

	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "false");
	}

	static boolean isErrorDetected;

	// 스레드 8개로 각각 12개씩 조회 트랜잭션 날리는 코드.. 네트워크는 하나이고 컨트랙트는 각 스레드별로 하나씩 주었음.
	public static void main(String[] args) throws Exception {
		// Load a file system based wallet for managing identities.
		Path walletPath = Paths.get("wallet");
		Wallet wallet = Wallet.createFileSystemWallet(walletPath);
		// load a CCP
		Path networkConfigPath = Paths.get("connection-org1.yaml");

		Gateway.Builder builder = Gateway.createBuilder();
		builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);

		isErrorDetected = false;

		try (Gateway gateway = builder.connect()) {

			// get the network and contract
			Network network = gateway.getNetwork("mychannel");
//			Contract contract = network.getContract("fabcar");

//			ArrayList<Network> networks = new ArrayList<>();
			ArrayList<Contract> contracts = new ArrayList<>();
			for(int i=0; i< 16; i++){
//				Network network = gateway.getNetwork("mychannel");
//				networks.add(network);
				contracts.add( network.getContract("fabcar") );
			}

			class CustomRunnable implements Runnable{

				Contract cr;
				int number;

				public CustomRunnable(Contract c, int n){
					this.cr = c;
					this.number = n;
				}

				@Override
				public void run() {

					try {
						for (int i = 0; i < 160; i++) {
							// 조회 트랜잭션 1
							byte[] result = cr.evaluateTransaction("queryAllCars");
							System.out.println(new String(result));

							// 조회 트랜잭션 2
//							byte[] result = cr.evaluateTransaction("queryCar", "CAR4");
//							System.out.println(new String(result));

							// 업데이트 트랜잭션
//							cr.submitTransaction("changeCarOwner", "CAR"+this.number, "Archie");
						}
					} catch (Exception e) {
						e.printStackTrace();
						isErrorDetected = true;
					}

				}

			}

			Thread th1 = new Thread( new CustomRunnable(contracts.get(0), 0) );Thread th2 = new Thread( new CustomRunnable(contracts.get(1), 1) );
			Thread th3 = new Thread( new CustomRunnable(contracts.get(2), 2) );Thread th4 = new Thread( new CustomRunnable(contracts.get(3), 3) );
			Thread th5 = new Thread( new CustomRunnable(contracts.get(4), 4) );Thread th6 = new Thread( new CustomRunnable(contracts.get(5), 5) );
			Thread th7 = new Thread( new CustomRunnable(contracts.get(6), 6) );Thread th8 = new Thread( new CustomRunnable(contracts.get(7), 7) );
			Thread th9 = new Thread( new CustomRunnable(contracts.get(8), 8) );Thread th10 = new Thread( new CustomRunnable(contracts.get(9), 9) );
			Thread th11 = new Thread( new CustomRunnable(contracts.get(10), 10) );Thread th12 = new Thread( new CustomRunnable(contracts.get(11), 11) );
			Thread th13 = new Thread( new CustomRunnable(contracts.get(12), 12) );Thread th14 = new Thread( new CustomRunnable(contracts.get(13), 13) );
			Thread th15 = new Thread( new CustomRunnable(contracts.get(14), 14) );Thread th16 = new Thread( new CustomRunnable(contracts.get(15), 15) );

			long start = System.currentTimeMillis();

			th1.start();th2.start();th3.start();th4.start();
			th5.start();th6.start();th7.start();th8.start();
			th9.start();th10.start();th11.start();th12.start();
			th13.start();th14.start();th15.start();th16.start();

			th1.join();th2.join();th3.join();th4.join();
			th5.join();th6.join();th7.join();th8.join();
			th9.join();th10.join();th11.join();th12.join();
			th13.join();th14.join();th15.join();th16.join();

			long end = System.currentTimeMillis();
			System.out.println("실행 시간 : " + (end - start) / 1000.0);
			if (isErrorDetected)
				System.out.println("에러 있음!!");
			else
				System.out.println("에러 없음.");

		}

	}

	public static void main__________old(String[] args) throws Exception {
		// Load a file system based wallet for managing identities.
		Path walletPath = Paths.get("wallet");
		Wallet wallet = Wallet.createFileSystemWallet(walletPath);
		// load a CCP
		Path networkConfigPath = Paths.get("connection-org1.yaml");

		Gateway.Builder builder = Gateway.createBuilder();
		builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);

		// create a gateway connection
		try (Gateway gateway = builder.connect()) {

			// get the network and contract
			Network network = gateway.getNetwork("mychannel");
			Contract contract = network.getContract("fabcar");

			byte[] result;

			result = contract.evaluateTransaction("queryAllCars");
			System.out.println(new String(result));

			contract.submitTransaction("createCar", "CAR10", "VW", "Polo", "Grey", "Mary");
			contract.submitTransaction("createCar", "CAR11", "VW", "Polo", "Grey", "Mary");
			contract.submitTransaction("createCar", "CAR12", "VW", "Polo", "Grey", "Mary");
			contract.submitTransaction("createCar", "CAR13", "VW", "Polo", "Grey", "Mary");
			contract.submitTransaction("createCar", "CAR14", "VW", "Polo", "Grey", "Mary");
			contract.submitTransaction("createCar", "CAR15", "VW", "Polo", "Grey", "Mary");
			contract.submitTransaction("createCar", "CAR16", "VW", "Polo", "Grey", "Mary");

//			contract.submitTransaction("createCar", "CAR41", "VW", "Polo", "Grey", "Mary");
//
//			result = contract.evaluateTransaction("queryCar", "CAR41");
//			System.out.println(new String(result));

			contract.submitTransaction("changeCarOwner", "CAR4", "Archie");

			result = contract.evaluateTransaction("queryCar", "CAR4");
			System.out.println(new String(result));

//			contract.submitTransaction("createCar", "CAR108", "VW", "Polooo", "Grey", "Kyoujin");
//			result = contract.evaluateTransaction("queryCar", "CAR108");
//			System.out.println(new String(result));
		}
	}

}
