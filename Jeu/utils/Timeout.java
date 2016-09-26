/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu.utils;

/**
 *
 * @author Ivan
 */
public class Timeout extends Thread {
    private int t;
    private Runnable r;
    
    public static void setTimeout(final int t, final Runnable r) {
        Timeout th = new Timeout(t,r);
        th.start();
    }
    
    public Timeout (int t, Runnable r) {
        this.t=t;
        this.r=r;
    }
    
    public void run() {
        try {
            this.sleep(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        r.run();
    }
}
