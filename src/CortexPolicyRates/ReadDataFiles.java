package CortexPolicyRates;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




public class ReadDataFiles {
	
	public double QBENetPremium;
	public double QBEAdmin;
	public double UWFund;
	public double LondonRemit;
	public double SalesPersonComm;
	public double DealerComm;
	public double ManufacturerComm;
	public double FBFMCommission;
	public double ExcIPT;
	public double IPT;
	public double CustomerPremIncIPT;
	public double SchemeViewerPremiumCommission;
	public double Spare1;
	public String renewalVersion;
	public String sol;
	public String version;
	public String quoteCreationDate;
	public String discountCode;
	public double ipt;
	public double MaxQBENetPremium;
	public	double MaxQBEAdmin;
	public double MaxSalesPersonComm;
	public double MaxDealerCommission;
	public double MaxManufacturerCommission;
	public double MaxFBFMCommission;
	
	public Map<String,Map<String,Map<String,Double>>> rates=new HashMap<>();
	
	public Map<String,Map<String,Map<String,Double>>> ratesOutsideVersion=new HashMap<>();
	
	public String ratesLocation="C:\\Users\\anilk\\eclipse-workspace\\CortexRates\\TestData\\Rates\\";
	
	public String rovLocation="C:\\Users\\anilk\\eclipse-workspace\\CortexRates\\TestData\\RatesOutSideConfiguration\\";
	
	public String[] pathLocation= {ratesLocation,rovLocation};
	
	public String ratesDates[]= {"12-12-2022","19-05-2023","08-08-2023","06-10-2023","06-11-2023","24-11-2023","18-02-2024","22-04-2024","06-06-2024","29-07-2024","30-10-2024","24-02-2025"};
	
	public void readRatesFiles()
	
	{
		for(String path:pathLocation)    
		{
	
		File file=new File(path);
		
		File[] listFiles=file.listFiles();
		
		for(File fileName:listFiles)
		{
			//System.out.println(fileName.getName());
			
			readFiles(fileName.getPath(),path);
			
			
		}
		
		
		}
	}
	
	public void readFiles(String filePath,String path)
	{
		try {
			FileInputStream fi=new FileInputStream(filePath);
			
			XSSFWorkbook workbook=new XSSFWorkbook(fi);
			
			int numberSheets=workbook.getNumberOfSheets();
			
			for(int i=0;i<numberSheets;i++)
			{
				XSSFSheet sheet=workbook.getSheetAt(i);
				
				//System.out.println(sheet.getSheetName());
				
				int lastRow=sheet.getLastRowNum()+1;
				
				for(int j=1;j<lastRow;j++)
				{
					XSSFRow row=sheet.getRow(j);
					
				//System.out.println(j);
					
					BigDecimal bigDecimalValue = new BigDecimal(row.getCell(0).getNumericCellValue());
					
					String version=bigDecimalValue.toPlainString();
					
				//	System.out.println(version);

					String configId=returnString(row.getCell(1));
				
					String key=returnString(row.getCell(2));
					
					//BigDecimal bigDecimalValue1 = new BigDecimal(row.getCell(3).getNumericCellValue());
					
					BigDecimal bigDecimalValue1 = new BigDecimal(row.getCell(3).getNumericCellValue());

					double value=0;
					
				
					
					if(bigDecimalValue1.toPlainString().equals("999999999"))
					{
						DecimalFormat decimalFormat = new DecimalFormat("0");
						String value1=decimalFormat.format(bigDecimalValue1);
						value=Double.parseDouble(value1);
					}
					
					else
					{
						value=row.getCell(3).getNumericCellValue();
					}
					
					//System.out.println(value);
					
					if(path.equalsIgnoreCase(ratesLocation))
					{
					addToRatesMap(version,configId,key,value);
					}
					else
					{
						addToRatesOutsideVersionMap(version,configId,key,value);
					}
				
				}
				
				
			}
			
			workbook.close();
			fi.close();
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addToRatesMap(String version,String configId,String Key,Double relativity)
	{
	
	    /*rates.putIfAbsent(version, new HashMap<>());
	
	    Map<String, Map<String, Double>> versionMap = rates.get(version);
	 
	    versionMap.putIfAbsent(configId, new HashMap<>());
	
	    Map<String, Double> solMap = versionMap.get(configId);
	   
	    solMap.put(Key, relativity);*/
	    
	    Map<String,Double> temp1;
	    
	    Map<String,Map<String,Double>> temp2;
	    
	    if(rates.containsKey(version))
	    {
	    	if(rates.get(version).containsKey(configId))
	    	{
	    		rates.get(version).get(configId).put(Key,relativity);
	    	}
	    	
	    	else
	    	{
	    		temp1=new HashMap<>();
	    		temp2=new HashMap<>();
	    		temp1.put(Key, relativity);
	    		temp2.put(configId, temp1);
	    		rates.get(version).putAll(temp2);
	    	}

	    }
	    
	    else
	    {
	    	temp1=new HashMap<>();
    		temp2=new HashMap<>();
    		temp1.put(Key, relativity);
    		temp2.put(configId, temp1);
    		
    		rates.put(version, temp2);
	    }
	    
	}
	
	public void addToRatesOutsideVersionMap(String version,String configId,String Key,Double relativity)
	{
	
	    /*rates.putIfAbsent(version, new HashMap<>());
	
	    Map<String, Map<String, Double>> versionMap = rates.get(version);
	 
	    versionMap.putIfAbsent(configId, new HashMap<>());
	
	    Map<String, Double> solMap = versionMap.get(configId);
	   
	    solMap.put(Key, relativity);*/
	    
	    Map<String,Double> temp1;
	    
	    Map<String,Map<String,Double>> temp2;
	    
	    if(ratesOutsideVersion.containsKey(version))
	    {
	    	if(ratesOutsideVersion.get(version).containsKey(configId))
	    	{
	    		ratesOutsideVersion.get(version).get(configId).put(Key,relativity);
	    	}
	    	
	    	else
	    	{
	    		temp1=new HashMap<>();
	    		temp2=new HashMap<>();
	    		temp1.put(Key, relativity);
	    		temp2.put(configId, temp1);
	    		ratesOutsideVersion.get(version).putAll(temp2);
	    	}

	    }
	    
	    else
	    {
	    	temp1=new HashMap<>();
    		temp2=new HashMap<>();
    		temp1.put(Key, relativity);
    		temp2.put(configId, temp1);
    		
    		ratesOutsideVersion.put(version, temp2);
	    }
	    
	}
	
	public String returnString(XSSFCell cell)
	{
		XSSFCell cellValue=cell;
		
		String value;
		
		if(cellValue.getCellType()==CellType.NUMERIC)
		{
			//System.out.println(cellValue.getNumericCellValue());
			value=String.valueOf((int)cellValue.getNumericCellValue());
		}
		
		else
		{
			//System.out.println(cellValue.getStringCellValue());
			value=cellValue.getStringCellValue();
		}
		
		return value;
		
	}
	
	public void calculateRates(String rateKey,String policyNumber,String quoteCreationDate,String discountCode)
	{
		String keyArray[]=rateKey.trim().split("/");
		
		this.renewalVersion=getRenewalVersion(policyNumber.trim().split("\\.")[2]);
		
		this.version=keyArray[0].substring(0, 8);
		
		 this.sol=keyArray[0].substring(8).replaceAll("-", "");
		 
		 this.quoteCreationDate=quoteCreationDate.trim();
		
		String excess=excessAdjustment(keyArray[12]);
		
		this.discountCode=discountCode.trim();
		
		//System.out.println(version);
		double coverage=rates.get(version).get(getConfigId("RateGroupConfigID",version,sol)).get(keyArray[7]);
		double AGE=rates.get(version).get(getConfigId("IncepAgeConfigID",version,sol)).get(keyArray[8]);
		double MiBand=rates.get(version).get(getConfigId("IncepMileageConfigID",version,sol)).get(keyArray[9]);
		double VRBand=rates.get(version).get(getConfigId("VehRateBandConfigID",version,sol)).get(keyArray[11]);
		double Excess=rates.get(version).get(getConfigId("DeductibleConfigID",version,sol)).get(excess);
		double ftype=rates.get(version).get(getConfigId("FuelTypeConfigID",version,sol)).get(keyArray[13]);
		double VUsage=rates.get(version).get(getConfigId("VehUseConfigID",version,sol)).get(keyArray[14]);
		double VClass=rates.get(version).get(getConfigId("VehClassConfigID",version,sol)).get(keyArray[16]);
	    double CCBand=rates.get(version).get(getConfigId("CCBandConfigID",version,sol)).get(keyArray[15]);
		double PPriceBand=rates.get(version).get(getConfigId("PurchasePriceConfigID",version,sol)).get(keyArray[17]);
		double LBand=rates.get(version).get(getConfigId("LocationBandConfigID",version,sol)).get(keyArray[18]);
		double CAConfigID=rates.get(version).get(getConfigId("CustomerAnalyticsConfigID",version,sol)).get(keyArray[19]);
		double DriveType=rates.get(version).get(getConfigId("DriveType",version,sol)).get(keyArray[20]);
		double QWdayConfigID=rates.get(version).get(getConfigId("QuoteWeekdayConfigID",version,sol)).get(keyArray[21]);
		double TDConfigID=rates.get(version).get(getConfigId("TypeOfDeviceOSConfigID",version,sol)).get(keyArray[22]);
		double FirstOwner=rates.get(version).get(getConfigId("FirstOwnerFlag",version,sol)).get(keyArray[23]);
		double baseValue=rates.get(version).get(getConfigId("RateGroupConfigID",version,sol)).get("BaseValue");
		double AA=rates.get(version).get(getConfigId("RateGroupConfigID",version,sol)).get("AddonSurchargeVal");
		ipt=ratesOutsideVersion.get(version).get(getConfigId("IPT",version,sol)).get(sol);
		double qbeAdminFee=rates.get(version).get(getConfigId("RateGroupConfigID",version,sol)).get("QBEAdminFee");
		double ManuFacureComm=rates.get(version).get(getConfigId("RateGroupConfigID",version,sol)).get("ManufacturerComm");
		double DealerComm=rates.get(version).get(getConfigId("RateGroupConfigID",version,sol)).get("DealerComms");
		double salesPersonComm=rates.get(version).get(getConfigId("RateGroupConfigID",version,sol)).get("SalesPersonComm");
		double fbfmComm=rates.get(version).get(getConfigId("RateGroupConfigID",version,sol)).get("FBFMComm");
		
		this.QBENetPremium=roundDecimals((((coverage*AGE*MiBand*VRBand*Excess*ftype*VUsage*VClass*CCBand*PPriceBand*LBand*CAConfigID*DriveType*QWdayConfigID*TDConfigID*FirstOwner)*baseValue)+AA),2);
		this.QBEAdmin=roundDecimals((qbeAdminFee*QBENetPremium),2);
		this.UWFund=roundDecimals((QBENetPremium-QBEAdmin),2);
		//ROUND((A.2)*[SalesPersonComm%]/(1-([SalesPersonComm%]+[FBFMCommission%]+[DealerCommision%]+[ManufacturerCommission%])),2)
		this.SalesPersonComm=roundDecimals(((QBENetPremium)*salesPersonComm)/(1-(salesPersonComm+fbfmComm+DealerComm+ManuFacureComm)),2);
		this.DealerComm=roundDecimals(((QBENetPremium)*DealerComm)/(1-(salesPersonComm+fbfmComm+DealerComm+ManuFacureComm)),2);
		this.ManufacturerComm=roundDecimals(((QBENetPremium)*ManuFacureComm)/(1-(salesPersonComm+fbfmComm+DealerComm+ManuFacureComm)),2);
		this.FBFMCommission=roundDecimals(((QBENetPremium)*fbfmComm)/(1-(salesPersonComm+fbfmComm+DealerComm+ManuFacureComm)),2);
	    this.ExcIPT=roundDecimals((this.QBENetPremium+this.SalesPersonComm+this.DealerComm+this.FBFMCommission+this.ManufacturerComm),2);
	    this.IPT=roundDecimals((this.ExcIPT*ipt),2);
	    this.LondonRemit=roundDecimals((this.QBENetPremium+this.SalesPersonComm+this.DealerComm+this.ManufacturerComm+this.IPT),2);
	    this.CustomerPremIncIPT=roundDecimals((this.ExcIPT+this.IPT),2);
	    this.SchemeViewerPremiumCommission=roundDecimals((this.SalesPersonComm+this.ManufacturerComm+this.DealerComm+this.FBFMCommission),2);
	    this.Spare1=roundDecimals((this.CustomerPremIncIPT-this.SalesPersonComm-this.ManufacturerComm-this.DealerComm-this.FBFMCommission),2);
	    
	    stageSecond(); 
	    
	}
	
	public void stageSecond()
	{
	double stage2CoverAmount=validateCoverAmoount();
	
	double rateFactor=roundDecimals((stage2CoverAmount/this.CustomerPremIncIPT),5);
	
	/*this.QBENetPremium=roundDecimals((this.QBENetPremium*rateFactor),2);
	this.QBEAdmin=roundDecimals((this.QBEAdmin*rateFactor),2);
	this.UWFund=roundDecimals((this.UWFund*rateFactor),2);
	this.SalesPersonComm=roundDecimals((this.SalesPersonComm*rateFactor),2);
	this.DealerComm=roundDecimals((this.DealerComm*rateFactor),2);
	this.ManufacturerComm=roundDecimals((this.ManufacturerComm*rateFactor),2);
	this.FBFMCommission=roundDecimals((this.FBFMCommission*rateFactor),2);
	this.ExcIPT=roundDecimals((this.ExcIPT*rateFactor),2);
	this.IPT=roundDecimals((this.IPT*rateFactor),2);
	this.LondonRemit=roundDecimals((this.LondonRemit*rateFactor),2);*/
	
	/*this.SchemeViewerPremiumCommission=roundDecimals((this.SalesPersonComm+this.ManufacturerComm+this.DealerComm+this.FBFMCommission),2);
	this.Spare1=roundDecimals((this.Spare1*rateFactor),2);*/
	this.CustomerPremIncIPT=stage2CoverAmount;
	
	stageThird(rateFactor);
	
	}
	
	public void stageThird(double rateFactor)
	{
		String versionKey=getRovVersion();
		String configId=getConfigId("RenewSeqConfigID",version,sol);
		double surCharge=(100+(ratesOutsideVersion.get(versionKey).get(configId).get(renewalVersion)*100));
	    double factor=roundDecimals(((rateFactor*surCharge)/100),5);
	    
	    this.QBENetPremium=roundDecimals((this.QBENetPremium*factor),2);
		this.QBEAdmin=roundDecimals((this.QBEAdmin*factor),2);
		this.UWFund=roundDecimals((this.UWFund*factor),2);
		this.SalesPersonComm=roundDecimals((this.SalesPersonComm*factor),2);
		this.DealerComm=roundDecimals((this.DealerComm*factor),2);
		this.ManufacturerComm=roundDecimals((this.ManufacturerComm*factor),2);
		this.FBFMCommission=roundDecimals((this.FBFMCommission*factor),2);
		this.ExcIPT=roundDecimals((this.ExcIPT*factor),2);
		this.IPT=roundDecimals((this.IPT*factor),2);
		this.LondonRemit=roundDecimals((this.LondonRemit*factor),2);
		this.CustomerPremIncIPT=roundDecimals((this.ExcIPT+this.IPT),2);
		this.SchemeViewerPremiumCommission=roundDecimals((this.SalesPersonComm+this.ManufacturerComm+this.DealerComm+this.FBFMCommission),2);
		this.Spare1=roundDecimals((this.Spare1*factor),2);
		
		stageFourth();
	}
	
	public void stageFourth()
	{
		double discount=getDiscount();
		double discountAmount=roundDecimals((this.ExcIPT*discount)/100,2);
		this.FBFMCommission=roundDecimals((this.FBFMCommission-discountAmount),2);
		this.ExcIPT=roundDecimals((this.ExcIPT-discountAmount),2);
		this.IPT=roundDecimals((this.ExcIPT*ipt),2);
		this.CustomerPremIncIPT=roundDecimals((this.ExcIPT+this.IPT),2);
		this.LondonRemit=roundDecimals((this.QBENetPremium+this.SalesPersonComm+this.DealerComm+this.ManufacturerComm+this.IPT),2);
	    this.SchemeViewerPremiumCommission=roundDecimals((this.SalesPersonComm+this.ManufacturerComm+this.DealerComm+this.FBFMCommission),2);
	    this.Spare1=roundDecimals((this.CustomerPremIncIPT-this.SalesPersonComm-this.ManufacturerComm-this.DealerComm-this.FBFMCommission),2);
	    
	    if((version.equals("20221212")||version.equals("20230519"))&&renewalVersion.equals("1"))
	    		{
	    	stageSeventh();
	    		}
	    else
	    {
	    	stageSixth();
	    }
	}
	
	public void stageSixth()
	{
		String versionKey=getRovVersion();
		String configId=getConfigId("RateGroupConfigID",version,sol);
		
	MaxQBENetPremium=ratesOutsideVersion.get(versionKey).get(configId).get("MaxQBENetPremium");
		MaxQBEAdmin=ratesOutsideVersion.get(versionKey).get(configId).get("MaxQBEAdmin");
	MaxSalesPersonComm=ratesOutsideVersion.get(versionKey).get(configId).get("MaxSalesPersonComm");
		MaxDealerCommission=ratesOutsideVersion.get(versionKey).get(configId).get("MaxDealerCommission");
		MaxManufacturerCommission=ratesOutsideVersion.get(versionKey).get(configId).get("MaxManufacturerCommission");
		MaxFBFMCommission=ratesOutsideVersion.get(versionKey).get(configId).get("MaxFBFMCommission");
		this.QBENetPremium=getMaxVale(MaxQBENetPremium,QBENetPremium);
		this.QBEAdmin=roundDecimals((this.QBENetPremium*MaxQBEAdmin),2);
		this.UWFund=roundDecimals((this.QBENetPremium-this.QBEAdmin),2);
		this.SalesPersonComm=getMaxVale(MaxSalesPersonComm,this.SalesPersonComm);
		this.DealerComm=getMaxVale(MaxDealerCommission,this.DealerComm);
		this.ManufacturerComm=getMaxVale(MaxManufacturerCommission,this.ManufacturerComm);
		this.FBFMCommission=getMaxVale(MaxFBFMCommission,this.FBFMCommission);
		this.ExcIPT=roundDecimals((this.QBENetPremium+this.SalesPersonComm+this.DealerComm+this.ManufacturerComm+this.FBFMCommission),2);
		this.IPT=roundDecimals((this.ExcIPT*ipt),2);
		this.CustomerPremIncIPT=roundDecimals((this.ExcIPT+this.IPT),2);
		this.LondonRemit=roundDecimals((this.QBENetPremium+this.SalesPersonComm+this.DealerComm+this.ManufacturerComm+this.IPT),2);
		stageSeventh();
	}
	
	public void stageSeventh()
	{
	
		double roundingCoverAmount=roundDecimals((roundDecimals((this.CustomerPremIncIPT/12),1)*12),2);
		double rateFactor=roundDecimals((roundingCoverAmount/this.CustomerPremIncIPT),5);
		this.SalesPersonComm=getSeventhMax(rateFactor,this.SalesPersonComm,MaxSalesPersonComm);
		this.DealerComm=getSeventhMax(rateFactor,this.DealerComm,MaxDealerCommission);
		this.ManufacturerComm=getSeventhMax(rateFactor,this.ManufacturerComm,MaxManufacturerCommission);
		this.FBFMCommission=getSeventhMax(rateFactor,this.FBFMCommission,MaxFBFMCommission);
		this.CustomerPremIncIPT=roundingCoverAmount;
		this.IPT=roundDecimals((this.IPT*rateFactor),2);
		this.ExcIPT=roundDecimals((this.CustomerPremIncIPT-this.IPT),2);
		this.QBENetPremium=roundDecimals((this.ExcIPT-this.SalesPersonComm-this.DealerComm-this.FBFMCommission-this.ManufacturerComm),2);
		this.QBEAdmin=roundDecimals((this.ExcIPT-this.SalesPersonComm-this.DealerComm-this.FBFMCommission-this.ManufacturerComm)*this.MaxQBEAdmin,2);
		this.UWFund=roundDecimals((this.QBENetPremium-this.QBEAdmin),2);
		this.LondonRemit=roundDecimals((this.QBENetPremium+this.SalesPersonComm+this.DealerComm+this.ManufacturerComm+this.IPT),2);
		this.SchemeViewerPremiumCommission=roundDecimals((this.SalesPersonComm+this.ManufacturerComm+this.DealerComm+this.FBFMCommission),2);
	    this.Spare1=roundDecimals((this.CustomerPremIncIPT-this.SalesPersonComm-this.ManufacturerComm-this.DealerComm-this.FBFMCommission),2);
	    
	    System.out.println("QBENetPremium: " + QBENetPremium 
	    		+ "\nQBEAdmin: " + QBEAdmin + 
	    		"\nUWFund: " + UWFund
	    		+ "\nLondonRemit: " + LondonRemit
	    		+ "\nSalesPersonComm: " + SalesPersonComm +
	    		"\nDealerComm: " + DealerComm
	    		+"\nManufacturerComm :"+ManufacturerComm
	    		+"\nFBFMCommission :"+FBFMCommission
	    		+"\nExcIPT :"+ExcIPT
	    		+"\nIPT :"+IPT
	    		+"\nCustomerPremIncIPT :"+CustomerPremIncIPT);
	}
	
	public double getSeventhMax(double rateFactor,double disbursement,double maxValue)
	{
		double finalValue=0;
		
		if(disbursement>=maxValue)
		{
			finalValue=maxValue;
		}
		
		else
		{
			finalValue=roundDecimals((disbursement*rateFactor),2);
		}
		
		
		return finalValue;
		
	}
	
	public double getMaxVale(double keyValue,double disbursement)
	{
		double maxValue=0;
		if(disbursement>=keyValue)
		{
			maxValue=keyValue;
		}
		else
		{
			maxValue=disbursement;
		}
		
		return maxValue;
		
	}
	
	public double getDiscount()
	{
		double discount=0;
		if(this.renewalVersion.equals("1")&&!(this.discountCode=="NA")&&(this.sol.equalsIgnoreCase("FMDCE")||this.sol.equalsIgnoreCase("WDGO")||this.sol.equalsIgnoreCase("WDSO")))
		{
		if(this.discountCode.equalsIgnoreCase("Auto10"))
		{
			discount=10;
		}
		
		else if(this.discountCode.equalsIgnoreCase("Auto20"))
		{
			discount=20;
		}
		}
		
		return discount;
		
	}
	public String getRovVersion()
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		LocalDate inputDate = LocalDate.parse(quoteCreationDate, formatter);
		
		LocalDate closestDate = null;
		
		long minDifference = Long.MAX_VALUE; 
		
		for(String date:ratesDates)
		{
			LocalDate inputDate1 = LocalDate.parse(date, formatter);
			
			if(!inputDate1.isAfter(inputDate))
			{
				long daysDifference = Math.abs(ChronoUnit.DAYS.between(inputDate, inputDate1));

                if (daysDifference < minDifference) {
                    minDifference = daysDifference;
                    closestDate = inputDate1;
                }
			}
		}
		 DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyyMMdd");
	        return closestDate.format(formatter1);
		
	}
	
	public double validateCoverAmoount()
	{
		double minValue=rates.get(version).get(getConfigId("RateGroupConfigID",version,sol)).get("MinValue");
		double maxValue=rates.get(version).get(getConfigId("RateGroupConfigID",version,sol)).get("MaxValue");
		double maxNoQuote=rates.get(version).get(getConfigId("RateGroupConfigID",version,sol)).get("MaxNoQuoteValue");
		double tempCoverAmount=0;
		
		if(minValue>this.CustomerPremIncIPT)
		{
			tempCoverAmount=minValue;
		}
		
		else if(minValue<=this.CustomerPremIncIPT&&maxValue>this.CustomerPremIncIPT)
		{
			tempCoverAmount=this.CustomerPremIncIPT;
		}
		
		else if(maxValue<=this.CustomerPremIncIPT&&maxNoQuote>this.CustomerPremIncIPT)
		{
			tempCoverAmount=maxValue;
		}
		
		else if(maxNoQuote<=this.CustomerPremIncIPT)
		{
			tempCoverAmount=0;
		}
		
		return tempCoverAmount;
	}
	
	public String getRenewalVersion(String renewalVersion)
	{
		int number=Integer.parseInt(renewalVersion);
		
		String version=String.valueOf(number);
		
		return version;
	}
	
	public double roundDecimals(double value,int decimalPoint)
	{
		 
        String formattedValue = String.format("%."+decimalPoint+"f", value);
        
        double roundedValue = Double.parseDouble(formattedValue);
        
        return roundedValue;
	}
	
	public String excessAdjustment(String excess)
	{
		String exces=excess.substring(1);
		
		switch(exces)
		{
		case "000": exces="0";break;
		
		case "050": exces="50";break;
		
		}
		
		return exces;
		
		
	}
	
	public String getConfigId(String key,String version,String sol)
	{
		String configId="";
		
		/*for(Entry<String, Map<String, Double>> entry:rates.get(version).entrySet())
			{
			
			Map<String,Double> new1=entry.getValue();
			
			if(new1.containsKey("AddonSurchargeVal "))
			{
				System.out.println(new1.get("AddonSurchargeVal "));
			}
			//for(Map<String, Double> entry1:)
			}*/
		
        DecimalFormat df=new DecimalFormat("0"); 
		
        configId=df.format(rates.get(version).get(sol).get(key));
        
		return configId;
	}
	
}


