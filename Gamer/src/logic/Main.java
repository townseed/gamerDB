package logic;

import graphics.Frame;

public class Main {

    public Main() {}

    public void run() {
    	Frame gamerFrame = new Frame();
        gamerFrame.run();
    }

    /**
     * here we go!
     * @param args
     */
    public static void main(String[] args) {
        Main main = new Main();
        main.run(); 
    }
}
