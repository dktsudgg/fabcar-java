/*
SPDX-License-Identifier: Apache-2.0
*/

package org.example;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ClientApp2 {

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
		builder.identity(wallet, "appUsergcp").networkConfig(networkConfigPath).discovery(true);

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
				contracts.add( network.getContract("sso") );
			}

			class CustomRunnable implements Runnable{

				Contract cr;
				int number;

				ArrayList<Double> latencies;

				public CustomRunnable(Contract c, int n){
					this.cr = c;
					this.number = n;

					this.latencies = new ArrayList<Double>();
				}

				@Override
				public void run() {

					try {
						for (int i = 0; i < 10; i++) {
							long unit_start = System.currentTimeMillis();

							// 조회 트랜잭션 1
//							byte[] result = cr.evaluateTransaction("queryAllCars");
//							System.out.println(new String(result));

							// 조회 트랜잭션 2
//							byte[] result = cr.evaluateTransaction("queryCar", "CAR128");
//							System.out.println(new String(result));

							// 업데이트 트랜잭션
							byte[] result = cr.submitTransaction("queryLedger", "PUBKEY"+this.number, "1" );
							System.out.println(new String(result));

							long unit_end = System.currentTimeMillis();
							double unit_latency = (unit_end - unit_start) / 1000.0;
							this.latencies.add(unit_latency);
						}
					} catch (Exception e) {
						e.printStackTrace();
						isErrorDetected = true;
					}

				}

			}

			CustomRunnable cr1 = new CustomRunnable(contracts.get(0), 0);	CustomRunnable cr2 = new CustomRunnable(contracts.get(1), 1);
			CustomRunnable cr3 = new CustomRunnable(contracts.get(2), 2);	CustomRunnable cr4 = new CustomRunnable(contracts.get(3), 3);
			CustomRunnable cr5 = new CustomRunnable(contracts.get(4), 4);	CustomRunnable cr6 = new CustomRunnable(contracts.get(5), 5);
			CustomRunnable cr7 = new CustomRunnable(contracts.get(6), 6);	CustomRunnable cr8 = new CustomRunnable(contracts.get(7), 7);
			CustomRunnable cr9 = new CustomRunnable(contracts.get(8), 8);	CustomRunnable cr10 = new CustomRunnable(contracts.get(9), 9);
			CustomRunnable cr11 = new CustomRunnable(contracts.get(10), 10);	CustomRunnable cr12 = new CustomRunnable(contracts.get(11), 11);
			CustomRunnable cr13 = new CustomRunnable(contracts.get(12), 12);	CustomRunnable cr14 = new CustomRunnable(contracts.get(13), 13);
			CustomRunnable cr15 = new CustomRunnable(contracts.get(14), 14);	CustomRunnable cr16 = new CustomRunnable(contracts.get(15), 15);
			Thread th1 = new Thread( cr1 );Thread th2 = new Thread( cr2 );
			Thread th3 = new Thread( cr3 );Thread th4 = new Thread( cr4 );
			Thread th5 = new Thread( cr5 );Thread th6 = new Thread( cr6 );
			Thread th7 = new Thread( cr7 );Thread th8 = new Thread( cr8 );
			Thread th9 = new Thread( cr9 );Thread th10 = new Thread( cr10 );
			Thread th11 = new Thread( cr11 );Thread th12 = new Thread( cr12 );
			Thread th13 = new Thread( cr13 );Thread th14 = new Thread( cr14 );
			Thread th15 = new Thread( cr15 );Thread th16 = new Thread( cr16 );

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

			// 평균 latency 계산..
			ArrayList<Double> whole_latencies = new ArrayList<Double>();
			whole_latencies.addAll(cr1.latencies);	whole_latencies.addAll(cr2.latencies);
			whole_latencies.addAll(cr3.latencies);	whole_latencies.addAll(cr4.latencies);
			whole_latencies.addAll(cr5.latencies);	whole_latencies.addAll(cr6.latencies);
			whole_latencies.addAll(cr7.latencies);	whole_latencies.addAll(cr8.latencies);
			whole_latencies.addAll(cr9.latencies);	whole_latencies.addAll(cr10.latencies);
			whole_latencies.addAll(cr11.latencies);	whole_latencies.addAll(cr12.latencies);
			whole_latencies.addAll(cr13.latencies);	whole_latencies.addAll(cr14.latencies);
			whole_latencies.addAll(cr15.latencies);	whole_latencies.addAll(cr16.latencies);
			double latencies_sum = 0;
			double max_latency = -0.1;
			double min_latency = 9999.0;
			for(int i=0; i<whole_latencies.size(); i++){
				double ltc = whole_latencies.get(i);
				latencies_sum += ltc;

				if(ltc > max_latency)
					max_latency = ltc;

				if(ltc < min_latency)
					min_latency = ltc;
			}
			System.out.println("총 요청 수 : " + whole_latencies.size());
			System.out.println("응답 TPS : " + whole_latencies.size()/((end - start) / 1000.0) );
			System.out.println("평균 latency : " + latencies_sum / (double)whole_latencies.size());
			System.out.println("가장 큰 latency : " + max_latency);
			System.out.println("가장 작은 latency : " + min_latency);

			if (isErrorDetected)
				System.out.println("에러 있음!!");
			else
				System.out.println("에러 없음.");

		}

	}

	public static void main___old(String[] args) throws Exception {
		// Load a file system based wallet for managing identities.
		Path walletPath = Paths.get("wallet");
		Wallet wallet = Wallet.createFileSystemWallet(walletPath);
		// load a CCP
		Path networkConfigPath = Paths.get("connection-org1.yaml");

		Gateway.Builder builder = Gateway.createBuilder();
		builder.identity(wallet, "appUsergcp").networkConfig(networkConfigPath).discovery(true);

		// create a gateway connection
		try (Gateway gateway = builder.connect()) {

			// get the network and contract
			Network network = gateway.getNetwork("mychannel");
			Contract contract = network.getContract("sso");

			byte[] result;

			for(int i=6; i<=10000; i++){
				contract.submitTransaction("createUserPublicKey", "PUBKEY"+i, "ci_"+i, "publicKey_"+i);
				System.out.println("PUBKEY"+i+" is created..");
			}

//			contract.submitTransaction("createCar", "CAR41", "VW", "Polo", "Grey", "Mary");
//
//			result = contract.evaluateTransaction("queryCar", "CAR41");
//			System.out.println(new String(result));
		}
	}

}
