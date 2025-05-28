package CortexPolicyRates;

public class CortexMain {
	
	public static void main(String[] arg)
	{
		ReadDataFiles rdg=new ReadDataFiles();
		
		rdg.readRatesFiles();

		rdg.calculateRates("20221212ARG----/DA/UALL/SCALL/OY/012M/999K/A/Mo096/Mi042/Mo---Mi---/VR06/£000/FTD/VU-/CC-/VCC/PP-/LB80/CA---/TNA/WD---/DO---/FO-/	","ARG.63828400.02.01	", "16-01-2024","NA");

		System.out.println("newChange");
		
		System.out.println("newChange by workspace 2");
		
		System.out.println("newChange by workspace 1");
	}

}
