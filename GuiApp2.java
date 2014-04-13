
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.lang.*;
import java.util.*;

public class GuiApp2 extends JFrame implements ActionListener
{
    JLabel iflbl;
    JLabel oflbl;
    JButton brw;
    JButton cb;
    JFileChooser chooser;
    File chosenFile;
     
    //constructor
    GuiApp2()
    {
        iflbl = new JLabel("input file :");
        oflbl = new JLabel("output file :");
        brw = new JButton("browse");
        cb = new JButton("Convert");
        chooser= new JFileChooser();

		
        Container con=getContentPane();
        this.setSize(800,800);
        con.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        brw.addActionListener(this);
        cb.addActionListener(this);
        
        con.add(iflbl);
		
        con.add(brw);
        con.add(cb);
        con.add(oflbl);
       
        setVisible(true);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    
    }
	
	
	
	
 public void run2(String filename) throws IOException
    {   //data structure to store mappings 
        HashMap<String,String> hhash=new HashMap<String,String>();
        HashMap<String,Integer> nu2a=new HashMap<String,Integer>();
        HashMap<Integer,String> a2u=new HashMap<Integer,String>();
        
		
		//reading mapping files 
		
		
		   //non-unicode to ascii
		FileInputStream ascii_map = new FileInputStream("NonUnicodetoASCII.dat");
        BufferedReader sc3 = new BufferedReader(new InputStreamReader(ascii_map,"ISO8859-1"));
        
		   //ascii to unicode
		FileInputStream unicode_map = new FileInputStream("ASCIItoUnicode.dat");
        BufferedReader sc2 = new BufferedReader(new InputStreamReader(unicode_map,"UTF-8"));
            
			//halant mapping 
		FileInputStream hl_map = new FileInputStream("Halant Mapping.txt");
        BufferedReader sc5 = new BufferedReader(new InputStreamReader(hl_map,"UTF-8"));
             
			 //input text
		FileInputStream fr = new FileInputStream(filename);
             
			 //output text
		FileOutputStream f=new FileOutputStream(filename+"_output.txt");
        OutputStreamWriter few=new OutputStreamWriter(f,"UTF-8");
        BufferedWriter fw=new BufferedWriter(few);
        BufferedReader br = new BufferedReader(new InputStreamReader(fr,"ISO8859-1"));
        FileOutputStream f2=new FileOutputStream("left over.txt");
        OutputStreamWriter few2=new OutputStreamWriter(f2,"ISO8859-1");
        /*FileOutputStream o1=new FileOutputStream("NonUnicodetoASCII.map");
        ObjectOutputStream o11=new ObjectOutputStream(o1);
        FileOutputStream o2=new FileOutputStream("ASCIItoUnicode.map");
        ObjectOutputStream o22=new ObjectOutputStream(o2);*/
        /*FileInputStream oScript=new FileInputStream("Script.map");
        ObjectInputStream o3=new ObjectInputStream(oScript);
        FileInputStream oNu2A=new FileInputStream("NonUnicodetoASCII.map");
        ObjectInputStream o2=new ObjectInputStream(oNu2A);
        FileInputStream oA2U=new FileInputStream("ASCIItoUnicode.map");
        ObjectInputStream o1=new ObjectInputStream(oA2U);*/
        int n=0;
        String k;
        /*try
        {
        finalhash=(HashMap<String,String>)o3.readObject();
        }catch(ClassNotFoundException e)
        {
        }
        try
        {
        nu2a=(HashMap<String,Integer>)o2.readObject();
        }catch(ClassNotFoundException e)
        {
        }
        try
        {
        a2u=(HashMap<Integer,String>)o1.readObject();
        }catch(ClassNotFoundException e)
        {
        }           
        oScript.close();
        oNu2A.close();
        oA2U.close();*/
        
        //ASCII to Unicode Map
        while((k=sc2.readLine())!=null)
        {
            if(!(Character.isDigit(k.charAt(0))))
            {
                k=k.substring(1);
            }
            int p=k.indexOf(" ");
            if(p>0)
            {
                Integer v=Integer.parseInt(k.substring(0,p));
                k=k.substring(p+1);
                a2u.put(v,k);
            }
        }
        
        //Non-Unicode to ASCII Map
        while((k=sc3.readLine())!=null)
        {
            int p=k.indexOf(" ");
            if(p>0)
            {
                Integer v=Integer.parseInt(k.substring(0,p));
                k=k.substring(p+1);
                nu2a.put(k,v);
            }
        }
        
        //Halant Map
        int gy=0;
        while((k=sc5.readLine())!=null)
        {
            if(gy==0)
            {
                k=k.substring(1);
                gy++;
            }
            int p=k.indexOf(" ");
            if(p>0)
            {
                String v=k.substring(0,p);
                k=k.substring(p+1);
                hhash.put(v,k);
            }
        }
        /*oo.writeObject(finalhash);
        oo.flush();
        oo.close();
        ooutput.close();*/
        /*o11.writeObject(nu2a);
        o11.flush();
        o11.close();
        o1.close();
        o22.writeObject(a2u);
        o22.flush();
        o22.close();
        o2.close();*/
        
        //Read complete input and save as a String
        String temp;
        String str="";
        while((temp=(br.readLine()))!=null)
        {
            str=str+temp+"\n";
        }
        fr.close();
        String rep="";
        boolean tag=false;
        int asc;
        
        // Character Manipulation
        out:
        for(int i=0;i<str.length();i++)
        {
            rep="";
            if(str.charAt(i)==' ')
            {
                String sp=" ";
                fw.write(sp,0,sp.length());
                continue out;
            }
            else if(str.charAt(i)=='\n')
            {
                String nl="\r\n";
                fw.write(nl,0,nl.length());
                continue out;
            }
            
            //Method 1
			
			if((i+4)<(str.length()))	
			{
				if(nu2a.containsKey(""+str.charAt(i)))
				{
                int ky=nu2a.get(""+str.charAt(i));
                 int ky1=(int)str.charAt(i+1);
				  int ky2=(int)str.charAt(i+2);
				   int ky3=(int)str.charAt(i+3);
				
				
				//sthi replace
				
				//e replace
				if((ky==108)&&(ky1==102))
				{
				   i++;
				    rep=" ";
					rep=a2u.get(231);
					fw.write(rep,0,rep.length());
								continue out;
				}
				// r interchange    
				if((ky1==114) || (ky2==114) || (ky3==114))
					{
						if(ky1==114)
						{ 
							i++;
							rep=" ";
							rep=a2u.get(227)+hhash.get(a2u.get(ky));
							fw.write(rep,0,rep.length());
								continue out;

						}
						if(ky2==114)
						{ 
							i=i+2;
							rep=" ";
							rep=a2u.get(227)+hhash.get(a2u.get(ky));
							fw.write(rep,0,rep.length());
								continue out;
						}
						
						if(ky3==114)
						{
							i=i+3;
							rep=" ";
							rep=a2u.get(227)+hhash.get(a2u.get(ky))+a2u.get(ky1);
							fw.write(rep,0,rep.length());
								continue out;
						
						}
					} 
				

				//o and auu interchange
				if((ky==44) && ((ky2==118) || (ky2==120)  ))
					
					{
							i=i++;
							rep=" ";
							
							if(ky2==118)
							{
							rep=a2u.get(229);
							fw.write(rep,0,rep.length());
								continue out;
							}
							
							if(ky3==120)
							{
							rep=a2u.get(230);
							fw.write(rep,0,rep.length());
								continue out;
							}
							
					}		
				   
			   //hasikaar
                if((ky==108) || (ky==115) || (ky==122) || (ky==142))
                {
                    i++; // We are looking for the next one. 
                    
					
					if(ky1==102)
					{
						i++;
						rep=" ";
						rep=a2u.get(231);
						fw.write(rep,0,rep.length());
									continue out;
					}
					if(nu2a.containsKey(""+str.charAt(i)))
                    {
                        rep=" ";
                        if(a2u.containsKey(nu2a.get(""+str.charAt(i))))
                        {
                            rep=a2u.get(nu2a.get(""+str.charAt(i)));
                            if(hhash.containsKey(rep))
                            {
                                if(str.charAt(i+1)==' ')
                                {
                                    rep=hhash.get(rep)+a2u.get(ky);
                                    fw.write(rep,0,rep.length());
                                    continue out;
                                }
                                if(nu2a.containsKey(""+str.charAt(i+1)))
                                {
                                    asc=nu2a.get(""+str.charAt(i+1));
                                    if(asc==107)
                                    {
                                        i=i+1; // Note increment 
                                        rep=hhash.get(rep)+a2u.get(ky);
                                        fw.write(rep,0,rep.length());
                                        continue out;
                                    }
                                }
                                if(((int)str.charAt(i+1))==107)
                                {
                                    i=i+1;
                                    rep=hhash.get(rep)+a2u.get(ky);
                                    fw.write(rep,0,rep.length());
                                    continue out;
                                }
                            }
                            rep=rep+a2u.get(ky);
                            fw.write(rep,0,rep.length());
                            continue out;
                        }
                    }
                    if(str.charAt(i)=='<')
                    {
                        if(Character.isDigit(str.charAt(i+1)))
                        {
                            int ei=str.indexOf('>',i);
                            boolean flag=true;
                            String tocheckdigit=str.substring(i+1,ei);
                            for(int h=0;h<tocheckdigit.length();h++)
                            {
                                if(!(Character.isDigit(tocheckdigit.charAt(h))))
                                {
                                    flag=false;
                                }
                            }
                            if(flag)
                            {
                                int key=Integer.parseInt(str.substring(i+1,ei));
                                if(a2u.containsKey(key))
                                {
                                    rep=a2u.get(key);
                                    if(hhash.containsKey(rep))
                                    {
                                        if(str.charAt(i+1)==' ')
                                        {
                                            rep=hhash.get(rep)+a2u.get(ky);
                                            fw.write(rep,0,rep.length());
                                            continue out;
                                        }
                                        if(nu2a.containsKey(""+str.charAt(i+1)))
                                        {
                                            asc=nu2a.get(""+str.charAt(i+1));
                                            if(asc==107)
                                            {
                                                i=i+1;
                                                rep=hhash.get(rep)+a2u.get(ky);
                                                fw.write(rep,0,rep.length());
                                                continue out;
                                            }
                                        }
                                        if(((int)str.charAt(i+1))==107)
                                        {
                                            i=i+1;
                                            rep=hhash.get(rep)+a2u.get(ky);
                                            fw.write(rep,0,rep.length());
                                            continue out;
                                        }
                                    }
                                    rep=rep+a2u.get(ky);
                                    fw.write(rep,0,rep.length());
                                    continue out;
                                }
                            }
                        }
                    }
                    int ext=(int)str.charAt(i);
                    if(a2u.containsKey(ext))
                    {
                        rep=a2u.get(ext);
                        if(hhash.containsKey(rep))
                        {
                            if(str.charAt(i+1)==' ')
                            {
                                rep=hhash.get(rep)+a2u.get(ky);
                                fw.write(rep,0,rep.length());
                                continue out;
                            }
                            if(nu2a.containsKey(""+str.charAt(i+1)))
                            {
                                asc=nu2a.get(""+str.charAt(i+1));
                                if(asc==107)
                                {
                                    i=i+1;
                                    rep=hhash.get(rep)+a2u.get(ky);
                                    fw.write(rep,0,rep.length());
                                    continue out;
                                }
                            }
                            if(((int)str.charAt(i+1))==107)
                            {
                                i=i+1;
                                rep=hhash.get(rep)+a2u.get(ky);
                                fw.write(rep,0,rep.length());
                                continue out;
                            }
                        }
                        rep=rep+a2u.get(ky);
                        fw.write(rep,0,rep.length());
                        continue out;
                    }
                }
                
                //Independent vowels rendering
                
                if(ky==107)
                {
                    int nxtkey=0;
                    if(nu2a.containsKey(""+str.charAt(i+1)))
                    {
                        nxtkey=nu2a.get(""+str.charAt(i+1));
                    }
                    else
                    {
                        if(str.charAt(i+1)=='<')
                        {
                            if(Character.isDigit(str.charAt(i+1)))
                            {
                                int ei=str.indexOf('>',i);
                                boolean flag=true;
                                String tocheckdigit=str.substring(i+1,ei);
                                for(int h=0;h<tocheckdigit.length();h++)
                                {
                                    if(!(Character.isDigit(tocheckdigit.charAt(h))))
                                    {
                                        flag=false;
                                    }
                                }
                                if(flag)
                                {
                                    nxtkey=Integer.parseInt(str.substring(i+1,ei));
                                }
                            }
                        }
                        else
                        {
                            nxtkey=(int)str.charAt(i+1);
                        }
                    }
                    if((nxtkey==118) || (nxtkey==120))
                    {
                        i++;
                        if(nxtkey==118)
                        {
                            rep=a2u.get(223);
                        }
                        if(nxtkey==120)
                        {
                            rep=a2u.get(224);
                        }
                        fw.write(rep,0,rep.length());
                        continue out;
                    }
					
						if((nxtkey==119) )
						{
							i++;
							if(nxtkey==119)
							{
								rep=a2u.get(224);
							}
							fw.write(rep,0,rep.length());
							continue out;
						}
					
					
                }
                
                //129= 'aa' ,130 = 'aa'
                if((ky==129) || (ky==130))
                {
                    int nxtkey=0;
                    if(nu2a.containsKey(""+str.charAt(i+1)))
                    {
                        nxtkey=nu2a.get(""+str.charAt(i+1));
                    }
                    else
                    {
                        if(str.charAt(i+1)=='<')
                        {
                            if(Character.isDigit(str.charAt(i+1)))
                            {
                                int ei=str.indexOf('>',i);
                                boolean flag=true;
                                String tocheckdigit=str.substring(i+1,ei);
                                for(int h=0;h<tocheckdigit.length();h++)
                                {
                                    if(!(Character.isDigit(tocheckdigit.charAt(h))))
                                    {
                                        flag=false;
                                    }
                                }
                                if(flag)
                                {
                                    nxtkey=Integer.parseInt(str.substring(i+1,ei));
                                }
                            }
                        }
                        else
                        {
                            nxtkey=(int)str.charAt(i+1);
                        }
                    }
                    if((nxtkey==118) || (nxtkey==120))
                    {
                        i++;
                        if(nxtkey==118)
                        {
                            rep=a2u.get(225);
                        }
                        if(nxtkey==120)
                        {
                            rep=a2u.get(226);
                        }
                        fw.write(rep,0,rep.length());
                        continue out;
                    }
                }
                
				
                //Halant Mapping + independent consonant mapping
                if(a2u.containsKey(ky))
                {
				
					
                    rep=a2u.get(ky);
                }
               
			   if(hhash.containsKey(rep))
                {
                    if(str.charAt(i+1)==' ')
                    {
                        rep=hhash.get(rep);
                        fw.write(rep,0,rep.length());
                        continue out;
                    }
                    if(nu2a.containsKey(""+str.charAt(i+1)))
                    {
                        asc=nu2a.get(""+str.charAt(i+1));
                        if(asc==107)
                        {
                            i=i+1;
                            rep=hhash.get(rep);
                            fw.write(rep,0,rep.length());
                            continue out;
                        }
                    }
					
					
					
					if((ky==108)&& (ky1==102))
				{
					i++;
				    rep=" ";
					rep=a2u.get(231);
					fw.write(rep,0,rep.length());
								continue out;
				}
					
                    if(ky1==107)
                    {
					
					if(((ky==129) || (ky==130)) && ((ky2==118) || (ky2==120)  ))
					
					{
							i=i++;
							rep=" ";
							
							if(ky2==118)
							{
							rep=a2u.get(229);
							fw.write(rep,0,rep.length());
								continue out;
							}
							
							if(ky3==120)
							{
							rep=a2u.get(230);
							fw.write(rep,0,rep.length());
								continue out;
							}
							
					}		
					
					
                        i=i+1;
                        rep=hhash.get(rep);
                        fw.write(rep,0,rep.length());
                        continue out;
                    }
                }
               
			   fw.write(rep,0,rep.length());
                continue out;
            }
            }
            //Method 2
            if(str.charAt(i)=='<')
            {
                if(Character.isDigit(str.charAt(i+1)))
                {
                    int ei=str.indexOf('>',i);
                    boolean flag=true;
                    String tocheckdigit=str.substring(i+1,ei);
                    for(int h=0;h<tocheckdigit.length();h++)
                    {
                        if(!(Character.isDigit(tocheckdigit.charAt(h))))
                        {
                            flag=false;
                        }
                    }
                    if(flag)
                    {
						
						
						int ky=Integer.parseInt(str.substring(i+1,ei));
						 int ky1=(int)str.charAt(ei+1);
							int ky2=(int)str.charAt(ei+2);
								int ky3=(int)str.charAt(ei+3);
						

						if(ky==219)
						{
								i=i+3;
								rep=" ";
								fw.write(rep,0,rep.length());
                                continue out;
						
						
						}
						
						if((ky==129) || (ky==130))
                        {
                            int nxtkey=0;
							
							if((ky1==107) && (ky2==118))
							{
								i=ei+2;
								rep=a2u.get(229);
								fw.write(rep,0,rep.length());
                                continue out;
							
							}
							
							if((ky1==118) || (ky1==120))
                            {
                                i=ei+1;
                                if(ky1==118)
                                {
                                    rep=a2u.get(225);
                                }
                                if(ky1==120)
                                {
                                    rep=a2u.get(226);
                                }
                                fw.write(rep,0,rep.length());
                                continue out;
                            }
						}								
						
                        if((ky==108) || (ky==115) || (ky==122) || (ky==142))
                        {
                            i++;
                            if(nu2a.containsKey(""+str.charAt(i)))
                            {
                                rep=" ";
                                if(a2u.containsKey(nu2a.get(""+str.charAt(i))))
                                {
                                    rep=a2u.get(nu2a.get(""+str.charAt(i)));
                                    if(hhash.containsKey(rep))
                                    {
                                        if(str.charAt(i+1)==' ')
                                        {
                                            rep=hhash.get(rep)+a2u.get(ky);
                                            fw.write(rep,0,rep.length());
                                            continue out;
                                        }
                                        if(nu2a.containsKey(""+str.charAt(i+1)))
                                        {
                                            asc=nu2a.get(""+str.charAt(i+1));
                                            if(asc==107)
                                            {
                                                i=i+1;
                                                rep=hhash.get(rep)+a2u.get(ky);
                                                fw.write(rep,0,rep.length());
                                                continue out;
                                            }
                                        }
                                        if(((int)str.charAt(i+1))==107)
                                        {
                                            i=i+1;
                                            rep=hhash.get(rep)+a2u.get(ky);
                                            fw.write(rep,0,rep.length());
                                            continue out;
                                        }
                                    }
                                    rep=rep+a2u.get(ky);
                                    fw.write(rep,0,rep.length());
                                    continue out;
                                }
                            }
                            if(str.charAt(i)=='<')
                            {
                                if(Character.isDigit(str.charAt(i+1)))
                                {
                                    int ei1=str.indexOf('>',i);
                                    tocheckdigit=new String(str.substring(i+1,ei1));
                                    for(int h=0;h<tocheckdigit.length();h++)
                                    {
                                        if(!(Character.isDigit(tocheckdigit.charAt(h))))
                                        {
                                            flag=false;
                                        }
                                    }
                                    if(flag)
                                    {
                                        int key=Integer.parseInt(str.substring(i+1,ei1));
                                        if(a2u.containsKey(key))
                                        {
                                            rep=a2u.get(key);
                                            if(hhash.containsKey(rep))
                                            {
                                                if(str.charAt(i+1)==' ')
                                                {
                                                    rep=hhash.get(rep)+a2u.get(ky);
                                                    fw.write(rep,0,rep.length());
                                                    continue out;
                                                }
                                                if(nu2a.containsKey(""+str.charAt(i+1)))
                                                {
                                                    asc=nu2a.get(""+str.charAt(i+1));
                                                    if(asc==107)
                                                    {
                                                        i=i+1;
                                                        rep=hhash.get(rep)+a2u.get(ky);
                                                        fw.write(rep,0,rep.length());
                                                        continue out;
                                                    }
                                                }
                                                if(((int)str.charAt(i+1))==107)
                                                {
                                                    i=i+1;
                                                    rep=hhash.get(rep)+a2u.get(ky);
                                                    fw.write(rep,0,rep.length());
                                                    continue out;
                                                }
                                            }
                                            rep=rep+a2u.get(ky);
                                            fw.write(rep,0,rep.length());
                                            continue out;
                                        }
                                    }
                                }
                            }
                            int ext=(int)str.charAt(i);
                            if(a2u.containsKey(ext))
                            {
                                rep=a2u.get(ext);
                                if(hhash.containsKey(rep))
                                {
                                    if(str.charAt(i+1)==' ')
                                    {
                                        rep=hhash.get(rep)+a2u.get(ky);
                                        fw.write(rep,0,rep.length());
                                        continue out;
                                    }
                                    if(nu2a.containsKey(""+str.charAt(i+1)))
                                    {
                                        asc=nu2a.get(""+str.charAt(i+1));
                                        if(asc==107)
                                        {
                                            i=i+1;
                                            rep=hhash.get(rep)+a2u.get(ky);
                                            fw.write(rep,0,rep.length());
                                            continue out;
                                        }
                                    }
                                    if(((int)str.charAt(i+1))==107)
                                    {
                                        i=i+1;
                                        rep=hhash.get(rep)+a2u.get(ky);
                                        fw.write(rep,0,rep.length());
                                        continue out;
                                    }
                                }
                                rep=rep+a2u.get(ky);
                                fw.write(rep,0,rep.length());
                                continue out;
                            }
                        }
                        if(ky==107)
                        {
                            int nxtkey=0;
                            if(nu2a.containsKey(""+str.charAt(i+1)))
                            {
                                nxtkey=nu2a.get(""+str.charAt(i+1));
                            }
                            else
                            {
                                if(str.charAt(i+1)=='<')
                                {
                                    if(Character.isDigit(str.charAt(i+1)))
                                    {
                                        int ei1=str.indexOf('>',i);
                                        flag=true;
                                        tocheckdigit=str.substring(i+1,ei1);
                                        for(int h=0;h<tocheckdigit.length();h++)
                                        {
                                            if(!(Character.isDigit(tocheckdigit.charAt(h))))
                                            {
                                                flag=false;
                                            }
                                        }
                                        if(flag)
                                        {
                                            nxtkey=Integer.parseInt(str.substring(i+1,ei1));
                                        }
                                    }
                                }
                                else
                                {
                                    nxtkey=(int)str.charAt(i+1);
                                }
                            }
                            if((nxtkey==118) || (nxtkey==120))
                            {
                                i++;
                                if(nxtkey==118)
                                {
                                    rep=a2u.get(223);
                                }
                                if(nxtkey==120)
                                {
                                    rep=a2u.get(224);
                                }
                                fw.write(rep,0,rep.length());
                                continue out;
                            }
							
							if((nxtkey==119) )
							{
								i++;
								if(nxtkey==119)
								{
									rep=a2u.get(224);
								}
								fw.write(rep,0,rep.length());
								continue out;
							}
							
                        }
                        if((ky==129) || (ky==130))
                        {
                            int nxtkey=0;
							
							
							if((ky1==118) || (ky1==120))
                            {
                                i=ei+1;
                                if(ky1==118)
                                {
                                    rep=a2u.get(225);
                                }
                                if(ky1==120)
                                {
                                    rep=a2u.get(226);
                                }
                                fw.write(rep,0,rep.length());
                                continue out;
                            }
							
							
                            if(nu2a.containsKey(""+str.charAt(i+1)))
                            {
                                nxtkey=nu2a.get(""+str.charAt(i+1));
                            }
                            else
                            {
                                if(str.charAt(i+1)=='<')
                                {
                                    if(Character.isDigit(str.charAt(i+1)))
                                    {
                                        ei=str.indexOf('>',i);
                                        flag=true;
                                        tocheckdigit=str.substring(i+1,ei);
                                        for(int h=0;h<tocheckdigit.length();h++)
                                        {
                                            if(!(Character.isDigit(tocheckdigit.charAt(h))))
                                            {
                                                flag=false;
                                            }
                                        }
                                        if(flag)
                                        {
                                            nxtkey=Integer.parseInt(str.substring(i+1,ei));
                                        }
                                    }
                                }
                                else
                                {
                                    nxtkey=(int)str.charAt(i+1);
                                }
                            }
                            if((nxtkey==118) || (nxtkey==120))
                            {
                                i++;
                                if(nxtkey==118)
                                {
                                    rep=a2u.get(225);
                                }
                                if(nxtkey==120)
                                {
                                    rep=a2u.get(226);
                                }
                                fw.write(rep,0,rep.length());
                                continue out;
                            }
                        }
                        if(a2u.containsKey(ky))
                        {
                            rep=a2u.get(ky);
                            i=ei;
                            if(hhash.containsKey(rep))
                            {
                                if(str.charAt(i+1)==' ')
                                {
                                    rep=hhash.get(rep);
                                    fw.write(rep,0,rep.length());
                                    continue out;
                                }
                                if(nu2a.containsKey(""+str.charAt(i+1)))
                                {
                                    if((nu2a.get(""+str.charAt(i+1)))==107)
                                    {
                                        i=i+1;
                                        rep=hhash.get(rep);
                                        fw.write(rep,0,rep.length());
                                        continue out;
                                    }
                                }
                                if(((int)str.charAt(i+1))==107)
                                {
                                    i=i+1;
                                    rep=hhash.get(rep);
                                    fw.write(rep,0,rep.length());
                                    continue out;
                                }
                            }
                            fw.write(rep,0,rep.length());
                            continue;
                        }
                    }
                }
            }
            
            //Method 3
			if((i+4)<(str.length()))	
			{
					int ext=(int)str.charAt(i);
					int next= (int)str.charAt(i+1);
					int snext=(int)str.charAt(i+2);
					int ssnext = (int)str.charAt(i+3);
					
					if((ext==108) && (next==98))
					{
					i=i+3;
				    rep=" ";
					rep=a2u.get(235)+hhash.get(a2u.get(snext))+a2u.get(108);
					fw.write(rep,0,rep.length());
					continue out;
				}
					
					
					if((ext==108) && (next==102))
					{
				   i++;
				    rep=" ";
					rep=a2u.get(231);
					fw.write(rep,0,rep.length());
								continue out;
				}
					if((next==114) || (snext==114) || (ssnext==114) && (ext!=107))
					{
						if(next==114)
						{ 
							i++;
							rep=" ";
							rep=a2u.get(227)+a2u.get(ext);
							fw.write(rep,0,rep.length());
								continue out;

						}
						if(snext==114)
						{ 
							i=i+2;
							rep=" ";
							rep=a2u.get(227)+a2u.get(ext);
							fw.write(rep,0,rep.length());
								continue out;
						}
						
						if(ssnext==114)
						{
							i=i+3;
							rep=" ";
							rep=a2u.get(227)+a2u.get(ext)+a2u.get(next);
							fw.write(rep,0,rep.length());
								continue out;
						
						}
					} 
					
					
					if(snext==124 && (ext!=107))
					{
							i=i+2;
							rep=" ";
							rep=a2u.get(ext)+a2u.get(217);
							fw.write(rep,0,rep.length());
								continue out;
					
					
					}
					
					if(((ext==129) || (ext==130))  && ((snext==118) || (snext==120)  ))
					
					{
							i=i+3;
							rep=" ";
							
							if(snext==118)
							{
							rep=a2u.get(229);
							fw.write(rep,0,rep.length());
								continue out;
							}
							
							if(snext==120)
							{
							rep=a2u.get(230);
							fw.write(rep,0,rep.length());
								continue out;
							}
							
					}
					
					if(((ext==108) || (ext==115) || (ext==122) || (ext==142)) && (next!=102) && (next!=98))
					{
						i++;
						
						
						if(nu2a.containsKey(""+str.charAt(i)))
						{
							rep=" ";
							if(a2u.containsKey(nu2a.get(""+str.charAt(i))))
							{
								rep=a2u.get(nu2a.get(""+str.charAt(i)));
								if(hhash.containsKey(rep))
								{
									if(str.charAt(i+1)==' ')
									{
										rep=hhash.get(rep)+a2u.get(ext);
										fw.write(rep,0,rep.length());
										continue out;
									}
									if(nu2a.containsKey(""+str.charAt(i+1)))
									{
										asc=nu2a.get(""+str.charAt(i+1));
										if(asc==107)
										{
											i=i+1;
											rep=hhash.get(rep)+a2u.get(ext);
											fw.write(rep,0,rep.length());
											continue out;
										}
									}
									if(((int)str.charAt(i+1))==107)
									{
										i=i+1;
										rep=hhash.get(rep)+a2u.get(ext);
										fw.write(rep,0,rep.length());
										continue out;
									}
								}
								rep=rep+a2u.get(ext);
								fw.write(rep,0,rep.length());
								continue out;
							}
						}
						if(str.charAt(i)=='<')
						{
							if(Character.isDigit(str.charAt(i+1)))
							{
								int ei=str.indexOf('>',i);
								boolean flag=true;
								String tocheckdigit=str.substring(i+1,ei);
								for(int h=0;h<tocheckdigit.length();h++)
								{
									if(!(Character.isDigit(tocheckdigit.charAt(h))))
									{
										flag=false;
									}
								}
								if(flag)
								{
									int key=Integer.parseInt(str.substring(i+1,ei));
									if(a2u.containsKey(key))
									{
										rep=a2u.get(key);
										if(hhash.containsKey(rep))
										{
											if(str.charAt(i+1)==' ')
											{
												rep=hhash.get(rep)+a2u.get(ext);
												fw.write(rep,0,rep.length());
												continue out;
											}
											if(nu2a.containsKey(""+str.charAt(i+1)))
											{
												asc=nu2a.get(""+str.charAt(i+1));
												if(asc==107)
												{
													i=i+1;
													rep=hhash.get(rep)+a2u.get(ext);
													fw.write(rep,0,rep.length());
													continue out;
												}
											}
											if(((int)str.charAt(i+1))==107)
											{
												i=i+1;
												rep=hhash.get(rep)+a2u.get(ext);
												fw.write(rep,0,rep.length());
												continue out;
											}
										}
										rep=rep+a2u.get(ext);
										fw.write(rep,0,rep.length());
										continue out;
									}
								}
							}
						}
						int ext1=(int)str.charAt(i);
						if(a2u.containsKey(ext1))
						{
							rep=a2u.get(ext1);
							if(hhash.containsKey(rep))
							{
								if(str.charAt(i+1)==' ')
								{
									rep=hhash.get(rep)+a2u.get(ext);
									fw.write(rep,0,rep.length());
									continue out;
								}
								if(nu2a.containsKey(""+str.charAt(i+1)))
								{
									asc=nu2a.get(""+str.charAt(i+1));
									if(asc==107)
									{
										i=i+1;
										rep=hhash.get(rep)+a2u.get(ext);
										fw.write(rep,0,rep.length());
										continue out;
									}
								}
								if(((int)str.charAt(i+1))==107)
								{
									i=i+1;
									rep=hhash.get(rep)+a2u.get(ext);
									fw.write(rep,0,rep.length());
									continue out;
								}
							}
							rep=rep+a2u.get(ext);
							fw.write(rep,0,rep.length());
							continue out;
						}
					}
					if(ext==107)
					{
						int nxtkey=0;
						if(nu2a.containsKey(""+str.charAt(i+1)))
						{
							nxtkey=nu2a.get(""+str.charAt(i+1));
						}
						else
						{
							if(str.charAt(i+1)=='<')
							{
								if(Character.isDigit(str.charAt(i+1)))
								{
									int ei=str.indexOf('>',i);
									boolean flag=true;
									String tocheckdigit=str.substring(i+1,ei);
									for(int h=0;h<tocheckdigit.length();h++)
									{
										if(!(Character.isDigit(tocheckdigit.charAt(h))))
										{
											flag=false;
										}
									}
									if(flag)
									{
										nxtkey=Integer.parseInt(str.substring(i+1,ei));
									}
								}
							}
							else
							{
								nxtkey=(int)str.charAt(i+1);
							}
						}
						if((nxtkey==118) || (nxtkey==120))
						{
							i++;
							if(nxtkey==118)
							{
								rep=a2u.get(223);
							}
							if(nxtkey==120)
							{
								rep=a2u.get(224);
							}
							fw.write(rep,0,rep.length());
							continue out;
						}
						
						if((nxtkey==119) )
						{
							i++;
							if(nxtkey==119)
							{
								rep=a2u.get(224);
							}
							fw.write(rep,0,rep.length());
							continue out;
						}
						
						
						
						
					}
					if((ext==129) || (ext==130))
					{
						int nxtkey=0;
						if(nu2a.containsKey(""+str.charAt(i+1)))
						{
							nxtkey=nu2a.get(""+str.charAt(i+1));
						}
						else
						{
							if(str.charAt(i+1)=='<')
							{
								if(Character.isDigit(str.charAt(i+1)))
								{
									int ei=str.indexOf('>',i);
									boolean flag=true;
									String tocheckdigit=str.substring(i+1,ei);
									for(int h=0;h<tocheckdigit.length();h++)
									{
										if(!(Character.isDigit(tocheckdigit.charAt(h))))
										{
											flag=false;
										}
									}
									if(flag)
									{
										nxtkey=Integer.parseInt(str.substring(i+1,ei));
									}
								}
							}
							else
							{
								nxtkey=(int)str.charAt(i+1);
							}
						}
						if((nxtkey==118) || (nxtkey==120))
						{
							i++;
							if(nxtkey==118)
							{
								rep=a2u.get(225);
							}
							if(nxtkey==120)
							{
								rep=a2u.get(226);
							}
							fw.write(rep,0,rep.length());
							continue out;
						}
					}
					if(a2u.containsKey(ext))
					{
					rep=a2u.get(ext);
					if(hhash.containsKey(rep))
					{
                    if(str.charAt(i+1)==' ')
                    {
                        rep=hhash.get(rep);
                        fw.write(rep,0,rep.length());
                        continue out;
                    }
                    if(nu2a.containsKey(""+str.charAt(i+1)))
                    {
                        if((nu2a.get(""+str.charAt(i+1)))==107)
                        {
                            i=i+1;
                            rep=hhash.get(rep);
                            fw.write(rep,0,rep.length());
                            continue out;
                        }
                    }
                    if(((int)str.charAt(i+1))==107)
                    {
                        i=i+1;
                        rep=hhash.get(rep);
                        fw.write(rep,0,rep.length());
                        continue out;
                    }
                }
                fw.write(rep,0,rep.length());
                continue out;
            }
            
		}	
            //When no mapping found
            String wr="here     "+str.charAt(i)+"   here";
            String nl="\r\n";
            few2.write(nl,0,nl.length());
            few2.write(wr,0,wr.length());
            few2.write(nl,0,nl.length());
            //fw.write(nl,0,nl.length());
            //fw.write(wr,0,wr.length());
            //fw.write(nl,0,nl.length());
            continue out;
        }
        fw.close();
        f.close();
        fr.close();
        few2.close();
        f2.close();
    }
	
	    
    public void actionPerformed(ActionEvent ae)
    {
        JButton src=(JButton)ae.getSource();
        
        if(src == brw)
        {
           int choice = chooser.showOpenDialog(this);

            if (choice != JFileChooser.APPROVE_OPTION) return;

            chosenFile = chooser.getSelectedFile(); 

            iflbl.setText(chosenFile.getAbsolutePath());
        }
		
        else if(src == cb)
        {
			  try{
			run2(chosenFile.getAbsolutePath());
			oflbl.setText("output file created at "+"output_"+chosenFile.getAbsolutePath() );
		}
		catch(IOException ex){
			System.out.println (ex.toString());
        
		}
			
            
			
        }
    }
    public static void main(String[] args) 
    {
        new GuiApp2();
    }
}



