/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author SENA
 */
public class AboutForm {
    private JTextArea jTAAboutScript;
    private Box box;  
    public  AboutForm()
    {
     box = Box.createHorizontalBox();
     String script = 
 " Copyright (c) 2014, 2016 Augustine Sena,Inc (アウグスティヌスセナ、株式会社) \n"+
 " and/or its affiliates. All rights reserved.\n\n"+
 
 " Redistribution and use in source and binary forms, with or without\n"+
 " modification, are permitted provided that the following conditions\n"+
 " are met:\n"+
 "   - Redistributions of source code must retain the above copyright\n"+
 "     notice, this list of conditions and the following disclaimer.\n\n"+
 
 "   - Redistributions in binary form must reproduce the above copyright\n"+
 "     notice, this list of conditions and the following disclaimer in the\n"+
 "     documentation and/or other materials provided with the distribution.\n\n"+
 
 "   - Neither the name of Augustine Sena (オーガスティンセナ) or the names of its\n"+
 "     contributors may be used to endorse or promote products derived\n"+
 "     from this software without specific prior written permission.\n\n"+
 
 " THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS\n"+
 " IS\" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,\n"+
 " THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR\n"+
 " PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR\n"+
 " CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,\n"+
 " EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,\n"+
 " PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR\n"+
 " PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF\n"+
 " LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING\n"+
 " NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS\n"+
 " SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.\n"+
 " ==================================(311410001)===================================\n\n";
                jTAAboutScript = new JTextArea(script, 15, 25);
                box.add( new JScrollPane( jTAAboutScript ) );
              
                JOptionPane.showMessageDialog(null,box,"About",JOptionPane.PLAIN_MESSAGE);
                

    }
}
