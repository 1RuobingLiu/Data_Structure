//Ruobing Liu
//2022F CS 570-WS
/**
 * This program illustrates a Class named BinaryNumber, to implement operations including addition on binary calculations,
   using array as binary number format. 
 * In this program, little-endian format is chosen for binary representation. And each of the index of binary numbers is started from 0 to the number_length-1.
 */

import java.util.Arrays;
public class BinaryNumber {
	//Binary number array: data; boolean to show the overflow result of addition: overflow.
	private int[] data;
	private boolean overflow;
	
	//Two constructors to create binary number from input length or binary string.
	public BinaryNumber(int length) {
		data=new int[length];
		if (length<1) {
			System.out.print("Illegal input. Please enter a positive integar.");
		}
		else {
			for (int i=0; i<length; i++) {
				data[i] = 0;
			}
		}
	}
	
	public BinaryNumber(String str) {
		data=new int[str.length()];
		for (int i=0; i<str.length();i++) {
			data[i]= Character.getNumericValue(str.charAt(i));
			if (data[i]!=1 && data[i]!=0) {
				System.out.print("This is not for binary! Please enter String containing only 1 or 0.");
			}
		}
	}
	
	//To get the length of binary number and the number digit on the input index.
	public int getLength() {
		return data.length;
	}
	public int getDigit(int index) {
		if (index>=data.length) {
			System.out.printf("The index %d is out of bounds",index);
		}
		else if(data[index]>1) {
			System.out.printf("The index %d number %d is not binary",index,data[index]);
		}
		return data[index];
	}

	//To result certain amount of 0 on the left of the binary number. 
	//Using Arrays.copyOf on the original data binary number array and creating another array. 
	public void shiftR(int amount) {
		int ori_len=data.length;
		data=Arrays.copyOf(data,data.length+amount);
		int[] arr=Arrays.copyOf(data,data.length);
		//The data array will be modified according to loop index of the other array.
		for (int i=0;i<amount;i++) {
			data[i]=arr[ori_len+i];
		}
		for (int j=0;j<ori_len;j++) {
			data[amount+j]=arr[j];
		}
	}
	
	//Addition of the binary number that receives the message and the other given as a parameter, aBinaryNumber. 
	//For the last, middle and the first index numbers calculation, there are different process.
	public void add(BinaryNumber aBinaryNumber) {
		//Compare if the length of the two numbers coincide. If so, continue processing.
		if (getLength()!=aBinaryNumber.getLength()) {
			System.out.print("The lengths of binary numbers do not coincide, illegal binary calculation.");
		}
		else {
			//The extended length (overflow) determination based on consideration of the numbers of last index.
			if (data[aBinaryNumber.getLength()-1]+aBinaryNumber.getDigit(aBinaryNumber.getLength()-1)>0) {
				overflow=true;
				data=Arrays.copyOf(data,aBinaryNumber.getLength()+1);
				data[aBinaryNumber.getLength()]=1;
			}
			else {
				overflow=false;
			}
			
			int carried=0;
			/**
			//The numbers of first index calculation.
			if (data[0]+aBinaryNumber.getDigit(0)==1) {
				data[0]=1;
			}
			else if (data[0]+aBinaryNumber.getDigit(0)==2) {
				data[0]=0;
				carried=1;
			}
			else {
				data[0]=0;
			}
			**/
			//The numbers of first and middle indexes calculation.
			for (int j=0;j<aBinaryNumber.getLength();j++) {
				if (carried+data[j]+aBinaryNumber.getDigit(j)==1) {
					data[j]=1;
				}
				else if (carried+data[j]+aBinaryNumber.getDigit(j)==2) {
					data[j]=0;
					carried=1;
				}
				else if (carried+data[j]+aBinaryNumber.getDigit(j)==0){
					data[j]=0;
				}
				else {
					data[j]=1;
					carried=1;
				}
			}

			
			
		}
	}
	
	//To get the final binary number in the format of string.
	public String toString() {
		
		if (overflow==true) {
			System.out.print("Overflow!");
		}
		//Transforming each of the integer elements in the data array to string, for output.
		String binary_num_str= Integer.toString(data[0]);
		for(int i = 1; i < data.length; i++) {
			binary_num_str = binary_num_str + Integer.toString(data[i]);
		}
		return binary_num_str;
	}
	
	//To output and transform the binary number to decimal number; little-endian format (from the left to right index, the power of 2 is 0 to the length of binary number-1).
	public int toDecimal()  {
		int decimal_num=0;
		for (int i=0;i<data.length;i++) {
			decimal_num+=data[i]*Math.pow(2, i);	
		}
		return decimal_num;
	}
	
	//Clear the number on the last index, if the binary number is resulted from overflow addition.
	public void clearOverflow() {
		if (overflow==true) {
			overflow=false;
			data=Arrays.copyOf(data,data.length-1);
		}
	}
	
	public static void main(String[] args) {
		BinaryNumber test=new BinaryNumber("100");
		BinaryNumber aBinaryNumber=new BinaryNumber("010");
		test.add(aBinaryNumber);
		
		System.out.print(test.toString());
		
		
	}
}






