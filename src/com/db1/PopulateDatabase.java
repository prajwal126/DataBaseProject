package com.db1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class PopulateDatabase {
	static final String DB_URL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=acaddbprod-1.uta.edu)(PORT=1523)))(CONNECT_DATA=(SERVICE_NAME=pcse1p.data.uta.edu)))";

	static final String USER = "pxp0483"; // rxm4662
	static final String PASS = "Transmission123"; // Rajath01121992

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");

			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connection successfull...");
			stmt = conn.createStatement();

			System.out.println(
					"1. Load and Display Data \n2.Insert Data \n3.Retrieve all privileges associate with a particular ROLE and with a particular USER_ACCOUNT \n4.Check account privilege \n5.Exit");

			Scanner input = new Scanner(System.in);
			int selection = input.nextInt();

			while (selection < 5) {
				switch (selection) {
				case 1:
					System.out.println("Load and Display Data");
					File fileDirectory = new File("./DataFiles");
					for (File file : fileDirectory.listFiles()) {
						FileReader fr = new FileReader(file); // reads the file
						BufferedReader br = new BufferedReader(fr); // creates a buffering character input stream
						String line;
						String dataFile = file.getName();
						System.out.println(dataFile);
						switch (dataFile.substring(0, dataFile.indexOf("."))) {
						/*
						 * case "1_USER_ROLE": while ((line = br.readLine()) != null) { String[] data =
						 * line.split(","); stmt = conn.createStatement(); String sql =
						 * "INSERT INTO USER_ROLE VALUES (" + data[0] + ", " + data[1] + ")";
						 * stmt.executeUpdate(sql); } fr.close(); break; case "2_USER_ACCOUNT": while
						 * ((line = br.readLine()) != null) { String[] data = line.split(","); stmt =
						 * conn.createStatement();
						 * 
						 * String sql = "INSERT INTO USER_ACCOUNT VALUES (" + data[0] + ", " + data[1] +
						 * ", " + data[2] + ", " + data[3] + ")"; stmt.executeUpdate(sql); } fr.close();
						 * break; case "3_USER_TABLES": while ((line = br.readLine()) != null) {
						 * String[] data = line.split(","); stmt = conn.createStatement();
						 * 
						 * String sql = "INSERT INTO USER_TABLES VALUES (" + data[0] + ", " + data[1] +
						 * ")"; stmt.executeUpdate(sql); } fr.close(); break; case "4_USER_PRIVILEGES":
						 * while ((line = br.readLine()) != null) { String[] data = line.split(",");
						 * stmt = conn.createStatement(); String sql =
						 * "INSERT INTO USER_PRIVILEGES VALUES (" + data[0] + ", " + data[1] + ", " +
						 * data[2] + ", " + data[3] + ", "+ data[4] + ")"; stmt.executeUpdate(sql); }
						 * fr.close(); break; case "5_USER_ACCESS": while ((line = br.readLine()) !=
						 * null) { String[] data = line.split(","); stmt = conn.createStatement();
						 * String sql = "INSERT INTO USER_ACCESS VALUES (" + data[0] + ", " + data[1] +
						 * ", " + data[2] + ", " + data[3] + ")"; stmt.executeUpdate(sql); } fr.close();
						 * break;
						 */
						}
					}
					// Question 2
					System.out.println();
					ResultSet rs = stmt.executeQuery("SELECT * FROM USER_ROLE");
					System.out.println("USER_ROLE TABLE: ");
					System.out.println("ROLE_NAME  |  DESCRIPTION");

					while (rs.next()) {
						String name = rs.getString("ROLE_NAME");
						String desc = rs.getString("ROLE_DESCRIPTION");
						System.out.println(name + "  |  " + desc);
					}
					System.out.println("--------------------------------------------------------");
					ResultSet rs2 = stmt.executeQuery("SELECT * FROM USER_ACCOUNT");
					System.out.println("USER_ACCOUNT TABLE: ");
					System.out.println("IDNO  |  ROLE_NAME  |  NAME  |  PHONE");

					while (rs2.next()) {
						int idno = rs2.getInt("IDNO");
						String role = rs2.getString("ROLE_NAME");
						String name = rs2.getString("NAME");
						String phone = rs2.getString("PHONE");
						System.out.println(idno + "  |  " + role + "  |  " + name + "  |  " + phone);
					}
					System.out.println("--------------------------------------------------------");
					ResultSet rs3 = stmt.executeQuery("SELECT * FROM USER_TABLES");
					System.out.println("USER_TABLES TABLE: ");
					System.out.println("Table_Name  |  IDNO");

					while (rs3.next()) {
						String name = rs3.getString("TABLE_NAME");
						int id = rs3.getInt("IDNO");
						System.out.println(name + "  |  " + id);
					}
					System.out.println("--------------------------------------------------------");
					ResultSet rs4 = stmt.executeQuery("SELECT * FROM USER_PRIVILEGES");
					System.out.println("USER_PRIVILEGES TABLE: ");
					System.out.println(
							"PRIVILEGE_ID  |  PRIVILEGE_NAME  |  ROLE_NAME  |  IS_ACC_PRIVLEGE  |  IS_RELATIONSHIP_PRIVILEGE");

					while (rs4.next()) {
						int id = rs4.getInt("PRIVILEGE_ID");
						String role = rs4.getString("PRIVILEGE_NAME");
						String name = rs4.getString("ROLE_NAME");
						String acc = rs4.getInt("is_account_privilege") == 1 ? "YES" : "NO";
						String rel = rs4.getInt("is_relationship_privilege") == 1 ? "YES" : "NO";
						System.out.println(id + "  |  " + role + "  |  " + name + "  |  " + acc + "  |  " + rel);
					}
					System.out.println("--------------------------------------------------------");
					ResultSet rs5 = stmt.executeQuery("SELECT * FROM USER_ACCESS");
					System.out.println("USER_ACCESS TABLE: ");
					System.out.println("ID  |  PRIVILEGE_ID  |  ROLE_NAME  |  TABLE_NAME");

					while (rs5.next()) {
						int id = rs5.getInt("UNIQUEID");
						int prv = rs5.getInt("PRIVILEGE_ID");
						String role = rs5.getString("ROLE_NAME");
						String tbl = rs5.getString("TABLE_NAME");
						System.out.println(id + "  |  " + prv + "  |  " + role + "  |  " + tbl);
					}

					break;

				case 2:
					int idNo = 0, phoneNumber = 0, isAccountPriv = 0, isRelationshipPriv = 0;
					String userName = "", newRoleName = "", roleDescription = "", tableName = "", tableOwner = "",
							privilegeName = "", privilegeType = "", userAccountRole = "", privilegeRole = "",
							privilageRoleTable = "";
					while (true) {
						System.out.println("Please choose the operation you would like to perform");
						Scanner scanner = new Scanner(System.in);
						System.out.println("1. Enter all the information about a new USER_ACCOUNT.");
						System.out.println("2. Enter all the information about a new ROLE.");
						System.out.println(
								"3. Enter all the information about a new TABLE. This should include specifying the owner user account of the table.");
						System.out.println("4. Enter new PRIVILEGE, including the privilege type.");
						System.out.println("5. Relate a USER_ACCOUNT to a ROLE.");
						System.out.println("6. Relate an ACCOUNT_PRIVILEGE to a ROLE.");
						System.out.println("7. Relate a RELATION_PRIVILEGE, ROLE, and TABLE.");
						System.out.println("8. Back to previous menu.");
						int option = scanner.nextInt();
						scanner.nextLine();
						if (option == 8) {
							break;
						}
						switch (option) {

						case 1:
							System.out.println("Enter user Name");
							userName = scanner.next();
							scanner.nextLine();
							System.out.println("Enter user ID");
							idNo = scanner.nextInt();
							scanner.nextLine();
							System.out.println("Enter user Phone Number");
							phoneNumber = scanner.nextInt();
							scanner.nextLine();
							break;

						case 2:
							System.out.println("Enter Role Name");
							newRoleName = scanner.next();
							scanner.nextLine();
							System.out.println("Enter Role Description");
							roleDescription = scanner.nextLine();
							break;

						case 3:
							System.out.println("Enter Table Name");
							tableName = scanner.next();
							scanner.nextLine();
							System.out.println("Enter Owner ID");
							tableOwner = scanner.next();
							scanner.nextLine();
							break;

						case 4:
							System.out.println("Enter Privilege Name");
							privilegeName = scanner.next();
							scanner.nextLine();
							System.out.println(
									"Enter privilege type. A for account previlege or R for relationship privilege");
							privilegeType = scanner.next();
							scanner.nextLine();
							if (privilegeType.equalsIgnoreCase("A")) {
								isAccountPriv = 1;
							} else {
								isRelationshipPriv = 1;
							}
							break;

						case 5:
							System.out.println("Enter the Role Name for user with user id : " + idNo);
							userAccountRole = scanner.next();
							scanner.nextLine();
							break;

						case 6:
							System.out.println("Enter the account privilege for role : " + newRoleName);
							privilegeRole = scanner.next();
							scanner.nextLine();
							break;
						case 7:
							System.out.println(
									"Enter the privilage name, role and table name to associalte together. Comma separated values.");
							privilageRoleTable = scanner.next();
							scanner.nextLine();
							String[] userACcessRole = privilageRoleTable.split(",");
							ResultSet privRs = stmt.executeQuery("SELECT count(*) as counts FROM USER_PRIVILEGES");
							int privId = 0;
							while (privRs.next()) {
								privId = privRs.getInt("counts") + 1;
							}

							ResultSet userAccessRs = stmt.executeQuery("SELECT count(*) as counts FROM USER_ACCESS");
							int userAccessId = 0;
							while (userAccessRs.next()) {
								userAccessId = userAccessRs.getInt("counts") + 1;
							}

							String usr = "INSERT INTO USER_ACCOUNT VALUES (" + idNo + ",'" + newRoleName + "','"
									+ userName + "'," + phoneNumber + ")";

							String role = "INSERT INTO USER_ROLE VALUES ('" + newRoleName + "','" + roleDescription
									+ "')";
							String tbl = "INSERT INTO USER_TABLES VALUES ('" + tableName + "'," + tableOwner + ")";

							String prv = "INSERT INTO USER_PRIVILEGES VALUES (" + privId + ",'" + privilegeName + "','"
									+ newRoleName + "'," + isAccountPriv + "," + isRelationshipPriv + ")";

							String acc = "INSERT INTO USER_ACCESS VALUES (" + userAccessId + ",'" + privId + "','"
									+ userACcessRole[1] + "','" + userACcessRole[2] + "')";

							stmt.addBatch(role);
							stmt.addBatch(usr);
							stmt.addBatch(tbl);
							stmt.addBatch(prv);
							stmt.addBatch(acc);
							stmt.executeBatch();

							break;

						default:
							break;
						}

					}

				case 3:
					System.out.println(
							"Retrieve all privileges associate with a particular ROLE and with a particular USER_ACCOUNT");
					break;

				case 4:
					System.out.println(
							"Retrieve all privileges associate with a particular ROLE and with a particular USER_ACCOUNT");
					break;

				}
				System.out.println();
				System.out.println(
						"1. Load and Display Data \n2.Insert Data \n3.Retrieve all privileges associate with a particular ROLE and with a particular USER_ACCOUNT \n4.Check account privilege \n5.Exit");
				selection = input.nextInt(); // add this
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		System.out.println("GoodBye!");
	}
}
