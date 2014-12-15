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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mattlao
 */
public class StateInformation extends JFrame{


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



        System.out.println(SwingUtilities.isEventDispatchThread());


        Thread t1 = new Thread(new showgui(state, capital));

        t1.start();


    }




}
//******************/******************/******************/
/******************the GUI thread********************/

class showgui extends JFrame implements Runnable
{
    ArrayList<String> st;
    ArrayList<String> ca;
    public showgui(ArrayList sta, ArrayList caa)
    {

        st=sta;
        ca=caa;

    }
JFrame jf;
@Override
    public void run()
    {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.


        jf = new JFrame("State Capitols");
        jf.setSize(500, 200);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(new GridLayout());
        jf.setLocationRelativeTo(null);
        jf.setResizable(true);
           // jf.add(con);


        //Container con = jf.getContentPane();
       // con.setLayout(new GridLayout(2,0));
       // con.setPreferredSize(new Dimension(200,400));


        JLabel statecap = new JLabel();
        statecap.setPreferredSize(new Dimension(30,20));
       // statecap.setAlignmentX(CENTER);
        //statecap.setBackground(Color.red);



        JTextField jtfstate = new JTextField();
        jtfstate.setPreferredSize(new Dimension(50,20));

        JTextField jtfcapitol = new JTextField();
        jtfcapitol.setPreferredSize(new Dimension(50,20));

        JPanel textfields = new JPanel();
        textfields.setPreferredSize(new Dimension(200,50));


        submitelisten sl = new submitelisten(jtfstate,jtfcapitol,statecap,st,ca);

        JButton sub = new JButton("submit");
        sub.addActionListener(sl);

        textfields.setLayout(new GridLayout(4,0));
        textfields.add(statecap);
        textfields.add(jtfstate);
        textfields.add(jtfcapitol);
        textfields.add(sub);

        //con.add(textfields);
         jf.add(textfields);
         jf.setVisible(true);



    }
}


class submitelisten implements ActionListener
{
        JTextField jtfstate;// = new JTextField();
        JTextField jtfcapitol;// = new JTextField();
        JLabel output;// = new JPanel();
        ArrayList<String> state = new ArrayList<>();
        ArrayList<String> capital = new ArrayList<>();


    public submitelisten(JTextField s, JTextField c, JLabel out, ArrayList st, ArrayList ca)
    {

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
  //   for(int i=0;i<state.size();i++)
    //      {
            if(statefield.contains("^")||statefield.contains("#")||statefield.contains("."))
            {
                //see what kind of regex it is
                if(statefield.contains("^")&&statefield.contains("#")&&statefield.contains("."))
                    {
                        for(int i=0;i<state.size();i++)
                        {
                        //   find

                        }
                    }
                else if((statefield.contains("^")||statefield.contains("#"))&&statefield.contains("."))
                    {
                        for(int i=0;i<state.size();i++)
                        {

                        }
                    }

                else if(statefield.contains("^"))
                    {
                        //now find entries that begin with the string
                        String inp="";
                        for(int i=1;i<statefield.length();i++)
                        {
                            inp+=statefield.charAt(i);

                        }
/*
                        if(inp.contains("^"))
                        {
                            stateout.add("wrong regex");
                            break;


                        }*/

                        for(int i=0;i<state.size();i++)
                        {
                            if (state.get(i).startsWith(inp,0))
                            {
                                stateout.add(state.get(i));

                            }


                        }

                    }

                else if(statefield.contains("#"))
                    {
                        for(int i=0;i<state.size();i++)
                        {

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

    //      }
          //match capitol
          for(int i=0;i<capital.size();i++)
          {
                    if(capital.get(i).toLowerCase().equalsIgnoreCase(capitolfield.toLowerCase()))
                    {
                            capout.add(capital.get(i));
                            break;
                    }
                            else
                    {
                           // capout="none found";

                    }
          }


          String outputstate="";
          for(int g=0;g<stateout.size();g++)
          {
            outputstate+=stateout.get(g)+" ";

          }
        String outputcap="";
          for(int g=0;g<stateout.size();g++)
          {
            outputcap+=capout.get(g)+" ";

          }


          output.setText("States: "+outputstate+" --- Capitals: "+outputcap);
    }


}