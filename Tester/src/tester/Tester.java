/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

/**
 *
 * @author Nick Eyring
 */
public class Tester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        
       //String s = Parser.FileDecoder.getProperties("Espagnol");
        
       // System.out.println(s);
        
       //String s1 = Parser.FileEncoder.encodeProperties("Espagnol");
       
       //System.out.println(s1);
        
        //String s = Parser.FileEncoder.encodeProperties("English");
        
        //System.out.println(s);
        
        String s = Bot.PubBot.getWikiPage("Bundle/en");
        
        System.out.println(s);
        
    }
}
