import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author pandrew
 *
 */

abstract class Customer {
	private String firstname, lastname;
	private String phonenumber;
	private int employeeid;
	private String address[]; 
	private double balance;
	
	public Customer(String firstname, String lastname, String phonenumber, int employeeid, String[] address,double balance) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.phonenumber = phonenumber;
		this.employeeid = employeeid;
		this.address = address;
		this.balance = balance;
	}
	
	public String getFirstname() { 	return firstname;	}
	public String getLastname() { 	return lastname;	}
	public String getPhonenumber() {	return phonenumber;		}
	public int getEmployeeid() {	return employeeid;		}
	public String getAddress() {
		return (address[0] + "-" + address[1] + ", " + address[2] + ", " + address[3]) ;
	}
	public double getBalance(){	return balance;	}
	public abstract String toString();
}

class ABCustomer extends Customer{

	public ABCustomer(String firstname, String lastname, String phonenumber, int employeeid,String city,String pincode,String area,String state,double balance) {
		super(firstname, lastname, phonenumber, employeeid, new String[]{city,pincode,area,state},balance);
		//Address for ABBank includes - city,pincode,area,state;
	}
	
	public String toString(){
		String str = "\nBank ABC\n";
		str += ("Name : " + getFirstname() + " " + getLastname() + "\n");
		str += ("Employee ID : " + getEmployeeid() + "\n");
		str += ("Address : " + getAddress() + "\n");
		str += ("Account Balance : Rs. " + getBalance());
		return str;	
	}
	
	public boolean equals(String firstname,String lastname){
		return(getFirstname().equals(firstname) && getLastname().equals(lastname));
	}
}

//----------------------------------------------------------------------------------------------------

class Loan  {
	int typeofloan;
	//1 = Housing Loan 2 = Education Loan 3 = Personal Loan 4 = Travel Loan
	ABCustomer account;
	double principal,interest,rate,time;
	
	public Loan(int typeofloan,ABCustomer account,double time) {
		this.typeofloan = typeofloan;
		this.account = account;
		this.time = time;
		switch(typeofloan){
		case 1:
			this.rate = 6;
			break;
		case 2:
			this.rate = 8;
			break;
		case 3:
			this.rate = 5;
			break;
		case 4:
			this.rate=1;
			break;
		}
	}

	public void setPrincipal(double principal) {	this.principal = principal;		}
	public void setInterest(double interest) {	this.interest = interest;	}

	@Override
	public String toString() {
		String str = "\nLOAN DETAILS :";
		str += ("\nPrincipal amount : Rs. " + principal);
		str += ("\nInterest : Rs. " + Math.round(interest));
		str += ("\nTotal Amount : Rs. " + Math.round(principal+interest));
		return str;
	}
}

//-------------------------------------------------------------------------------------------------------------

public class Bank {

	//Storing the customer account details
	public static ArrayList<ABCustomer> customerlist = null;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		//Static account details loaded into the arraylist
		customerlist = new ArrayList<ABCustomer>();
		customerlist.add(new ABCustomer("James","Philip","9878481923",12345,"CityA","500102","Street XYZ","StateA",1000000));
		customerlist.add(new ABCustomer("Sarah","Matthew","8795931921",67809,"CityA","500110","Street LMN","StateA",2500000));
		customerlist.add(new ABCustomer("Andrew","Winston","9672818372",17283,"CityA","500073","Street ABC","StateA",3000000));
		customerlist.add(new ABCustomer("Rebecca","Jacob","9574839129",62738,"CityA","500101","Street PQR","StateA",5000000));
		
		System.out.print("Enter the first name : ");
		String firstname = sc.next();
		System.out.print("Enter the last name : ");
		String lastname = sc.next();
		
		//Locating the customer account
		ABCustomer customer = findCustomer(firstname,lastname);
		
		System.out.println(customer.toString());
		
		
		//Loan
		System.out.println();
		System.out.println("LOAN\n1. Housing Loan 2. Education Loan 3. Personal Loan 4. Travel Loan");
		System.out.print("Enter yout choice : ");
		int choice = sc.nextInt();
		System.out.print("Enter the number of years for which loan is required : ");
		double time = sc.nextDouble();
		System.out.print("Enter the first name of the parent : ");
		String pfirstname = sc.next();
		System.out.print("Enter the last name of the parent : ");
		String plastname = sc.next();
		Loan loan = new Loan(choice,customer,time);
		
		//Function to check if customer is eligible for a loan
		boolean sanction = sanctionLoan(loan,pfirstname,plastname);
		
		//Loan calculation
		if(sanction){
			System.out.print("\nEnter the principal amount for the loan (Should not exceed Rs.1 Crore) : ");
			double principal = sc.nextDouble();
			loan.setPrincipal(principal);
			calculateLoan(loan);
		}	
		
		System.out.println(loan.toString());
	}
	
	static ABCustomer findCustomer(String firstname,String lastname){
		for(ABCustomer customer : customerlist){
			if(customer.equals(firstname,lastname))
				return customer;
		}
		return null;
	}

	static boolean sanctionLoan(Loan loan,String pfirstname,String plastname){
		switch(loan.typeofloan){
		
		case 1: //Housing Loan
		if(loan.time >= 5){
			System.out.println("Maximum duration for Housing Loan is " + 5 + "years");
			System.out.println("Process failed and exited");
			return false;
		}
		if(loan.account.getBalance() < 1000000){
			System.out.println("Minimum account balance for Housing Loan is Rs." + 1000000);
			System.out.println("Process failed and exited");
			return false;
		}
		System.out.println("The account holder is eligible for a Housing Loan");
		return true;
		
		case 2: //Education Loan
		if(loan.time >= 6){
			System.out.println("Maximum duration for Education Loan is 6 years");
			System.out.println("Process failed and exited");
			return false;
		}
		ABCustomer parent = findCustomer(pfirstname,plastname);
		if(parent.getBalance() < 1000000){
			System.out.println("Minimum account balance for Education Loan is Rs. 1000000");
			System.out.println("Process failed and exited");
				return false;
		}
		System.out.println("The account holder is eligible for an Education Loan");
		return true;
		
		case 3: //Personal Loan
		if(loan.time >= 1){
			System.out.println("Maximum duration for Personal Loan is 1 year");
			System.out.println("Process failed and exited");
			return false;
		}
		System.out.println("The account holder is eligible for a Personal Loan");
		return true;
		
		case 4: //Travel Loan
		if(loan.time >= 1){
			System.out.println("Maximum duration for Travel Loan is 1 year");
			System.out.println("Process failed and exited");
			return false;
		}
		if(loan.account.getBalance() < 5000000){
			System.out.println("Minimum account balance for Travel Loan is Rs. 5000000");
			System.out.println("Process failed and exited");
			return false;
		}
		System.out.println("The account holder is eligible for a Travel Loan");
		return true;
		
		case 5:
		System.out.println("Invalid Choice");
		System.out.println("Process failed and exited");
		return false;
		}
		return false;
	}
	
	static void calculateLoan(Loan loan){
		double interest;
		switch(loan.typeofloan){
		case 1: case 2: case 3: //Housing, Education, Personal Loan = Compound Interest
		double temp = Math.pow((1 + (loan.rate/100)),loan.time);
		interest = loan.principal * temp;
		loan.setInterest(interest);
		break;
		case 4: //Travel loan = Simple Interest
		interest = (loan.principal * loan.interest * loan.rate)/100;
		loan.setInterest(interest);
		break;
		}	
	}
}
