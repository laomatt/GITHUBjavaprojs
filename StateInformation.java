/*
Author: Matt Lao
Class: 211s
Date: 12/15/14
Title: State Info
Description: takes input from two text fields, and searches as regular expressions matches for states and capitols.
*/

import java.awt.*;
import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.*;

public class StateInformation extends JFrame{

/********************the main method for the setup operations******************************/
    public static void main(String[] args) throws FileNotFoundException
    {
        HashMap data = new HashMap<String, String>();
        ArrayList<String> state = new ArrayList<>();
        ArrayList<String> capital = new ArrayList<>();
        Scanner sc = new Scanner(new File("states"));

        String line="";

        while (sc.hasNextLine())
        {
            line=sc.nextLine();

            String[] tokens = line.split("        +");
            data.put(tokens[0], tokens[1]);
            state.add(tokens[0]);
            capital.add(tokens[1]);

        }

        Thread t1 = new Thread(new showgui(state, capital, data));

        t1.start();


    }




}
//******************/******************/******************/
/******************the GUI thread********************/

class showgui extends JFrame implements Runnable
{
    ArrayList<String> st;
    ArrayList<String> ca;
    HashMap data = new HashMap<String, String>();
    public showgui(ArrayList sta, ArrayList caa, HashMap d)
    {
        data=d;
        st=sta;
        ca=caa;

    }
JFrame jf;
@Override
    public void run()
    {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        jf = new JFrame("State Capitols");
        jf.setSize(500, 700);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(new GridLayout());
        jf.setLocationRelativeTo(null);
        jf.setResizable(true);

        JTextArea statecap = new JTextArea();
        statecap.setPreferredSize(new Dimension(500,500));

        JTextField jtfstate = new JTextField();
        jtfstate.setPreferredSize(new Dimension(500,20));

        JTextField jtfcapitol = new JTextField();
        jtfcapitol.setPreferredSize(new Dimension(500,20));

        JPanel textfields = new JPanel();
        textfields.setPreferredSize(new Dimension(500,800));


        submitelisten sl = new submitelisten(jtfstate,jtfcapitol,statecap,st,ca, data);

        JButton sub = new JButton("submit");
        sub.setPreferredSize(new Dimension(500,50));
        sub.addActionListener(sl);

        JLabel stateser=new JLabel("search states");
        JLabel capser=new JLabel("search capitols");
        JLabel op=new JLabel("output");

        textfields.setLayout(new FlowLayout());
        textfields.add(statecap);
        textfields.add(stateser);
        textfields.add(jtfstate);
        textfields.add(capser);
        textfields.add(jtfcapitol);
        textfields.add(sub);

        //con.add(textfields);
         jf.add(textfields);
         jf.setVisible(true);



    }
}

/********************************* My listener class which contains my search algorithm*********************************************************/
class submitelisten implements ActionListener
{
        JTextField jtfstate;// = new JTextField();
        JTextField jtfcapitol;// = new JTextField();
        JTextArea output;// = new JPanel();
        ArrayList<String> state = new ArrayList<>();
        ArrayList<String> capital = new ArrayList<>();
        HashMap data = new HashMap<String, String>();

/***************************************** the main method for the search algorithm  ***********************************************************/
    public submitelisten(JTextField s, JTextField c, JTextArea out, ArrayList st, ArrayList ca, HashMap da)
    {

        data=da;
        state = st;
        capital = ca;
        jtfstate = s;
        jtfcapitol = c;
        output = out;

    }

     String statefield;
     String capitolfield;

    @Override
    public void actionPerformed(ActionEvent e)
    {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

          statefield = jtfstate.getText().toString();
          capitolfield = jtfcapitol.getText().toString();


          //now we parse out 'state' and 'capoitol' arraylists
          //to match out statefield and capitolfield text

          ArrayList<String> stateout=new ArrayList<>();
          ArrayList<String> capout=new ArrayList<>();
    //******//******//******//******//******/    match state    /******//******//******//******//******//******/
            if(statefield.contains("^")||statefield.contains("#")||statefield.contains("."))
            {
                //see what kind of regex it is
                if(statefield.startsWith("^")&&statefield.endsWith("#")&&statefield.contains("."))
                    {

                        //regex for specified start and end with  variable length middle specified
                        if(statefield.contains(".*"))
                        {
                                                    //get string for the start of the search criteria
                            String inp="";
                                for(int i=1;i<statefield.indexOf(".");i++)
                                {
                                    inp+=statefield.charAt(i);

                                }
                            //get string for the end of the search criteria
                            String onp="";
                                for(int i=statefield.indexOf("*")+1;i<statefield.length()-1;i++)
                                {
                                    onp+=statefield.charAt(i);

                                }


                                //now transverse the array list to filter out what starts with inp and ends with onp
                            for(int i=0;i<state.size();i++)
                            {
                                    if (state.get(i).toLowerCase().startsWith(inp.toLowerCase())&&state.get(i).toLowerCase().endsWith(onp.toLowerCase()))
                                    {
                                        stateout.add(state.get(i));

                                    }

                            }
                        }
                        //regex for specified start and end with  specific length middle specified
                        else if(!statefield.contains("*"))
                        {
                                int counter=0;
                        //count how many '.' there are

                            for(int i=0; i<statefield.length();i++)
                                {
                                        if(statefield.charAt(i)=='.')
                                        {
                                            counter++;
                                        }
                                }


                        //get string for the start of the search criteria
                            String inp="";
                                for(int i=1;i<statefield.indexOf(".");i++)
                                {
                                    inp+=statefield.charAt(i);

                                }

                        //get string for the end of the search criteria
                            String onp="";
                                for(int i=statefield.lastIndexOf(".")+1;i<statefield.length()-1;i++)
                                {
                                    onp+=statefield.charAt(i);

                                }

                                for(int i=0;i<state.size();i++)
                                {
                                        if ((state.get(i).toLowerCase().startsWith(inp.toLowerCase()))&&(state.get(i).toLowerCase().endsWith(onp.toLowerCase()))&&state.get(i).length()==counter+onp.length()+inp.length())
                                        {
                                            stateout.add(state.get(i));

                                        }

                                }

                        }

                    }

                    //regex for specified start OR end with specified word length

                else if(statefield.startsWith("^")&&statefield.endsWith("."))
                        {
                               String inp="";
                                for(int i=1;i<statefield.indexOf(".");i++)
                                {
                                    inp+=statefield.charAt(i)+"";

                                }

                            for(int i=0;i<state.size();i++)
                            {
                                if((state.get(i).toLowerCase().startsWith(inp.toLowerCase()))&&(state.get(i).length()==statefield.length()-1))
                                {
                                        stateout.add(state.get(i));
                                }
                            }
                        }

                else if(statefield.endsWith("#")&&statefield.startsWith("."))
                        {
                            String onp="";
                                for(int i=statefield.lastIndexOf(".")+1;i<statefield.length()-1;i++)
                                {
                                    onp+=statefield.charAt(i);

                                }
                            for(int i=0;i<state.size();i++)
                            {

                                if((state.get(i).toLowerCase().endsWith(onp.toLowerCase()))&&(state.get(i).length()==statefield.length()-1))
                                {
                                        stateout.add(state.get(i));
                                }
                            }
                        }

                        //regex for specified word length
                else if(statefield.startsWith(".")&&statefield.endsWith("."))
                {
                        for(int i=0;i<state.size();i++)
                        {
                            if (state.get(i).length()==statefield.length())
                            {
                                stateout.add(state.get(i));

                            }


                        }


                }

                else if(statefield.startsWith("^"))
                    {
                        //now find entries that begin with the string
                        String inp="";
                        for(int i=1;i<statefield.length();i++)
                        {
                            inp+=statefield.charAt(i);

                        }

                        for(int i=0;i<state.size();i++)
                        {
                            if (state.get(i).toLowerCase().startsWith(inp.toLowerCase()))
                            {
                                stateout.add(state.get(i));

                            }


                        }

                    }

                else if(statefield.endsWith("#"))
                    {
                        String onp="";
                        for(int i=0;i<statefield.length()-1;i++)
                        {
                            onp+=statefield.charAt(i);

                        }

                        for(int i=0;i<state.size();i++)
                        {
                             if (state.get(i).toLowerCase().endsWith(onp.toLowerCase()))
                            {
                                stateout.add(state.get(i));

                            }

                        }


                    }

                else
                {

                       for(int i=0;i<state.size();i++)
                    {

                            stateout.add("nothing to show");
                    }
                   // stateout="works2";

                }
            }
            else
            {
                 //match state
                for(int i=0;i<state.size();i++)
                {
                    if(state.get(i).toLowerCase().contains(statefield.toLowerCase()))
                    {
                            stateout.add(state.get(i));
                            break;
                    }
                    else
                    {
                            //stateout="none found";

                    }
                }
            }




          //******//******//******//******//******/    match capitol    /******//******//******//******//******//******/
         if(capitolfield.contains("^")||capitolfield.contains("#")||capitolfield.contains("."))
            {
                //see what kind of regex it is
                if(capitolfield.startsWith("^")&&capitolfield.endsWith("#")&&capitolfield.contains("."))
                    {

                        //regex for specified start and end with  variable length middle specified
                        if(capitolfield.contains(".*"))
                        {
                                                    //get string for the start of the search criteria
                            String inp="";
                                for(int i=1;i<capitolfield.indexOf(".");i++)
                                {
                                    inp+=capitolfield.charAt(i);

                                }
                            //get string for the end of the search criteria
                            String onp="";
                                for(int i=capitolfield.indexOf("*")+1;i<capitolfield.length()-1;i++)
                                {
                                    onp+=capitolfield.charAt(i);

                                }


                                //now transverse the array list to filter out what starts with inp and ends with onp
                            for(int i=0;i<capital.size();i++)
                            {
                                    if (capital.get(i).toLowerCase().startsWith(inp.toLowerCase())&&capital.get(i).toLowerCase().endsWith(onp.toLowerCase()))
                                    {
                                        capout.add(capital.get(i));

                                    }

                            }
                        }
                        //regex for specified start and end with  specific length middle specified
                        else if(!capitolfield.contains("*"))
                        {
                                int counter=0;
                        //count how many '.' there are

                            for(int i=0; i<capitolfield.length();i++)
                                {
                                        if(capitolfield.charAt(i)=='.')
                                        {
                                            counter++;
                                        }
                                }


                        //get string for the start of the search criteria
                            String inp="";
                                for(int i=1;i<capitolfield.indexOf(".");i++)
                                {
                                    inp+=capitolfield.charAt(i);

                                }

                        //get string for the end of the search criteria
                            String onp="";
                                for(int i=capitolfield.lastIndexOf(".")+1;i<capitolfield.length()-1;i++)
                                {
                                    onp+=capitolfield.charAt(i);

                                }

                                for(int i=0;i<capital.size();i++)
                                {
                                        if ((capital.get(i).toLowerCase().startsWith(inp.toLowerCase()))&&(capital.get(i).toLowerCase().endsWith(onp.toLowerCase()))&&capital.get(i).length()==counter+onp.length()+inp.length())
                                        {
                                            capout.add(capital.get(i));

                                        }

                                }

                        }

                    }

                    //regex for specified start OR end with specified word length

                else if(capitolfield.startsWith("^")&&capitolfield.endsWith("."))
                        {
                               String inp="";
                                for(int i=1;i<capitolfield.indexOf(".");i++)
                                {
                                    inp+=capitolfield.charAt(i)+"";

                                }

                            for(int i=0;i<capital.size();i++)
                            {
                                if((capital.get(i).toLowerCase().startsWith(inp.toLowerCase()))&&(capital.get(i).length()==capitolfield.length()-1))
                                {
                                        capout.add(capital.get(i));
                                }
                            }
                        }

                else if(capitolfield.endsWith("#")&&capitolfield.startsWith("."))
                        {
                            String onp="";
                                for(int i=capitolfield.lastIndexOf(".")+1;i<capitolfield.length()-1;i++)
                                {
                                    onp+=capitolfield.charAt(i);

                                }
                            for(int i=0;i<capital.size();i++)
                            {

                                if((capital.get(i).toLowerCase().endsWith(onp.toLowerCase()))&&(capital.get(i).length()==capitolfield.length()-1))
                                {
                                        capout.add(capital.get(i));
                                }
                            }
                        }

                        //regex for specified word length
                else if(capitolfield.startsWith(".")&&capitolfield.endsWith("."))
                {
                        for(int i=0;i<capital.size();i++)
                        {
                            if (capital.get(i).length()==capitolfield.length())
                            {
                                capout.add(capital.get(i));

                            }


                        }


                }

                else if(capitolfield.startsWith("^"))
                    {
                        //now find entries that begin with the string
                        String inp="";
                        for(int i=1;i<capitolfield.length();i++)
                        {
                            inp+=capitolfield.charAt(i);

                        }

                        for(int i=0;i<capital.size();i++)
                        {
                            if (capital.get(i).toLowerCase().startsWith(inp.toLowerCase()))
                            {
                                capout.add(capital.get(i));

                            }


                        }

                    }

                else if(capitolfield.endsWith("#"))
                    {
                        String onp="";
                        for(int i=0;i<capitolfield.length()-1;i++)
                        {
                            onp+=capitolfield.charAt(i);

                        }

                        for(int i=0;i<capital.size();i++)
                        {
                             if (capital.get(i).toLowerCase().endsWith(onp.toLowerCase()))
                            {
                                capout.add(capital.get(i));

                            }

                        }


                    }

                else
                {

                       for(int i=0;i<capital.size();i++)
                    {

                            capout.add("nothing to show");
                    }
                   // capout="works2";

                }
            }
            else
            {
                 //match capital
                for(int i=0;i<capital.size();i++)
                {
                    if(capital.get(i).toLowerCase().contains(capitolfield.toLowerCase()))
                    {
                            capout.add(capital.get(i));
                            break;
                    }
                    else
                    {
                            //capout="none found";

                    }
                }
            }



/****************************** code for the output textarea box ******************************************/
String outtext="";
outtext+="-------------State search matches:------------- \n";
        for (int i=0; i<stateout.size();i++)
        {


            outtext+="State: "+stateout.get(i)+" --- Capital: "+data.get(stateout.get(i))+"\n";

        }

outtext+="\n";
outtext+="\n";

outtext+="-------------Capital search matches:------------- \n";
        for (int i=0; i<stateout.size();i++)
        {
            outtext+="Capital: "+capout.get(i)+" --- State: "+state.get(capital.indexOf(capout.get(i)))+"\n";

        }

output.setText(outtext);
    }


}
